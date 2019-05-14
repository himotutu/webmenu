package com.qhit.webNav.service.impl;

import com.qhit.webNav.service.IWebNavService;
import java.util.List;
import com.qhit.webNav.dao.IWebNavDao;
import com.qhit.webNav.pojo.WebNav;
import org.springframework.stereotype.Service;
import javax.annotation.Resource; 

/**
* Created by GeneratorCode on 2019/05/08
*/

@Service 
public class WebNavServiceImpl  implements IWebNavService {

    @Resource 
    IWebNavDao dao;

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
        WebNav webNav = findById(id); 
        return dao.delete(webNav); 
    } 

    @Override 
    public List findAll() { 
        return dao.findAll(); 
    } 

    @Override 
    public WebNav findById(Object id) { 
        List<WebNav> list = dao.findById(id); 
        return  list.get(0); 
    } 

    @Override 
    public List<WebNav> search(WebNav webNav) { 
        return dao.search(webNav); 
    } 

}