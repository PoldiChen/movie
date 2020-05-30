package com.poldichen.movie.job;

/**
 * @author poldi.chen
 * @className CelebrityJob
 * @description TODO
 * @date 2020/5/25 23:04
 **/

@JobAnnotation
public class CelebrityJob {

    public void executeDeclare(String param1, String param2) {}

    public static void execute(String args) {
        System.out.println("CelebrityJob@execute");
        System.out.println(args);
    }
}
