package com.poldichen.movie.util;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author poldi.chen
 * @className ActorFetchUtil
 * @description TODO
 * @date 2020/3/8 18:58
 **/
public class ActorFetchUtil {

    private static final String KEY_BIRTH_DATE = "生日: ";
    private static final String KEY_AGE = "年齡: ";
    private static final String KEY_HEIGHT = "身高: ";
    private static final String KEY_HOBBY = "愛好: ";
    private static final String KEY_BIRTH_PLACE = "出生地: ";

    public static void parseActorList(String actorListUrl) {
        Document doc;
        try {
            doc = FetchUtil.getUrlDoc(actorListUrl);
        } catch (Exception e) {
            Map<String, Object> systemLogParams = new HashMap<>();
            systemLogParams.put("log_id", actorListUrl);
            systemLogParams.put("type", "get_actor_list_doc_fail");
            systemLogParams.put("detail", e.getMessage());
            MovieApiUtil.addSystemLog(systemLogParams);
            return;
        }
        Element waterfall = doc.selectFirst("div[id=\"waterfall\"]");
        Elements items = waterfall.select("div[class=\"item\"]");
        for (Element actorElement : items) {
            String actorUrl = actorElement.selectFirst("a[class=\"avatar-box text-center\"]").attr("href");
            String actorCoverUrl = actorElement.selectFirst("img").attr("src");
            String actorName = actorElement.selectFirst("div[class=\"photo-info\"]").text();
            System.out.println(actorName);
            System.out.println(actorUrl);
            System.out.println(actorCoverUrl);
            System.out.println("===============================");
            parseActorDetail(actorName, actorUrl, actorCoverUrl);
        }
        Map<String, Object> systemLogParams = new HashMap<>();
        systemLogParams.put("log_id", actorListUrl);
        systemLogParams.put("type", "parse_actor_list_finish");
        systemLogParams.put("detail", "");
        MovieApiUtil.addSystemLog(systemLogParams);
    }

    public static void parseActorDetail(String actorName, String actorUrl, String actorCoverUrl) {
        int actorId = MovieApiUtil.isActorExist(actorName);

        // get actor detail document
        Document doc;
        try {
            doc = FetchUtil.getUrlDoc(actorUrl);
        } catch (Exception e) {
            Map<String, Object> systemLogParams = new HashMap<>();
            systemLogParams.put("log_id", actorName);
            systemLogParams.put("type", "get_actor_detail_doc_fail");
            systemLogParams.put("detail", e.getMessage());
            MovieApiUtil.addSystemLog(systemLogParams);
            return;
        }

        // update cover info
        String extension = actorCoverUrl.substring(actorCoverUrl.lastIndexOf(".") + 1);
        String pictureFileName = "actor_cover_" + actorName + "." + extension;
        Map<String, Object> params = new HashMap<>();
        params.put("path", "/");
        params.put("file_name", pictureFileName);
        params.put("extension", extension);
        params.put("url", actorCoverUrl);
        int pictureId = MovieApiUtil.addPicture(params);

        // get actor detail
        Element actorBox = doc.selectFirst("div[class=\"avatar-box\"]");
        Elements infos = actorBox.select("p");
        String age = "";
        String birthDate = "";
        String height = "";
        String hobby = "";
        String birthPlace = "";
        for (Element element : infos) {
            String text = element.text();
            if (text.indexOf(KEY_AGE) != -1) {
                age = text.substring(KEY_AGE.length());
            }
            if (text.indexOf(KEY_BIRTH_DATE) != -1) {
                birthDate = text.substring(KEY_BIRTH_DATE.length());
            }
            if (text.indexOf(KEY_BIRTH_PLACE) != -1) {
                birthPlace = text.substring(KEY_BIRTH_PLACE.length());
            }
            if (text.indexOf(KEY_HEIGHT) != -1) {
                height = text.substring(KEY_HEIGHT.length());
            }
            if (text.indexOf(KEY_HOBBY) != -1) {
                hobby = text.substring(KEY_HOBBY.length());
            }
        }
        System.out.println(age);
        System.out.println(birthDate);
        System.out.println(height);
        System.out.println(hobby);
        System.out.println(birthPlace);

        Map<String, Object> actorParams = new HashMap<>();
        actorParams.put("name", actorName);
        actorParams.put("age", age);
        actorParams.put("birth_date", birthDate);
        actorParams.put("height", height);
        actorParams.put("hobby", hobby);
        actorParams.put("birth_place", birthPlace);
        Map<String, Integer> mapPicture = new HashMap<>();
        mapPicture.put("id", pictureId);
        List<Map<String, Integer>> covers = new ArrayList<>();
        covers.add(mapPicture);
        actorParams.put("covers", covers);

        if (actorId == 0) {
            MovieApiUtil.addActor(actorParams);
        } else {
            MovieApiUtil.updateActor(actorId, actorParams);
        }

    }
}
