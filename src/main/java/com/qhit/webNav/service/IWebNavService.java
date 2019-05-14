package com.qhit.webNav.service;

import java.util.List;
import com.qhit.webNav.pojo.WebNav;
/**
* Created by GeneratorCode on 2019/05/08
*/
public interface IWebNavService {

    boolean insert(Object object);

    boolean  update(Object object);

    boolean  updateSelective(Object object);

    boolean delete(Object id);

    List findAll();

    WebNav findById(Object id);

    List<WebNav> search(WebNav webNav);

}