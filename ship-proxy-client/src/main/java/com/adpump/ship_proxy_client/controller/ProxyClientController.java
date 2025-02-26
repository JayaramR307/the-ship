package com.adpump.ship_proxy_client.controller;

import com.adpump.ship_proxy_client.service.ProxyClientService;
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
    public String queueRequest(@RequestParam String url) throws InterruptedException {
        return proxyClientService.sendRequest(url);
    }
}
