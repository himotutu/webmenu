package com.qhit.webFun.service.impl;

import com.qhit.webFun.service.IWebFunService;
import java.util.List;
import com.qhit.webFun.dao.IWebFunDao;
import com.qhit.webFun.pojo.WebFun;
import org.springframework.stereotype.Service;
import javax.annotation.Resource; 

/**
* Created by GeneratorCode on 2019/05/08
*/

@Service 
public class WebFunServiceImpl  implements IWebFunService {

    @Resource 
    IWebFunDao dao;

    @Override 
    public boolean insert(Object object) { 
        return dao.insert(object); 
    } 

    @Override 
    public boolean update(Object object) { 
        return dao.update(object); 
    } 

    @Override 
    public boolean updateSelective(Object object) { 
        return dao.updateSelective(object); 
    } 

    @Override 
    public boolean delete(Object id) { 
        WebFun webFun = findById(id); 
        return dao.delete(webFun); 
    } 

    @Override 
    public List findAll() { 
        return dao.findAll(); 
    } 

    @Override 
    public WebFun findById(Object id) { 
        List<WebFun> list = dao.findById(id); 
        return  list.get(0); 
    } 

    @Override 
    public List<WebFun> search(WebFun webFun) { 
        return dao.search(webFun); 
    } 

}