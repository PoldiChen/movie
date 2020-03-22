package com.poldichen.movie.util.proxy;

import com.poldichen.movie.util.FetchUtil;
import com.poldichen.movie.util.MovieApiUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.Map;

/**
 * @author poldi.chen
 * @className ProxyAddressUtil
 * @description TODO
 * @date 2020/3/22 14:19
 **/
public class ProxyAddressUtil {

    public static void main(String[] args) throws Exception {
        getProxy();
    }

    public static void getProxy() throws Exception {
        Document doc = FetchUtil.getUrlDocHttp("https://www.xicidaili.com/nn/2");
        Element table = doc.selectFirst("table[id=\"ip_list\"]");
        Elements trs = table.select("tr");

        for (Element tr : trs) {

            String country = "";
            String locate = "";
            String protocol = "";
            String address = "";
            String port = "";
            String speed = "";
            String connectTime = "";
            String availableTime = "";
            String verifyTime = "";

            Elements tds = tr.select("td");
            if (tds.size() == 0) {
                continue;
            }
            Element tdCountry = tds.get(0);
            Element imgCountry = tdCountry.selectFirst("img");
            if (imgCountry != null) {
                country = imgCountry.attr("alt").toLowerCase();
            }

            Element tdAddress = tds.get(1);
            address = tdAddress.text();

            Element tdPort = tds.get(2);
            port = tdPort.text();

            Element tdLocate = tds.get(3);
            Element anchorLocate = tdLocate.selectFirst("a");
            if (anchorLocate != null) {
                locate = anchorLocate.text();
            }

            Element tdProtocol = tds.get(5);
            protocol = tdProtocol.text();

            Element tdSpeed = tds.get(6);
            Element divSpeed = tdSpeed.selectFirst("div");
            speed = divSpeed.attr("title");

            Element tdConnectTime = tds.get(7);
            Element divConnectTime = tdConnectTime.selectFirst("div");
            connectTime = divConnectTime.attr("title");

            Element tdAvailableTime = tds.get(8);
            availableTime = tdAvailableTime.text();

            Element tdVerifyTime = tds.get(9);
            verifyTime = "20" + tdVerifyTime.text();


            System.out.println(address);

            Map<String, Object> params = new HashMap<>();
            params.put("country", country);
            params.put("locate", locate);
            params.put("protocol", protocol);
            params.put("address", address);
            params.put("port", port);
            params.put("speed", speed);
            params.put("connect_time", connectTime);
            params.put("available_time", availableTime);
            params.put("verify_time", verifyTime + ":00");
            params.put("available", 1);

            System.out.println(params.toString());

            MovieApiUtil.addProxyAddress(params);






        }

    }
}
