package com.pino.proxydemo.proxy;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class ProxyConfiguration {

    @Value("${system.proxy.type}")
    private String proxyType;
    @Value("${system.proxy.host}")
    private String proxyHost;
    @Value("${system.proxy.port}")
    private int proxyPort;
    @Value("${system.proxy.exclude}")
    private String proxyExclude;
}
