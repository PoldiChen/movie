package com.poldichen.movie.job;

import com.poldichen.movie.entity.Picture;
import com.poldichen.movie.util.MovieApiUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author poldi.chen
 * @className PictureJob
 * @description TODO
 * @date 2020/4/18 10:57
 **/
@JobAnnotation
public class PictureJob {

    public static void main(String[] args) {
        String dic = "D:\\@git\\movie\\pictures";
        String keyWord = "cover";
        executeDownloadPicture(dic, keyWord);
    }

    public void executeDeclare(String args) {}

    /**
     * 单参数版本，兼容 JobController 反射调用（只传 job_params 一个字符串）.
     * 参数格式：逗号分隔 "directory,keyWord"，缺省时用 D:\@git\movie\pictures 和 cover
     * 例如: "D:\@git\movie\pictures,cover" 或 "" 用默认值
     */
    public void execute(String args) {
        String directory = "D:\\@git\\movie\\pictures";
        String keyWord = "cover";
        if (args != null && !args.isEmpty()) {
            String[] parts = args.split(",", 2);
            if (parts.length > 0 && !parts[0].isEmpty()) directory = parts[0];
            if (parts.length > 1 && !parts[1].isEmpty()) keyWord = parts[1];
        }
        // 刷新本机 API token（后端重启后 MovieApiUtil.auth 静态字段为 null）
        MovieApiUtil.getAuth("jack", "123456");
        executeDownloadPicture(directory, keyWord);
    }

    public static void executeDownloadPicture(String directory, String keyWord) {
        new Thread(() -> {
            List<Picture> pictures = MovieApiUtil.getPicture(keyWord);
            File file = new File(directory);
            String[] fileNameArr = file.list();
            List<String> fileNames = (fileNameArr == null)
                    ? new ArrayList<>() : Arrays.asList(fileNameArr);
            for (Picture picture : pictures) {
                String fileName = picture.getFileName();
                if (fileNames.contains(fileName)) {
                    System.out.println(fileName);
                    System.out.println("continue");
                    continue;
                }
                downloadImage2(directory, picture.getUrl(), picture.getFileName());
            }
        }).start();
        Map<String, Object> systemLogParams = new HashMap<>();
        systemLogParams.put("log_id", "job-picture-download");
        systemLogParams.put("type", "download_picture");
        systemLogParams.put("level", "INFO");
        systemLogParams.put("detail", "");
        MovieApiUtil.addSystemLog(systemLogParams);
    }

    public static void downloadImage2(String directory, String imageUrl, String fileName) {
        System.out.println(imageUrl);
        HttpURLConnection conn = null;
        InputStream inputStream = null;
        BufferedInputStream bis = null;
        FileOutputStream out = null;
        try {
            File file0 = new File(directory);
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
            // 注意: 不要 setDoOutput(true)，否则 GET 请求会被当作带 body 的请求，导致响应内容为空
            conn.setConnectTimeout(1000 * 30);
            conn.setReadTimeout(1000 * 30);
            conn.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 "
                            + "(KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");
            conn.setRequestProperty("Referer", "https://movie.douban.com/");
            conn.setRequestProperty("Accept", "image/webp,image/apng,image/*,*/*;q=0.8");
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
            systemLogParams.put("level", "ERROR");
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

    public static void downloadImage(String imageUrl, String fileName) {
        fileName = "D:\\@git\\movie\\pictures\\" + fileName;
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
}
