package com.poldichen.movie.job;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.poldichen.movie.util.HttpClientUtil;
import com.poldichen.movie.util.MovieApiUtil;
import com.poldichen.movie.util.TimeUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 从豆瓣电影列表 API 拉取数据的 Job.
 *
 * 调用豆瓣 new_search_subjects API（返回 JSON，不抓详情页规避反爬验证），
 * 拿到电影名/封面/评分/导演/演员列表，写入本机 movie/celebrity/picture 表。
 *
 * 触发方式：POST /job  body: {"job_name":"com.poldichen.movie.job.DoubanMovieListJob","job_params":"2"}
 *   - job_params: 要抓取的页数（每页 20 部电影），如 "2" 表示抓取前 40 部电影
 */
@JobAnnotation
public class DoubanMovieListJob {

    private static final String LIST_URL_TEMPLATE =
            "https://movie.douban.com/j/new_search_subjects?sort=S&range=0,10&tags=&start=%d";

    private static final String UA =
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) "
                    + "Chrome/120.0.0.0 Safari/537.36";

    // 每部电影入库后 sleep，避免触发豆瓣反爬
    private static final long SLEEP_PER_MOVIE_MS = 500L;

    // 每页之间 sleep
    private static final long SLEEP_PER_PAGE_MS = 3000L;

    public void executeDeclare(String endPageIndex) {}

    public void execute(String args) {
        int endIndex;
        try {
            endIndex = Integer.parseInt(args.trim());
        } catch (Exception e) {
            System.out.println("[DoubanMovieListJob] invalid args: " + args + ", use default 1");
            endIndex = 1;
        }
        if (endIndex < 1) endIndex = 1;
        if (endIndex > 50) endIndex = 50;

        final int finalEndIndex = endIndex;

        // 刷新本机 API token
        MovieApiUtil.getAuth("jack", "123456");

        new Thread(() -> {
            int totalMovies = 0;
            for (int i = 0; i < finalEndIndex; i++) {
                int start = i * 20;
                String listUrl = String.format(LIST_URL_TEMPLATE, start);
                System.out.println("[DoubanMovieListJob] page=" + (i + 1) + " url=" + listUrl);

                Map<String, String> headers = new HashMap<>();
                headers.put("User-Agent", UA);
                headers.put("Accept", "application/json, text/plain, */*");
                headers.put("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8");
                headers.put("Referer", "https://movie.douban.com/");

                String respJson;
                try {
                    respJson = HttpClientUtil.doGet(listUrl, headers);
                } catch (Exception e) {
                    System.out.println("[DoubanMovieListJob] getMovieList FAIL: " + e.getMessage());
                    log("douban_get_movie_list_fail",
                            "page=" + (i + 1) + " url=" + listUrl + " err=" + e.getMessage());
                    sleepQuiet(SLEEP_PER_PAGE_MS);
                    continue;
                }

                JSONArray dataArray;
                try {
                    JSONObject root = JSON.parseObject(respJson);
                    dataArray = root.getJSONArray("data");
                } catch (Exception e) {
                    System.out.println("[DoubanMovieListJob] parseListJson FAIL: " + e.getMessage());
                    log("douban_parse_list_fail",
                            "page=" + (i + 1) + " err=" + e.getMessage());
                    sleepQuiet(SLEEP_PER_PAGE_MS);
                    continue;
                }
                if (dataArray == null) {
                    System.out.println("[DoubanMovieListJob] data is null, skip page " + (i + 1));
                    sleepQuiet(SLEEP_PER_PAGE_MS);
                    continue;
                }

                System.out.println("[DoubanMovieListJob] page=" + (i + 1)
                        + " got " + dataArray.size() + " movies");

                for (Object movieObj : dataArray) {
                    JSONObject m = JSON.parseObject(movieObj.toString());
                    try {
                        addMovieFromListJson(m);
                        totalMovies++;
                    } catch (Exception e) {
                        System.out.println("[DoubanMovieListJob] addMovie FAIL: "
                                + m.getString("id") + " " + e.getMessage());
                        log("douban_add_movie_fail",
                                "id=" + m.getString("id") + " err=" + e.getMessage());
                    }
                    sleepQuiet(SLEEP_PER_MOVIE_MS);
                }

                log("douban_page_finish",
                        "page=" + (i + 1) + " total=" + totalMovies);
                sleepQuiet(SLEEP_PER_PAGE_MS);
            }

            log("douban_job_finish",
                    "pages=" + finalEndIndex + " total_movies=" + totalMovies);
            System.out.println("[DoubanMovieListJob] DONE, total_movies=" + totalMovies);
        }, "douban-movie-list-job").start();
    }

    /**
     * 用列表 API 返回的 JSON 构造 movie 对象，调本机 API 写入.
     * 字段: title, id, url, rate, cover, directors[], casts[]
     */
    private static void addMovieFromListJson(JSONObject m) {
        String movieCode = m.getString("id");
        String title = m.getString("title");
        String coverUrl = m.getString("cover");
        String rate = m.getString("rate");
        JSONArray directors = m.getJSONArray("directors");
        JSONArray casts = m.getJSONArray("casts");

        // 已存在则跳过
        if (MovieApiUtil.isMovieExist(movieCode) != 0) {
            System.out.println("[DoubanMovieListJob] movie exist, skip: " + movieCode);
            return;
        }

        // 入封面图
        int pictureId = 0;
        if (coverUrl != null && !coverUrl.isEmpty()) {
            String extension = coverUrl.substring(coverUrl.lastIndexOf(".") + 1);
            if (extension.length() <= 4) {
                Map<String, Object> picParams = new HashMap<>();
                picParams.put("path", "/");
                picParams.put("file_name", "movie_cover_" + movieCode + "." + extension);
                picParams.put("extension", extension);
                picParams.put("url", coverUrl);
                picParams.put("size", 0);
                try {
                    pictureId = MovieApiUtil.addPicture(picParams);
                } catch (Exception e) {
                    System.out.println("[DoubanMovieListJob] addPicture FAIL: " + e.getMessage());
                }
            }
        }

        // 入导演/演员影人
        List<Integer> directorIds = new ArrayList<>();
        if (directors != null) {
            for (Object d : directors) {
                String name = d.toString();
                int cid = ensureCelebrity(name);
                if (cid != 0) directorIds.add(cid);
            }
        }
        List<Integer> actorIds = new ArrayList<>();
        if (casts != null) {
            for (Object c : casts) {
                String name = c.toString();
                int cid = ensureCelebrity(name);
                if (cid != 0) actorIds.add(cid);
            }
        }

        // 构造 movie 参数
        Map<String, Object> movieParams = new HashMap<>();
        movieParams.put("code", movieCode);
        movieParams.put("title", title);
        movieParams.put("name", title);
        movieParams.put("label", rate == null ? "" : ("评分:" + rate));
        movieParams.put("description", "来源: 豆瓣列表 API. URL: " + m.getString("url"));

        List<Map<String, Object>> covers = new ArrayList<>();
        if (pictureId != 0) {
            Map<String, Object> cover = new HashMap<>();
            cover.put("id", pictureId);
            covers.add(cover);
        }
        movieParams.put("covers", covers);

        List<Map<String, Object>> directorsList = new ArrayList<>();
        for (Integer id : directorIds) {
            Map<String, Object> d = new HashMap<>();
            d.put("id", id);
            directorsList.add(d);
        }
        movieParams.put("directors", directorsList);

        List<Map<String, Object>> actorsList = new ArrayList<>();
        for (Integer id : actorIds) {
            Map<String, Object> a = new HashMap<>();
            a.put("id", id);
            actorsList.add(a);
        }
        movieParams.put("actors", actorsList);

        movieParams.put("writers", new ArrayList<>());
        movieParams.put("cover_details", new ArrayList<>());
        movieParams.put("screenshots", new ArrayList<>());
        movieParams.put("resources", new ArrayList<>());

        MovieApiUtil.addMovie(movieParams);
        System.out.println("[DoubanMovieListJob] added: id=" + movieCode + " title=" + title);
    }

    /**
     * 按名字查/建影人，返回 id. 列表 API 没给影人豆瓣 ID/code，所以这里 code 不传.
     */
    private static int ensureCelebrity(String name) {
        if (name == null || name.isEmpty()) return 0;
        try {
            int existing = MovieApiUtil.isActorExist(null, name);
            if (existing != 0) return existing;
            Map<String, Object> params = new HashMap<>();
            params.put("name", name);
            params.put("search", 0);
            params.put("covers", new ArrayList<>());
            return MovieApiUtil.addActor(params);
        } catch (Exception e) {
            System.out.println("[DoubanMovieListJob] ensureCelebrity FAIL: "
                    + name + " " + e.getMessage());
            return 0;
        }
    }

    private static void log(String type, String detail) {
        try {
            Map<String, Object> logParams = new HashMap<>();
            logParams.put("log_id", "douban-" + type + "-" + TimeUtil.getTimeStr());
            logParams.put("level", "INFO");
            logParams.put("type", type);
            logParams.put("detail", detail);
            MovieApiUtil.addSystemLog(logParams);
        } catch (Exception e) {
            System.out.println("[DoubanMovieListJob] addSystemLog FAIL: " + e.getMessage());
        }
    }

    private static void sleepQuiet(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
    }
}
