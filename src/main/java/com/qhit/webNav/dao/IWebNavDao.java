package com.qhit.webNav.dao;

import org.apache.ibatis.annotations.Mapper;
import com.qhit.webNav.pojo.WebNav;
import java.util.List;

/** 
* Created by GeneratorCode on 2019/05/08
*/

@Mapper  
public interface IWebNavDao {

    boolean insert(Object object);

    boolean  update(Object object);

    boolean  updateSelective(Object object);

    boolean delete(Object object);

    List freeFind(String sql);

    List findAll();

    List findById(Object id);

    boolean freeUpdate(String sql);

    List<WebNav> search(WebNav webNav);

    List findByNname(Object nname);

    List findByUrl(Object url);

    List findByTarget(Object target);

    List findByType(Object type);

    List findBySort(Object sort);

    List findByStart(Object start);

}