package com.adpump.offshore_proxy_server.service;

public interface ProxyServerService {
    void queueRequest(String url) throws InterruptedException;
    String processRequest(String url);
}
