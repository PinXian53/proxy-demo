package com.pino.proxydemo.feign_client;

import com.pino.proxydemo.proxy.FeignProxyConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
    name = "HttpsMyIpClient",
    url = "https://myip.com.tw",
    configuration = FeignProxyConfiguration.class
)
public interface HttpsMyIpClient {

    @GetMapping
    String getIp();
}
