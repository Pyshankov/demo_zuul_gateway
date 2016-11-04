package com.example.zuul.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Created by pyshankov on 01.11.16.
 */
//@Component
public class SimplePreFilter extends ZuulFilter {

    @Autowired
    private DiscoveryClient discoveryClient;

    private RestTemplate restTemplate;


    @Override
    public String filterType() {
        return "post";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        System.out.println(ctx);
        System.out.println(new Date());
        Random random = new Random();
        List<ServiceInstance> servers = discoveryClient.getInstances("account");
        ServiceInstance instance = servers.get((random.nextInt() % servers.size()) + servers.size() % servers.size());
//        System.out.println(instance);

        System.out.println(String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));

        return null;
    }
}
