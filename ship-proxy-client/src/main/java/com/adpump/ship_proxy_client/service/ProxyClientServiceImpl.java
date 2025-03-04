package com.adpump.ship_proxy_client.service;

import org.springframework.stereotype.Service;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

@Service
public class ProxyClientServiceImpl implements ProxyClientService{
    private final String OFFSHORE_HOST = "localhost"; // Replace with actual offshore server IP
    private final int OFFSHORE_PORT = 9090;
    private Socket socket;
    private BufferedWriter writer;
    private BufferedReader reader;

    public ProxyClientServiceImpl() {
        try {
            connectToOffshoreProxy();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void connectToOffshoreProxy() throws IOException {
        if (socket == null || socket.isClosed()) {
            socket = new Socket();
            socket.connect(new InetSocketAddress(OFFSHORE_HOST, OFFSHORE_PORT), 5000);
            socket.setSoTimeout(10000);
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("Connected to Offshore Proxy!");
        }
    }

    public String sendRequest(String url) throws Exception {
        if (socket == null || socket.isClosed()) {
            connectToOffshoreProxy();
        }

        writer.write(url);
        writer.newLine();
        writer.flush();
        
        StringBuilder response = new StringBuilder();
        char[] buffer = new char[8192];
        int bytesRead;

        try {
            while ((bytesRead = reader.read(buffer, 0, buffer.length)) != -1) {
                response.append(buffer, 0, bytesRead);
                if (bytesRead < buffer.length) {
                    break;
                }
            }
        } catch (SocketTimeoutException e) {
            System.err.println("Error: Read timed out");
        }

        System.out.println("Full Response Length: " + response.length());
        return response.toString();
    }
}
