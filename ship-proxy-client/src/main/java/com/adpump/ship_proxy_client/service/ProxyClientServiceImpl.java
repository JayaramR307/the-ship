package com.adpump.ship_proxy_client.service;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class ProxyClientServiceImpl implements ProxyClientService{
    private final RestTemplate restTemplate = new RestTemplate();
    private final String proxyServerUrl = "http://localhost:9090/proxy";
    private final BlockingQueue<String> requestQueue = new LinkedBlockingQueue<>();

    public ProxyClientServiceImpl() {
        new Thread(this::processQueue).start();
    }

    @Override
    public String sendRequest(String url) throws InterruptedException {
        requestQueue.put(url);
        return processQueue();
    }

    private String processQueue() {
        while (true) {
            try {
                // Get next request (FIFO)
                String url = requestQueue.take();
                System.out.println("Processing request: " + url);
                return restTemplate.getForObject(url, String.class);
            } catch (Exception e) {
                e.printStackTrace();
                return "Error processing request.";
            }
        }
    }
}
