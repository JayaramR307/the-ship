package com.adpump.ship_proxy_client.controller;

import com.adpump.ship_proxy_client.service.ProxyClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/proxy")
public class ProxyClientController {
    private final ProxyClientService proxyClientService;

    public ProxyClientController(ProxyClientService proxyClientService) {
        this.proxyClientService = proxyClientService;
    }

    @GetMapping
    public ResponseEntity<String> fetchUrl(@RequestParam String url) {
        try {
            String response = proxyClientService.sendRequest(url);
            return ResponseEntity.ok()
                    .contentType(MediaType.TEXT_HTML)
                    .body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/status")
    public String getStatus() {
        return "Ship Proxy is running!";
    }
}
