package com.poldichen.movie.dao;

import com.poldichen.movie.entity.Picture;
import com.poldichen.movie.entity.ProxyAddress;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IProxyAddressDao {

    int createOne(@Param("proxyAddress") ProxyAddress proxyAddress);

    int update(@Param("id") int id, @Param("proxyAddress") ProxyAddress proxyAddress);

    List<ProxyAddress> getAll();
}
