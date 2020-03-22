package com.poldichen.movie.service.inter;

import com.poldichen.movie.entity.ProxyAddress;
import com.poldichen.movie.entity.Resource;

import java.util.List;

public interface IProxyAddressService {

    int createOne(ProxyAddress proxyAddress);

    int update(int id, ProxyAddress proxyAddress);

    List<ProxyAddress> getAll();
}
