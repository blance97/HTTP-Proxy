package com.company;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            ProxyServer ps = new ProxyServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
