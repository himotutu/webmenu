package com.qhit.webNav.controller; 

import com.qhit.webNav.pojo.WebNav; 
import com.qhit.webNav.service.IWebNavService; 
import org.springframework.web.bind.annotation.RequestMapping; 
import javax.annotation.Resource; 
import java.util.List; 
import org.springframework.web.bind.annotation.RestController; 

/** 
* Created by GeneratorCode on 2019/05/08
*/ 

@RestController 
@RequestMapping("/webNav") 
public class WebNavController { 

    @Resource 
    IWebNavService webNavService; 

    @RequestMapping("/insert") 
    public void insert(WebNav webNav) { 
        webNavService.insert(webNav); 
    } 

    @RequestMapping("/delete") 
    public void delete(Integer nid) { 
        webNavService.delete(nid); 
    } 

    @RequestMapping("/update") 
    public void update(WebNav webNav) { 
        webNavService.update(webNav); 
    } 

    @RequestMapping("/updateSelective") 
    public void updateSelective(WebNav webNav) { 
        webNavService.updateSelective(webNav); 
    } 

    @RequestMapping("/load") 
    public WebNav load(Integer nid) { 
        WebNav webNav = webNavService.findById(nid); 
        return webNav; 
    } 

    @RequestMapping("/list") 
    public List<WebNav> list()  { 
        List<WebNav> list = webNavService.findAll(); 
        return list; 
    } 

    @RequestMapping("/search") 
    public List<WebNav> search(WebNav webNav) { 
        List<WebNav> list = webNavService.search(webNav); 
        return list; 
    } 

} 
