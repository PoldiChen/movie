package com.poldichen.movie.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author poldi.chen
 * @className FileUtil
 * @description TODO
 * @date 2020/2/23 14:36
 **/
public class FileUtil {

    public static void main(String[] args) {
        initMovieName();
    }

    public static void initMovieName() {
        String path = "F:\\新建文件夹 (2)\\2.storage";
        File pathFile = new File(path);
        File[] files = pathFile.listFiles();
        System.out.println(files.length);
        for (File file : files) {
            String fileName = file.getName();
            if (fileName.contains(".")) {
                fileName = fileName.substring(0, fileName.indexOf("."));
            }
            if (fileName.contains("--")) {
                fileName = fileName.substring(fileName.indexOf("--") + 2);
            }

            Map<String, String> params = new HashMap<>();
            params.put("name", fileName);
            params.put("publish_date", "2020-02-23");
            HttpClientUtil.doPost("http://114.67.87.197:8080/movie", "marker-eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqYWNrIiwiZXhwIjoxNTgyNDQ0NjczfQ.iguY3PZsBuMKSBY6ZEDMhTIixilK8KGiPPaTa-SdmIAlawPAC2-mimgVOLHEi3xeYiqFBRDQtp7oz_HOkrI37Q",
                    params);

//            System.out.println(fileName);
        }
    }
}
