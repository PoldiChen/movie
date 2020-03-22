package com.poldichen.movie.util.proxy;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.URL;
import java.util.Random;

/**
 * @author poldi.chen
 * @className ProxyUtil
 * @description TODO
 * @date 2020/3/15 15:53
 **/
public class ProxyUtil {

    public static void main(String args[]) throws Exception {
        test();
    }

    public static void test2() throws Exception {
        // 要访问的目标页面
        String targetUrl = "https://movie.douban.com/subject/26348103/";

        // 代理服务器
        String proxyServer = "123.171.5.133";
        int proxyPort = 8118;

        // 代理隧道验证信息
        String proxyUser  = "username";
        String proxyPass  = "password";

        try {
            URL url = new URL(targetUrl);
            Authenticator.setDefault(new ProxyAuthenticator(proxyUser, proxyPass));
            // 创建代理服务器地址对象
            InetSocketAddress addr = new InetSocketAddress(proxyServer, proxyPort);
            // 创建HTTP类型代理对象
            Proxy proxy = new Proxy(Proxy.Type.HTTP, addr);
            // 设置通过代理访问目标页面
            HttpURLConnection connection = (HttpURLConnection) url.openConnection(proxy);
            // 设置Proxy-Tunnel
            // Random random = new Random();
            // int tunnel = random.nextInt(10000);
            // connection.setRequestProperty("Proxy-Tunnel",String.valueOf(tunnel));

            // 解析返回数据
            byte[] response = readStream(connection.getInputStream());

            System.out.println(new String(response));
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    /**
     * 将输入流转换成字符串
     *
     * @param inStream
     * @return
     * @throws Exception
     */
    public static byte[] readStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;

        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        outSteam.close();
        inStream.close();

        return outSteam.toByteArray();
    }

    public static void test() throws Exception {

        //        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("171.221.79.106", 8118));
//        HttpURLConnection connection = (HttpURLConnection) new URL("https://movie.douban.com/subject/26348103").openConnection(proxy);
//        connection.setConnectTimeout(6000); // 6s
//        connection.setReadTimeout(6000);
//        connection.setUseCaches(false);
//        if(connection.getResponseCode() == 200){
//            System.out.println("使用代理IP连接网络成功");
//        }

        System.getProperties().setProperty("proxySet", "true");
        System.getProperties().setProperty("http.proxyHost", "123.171.5.133");
        System.getProperties().setProperty("http.proxyPort", "8118");

        HttpURLConnection connection = (HttpURLConnection)new URL("https://movie.douban.com/subject/26348103").openConnection();
        connection.setConnectTimeout(6000); // 6s
        connection.setReadTimeout(6000);
        connection.setUseCaches(false);

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
        String response = "";
        String line;
        while ((line = reader.readLine()) != null) {
            response += line + "\n";
        }
        System.out.println(response);


        connection.getInputStream();
        System.out.println(connection.getResponseCode());
        System.out.println(connection.getResponseMessage());
    }


}
