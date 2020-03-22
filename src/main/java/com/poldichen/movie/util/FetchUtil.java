package com.poldichen.movie.util;


import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.poldichen.movie.entity.ProxyAddress;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import javax.imageio.ImageIO;

/**
 * @author poldi.chen
 * @className FetchUtil
 * @description TODO
 * @date 2020/3/1 10:44
 **/
public class FetchUtil {

    public static Document getUrlDoc(String url) throws Exception {
        Thread.sleep(getRandom());
        Document doc = Jsoup.connect(url)
                .maxBodySize(Integer.MAX_VALUE)
                .data("query", "Java")
                .cookie("auth", "token")
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.140 Safari/537.36 Edge/17.17134")
                .timeout(60000)
                .post();
        return doc;
    }

    public static Document getUrlDocHttp(String url) throws Exception {



        Thread.sleep(getRandom());

        Map<String, String> headers = new HashMap<>();
//        headers.put("cookie", "_free_proxy_session=BAh7B0kiD3Nlc3Npb25faWQGOgZFVEkiJTNiYWRmOWQ2NGYwYTQ2MzQ1ZGVkZjVlMzNhM2E1NzUyBjsAVEkiEF9jc3JmX3Rva2VuBjsARkkiMStLdlNXVGdKcmEyR0ZqenBBRjVaUGJDOW5SOGU1cmx5dnpXMnNBcWVmMmc9BjsARg%3D%3D--02e56448455ed12c3c6fc4158c086a8137372da4; Hm_lvt_0cf76c77469e965d2957f0553e6ecf59=1584275419,1584847564; Hm_lpvt_0cf76c77469e965d2957f0553e6ecf59=1584860994");
//        headers.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36");

        String response = HttpClientUtil.doGet(url, headers);
        Document doc = Jsoup.parse(response);
        return doc;
    }

//    private static void setProxy() {
//        List<ProxyAddress> proxyAddresses = MovieApiUtil.getProxyAddress();
//        for ()
//    }

    private static int getRandom() {
        Random random = new Random();
        return 1000 + random.nextInt(1000);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            System.out.println(getRandom());
        }

    }










}


