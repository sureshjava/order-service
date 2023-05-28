package com.aet.product.client;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ProductRestTemplate {

	
	
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	

	
}
