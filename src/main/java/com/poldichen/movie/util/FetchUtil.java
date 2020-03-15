package com.poldichen.movie.util;


import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.HashMap;

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
        Document doc = Jsoup.connect(url)
                .maxBodySize(Integer.MAX_VALUE)
                .data("query", "Java")
                .cookie("auth", "douban-fav-remind=1; bid=Wl_fK9wCXuo; ll=\"118282\"; _vwo_uuid_v2=D77F3D0D111DEED08EEDBD3EB46B0DB7B|05d12f5c3309186995b611c476f4566c; __yadk_uid=Dg4ep2cNWG0hrgZw24cwgZcQ1TvDAecw; trc_cookie_storage=taboola%2520global%253Auser-id%3D82a14e1d-d7d3-4112-b618-dac3bca1ab77-tuct1afd52c; __utmv=30149280.6253; __gads=ID=b43ec049b9384d4b:T=1566317147:S=ALNI_MZGFSV3f3_LPI0lyudZx4yQy2sCyw; douban-profile-remind=1; push_noty_num=0; push_doumail_num=0; viewed=\"5453891\"; gr_user_id=c728252b-5062-4b91-bac1-d9745ca3e996; dbcl2=\"62533792:7RiaOxk+CPI\"; __utmz=30149280.1584156452.54.9.utmcsr=movie.douban.com|utmccn=(referral)|utmcmd=referral|utmcct=/celebrity/1003494/; __utmz=223695111.1584156470.49.8.utmcsr=search.douban.com|utmccn=(referral)|utmcmd=referral|utmcct=/movie/subject_search; ct=y; _pk_ref.100001.4cf6=%5B%22%22%2C%22%22%2C1584233968%2C%22https%3A%2F%2Fsearch.douban.com%2Fmovie%2Fsubject_search%3Fsearch_text%3D%25E4%25B8%2580%26cat%3D1002%22%5D; _pk_ses.100001.4cf6=*; __utma=30149280.1396546215.1519473633.1584201921.1584233969.58; __utmb=30149280.0.10.1584233969; __utma=223695111.834493428.1565358274.1584201921.1584233969.53; __utmb=223695111.0.10.1584233969; ap_v=0,6.0; ck=fnep; __utmc=30149280; __utmc=223695111; _pk_id.100001.4cf6=97291528141a9588.1565358273.53.1584234467.1584202025.")
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.140 Safari/537.36 Edge/17.17134")
                .timeout(60000)
                .post();
        return doc;
    }

    public static Document getUrlDocHttp(String url) throws Exception {
        String response = HttpClientUtil.doGet(url, new HashMap<>());
        Document doc = Jsoup.parse(response);
        return doc;
    }










}


