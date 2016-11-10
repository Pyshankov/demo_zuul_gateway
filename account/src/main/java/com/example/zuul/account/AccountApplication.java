package com.example.zuul.account;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
public class AccountApplication {

	@Autowired
	private DiscoveryClient discoveryClient;

	@Autowired
	private  BookClient bookClient;

	@RequestMapping(value = "server/{id}")
	public List<ServiceInstance> availableServers(@PathVariable String id) {
		return discoveryClient.getInstances(id);
	}

	@RequestMapping(value = "/account/{val}")
	public String available(@PathVariable String val) {
		String s =  discoveryClient.getLocalServiceInstance().getHost() ;
		Integer s2 =  discoveryClient.getLocalServiceInstance().getPort();
		System.out.println(s +":"+ s2);
		return "My Account from:"+ s +":"+ s2 + "  "+val;
	}

	@RequestMapping(value = "/account")
	public String available() {
		return bookClient.available();
	}


	public static void main(String[] args) {
		SpringApplication.run(AccountApplication.class, args);
	}

	@FeignClient(serviceId = "book")
	public interface BookClient{

		@RequestMapping(method = RequestMethod.GET,value = "/available")
		String available();
	}


	@RestController
	class ServiceInstanceRestController {

		@Autowired
		private DiscoveryClient discoveryClient;

		@RequestMapping("/service-instances/{applicationName}")
		public List<ServiceInstance> serviceInstancesByApplicationName(
				@PathVariable String applicationName) {
			return this.discoveryClient.getInstances(applicationName);
		}

	}
	@Bean
	RestTemplate getRestTemplate(){
		return new RestTemplate();
	}


}
