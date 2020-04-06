package com.poldichen.movie.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.ParserConfig;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import sun.swing.BakedArrayList;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
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

        System.getProperties().setProperty("proxySet", "true");
        System.getProperties().setProperty("http.proxyHost", "218.21.230.156");
        System.getProperties().setProperty("http.proxyPort", "808");

        for (int i = 2; i < 100; i++) {
            int start = i * 20;
            String listUrl = "https://movie.douban.com/j/new_search_subjects?sort=S&range=0,10&tags=&start=" + start;
            List<String> movieUrls = getMovieList(listUrl);
            for (String movieUrl : movieUrls) {
                parseMovie(movieUrl);
            }
            Map<String, Object> systemLogParams = new HashMap<>();
            systemLogParams.put("log_id", start);
            systemLogParams.put("type", "get_movie_list_finish");
            systemLogParams.put("detail", listUrl);
            MovieApiUtil.addSystemLog(systemLogParams);
        }


//        parseMovie("https://movie.douban.com/subject/26348103");

//        parseCelebrity("https://movie.douban.com/celebrity/1037044/");

//        System.out.println(FetchUtil.getUrlDoc("https://movie.douban.com/celebrity/1037044/"));
//        System.out.println(HttpClientUtil.doGet("https://movie.douban.com/subject/27605662/", new HashMap<>()));
    }

    public static List<String> getMovieList(String movieListUrl) {
        List<String> urls = new ArrayList<>();
        String result = HttpClientUtil.doGet(movieListUrl, new HashMap<>());
        JSONObject jsonObject = JSONObject.parseObject(result);
        JSONArray data = jsonObject.getJSONArray("data");
        for (Object movieObj : data) {
            JSONObject movie = JSONObject.parseObject(movieObj.toString());
            String movieUrl = movie.getString("url");
            urls.add(movieUrl);
        }
        return urls;
    }

    public static int parseCelebrity(String celebrityUrl) {
        System.out.println("[parseCelebrity]" + celebrityUrl);

        String celebrityCode;
        String[] vals1 = celebrityUrl.split("/");
        celebrityCode = vals1[vals1.length-1];

        String celebrityName;
        String celebrityEnglishName = "";
        String celebrityGender = "";
        String celebrityConstellation = "";
        String celebrityBirthDate = "";
        String celebrityPassAwayDate = "";
        String celebrityBirthPlace = "";
        String celebrityProfession = "";
        String celebrityDescription = "";
        String celebrityCoverUrl = "";

        Document doc = null;
        try {
            doc = FetchUtil.getUrlDocHttp(celebrityUrl);
        } catch (Exception e) {
            return -1;
        }

        Element h1 = doc.selectFirst("h1");
        int spaceIndex = h1.text().indexOf(" ");
        if (spaceIndex == -1) {
            celebrityName = h1.text();
        } else {
            celebrityName = h1.text().substring(0, spaceIndex);
            celebrityEnglishName = h1.text().substring(spaceIndex +1 );
        }


        Element headline = doc.selectFirst("div[ id=\"headline\"]");
        Element anchor = headline.selectFirst("a[class=\"nbg\"]");
        celebrityCoverUrl = anchor.attr("href");

        Elements lis = headline.select("li");
        for (Element li : lis) {
            String val = li.childNodes().get(2).toString().substring(2).trim();
            if (li.toString().contains("性别")) {
                celebrityGender = val;
            }
            if (li.toString().contains("星座")) {
                celebrityConstellation = val;
            }
            if (li.toString().contains("出生日期")) {
                celebrityBirthDate = val;
            }
            if (li.toString().contains("出生地")) {
                celebrityBirthPlace = val;
            }
            if (li.toString().contains("职业")) {
                celebrityProfession = val;
            }
            if (li.toString().contains("生卒日期")) {
                String[] vals = val.split(" 至 ");
                celebrityBirthDate = vals[0];
                celebrityPassAwayDate = vals[1];
            }
        }

        Element introElement = doc.selectFirst("div[id=\"intro\"]");
        Element bd = introElement.selectFirst("div[class=\"bd\"]");
        Element introSpan = introElement.selectFirst("span[class=\"all hidden\"]");
        if (introSpan == null) {
            celebrityDescription = bd.text();
            celebrityDescription = celebrityDescription.replace((char) 12288, ' ');
            celebrityDescription = celebrityDescription.trim();
        } else {
            celebrityDescription = introSpan.text();
            celebrityDescription = celebrityDescription.replace((char) 12288, ' ');
            celebrityDescription = celebrityDescription.trim();
        }

        System.out.println(celebrityName);
        System.out.println(celebrityEnglishName);
        System.out.println(celebrityCoverUrl);
        System.out.println(celebrityGender);
        System.out.println(celebrityConstellation);

        System.out.println(celebrityBirthDate);
        System.out.println(celebrityPassAwayDate);
        System.out.println(celebrityBirthPlace);
        System.out.println(celebrityProfession);
        System.out.println(celebrityDescription);

        int celebrityId = MovieApiUtil.isActorExist(celebrityCode, null);
        if (celebrityId == 0) {
            int pictureId = uploadPicture("celebrity_cover", celebrityCode, celebrityCoverUrl);
            Map<String, Object> actorParams = new HashMap<>();
            actorParams.put("name", celebrityName);
            actorParams.put("code", celebrityCode);
            actorParams.put("english_name", celebrityEnglishName);
            actorParams.put("gender", celebrityGender);
            actorParams.put("constellation", celebrityConstellation);
            actorParams.put("birth_date", celebrityBirthDate);
            actorParams.put("pass_away_date", celebrityPassAwayDate);
            actorParams.put("birth_place", celebrityBirthPlace);
            actorParams.put("profession", celebrityProfession);
            actorParams.put("description", celebrityDescription);
            actorParams.put("search", 0);

            List<Map<String, Object>> celebrityCovers = new ArrayList<>();
            Map<String, Object> celebrityCover = new HashMap<>();
            celebrityCover.put("id", pictureId);
            celebrityCovers.add(celebrityCover);
            actorParams.put("covers", celebrityCovers);
            celebrityId = MovieApiUtil.addActor(actorParams);
        }
        return celebrityId;
    }

    public static int uploadPicture(String type, String unique, String url) {
        String extension = url.substring(url.lastIndexOf(".") + 1);
        if (extension.length() > 4) {
            return 0;
        }
        String fileName = type + "_" + unique + "." + extension;
        Map<String, Object> params = new HashMap<>();
        params.put("path", "/");
        params.put("file_name", fileName);
        params.put("extension", extension);
        params.put("url", url);
        int pictureId = MovieApiUtil.addPicture(params);
        return pictureId;
    }

    public static void parseMovie(String movieUrl) {
        System.out.println("[parseMovie]" + movieUrl);

        // get movie_code from url
        String movieCode;
        String[] vals = movieUrl.split("/");
        movieCode = vals[vals.length-1];

        // if movie exist
        int movieId = MovieApiUtil.isMovieExist(movieCode);
        if (movieId != 0) {
            return;
        }

        // declare params
        String movieTitle = "";
        String movieCoverUrl = "";
        String movieAliasName = "";
        String movieCategory = "";
        String movieLabel = "";
        String moviePublishDate = "";
        String movieLength = "";
        String moviePublishCountry = "";
        String movieLanguage = "";
        String movieDescription = "";
        List<Integer> directorIds = new ArrayList<>();
        List<Integer> writerIds = new ArrayList<>();
        List<Integer> actorIds = new ArrayList<>();

        // get movie doc
        Document doc = null;
        try {
            doc = FetchUtil.getUrlDocHttp(movieUrl);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> systemLogParams = new HashMap<>();
            systemLogParams.put("log_id", movieCode);
            systemLogParams.put("type", "parse_movie_detail_fail");
            systemLogParams.put("detail", e.getMessage());
            MovieApiUtil.addSystemLog(systemLogParams);
            return;
        }

        Element script = doc.selectFirst("script[type=\"application/ld+json\"]");
        if (script == null) {
            Map<String, Object> systemLogParams = new HashMap<>();
            systemLogParams.put("log_id", movieCode);
            systemLogParams.put("type", "movie_without_json_data");
            systemLogParams.put("detail", movieUrl);
            MovieApiUtil.addSystemLog(systemLogParams);
            return;
        }
        String detailStr = script.data();
        JSONObject movieObject = JSONObject.parseObject(detailStr);

        movieTitle = movieObject.getString("name");
        movieCoverUrl = movieObject.getString("image");
        moviePublishDate = movieObject.getString("datePublished");
        movieCategory = movieObject.getString("@type");

        JSONArray genreArray = movieObject.getJSONArray("genre");
        for (Object genre : genreArray) {
            movieLabel += genre.toString() + "/";
        }

        JSONArray directorArray = movieObject.getJSONArray("director");
        for (Object directorObj : directorArray) {
            JSONObject directorObject = JSONObject.parseObject(directorObj.toString());
            String directorUrl = directorObject.getString("url");
            int celebrityId = parseCelebrity("https://movie.douban.com" + directorUrl);
            if (celebrityId == -1) {
                Map<String, Object> systemLogParams = new HashMap<>();
                systemLogParams.put("log_id", movieCode);
                systemLogParams.put("type", "parse_movie_detail_fail");
                systemLogParams.put("detail", movieUrl);
                MovieApiUtil.addSystemLog(systemLogParams);
                return;
            }
            directorIds.add(celebrityId);
        }

        JSONArray writerArray = movieObject.getJSONArray("author");
        for (Object writerObj : writerArray) {
            JSONObject writerObject = JSONObject.parseObject(writerObj.toString());
            String writerUrl = writerObject.getString("url");
            int celebrityId = parseCelebrity("https://movie.douban.com" + writerUrl);
            if (celebrityId == -1) {
                Map<String, Object> systemLogParams = new HashMap<>();
                systemLogParams.put("log_id", movieCode);
                systemLogParams.put("type", "parse_movie_detail_fail");
                systemLogParams.put("detail", movieUrl);
                MovieApiUtil.addSystemLog(systemLogParams);
                return;
            }
            writerIds.add(celebrityId);
        }

        JSONArray actorArray = movieObject.getJSONArray("actor");
        for (Object actorObj : actorArray) {
            JSONObject actorObject = JSONObject.parseObject(actorObj.toString());
            String actorUrl = actorObject.getString("url");
            int celebrityId = parseCelebrity("https://movie.douban.com" + actorUrl);
            if (celebrityId == -1) {
                Map<String, Object> systemLogParams = new HashMap<>();
                systemLogParams.put("log_id", movieCode);
                systemLogParams.put("type", "parse_movie_detail_fail");
                systemLogParams.put("detail", movieUrl);
                MovieApiUtil.addSystemLog(systemLogParams);
                return;
            }
            actorIds.add(celebrityId);
        }

        Element info = doc.selectFirst("div[id=\"info\"]");

        List<Node> nodes = info.childNodes();
        for (int i = 0; i < nodes.size(); i++) {
            Node node = nodes.get(i);
            if (node.toString().contains("制片国家/地区:")) {
                moviePublishCountry = nodes.get(i+1).toString().trim();
            }
            if (node.toString().contains("语言:")) {
                movieLanguage = nodes.get(i+1).toString().trim();
            }
            if (node.toString().contains("又名:")) {
                movieAliasName = nodes.get(i+1).toString().trim();
            }
        }

        Elements publishDateElements = info.select("span[property=\"v:initialReleaseDate\"]");
        for (Element element : publishDateElements) {
            moviePublishDate += element.text() + "/";
        }

        Element lengthElement = info.selectFirst("span[property=\"v:runtime\"]");
        if (lengthElement != null) {
            movieLength = lengthElement.text();
        }

        Element descriptionElement = doc.selectFirst("span[property=\"v:summary\"]");
        if (descriptionElement != null) {
            movieDescription = descriptionElement.text();
            movieDescription = movieDescription.replace((char) 12288, ' ');
            movieDescription = movieDescription.trim();
        }

        System.out.println(movieTitle);
        System.out.println(movieCoverUrl);
        System.out.println(movieCategory);
        System.out.println(movieLabel);
        System.out.println(moviePublishDate);
        System.out.println(movieLength);
        System.out.println(moviePublishCountry);
        System.out.println(movieLanguage);
        System.out.println(movieDescription);

        int pictureId = uploadPicture("movie_cover", movieCode, movieCoverUrl);

        Map<String, Object> movieParams = new HashMap<>();
        movieParams.put("code", movieCode);
        movieParams.put("title", movieTitle);
        movieParams.put("name", movieTitle);
        movieParams.put("alias_name", movieAliasName);
        movieParams.put("category", movieCategory);
        movieParams.put("label", movieLabel);
        movieParams.put("publish_date", moviePublishDate);
        movieParams.put("length", movieLength);
        movieParams.put("produce_country", moviePublishCountry);
        movieParams.put("language", movieLanguage);
        movieParams.put("description", movieDescription);

        List<Map<String, Object>> movieCovers = new ArrayList<>();
        Map<String, Object> movieCover = new HashMap<>();
        movieCover.put("id", pictureId);
        movieCovers.add(movieCover);
        movieParams.put("covers", movieCovers);

        List<Map<String, Object>> movieActors = new ArrayList<>();
        for (int id : actorIds) {
            Map<String, Object> movieActor = new HashMap<>();
            movieActor.put("id", id);
            movieActors.add(movieActor);
        }
        movieParams.put("actors", movieActors);

        List<Map<String, Object>> movieDirectors = new ArrayList<>();
        for (int id : directorIds) {
            Map<String, Object> movieDirector = new HashMap<>();
            movieDirector.put("id", id);
            movieDirectors.add(movieDirector);
        }
        movieParams.put("directors", movieDirectors);

        List<Map<String, Object>> movieWriters = new ArrayList<>();
        for (int id : writerIds) {
            Map<String, Object> movieWriter = new HashMap<>();
            movieWriter.put("id", id);
            movieWriters.add(movieWriter);
        }
        movieParams.put("writers", movieWriters);

        MovieApiUtil.addMovie(movieParams);



    }
}
