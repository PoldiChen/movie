package com.poldichen.movie.util;


import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.*;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.imageio.ImageIO;

/**
 * @author poldi.chen
 * @className FetchUtil
 * @description TODO
 * @date 2020/3/1 10:44
 **/
public class FetchUtil {

    private static final String LENGTH_KEY = "";
    private static final String CODE_KEY = "";
    private static final String PUBLISH_DATE_KEY = "";
    private static final String PRODUCER_KEY = "";
    private static final String PUBLISHER_KEY = "";

    public static Document getUrlDoc(String url) throws Exception {
        Document doc = Jsoup.connect(url)
                .maxBodySize(Integer.MAX_VALUE)
                .data("query", "Java")
                .cookie("auth", "token")
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.140 Safari/537.36 Edge/17.17134")
                .timeout(20000)
                .post();
        return doc;
    }

    public static void parseMovieList(String url) throws Exception {
        Document doc = getUrlDoc(url);
        Elements movies = doc.select("div[class=\"item\"]");
        Iterator<Element> iterator = movies.iterator();
        while (iterator.hasNext()) {
            Element movie = iterator.next();
            Element anchor = movie.select("a[class=\"movie-box\"]").first();
            String movieUrl = anchor.attr("href");
            Element img = movie.selectFirst("img");
            String movieCoverUrl = img.attr("src");
            parseMovieDetail(movieUrl, movieCoverUrl);
        }
    }

