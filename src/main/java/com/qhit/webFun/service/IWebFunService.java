package com.qhit.webFun.service;

import java.util.List;
import com.qhit.webFun.pojo.WebFun;
/**
* Created by GeneratorCode on 2019/05/08
*/
public interface IWebFunService {

    boolean insert(Object object);

    boolean  update(Object object);

    boolean  updateSelective(Object object);

    boolean delete(Object id);

    List findAll();

    WebFun findById(Object id);

    List<WebFun> search(WebFun webFun);

}