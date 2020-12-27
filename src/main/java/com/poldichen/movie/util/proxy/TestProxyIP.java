package com.poldichen.movie.util.proxy;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;


/**
 * @author Jason
 * @date  Oct 27, 2010
 * @version 1.0
 */
public class TestProxyIP {

    public static void main(String[] args) {
        TestProxyIP testProxyIP = new TestProxyIP();
        for (int i = 0; i < 20; i++) {
            testProxyIP.getHtml("");
        }
    }

    private int count;

    public String getHtml(String url) {
        System.setProperty("http.maxRedirects", "50");
        System.getProperties().setProperty("proxySet", "true");
        // 如果不设置，只要代理IP和代理端口正确,此项不设置也可以
        String[] ipArray = new String[]{
                "93.91.200.146",
                "221.130.18.5",
                "221.130.23.135",
                "221.130.18.78",
                "221.130.23.134",
                "221.130.18.49",
                "111.1.32.36"};
        String ip = ipArray[count % ipArray.length];
        System.out.println(ip);
        System.getProperties().setProperty("http.proxyHost", ip);
        count ++;
        System.getProperties().setProperty("http.proxyPort", "80");

        return "";

        //确定代理是否设置成功
//        return fetch(url);
    }

    private String fetch(String address){
        StringBuffer html = new StringBuffer();
        String result = null;
        try{
            URL url = new URL(address);
            URLConnection conn = url.openConnection();
            conn.setRequestProperty("User-Agent","Mozilla/4.0 (compatible; MSIE 7.0; NT 5.1; GTB5; .NET CLR 2.0.50727; CIBA)");
            BufferedInputStream in = new BufferedInputStream(conn.getInputStream());

            try{
                String inputLine;
                byte[] buf = new byte[4096];
                int bytesRead = 0;
                while (bytesRead >= 0) {
                    inputLine = new String(buf, 0, bytesRead, "UTF-8");
                    html.append(inputLine);
                    bytesRead = in.read(buf);
                    inputLine = null;
                }
                buf = null;
            }finally{
                in.close();
                conn = null;
                url = null;
            }
            result = new String(html.toString().trim().getBytes("UTF-8"), "UTF-8").toLowerCase();

        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally{
            html = null;
        }
        return result;
    }
}
