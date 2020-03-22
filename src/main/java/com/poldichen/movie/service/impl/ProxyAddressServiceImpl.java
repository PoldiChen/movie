package com.poldichen.movie.service.impl;

import com.poldichen.movie.dao.IProxyAddressDao;
import com.poldichen.movie.dao.IResourceDao;
import com.poldichen.movie.entity.ProxyAddress;
import com.poldichen.movie.entity.Resource;
import com.poldichen.movie.service.inter.IProxyAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author poldi.chen
 * @className ProxyAddressServiceImpl
 * @description TODO
 * @date 2020/3/22 14:13
 **/
@Service
public class ProxyAddressServiceImpl implements IProxyAddressService {

    @Autowired
    private IProxyAddressDao proxyAddressDao;

    @Override
    public int createOne(ProxyAddress proxyAddress) {
        proxyAddressDao.createOne(proxyAddress);
        return proxyAddress.getId();
    }

    @Override
    public int update(int id, ProxyAddress proxyAddress) {
        return proxyAddressDao.update(id, proxyAddress);
    }

    @Override
    public List<ProxyAddress> getAll() {
        return proxyAddressDao.getAll();
    }
}
