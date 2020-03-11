package com.poldichen.movie.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * @author poldi.chen
 * @className DoubanUtil
 * @description TODO
 * @date 2020/3/8 10:07
 **/
public class DoubanUtil {

    public static void getAllMoviesDouban() throws IOException {
        String url = "http://movie.douban.com/chart";
        //获取html
        Document doc = Jsoup.connect(url)
                .maxBodySize(Integer.MAX_VALUE)
                .data("query", "Java")
                .cookie("auth", "token")
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.140 Safari/537.36 Edge/17.17134")
                .timeout(10000)
                .post();
        //System.out.println(doc);
        //逐层分析html
        Elements a = doc.select("div[class=\"\"]");
        Elements b= a.select("a[class=nbg]");
        for (Element element : b) {
            String video_url = element.attr("href");	// 电影链接地址
            String video_name = element.attr("title");	// 电影名字
            Elements d= element.select("img");
            String img_url = d.attr("src");             //电影图片
            System.out.println("video_name"+video_name);
            System.out.println("img_url"+img_url);
            System.out.println("video_url"+video_url);
        }
    }

    public static void getMovieDetailDouban(String movieUrl) throws Exception {
        //获取html
        Document doc = FetchUtil.getUrlDoc(movieUrl);
        System.out.println(doc);
        Elements a = doc.select("script[type=\"application/ld+json\"]");
        String movieJson = a.toString();
        System.out.println(movieJson);
        movieJson = movieJson.substring(movieJson.indexOf(">") + 1, movieJson.lastIndexOf("<"));
        System.out.println(movieJson);
        JSONObject movieObject = JSONObject.parseObject(movieJson);
        String imageUrl = movieObject.getString("image");
        System.out.println(imageUrl);
        PictureUtil.downloadImage(imageUrl, "a");
        JSONArray actors = movieObject.getJSONArray("actor");
        for (Object actor : actors) {
            JSONObject actorObject = JSONObject.parseObject(actor.toString());
            System.out.println(actorObject.getString("name"));
        }
    }
}
