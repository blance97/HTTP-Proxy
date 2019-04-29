package com.company;

import CachedPages.CachePagesManager;
import CachedPages.CachedPageModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

public class ProxyThread extends Thread {
    String url;
    Map<String, String> headers;
    private Socket socket;
    private CachePagesManager Cache;
    private BufferedReader in;
    private PrintWriter out;

    public ProxyThread(Socket socketClient) throws IOException {
        this.url = url;
        this.headers = headers;
        socket = socketClient;
        out = new PrintWriter(socket.getOutputStream(), true);
        this.Cache = new CachePagesManager(10);
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                if ("^".equals(inputLine)) {
//                    System.out.println("bye");
                    break;
                }
//                System.out.println(inputLine);
                this.createNewRequest(inputLine);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void createNewRequest(String input) throws Exception {
        urlModel parsedString;
        //String exampleInput = "GET http://www.google.com/ HTTP/1.0 Connection: close";
        try {
            parsedString = this.parseArguments(input);
            this.sendGETRequest(parsedString.url, parsedString.headers);
        } catch (IllegalArgumentException e) {
//            this.out.println("Input invalid");
        }
    }

    private urlModel parseArguments(String input) {
        Map<String, String> headers = new HashMap<>();
        String[] splitString = input.split(" ");
        if (splitString.length < 3) {
            this.out.println("Bad Request (400)");
            throw new IllegalArgumentException();
        }
        String method = splitString[0];
        if (!method.equals("GET")) {
            this.out.println("Not Implemented (501)");
            throw new IllegalArgumentException();
        }
        String url = splitString[1];
        String httpVersion = splitString[2];
        for (int i = 3; i < splitString.length; i += 2) {
            headers.put(splitString[i].substring(0, splitString[i].length() - 1), splitString[i + 1]);
        }
        return new urlModel(method, url, httpVersion, headers);
    }

    private void sendGETRequest(String url, Map<String, String> headers) throws Exception {
        StringBuilder result = new StringBuilder();
        HttpURLConnection conn;
        String header;
        String html;
        CachedPageModel cachedPage = this.Cache.isCached(url);
        if (cachedPage != null) {
            System.out.println("Already Cached:" + url);
            this.out.println(cachedPage.headers);
            this.out.println(cachedPage.html);
        } else {
            try {
                URL obj = new URL(url);
                conn = (HttpURLConnection) obj.openConnection();
                conn.setRequestMethod("GET");
                this.setHeaders(conn, headers);
                conn.connect();
                header = this.getHeaders(conn);
                this.out.println(header);
                BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                String line;
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }
                rd.close();
                html = result.toString();
                this.out.println(html);
                this.Cache.addCachedPage(new CachedPageModel(url, header, html));
            } catch (MalformedURLException e) {
//                this.out.println("URL could not be reached");
            }
        }
    }

    private void setHeaders(URLConnection conn, Map<String, String> headers) {
        for (String h : headers.keySet()) {
            conn.setRequestProperty(h, headers.get(h));
        }

    }

    private String getHeaders(URLConnection conn) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; ; i++) {
            String headerName = conn.getHeaderFieldKey(i);
            String headerVal = conn.getHeaderField(i);
            if (headerName == null && headerVal == null) {
                return result.toString();
            }
            result.append(headerName + ": " + headerVal + "\n");

        }
    }
}
