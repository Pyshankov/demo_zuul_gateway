package com.example.zuul.gateway;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@EnableZuulProxy
@SpringBootApplication
public class DemoApplication {

	@Autowired
	private DiscoveryClient discoveryClient;

	@RequestMapping(value = "server/{id}")
	public List<ServiceInstance> availableServers(@PathVariable String id) {
		return discoveryClient.getInstances(id);
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	IRule iRule(){
		return new RandomRule();
	}

}
