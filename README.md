# Proxy Demo
測試 Java 如何透過 Proxy Server 打 api

- 此測試專案的 Properties 設定方式：
    ```shell
    system.proxy.host={Proxy-IP}
    system.proxy.port={Proxy-Port}
    system.proxy.type={使用 Java Proxy(JAVA) or Feign Client Proxy(FEIGN)}
    system.proxy.exclude={不使用 Proxy 的網站}
    ```
- 執行 `ProxyDemoApplicationTests.java` 進行測試
  - 如果查出的 ip 是 proxy server 的，代表成功

---
## 設定重點整理
### 方式 1：設定 Java Proxy
- 說明
  - 設定方式最簡單，只需要設定
  - 會影響到所有的 http 連線，但可以設定排除名單
- 設定方式共兩種：
  > 測試發現，若設定在 application.properties，無法生效
  - 在 JVM 的啟動參數加上設定
      ```shell
      -Dhttp.proxyHost=127.0.0.1 -Dhttp.proxyPort=8888 -Dhttps.proxyHost=127.0.0.1 -Dhttps.proxyPort=8888
      ```
  - 啟動時，在程式內使用 System.setProperty 進行設定
      ```java
      System.setProperty("http.proxyHost", "127.0.0.1");
      System.setProperty("http.proxyPort", "8888");
      System.setProperty("https.proxyHost", "127.0.0.1");
      System.setProperty("https.proxyPort", "8888");
      // https://docs.oracle.com/javase/8/docs/api/java/net/doc-files/net-properties.html
      System.setProperty("http.nonProxyHosts", "httpbin.org|myip.com.tw|127.*|[::1]");
      ```

### 方式 2：設定 Feign Client Proxy
- 說明
  - 透過 Feign Client 打 api
  - 只針對要用的 Client 綁定設定就好
- 新增 Proxy 設定
    ```java
    @RequiredArgsConstructor
    @Configuration
    public class FeignProxyConfiguration {
    
        @Bean
        public Client feignClient() {
            return new Client.Proxied(null, null,
                new Proxy(Proxy.Type.HTTP,
                    new InetSocketAddress("ProxyHost", "ProxyPort")));
        }
    }
    ```
- 套用 Proxy 設定 (多設定 configuration)
    ```java
    @FeignClient(
        name = "MyClient",
        url = "http://127.0.0.1",
        configuration = FeignProxyConfiguration.class
    )
    public interface MyClient {
    
        @GetMapping
        String getIp();
    }
    ```
---

## 建立 Forward Proxy Server (正向代理)
使用 Squid (支援 http+https)
- 安裝流程
    ```shell
    sudo apt-get -y install squid
    
    sudo vim /etc/squid/squid.conf
    
    sudo systemctl start squid
    
    sudo firewall-cmd --zone=public --add-port=8888/tcp --permanent
    sudo firewall-cmd --reload
    ```
- sudo vim /etc/squid/squid.conf
    ```shell
    改了兩個設定(善用搜尋，設定超多很難找)：
    
    # 改 Port
    - 預設：http_port 3128
    - 改成：http_port 8888
    
    # 改允許存取的 IP (這邊是全開，但應該要設定指定的 IP 比較安全)
    - 預設：http_access deny all
    - 改成：http_access allow all
    ```
- 如果要查看 access log

    ```bash
    sudo tail -f /var/log/squid/access.log
    ```

---
## Forward Proxy Server 效果測試
要到非 Proxy Server 的電腦進行測試
```shell
# {proxy-server-ip} 換成 proxy server 的 ip
# 測試 http (確認 response 是不是回傳 proxy server 的 ip)
curl -i --proxy {proxy-server-ip}:8888 http://httpbin.org/ip
# 測試 https (確認 response 是不是回傳 proxy server 的 ip)
curl -i --proxy {proxy-server-ip}:8888 https://myip.com.tw
```