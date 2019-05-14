package com.qhit.webFun.pojo;


/** 
* Created by GeneratorCode on 2019/05/08
*/ 

public class WebFun { 
    private Integer fid;    //主键 
    private String fname;    //名称 
    private String url;    //路径 
    private Integer sort;    //排序 
    private Integer start;    //状态 

    public Integer getFid() { 
        return fid;
    }

    public void setFid(Integer fid) { 
        this.fid = fid;
    } 

    public String getFname() { 
        return fname;
    }

    public void setFname(String fname) { 
        this.fname = fname;
    }
    public String getUrl() { 
        return url;
    }

    public void setUrl(String url) { 
        this.url = url;
    }
    public Integer getSort() { 
        return sort;
    }

    public void setSort(Integer sort) { 
        this.sort = sort;
    } 

    public Integer getStart() { 
        return start;
    }

    public void setStart(Integer start) { 
        this.start = start;
    } 


 }