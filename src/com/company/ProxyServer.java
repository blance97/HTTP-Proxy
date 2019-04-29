package com.company;

import java.io.IOException;
import java.net.ServerSocket;





public class ProxyServer {
    final int numberOfOpenConnections = 100;
    int currentNumberOfOpenConnections = 0;
    private ServerSocket serverSocket;

    ProxyServer() throws IOException {
        serverSocket = new ServerSocket(5000);
        while(true){
//            System.out.println("number of active threads: " + (Thread.activeCount()-2));
            if(Thread.activeCount() >= 100){
//                System.out.println("Stop pls");
            }else{
                new ProxyThread(serverSocket.accept()).start();
            }

        }
    }
}
