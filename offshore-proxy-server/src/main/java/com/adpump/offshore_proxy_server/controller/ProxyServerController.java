package com.adpump.offshore_proxy_server.controller;

import com.adpump.offshore_proxy_server.service.ProxyServerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/proxy")
public class ProxyServerController {
    private final ProxyServerService proxyServerService;

    public ProxyServerController(ProxyServerService proxyServerService) {
        this.proxyServerService = proxyServerService;
    }

    /*@GetMapping
    public String queueRequest(@RequestParam String url) throws InterruptedException {
        proxyServerService.queueRequest(url);
        return "Your request has been queued at the proxy server.";
    }*/

    @GetMapping
    public ResponseEntity<String> processRequest(@RequestParam String url) {
        String response = proxyServerService.processRequest(url);
        return ResponseEntity.ok(response);
    }
}
