package com.poldichen.movie.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.ParserConfig;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import sun.swing.BakedArrayList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author poldi.chen
 * @className DoubanUtil
 * @description TODO
 * @date 2020/3/8 10:07
 **/
public class DoubanUtil {

    public static void main(String[] args) throws Exception {
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
//        getAllMoviesDouban();
//        getMovieList("https://movie.douban.com/j/new_search_subjects?sort=S&range=0,10&tags=&start=120");

        getMovieDetailDouban("https://movie.douban.com/subject/1291546/");
    }

    public static void getMovieList(String movieListUrl) {
        String result = HttpClientUtil.doGet(movieListUrl, new HashMap<>());
        JSONObject jsonObject = JSONObject.parseObject(result);
        JSONArray data = jsonObject.getJSONArray("data");
        for (Object movieObj : data) {
            JSONObject movie = JSONObject.parseObject(movieObj.toString());
            String movieUrl = movie.getString("url");
        }
    }

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

        String movieName = "";
        String movieCoverUrl = "";
        List<Map<String, String>> directors = new ArrayList<>();
        List<Map<String, String>> writers = new ArrayList<>();
        List<Map<String, String>> actors = new ArrayList<>();

        Document doc = FetchUtil.getUrlDoc(movieUrl);
        Element script = doc.selectFirst("script[type=\"application/ld+json\"]");
        String detailStr = script.data();
        JSONObject movieObject = JSONObject.parseObject(detailStr);

        movieName = movieObject.getString("name");
        movieCoverUrl = movieObject.getString("image");

        JSONArray directorArray = movieObject.getJSONArray("director");
        for (Object directorObj : directorArray) {
            JSONObject diretorObject = JSONObject.parseObject(directorObj.toString());
            
        }


    }
}
