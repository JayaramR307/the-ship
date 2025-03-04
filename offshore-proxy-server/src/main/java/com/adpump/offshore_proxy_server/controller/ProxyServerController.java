package com.adpump.offshore_proxy_server.controller;

import com.adpump.offshore_proxy_server.service.ProxyServerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/proxy")
public class ProxyServerController {
    private final ProxyServerService proxyServerService;

    public ProxyServerController(ProxyServerService offshoreProxyService) {
        this.proxyServerService = offshoreProxyService;
        proxyServerService.startServer(); // Start the TCP server
    }

    @GetMapping("/status")
    public String getStatus() {
        return "Offshore Proxy is running!";
    }
}
