package com.company;

import java.util.Map;

public class urlModel {
    public String method;
    public String url;
    public String httpVersion;
    public Map<String, String> headers;

    public urlModel(String method,String url ,String httpVersion,Map<String, String> headers){
        this.method = method;
        this.url = url;
        this.httpVersion = httpVersion;
        this.headers = headers;
    }
}
