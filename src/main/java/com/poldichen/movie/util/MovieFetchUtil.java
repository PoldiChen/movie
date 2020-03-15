package com.poldichen.movie.util;

import com.poldichen.movie.entity.Movie;
import com.poldichen.movie.entity.Picture;
import com.poldichen.movie.entity.Resource;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.*;

/**
 * @author poldi.chen
 * @className MovieFetchUtil
 * @description TODO
 * @date 2020/3/8 10:08
 **/
public class MovieFetchUtil {

    private static final String LENGTH_KEY = "長度: ";
    private static final String CODE_KEY = "識別碼: ";
    private static final String PUBLISH_DATE_KEY = "發行日期: ";
    private static final String PRODUCER_KEY = "製作商: ";
    private static final String PUBLISHER_KEY = "發行商: ";
    private static final String DIRECTOR_KEY = "導演: ";

    private static final String HOST = "https://www.busdmm.cloud"; // https://www.busdmm.cloud/MIAA-184
    private static final String URL_RESOURCE = "https://www.busdmm.cloud/ajax/uncledatoolsbyajax.php";



    public static void getResourceBatch() {
        List<Movie> movies = MovieApiUtil.getAllMovie();
        for (Movie movie : movies) {
            List<Resource> resources = movie.getResources();
            if (resources.size() == 0) {
                getResource(movie.getTitle());
            }
        }
    }

    public static void getResource(String movieCode) {
        System.out.println("get resource for: " + movieCode);
        Document doc;
        try {
            doc = FetchUtil.getUrlDoc(HOST + "/" + movieCode);
        } catch (Exception e) {
            Map<String, Object> systemLogParams = new HashMap<>();
            systemLogParams.put("log_id", movieCode);
            systemLogParams.put("type", "get_movie_doc_fail");
            systemLogParams.put("detail", e.getMessage());
            MovieApiUtil.addSystemLog(systemLogParams);
            return;
        }

        String docStr = doc.toString();
        int index = docStr.indexOf("var gid = ");
        if (index != -1) {
            String gid = docStr.substring(index + 10, index + 10 + 11);
            String resourceUrl = URL_RESOURCE + "?gid=" + gid + "&uc=0";
            String refer = HOST + "/" + movieCode;

            try {
                doc = Jsoup.connect(resourceUrl)
                        .maxBodySize(Integer.MAX_VALUE)
                        .data("query", "Java")
                        .cookie("auth", "token")
                        .referrer(refer)
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.140 Safari/537.36 Edge/17.17134")
                        .timeout(1000 * 30)
                        .post();
            } catch (Exception e) {
                Map<String, Object> systemLogParams = new HashMap<>();
                systemLogParams.put("log_id", movieCode);
                systemLogParams.put("type", "get_movie_resource_fail");
                systemLogParams.put("detail", e.getMessage());
                MovieApiUtil.addSystemLog(systemLogParams);
                return;
            }

            Elements anchors = doc.select("a[rel=\"nofollow\"]");
            int groupSize = anchors.size() / 3;
            List<Integer> resourceIds = new ArrayList<>();
            for (int i = 0; i < groupSize; i++) {
                String url = anchors.get(i*3).attr("href");
                String showName = anchors.get(i*3).text();
                String size = anchors.get(i*3+1).text();
                String shareDate = anchors.get(i*3+2).text();
                System.out.println(url);
                System.out.println(showName);
                System.out.println(size);
                System.out.println(shareDate);

                Map<String, Object> resourceParams = new HashMap<>();
                resourceParams.put("url", url);
                resourceParams.put("size", size);
                resourceParams.put("show_name", showName);
                resourceParams.put("share_date", shareDate);

                int resourceId = MovieApiUtil.addResource(resourceParams);
                resourceIds.add(resourceId);
                System.out.println("========================");
            }
            int movieId = MovieApiUtil.isMovieExist(movieCode);
            Map<String, Object> updateParams = new HashMap<>();
            updateParams.put("resource_id", resourceIds);
            MovieApiUtil.updateMovieResource(movieId, updateParams);
        }

    }

    public static void parseMovieDetail(String movieUrl, String movieCoverUrl) throws Exception {
        Document doc = FetchUtil.getUrlDoc(movieUrl);
        if (doc == null) {
            return;
        }

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
        String director = "";
        for (int i=0;i<movieInfo.childrenSize();i++) {
            String info = movieInfo.child(i).text();
            if (info.indexOf(CODE_KEY) != -1) {
                code = info.substring(info.indexOf(" ")+1);
                int movieId = MovieApiUtil.isMovieExist(code);
                if (movieId != 0) {
                    return;
                }

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
            if (info.indexOf(DIRECTOR_KEY) != -1) {
                director = info.substring(info.indexOf(" ")+1);
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
            int actorId = MovieApiUtil.isActorExist(actorName);
            if (actorId == 0) {
                Map<String, Object> actorParams = new HashMap<>();
                actorParams.put("name", actorName);
                actorParams.put("search", 0);
                actorParams.put("covers", new ArrayList<>());
                actorId = MovieApiUtil.addActor(actorParams);
            }
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
        movieParams.put("director", director);

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

    public static void parseMovieList(String url) throws Exception {
        Document doc = FetchUtil.getUrlDoc(url);
        if (doc == null) {
            File file = new File("E:\\movie\\fail.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            writer.write(url);
            writer.newLine();
            writer.flush();
            writer.close();
        }
        Elements movies = doc.select("div[class=\"item\"]");
        Iterator<Element> iterator = movies.iterator();
        while (iterator.hasNext()) {
            Element movie = iterator.next();
            Element anchor = movie.select("a[class=\"movie-box\"]").first();
            String movieUrl = anchor.attr("href");
            Element img = movie.selectFirst("img");
            String movieCoverUrl = img.attr("src");

            Element date = anchor.select("date").first();
            String movieCode = date.text();
            int movieId = MovieApiUtil.isMovieExist(movieCode);
            if (movieId != 0) {
                continue;
            }
            parseMovieDetail(movieUrl, movieCoverUrl);
        }
    }

    public static List<Integer> uploadPicture(String movieCode, String type, List<String> imgUrl) {
        List<Integer> result = new ArrayList<>();
        int index = 0;
        for (String url : imgUrl) {
            String extension = url.substring(url.lastIndexOf(".") + 1);
            String fileName = movieCode + "_" + type + "_" + index + "." + extension;
//            downloadImage2(url, fileName);
            Map<String, Object> params = new HashMap<>();
            params.put("path", "/");
            params.put("file_name", fileName);
            params.put("extension", extension);
            params.put("url", url);
            int pictureId = MovieApiUtil.addPicture(params);
            result.add(pictureId);
            index ++;
        }
        return result;
    }
}
