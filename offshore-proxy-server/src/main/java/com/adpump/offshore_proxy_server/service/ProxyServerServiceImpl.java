package com.adpump.offshore_proxy_server.service;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class ProxyServerServiceImpl implements ProxyServerService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final BlockingQueue<String> requestQueue = new LinkedBlockingQueue<>();

    public ProxyServerServiceImpl() {
        new Thread(this::processQueue).start();
    }

    @Override
    public void queueRequest(String url) throws InterruptedException {
        requestQueue.put(url);
    }

    @Override
    public synchronized String processRequest(String url) {
        System.out.println("Processing request: " + url);
        return restTemplate.getForObject(url, String.class);
    }

    private void processQueue() {
        while (true) {
            try {
                // Fetch next request (FIFO)
                String url = requestQueue.take();
                System.out.println("Proxy Server processing: " + url);
                String response = restTemplate.getForObject(url, String.class);
                System.out.println("Processed URL: " + url + " -> Response: " + response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
