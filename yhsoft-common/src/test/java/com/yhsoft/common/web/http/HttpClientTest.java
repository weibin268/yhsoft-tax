package com.yhsoft.common.web.http;

import org.junit.Test;

public class HttpClientTest {

    @Test
    public void sendGet() throws Exception {

        HttpClient httpClient=new HttpClient();

        System.out.println(httpClient.sendGet("https://www.baidu.com"));
    }

}