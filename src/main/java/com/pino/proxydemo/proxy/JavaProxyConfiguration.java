package com.pino.proxydemo.proxy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class JavaProxyConfiguration {

    private final ProxyConfiguration proxyConfiguration;

    @PostConstruct
    private void initProxySettings(){
        if("JAVA".equals(proxyConfiguration.getProxyType())){
            log.info("Proxy settings： Use java proxy");
            System.setProperty("http.proxyHost", proxyConfiguration.getProxyHost());
            System.setProperty("http.proxyPort", String.valueOf(proxyConfiguration.getProxyPort()));
            System.setProperty("https.proxyHost", proxyConfiguration.getProxyHost());
            System.setProperty("https.proxyPort", String.valueOf(proxyConfiguration.getProxyPort()));
            System.setProperty("http.nonProxyHosts", proxyConfiguration.getProxyExclude());
        }
    }

}