    public static void parseMovieDetail(String movieUrl, String movieCoverUrl) throws Exception {
        Document doc = getUrlDoc(movieUrl);

        // get movieName
        Element title = doc.select("h3").first();
        String movieName = title.text().substring(title.text().indexOf(" ") + 1);

        Element movieRow = doc.select("div[class=\"row movie\"]").first();

        // get movieCoverDetailUrl
        Element bigImg = movieRow.selectFirst("a[class=\"bigImage\"]");
        String movieCoverDetailUrl = bigImg.attr("href");

        Element movieInfo = movieRow.select("div[class=\"col-md-3 info\"]").first();

        // get actorName
        List<String> actorNames = new ArrayList<>();
        Elements actorElements = movieInfo.select("div[class=\"star-name\"]");
        Iterator<Element> actorIterator = actorElements.iterator();
        while (actorIterator.hasNext()) {
            actorNames.add(actorIterator.next().text());
        }
        if (actorNames.size() == 0) {
            actorNames.add("unknown");
        }

        // get code, publish_date, length, producer, publisher
        String code = "0";
        String publishDate = "";
        String length = "";
        String type = "";
        String producer = "";
        String publisher = "";
        for (int i=0;i<movieInfo.childrenSize();i++) {
            String info = movieInfo.child(i).text();
            if (info.indexOf(CODE_KEY) != -1) {
                code = info.substring(info.indexOf(" ")+1);
            }
            if (info.indexOf(PUBLISH_DATE_KEY) != -1) {
                publishDate = info.substring(info.indexOf(" ")+1);
            }
            if (info.indexOf(LENGTH_KEY) != -1) {
                length = info.substring(info.indexOf(" ")+1);
            }
            if (info.indexOf(PRODUCER_KEY) != -1) {
                producer = info.substring(info.indexOf(" ")+1);
            }
            if (info.indexOf(PUBLISHER_KEY) != -1) {
                publisher = info.substring(info.indexOf(" ")+1);
            }
        }

        // get movie type
        Elements typeElements = movieInfo.select("span[class=\"genre\"]");
        Iterator<Element> typeIterator = typeElements.iterator();
        while (typeIterator.hasNext()) {
            String typeVal = typeIterator.next().text();
            boolean addType = true;
            for (String actorName : actorNames) {
                if (typeVal.equals(actorName)) {
                    addType = false;
                    break;
                }
            }
            if (addType) {
                type += typeVal + "|";
            }
        }

        // get resource, screenshot
        List<String> screenshotUrls = new ArrayList<>();
        Elements screenshotElements = doc.select("a[class=\"sample-box\"]");
        Iterator<Element> screenshotIterator = screenshotElements.iterator();
        while (screenshotIterator.hasNext()) {
            String screenshotUrl = screenshotIterator.next().attr("href");
            screenshotUrls.add(screenshotUrl);
        }

        System.out.println("movie cover url: " + movieCoverUrl);

        System.out.println("movie name: " + movieName);
        System.out.println("movieCoverDetailUrl: " + movieCoverDetailUrl);
        System.out.println("actor name: " + actorNames.toString());

        System.out.println("code: " +code);
        System.out.println("publishDate: " + publishDate);
        System.out.println("length: " + length);
        System.out.println("type: " + type);
        System.out.println("producer: " + producer);
        System.out.println("publisher: " + publisher);

        System.out.println("screenshot: " + screenshotUrls.toString());

        List<String> list = new ArrayList<>();

        // add movie cover
        list.add(movieCoverUrl);
        List<Integer> coverIds = uploadPicture(code, "cover", list);
        System.out.println("coverIds: " + coverIds.toString());

        // add movie cover detail
        list = new ArrayList<>();
        list.add(movieCoverDetailUrl);
        List<Integer> coverDetailIds = uploadPicture(code, "cover_detail", list);
        System.out.println("coverDetailIds:" + coverDetailIds);

        // add movie screenshot
        List<Integer> screenshotIds = uploadPicture(code, "screenshot", screenshotUrls);
        System.out.println("screenshotIds:" + screenshotIds);

        // add actor
        List<Integer> actorIds = new ArrayList<>();
        for (String actorName : actorNames) {
            Map<String, Object> actorParams = new HashMap<>();
            actorParams.put("name", actorName);
            actorParams.put("search", 0);
            actorParams.put("covers", new ArrayList<>());
            int actorId = MovieApiUtil.addActor(actorParams);
            actorIds.add(actorId);
        }

        // add movie, relate picture, actor
        Map<String, Object> movieParams = new HashMap<>();
        movieParams.put("code", code);
        movieParams.put("name", movieName);
        movieParams.put("length", length);
        movieParams.put("publish_date", publishDate);
        movieParams.put("type", type);
        movieParams.put("producer", producer);
        movieParams.put("publisher", publisher);

        List<Map<String, Object>> movieActor = new ArrayList<>();
        for (Integer actorId : actorIds) {
            Map<String, Object> actorObj = new HashMap<>();
            actorObj.put("id", actorId);
            movieActor.add(actorObj);
        }
        movieParams.put("actors", movieActor);

        List<Map<String, Object>> movieCover = new ArrayList<>();
        for (Integer coverId : coverIds) {
            Map<String, Object> coverObj = new HashMap<>();
            coverObj.put("id", coverId);
            movieCover.add(coverObj);
        }
        movieParams.put("covers", movieCover);

        List<Map<String, Object>> movieCoverDetail = new ArrayList<>();
        for (Integer coverDetailId : coverDetailIds) {
            Map<String, Object> coverDetailObj = new HashMap<>();
            coverDetailObj.put("id", coverDetailId);
            movieCoverDetail.add(coverDetailObj);
        }
        movieParams.put("cover_details", movieCoverDetail);

        List<Map<String, Object>> movieScreenshot = new ArrayList<>();
        for (Integer screenshotId : screenshotIds) {
            Map<String, Object> screenshotObj = new HashMap<>();
            screenshotObj.put("id", screenshotId);
            movieScreenshot.add(screenshotObj);
        }
        movieParams.put("screenshots", movieScreenshot);

        movieParams.put("resources", new ArrayList<>());
        MovieApiUtil.addMovie(movieParams);

        System.out.println("=======================================================");
    }

