package com.qhit.webFun.dao;

import org.apache.ibatis.annotations.Mapper;
import com.qhit.webFun.pojo.WebFun;
import java.util.List;

/** 
* Created by GeneratorCode on 2019/05/08
*/

@Mapper  
public interface IWebFunDao {

    boolean insert(Object object);

    boolean  update(Object object);

    boolean  updateSelective(Object object);

    boolean delete(Object object);

    List freeFind(String sql);

    List findAll();

    List findById(Object id);

    boolean freeUpdate(String sql);

    List<WebFun> search(WebFun webFun);

    List findByFname(Object fname);

    List findByUrl(Object url);

    List findBySort(Object sort);

    List findByStart(Object start);

}