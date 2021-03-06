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

    public static void main(String[] args) throws Exception {

        String address = "https://www.cdnbus.blog/page/2";

        Document document = getUrlDoc(address);
        System.out.println(document);

    }

    public static Document getUrlDoc(String url) throws Exception {
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
        Map<String, String> headers = new HashMap<>();
        String response = HttpClientUtil.doGet(url, headers);
        Document doc = Jsoup.parse(response);
        return doc;
    }

    private static int getRandom() {
        Random random = new Random();
        return 1000 + random.nextInt(1000);
    }

}


