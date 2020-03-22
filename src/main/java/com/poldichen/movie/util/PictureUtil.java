package com.poldichen.movie.util;

import com.poldichen.movie.entity.Picture;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author poldi.chen
 * @className PictureUtil
 * @description TODO
 * @date 2020/3/10 23:10
 **/
public class PictureUtil {

    private static final String PICTURE_DIR = "E:\\movie\\picture_douban";

    public static void main(String[] args) {
        downloadImageBatch();
    }

    public static void downloadImageBatch() {
        List<Picture> pictures = MovieApiUtil.getPicture("cover");
        for (Picture picture : pictures) {
            downloadImage2(picture.getUrl(), picture.getFileName());
        }
    }

    public static void downloadImage(String imageUrl, String fileName) {
        fileName = "E:\\movie\\picture\\" + fileName;
        String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);
        try {
            URL url = new URL(imageUrl);
            final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(1000 * 30);
            connection.setReadTimeout(1000 * 30);
            connection.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_5) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");
            final BufferedImage image = ImageIO.read(connection.getInputStream());
            ImageIO.write(image, formatName, new File(fileName));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void downloadImage2(String imageUrl, String fileName) {
        System.out.println(imageUrl);
        HttpURLConnection conn = null;
        InputStream inputStream = null;
        BufferedInputStream bis = null;
        FileOutputStream out = null;
        try {
            File file0 = new File(PICTURE_DIR);
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
            conn.setConnectTimeout(1000 * 30);
            conn.setReadTimeout(1000 * 30);
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
            System.out.println("finish...");
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> systemLogParams = new HashMap<>();
            systemLogParams.put("log_id", fileName);
            systemLogParams.put("type", "download_picture_fail");
            systemLogParams.put("detail", e.getMessage());
            MovieApiUtil.addSystemLog(systemLogParams);
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
}
