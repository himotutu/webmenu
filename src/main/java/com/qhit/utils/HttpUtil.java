package com.qhit.utils;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by lenovo on 2019/5/6.
 */
@Component
public class HttpUtil {

    @Resource
    LoadBalancerClient loadBalancerClient;

    public Object getForList(String servername, String mapping, Class clazz, Map map){
        ServiceInstance instance = loadBalancerClient.choose(servername);
        String url = "http://"+instance.getHost()+":"+instance.getPort()+"/"+mapping+"?1=1";
        for (Object key:map.keySet()){
            url += "&"+key+"="+map.get(key);
        }
        RestTemplate restTemplate = new RestTemplate();
//        restTemplate.get
        return restTemplate.getForObject(url,clazz,map);
    }
}
