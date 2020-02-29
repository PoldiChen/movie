package com.poldichen.movie.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author poldi.chen
 * @className HtmlUtil
 * @description TODO
 * @date 2020/2/29 14:41
 **/
public class HtmlUtil {
    protected List<List<String>> data = new LinkedList<List<String>>();

    /**
     * 获取value值
     *
     * @param e
     * @return
     */
    public static String getValue(Element e) {
        return e.attr("value");
    }

    /**
     * 获取<tr>和</tr>之间的文本
     *
     * @param e
     * @return
     */
    public static String getText(Element e) {
        return e.text();
    }

    /**
     * 识别属性id的标签,一般一个html页面id唯一
     *
     * @param body
     * @param id
     * @return
     */
    public static Element getID(String body, String id) {
        Document doc = Jsoup.parse(body);
        // 所有#id的标签
        Elements elements = doc.select("#" + id);
        // 返回第一个
        return elements.first();
    }

    /**
     * 识别属性class的标签
     *
     * @param body
     * @param
     * @return
     */
    public static Elements getClassTag(String body, String classTag) {
        Document doc = Jsoup.parse(body);
        // 所有#id的标签
        return doc.select("." + classTag);
    }

    /**
     * 获取tr标签元素组
     *
     * @param e
     * @return
     */
    public static Elements getTR(Element e) {
        return e.getElementsByTag("tr");
    }

    /**
     * 获取td标签元素组
     *
     * @param e
     * @return
     */
    public static Elements getTD(Element e) {
        return e.getElementsByTag("td");
    }
    /**
     * 获取表元组
     * @param table
     * @return
     */
    public static List<List<String>> getTables(Element table){
        List<List<String>> data = new ArrayList<>();

        for (Element etr : table.select("tr")) {
            List<String> list = new ArrayList<>();
            for (Element etd : etr.select("td")) {
                String temp = etd.text();
                //增加一行中的一列
                list.add(temp);
            }
            //增加一行
            data.add(list);
        }
        return data;
    }
    /**
     * 读html文件
     * @param fileName
     * @return
     */
    public static String readHtml(String fileName){
        FileInputStream fis = null;
        StringBuffer sb = new StringBuffer();
        try {
            fis = new FileInputStream(fileName);
            byte[] bytes = new byte[1024];
            while (-1 != fis.read(bytes)) {
                sb.append(new String(bytes));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return sb.toString();
    }
    public static void main(String[] args) {
        // String url = "http://www.baidu.com";
        // String body = HtmlBody.getBody(url);
        // System.out.println(body);

        String auth = "marker-eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqYWNrIiwiZXhwIjoxNTgyOTYzNDkwfQ.VaQ2ir2oaF1KRl4PsrWboFJpEhwnlmOBgdL1z4ccdaaxvBDEzNWnkR40FkIOCAmYBM8kTWsOvKT21H_b4hkrVQ";

        Document document = Jsoup.parse(readHtml("E:\\movie\\书签_2020_2_29.html"));
        Element element = document.select("DT").first();
        Elements dts = element.getElementsByTag("DL").first().getElementsByTag("DT");
        System.out.println(dts.size());
        for (Element element1 : dts) {
            String text = element1.text();
            if (text.contains("test")) {
                System.out.println(text);
                int index = text.indexOf("test");
                if (index != -1) {
                    String actorName = text.substring(0, index);
                    System.out.println(actorName);
                    Map<String, Object> params = new TreeMap<>();
                    params.put("name", actorName);
                    params.put("search", 0);
                    String result = HttpClientUtil.doPost("http://localhost:8080/actor", auth, params);
                    System.out.println(result);
                }
            }
        }
    }

}
