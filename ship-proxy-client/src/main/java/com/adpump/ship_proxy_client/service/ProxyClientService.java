package com.adpump.ship_proxy_client.service;

public interface ProxyClientService {
    String sendRequest(String url) throws InterruptedException;
}
