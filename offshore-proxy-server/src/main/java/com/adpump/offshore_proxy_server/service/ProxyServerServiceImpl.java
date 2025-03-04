package com.adpump.offshore_proxy_server.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

@Service
public class ProxyServerServiceImpl implements ProxyServerService {
    private final int PORT = 9090;
    private final RestTemplate restTemplate = new RestTemplate();

    public void startServer() {
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(PORT)) {
                System.out.println("Offshore Proxy Server running on port " + PORT);

                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    new Thread(() -> handleClient(clientSocket)).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void handleClient(Socket clientSocket) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))) {

            String url;
            while ((url = reader.readLine()) != null) {
                System.out.println("Received request for: " + url);
                try {
                    String response = restTemplate.getForObject(url, String.class);
                    System.out.println("Response: " + response);
                    System.out.println("Response length: " + response.length());
                    writer.write(response);
                } catch (Exception e) {
                    writer.write("Error fetching: " + url + " -> " + e.getMessage());
                }
                writer.newLine();
                writer.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
