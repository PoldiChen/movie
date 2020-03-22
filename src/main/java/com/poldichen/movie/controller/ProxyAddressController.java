package com.poldichen.movie.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.poldichen.movie.entity.ProxyAddress;
import com.poldichen.movie.entity.Resp;
import com.poldichen.movie.service.inter.IProxyAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author poldi.chen
 * @className ProxyAddressController
 * @description TODO
 * @date 2020/3/22 14:15
 **/
@RestController
public class ProxyAddressController {

    @Autowired
    private IProxyAddressService proxyAddressService;

    @RequestMapping(value = "/proxy_address", method = RequestMethod.POST)
    public Resp createOne(@RequestBody String proxyAddressStr) {
        Resp resp = new Resp();
        ProxyAddress proxyAddress = JSON.parseObject(proxyAddressStr, new TypeReference<ProxyAddress>(){});
        int proxyAddressId = proxyAddressService.createOne(proxyAddress);
        resp.setData(proxyAddressId);
        return resp;
    }

    @RequestMapping(value = "/proxy_address/{id}", method = RequestMethod.PUT)
    public Resp update(@PathVariable int id, @RequestBody String proxyAddressStr) {
        Resp resp = new Resp();
        ProxyAddress proxyAddress = JSON.parseObject(proxyAddressStr, new TypeReference<ProxyAddress>(){});
        int result = proxyAddressService.update(id, proxyAddress);
        resp.setData(result);
        return resp;
    }

    @RequestMapping(value = "/proxy_address", method = RequestMethod.GET)
    public Resp getAll() {
        Resp resp = new Resp();
        List<ProxyAddress> proxyAddresses = proxyAddressService.getAll();
        resp.setData(proxyAddresses);
        return resp;
    }
}
