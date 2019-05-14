package com.qhit.webFun.controller; 

import com.qhit.webFun.pojo.WebFun; 
import com.qhit.webFun.service.IWebFunService; 
import org.springframework.web.bind.annotation.RequestMapping; 
import javax.annotation.Resource; 
import java.util.List; 
import org.springframework.web.bind.annotation.RestController; 

/** 
* Created by GeneratorCode on 2019/05/08
*/ 

@RestController 
@RequestMapping("/webFun") 
public class WebFunController { 

    @Resource 
    IWebFunService webFunService; 

    @RequestMapping("/insert") 
    public void insert(WebFun webFun) { 
        webFunService.insert(webFun); 
    } 

    @RequestMapping("/delete") 
    public void delete(Integer fid) { 
        webFunService.delete(fid); 
    } 

    @RequestMapping("/update") 
    public void update(WebFun webFun) { 
        webFunService.update(webFun); 
    } 

    @RequestMapping("/updateSelective") 
    public void updateSelective(WebFun webFun) { 
        webFunService.updateSelective(webFun); 
    } 

    @RequestMapping("/load") 
    public WebFun load(Integer fid) { 
        WebFun webFun = webFunService.findById(fid); 
        return webFun; 
    } 

    @RequestMapping("/list") 
    public List<WebFun> list()  { 
        List<WebFun> list = webFunService.findAll(); 
        return list; 
    } 

    @RequestMapping("/search") 
    public List<WebFun> search(WebFun webFun) { 
        List<WebFun> list = webFunService.search(webFun); 
        return list; 
    } 

} 
