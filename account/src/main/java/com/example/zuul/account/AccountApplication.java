package com.example.zuul.account;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@RestController
@EnableDiscoveryClient
@SpringBootApplication
public class AccountApplication {

	@Autowired
	private DiscoveryClient discoveryClient;

	@RequestMapping(value = "/account/{val}")
	public String available(@PathVariable String val) {
		String host = discoveryClient.getLocalServiceInstance().getHost();
		Integer port = discoveryClient.getLocalServiceInstance().getPort();
		System.out.println(host + ":" + port);
		return "My Account from:" + host + ":" + port + "  " + val;
	}


	public static void main(String[] args) {
		SpringApplication.run(AccountApplication.class, args);
	}


	@RestController
	class ServiceInstanceRestController {

		@Autowired
		private DiscoveryClient discoveryClient;

		@RequestMapping("/service-instances/{applicationName}")
		public List<ServiceInstance> serviceInstancesByApplicationName(@PathVariable String applicationName) {
			return this.discoveryClient.getInstances(applicationName);
		}

	}

	@Bean
	RestTemplate getRestTemplate() {
		return new RestTemplate();
	}


}
