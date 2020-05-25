package com.poldichen.movie.job;

/**
 * @author poldi.chen
 * @className CelebrityJob
 * @description TODO
 * @date 2020/5/25 23:04
 **/

@JobAnnotation
public class CelebrityJob {

    public static void execute(String param1, String param2) {
        System.out.println("CelebrityJob@execute");
        System.out.println(param1);
        System.out.println(param2);
    }
}
