package com.nhackindustries.leakyleaky;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class HttpClient {
  private static final HttpClient instance = new HttpClient();

  private final CloseableHttpClient closableHttpClient;

  private HttpClient() {
    RequestConfig req =
        RequestConfig.custom()
            .setConnectTimeout(30 * 1000)
            .setConnectionRequestTimeout(30 * 1000)
            .build();

    this.closableHttpClient = HttpClientBuilder.create().setDefaultRequestConfig(req).build();
  }

  public CloseableHttpClient getClosableHttpClient() {
    return closableHttpClient;
  }

  public static HttpClient getInstance() {
    return instance;
  }
}
