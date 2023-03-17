package com.pino.proxydemo;

import com.pino.proxydemo.feign_client.HttpMyIpClient;
import com.pino.proxydemo.feign_client.HttpsMyIpClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;

@ImportAutoConfiguration({ FeignAutoConfiguration.class})
@SpringBootTest
class ProxyDemoApplicationTests {

    @Autowired HttpMyIpClient httpMyIpClient;
    @Autowired HttpsMyIpClient httpsMyIpClient;

    @Test
    void testHttpProxy() {
        // 確認 IP 是不是 Proxy Server 的
        System.out.println(httpMyIpClient.getIp());
    }

    @Test
    void testHttpsProxy() {
        // 確認 IP 是不是 Proxy Server 的
        System.out.println(httpsMyIpClient.getIp());
    }

}
