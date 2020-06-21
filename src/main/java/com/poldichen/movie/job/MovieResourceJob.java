package com.poldichen.movie.job;

import com.poldichen.movie.config.MovieConfig;
import com.poldichen.movie.entity.Movie;
import com.poldichen.movie.entity.Resource;
import com.poldichen.movie.util.FetchUtil;
import com.poldichen.movie.util.MovieApiUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author poldi.chen
 * @className MovieResourceJob
 * @description TODO
 * @date 2020/4/18 11:22
 **/
@JobAnnotation
public class MovieResourceJob {

    @Autowired
    private MovieConfig movieConfig;

    public void execute(String parameter) {
        //
    }

    public void executeDeclare(String param1, String param2) {}

    public void getResourceBatch() {
        List<Movie> movies = MovieApiUtil.getAllMovie();
        for (Movie movie : movies) {
            List<Resource> resources = movie.getResources();
            if (resources.size() == 0) {
                getResource(movie.getTitle());
            }
        }
    }

    public void getResource(String movieCode) {
        System.out.println("get resource for: " + movieCode);
        Document doc;
        try {
            doc = FetchUtil.getUrlDoc(movieConfig.getHost() + "/" + movieCode);
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
            String resourceUrl = movieConfig.getHost() + movieConfig.getUrlResource() + "?gid=" + gid + "&uc=0";
            String refer = movieConfig.getHost() + "/" + movieCode;

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
}
