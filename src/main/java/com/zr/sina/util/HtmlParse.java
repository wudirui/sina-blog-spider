package com.zr.sina.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HtmlParse {
    private String url;
    private CloseableHttpClient httpClient;
    private HttpGet request;
    CloseableHttpResponse response;

    public HtmlParse(String url) {
        this.url = url;
        init();
    }

    private void init() {
        //1.生成httpclient，相当于该打开一个浏览器
        this.httpClient = HttpClients.createDefault();
        //2.创建get请求，相当于在浏览器地址栏输入 网址
        this.request = new HttpGet(url);
    }

    public String htmlParse() {
        String html = null;
        try {
            //3.执行get请求，相当于在输入地址栏后敲回车键
            this.response = httpClient.execute(request);
            //4.判断响应状态为200，进行处理
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                //5.获取响应内容
                HttpEntity httpEntity = response.getEntity();
                html = EntityUtils.toString(httpEntity, "utf-8");
            } else {
                //如果返回状态不是200，比如404（页面不存在）等，根据情况做处理，这里略
                System.out.println("返回状态不是200");
                System.out.println(EntityUtils.toString(response.getEntity(), "utf-8"));
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //6.关闭
            HttpClientUtils.closeQuietly(response);
            HttpClientUtils.closeQuietly(httpClient);
        }
        return html;
    }
}
