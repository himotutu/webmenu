package com.qhit.webNav.pojo;


/** 
* Created by GeneratorCode on 2019/05/08
*/ 

public class WebNav { 
    private Integer nid;    //主键 
    private String nname;    //名称 
    private String url;    //URL 
    private Integer target;    //跳转（1新窗口2本窗口） 
    private Integer type;    //类型（1头部2尾部） 
    private Integer sort;    //排序 
    private Integer start;    //状态（1启用2禁用） 

    public Integer getNid() { 
        return nid;
    }

    public void setNid(Integer nid) { 
        this.nid = nid;
    } 

    public String getNname() { 
        return nname;
    }

    public void setNname(String nname) { 
        this.nname = nname;
    }
    public String getUrl() { 
        return url;
    }

    public void setUrl(String url) { 
        this.url = url;
    }
    public Integer getTarget() { 
        return target;
    }

    public void setTarget(Integer target) { 
        this.target = target;
    } 

    public Integer getType() { 
        return type;
    }

    public void setType(Integer type) { 
        this.type = type;
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