    public static List<Integer> uploadPicture(String movieCode, String type, List<String> imgUrl) {
        List<Integer> result = new ArrayList<>();
        int index = 0;
        for (String url : imgUrl) {
            String extension = url.substring(url.lastIndexOf(".") + 1);
            String fileName = movieCode + "_" + type + "_" + index + "." + extension;
            downloadImage2(url, fileName);
            Map<String, Object> params = new HashMap<>();
            params.put("path", "/");
            params.put("file_name", fileName);
            params.put("extension", extension);
            int pictureId = MovieApiUtil.addPicture(params);
            result.add(pictureId);
            index ++;
        }
        return result;
    }

    public static void getMovieDetailDouban(String movieUrl) throws Exception {
        //获取html
        Document doc = getUrlDoc(movieUrl);
        System.out.println(doc);
        Elements a = doc.select("script[type=\"application/ld+json\"]");
        String movieJson = a.toString();
        System.out.println(movieJson);
        movieJson = movieJson.substring(movieJson.indexOf(">") + 1, movieJson.lastIndexOf("<"));
        System.out.println(movieJson);
        JSONObject movieObject = JSONObject.parseObject(movieJson);
        String imageUrl = movieObject.getString("image");
        System.out.println(imageUrl);
        downloadImage(imageUrl, "a");
        JSONArray actors = movieObject.getJSONArray("actor");
        for (Object actor : actors) {
            JSONObject actorObject = JSONObject.parseObject(actor.toString());
            System.out.println(actorObject.getString("name"));
        }
    }

    public static void downloadImage2(String imageUrl, String fileName) {
        System.out.println("下载开始...");
        HttpURLConnection conn = null;
        InputStream inputStream = null;
        BufferedInputStream bis = null;
        FileOutputStream out = null;
        try {
            File file0 = new File("E:\\movie\\picture");
            if (!file0.isDirectory() && !file0.exists()){
                file0.mkdirs();
            }
            out = new FileOutputStream(file0 + "\\" + fileName);
            // 建立链接
            URL httpUrl=new URL(imageUrl);
            conn = (HttpURLConnection) httpUrl.openConnection();
            //以Post方式提交表单，默认get方式
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setConnectTimeout(1000 * 60);
            conn.setReadTimeout(1000 * 60);
            conn.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_5) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");
            // post方式不能使用缓存
            conn.setUseCaches(false);
            //连接指定的资源
            conn.connect();
            //获取网络输入流
            inputStream=conn.getInputStream();
            bis = new BufferedInputStream(inputStream);
            byte b [] = new byte[1024];
            int len = 0;
            while ((len = bis.read(b)) != -1) {
                out.write(b, 0, len);
            }
            System.out.println("下载完成...");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out!=null) {
                    out.close();
                }
                if (bis!=null) {
                    bis.close();
                }
                if (inputStream!=null) {
                    inputStream.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public static void downloadImage(String imageUrl, String fileName) {
        fileName = "E:\\movie\\picture\\" + fileName;
        String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);
        long time2 = 0;
        try {
            URL url = new URL(imageUrl);
            long time1 = System.currentTimeMillis();
            final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            time2 = System.currentTimeMillis();
            System.out.println("time2 - time1: " + (time2 - time1));
            connection.setConnectTimeout(1000 * 30);
            connection.setReadTimeout(1000 * 30);
            connection.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_5) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");
            final BufferedImage image = ImageIO.read(connection.getInputStream());
            long time3 = System.currentTimeMillis();
            System.out.println("time3 - time2 : " + (time3-time2));
            ImageIO.write(image, formatName, new File(fileName));
            long time4 = System.currentTimeMillis();
            System.out.println("time4 - time 3 : " + (time4 - time3));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (SocketTimeoutException e) {
            long time5 = System.currentTimeMillis();
            System.out.println("time5 - time2 : " + (time5 - time2));
            System.out.println("download timeout: " + imageUrl);
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void getAllMovies() throws IOException {
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

}


