package com.pino.proxydemo.proxy;

import feign.Client;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;
import java.net.Proxy;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class FeignProxyConfiguration {

    private final ProxyConfiguration proxyConfiguration;

    @Bean
    @ConditionalOnProperty(prefix = "system.proxy", name = "type", havingValue = "FEIGN")
    public Client feignClient() {
        log.info("Proxy settings： Use feign client proxy");
        return new Client.Proxied(null, null,
            new Proxy(Proxy.Type.HTTP,
                new InetSocketAddress(proxyConfiguration.getProxyHost(), proxyConfiguration.getProxyPort())));
    }
}
