package com.pino.proxydemo.feign_client;

import com.pino.proxydemo.proxy.FeignProxyConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
    name = "HttpMyIpClient",
    url = "http://httpbin.org/ip",
    configuration = FeignProxyConfiguration.class
)
public interface HttpMyIpClient {

    @GetMapping
    String getIp();
}
