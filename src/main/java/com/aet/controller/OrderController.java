package com.aet.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.aet.entity.Order;
import com.aet.model.OrderRequest;
import com.aet.product.client.ProductRestTemplate;
import com.aet.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/order")
@Log4j2
@Slf4j
public class OrderController {
	
	
	Logger logger=LoggerFactory.getLogger(OrderController.class);

	String URL = "http://localhost:8080/product/update/{productId}?quantity={quantity}";

	@Value("${url}")
	private String url;

	@Value("${updateURL}")
	private String updateURL;

	@Autowired
	private OrderService orderservice;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private KafkaTemplate<Integer, String> kafkaTemplate;

	@Autowired
	ObjectMapper objectMapper;

	String topic = "micro-topic";

	@PostMapping("/placeOrder")
	@Retry(name = "order-api", fallbackMethod = "orderFallback")
	public String placeOrder(@RequestBody OrderRequest orderRequest) throws Exception {

		long orderId = orderservice.placeOrder(orderRequest);

		Map<String, Long> uriVariables = new HashMap<>();

		uriVariables.put("productId", orderRequest.getProductId());
		uriVariables.put("quantity", orderRequest.getQuantity());

		restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(new HttpHeaders()), Void.class, uriVariables);

		return "order placed successfully";
	}

	public String orderFallback(Exception ex) {
		return "Service is down";
	}

	@GetMapping("/{id}")
	@ResponseBody
	public Order getOrderDetails(@PathVariable("id") long id) {

		Order order = orderservice.getOrderDetails(id);

		
		return order;

	}

	@GetMapping
	@ResponseBody
	public List<Order> getDetails() {

		List<Order> order = orderservice.getDetails();
logger.info("order details : "+order);
		
		return order;

	}
	

	@PutMapping("/cancelOrder/{id}")
	@CircuitBreaker(fallbackMethod = "orderCircutiFallback", name = "order-api")
	public String cancelOrder(@PathVariable("id") long id) {

		String status = orderservice.cancelOrder(id);
		Order order = orderservice.getOrderDetails(id);
		Map<String, Long> uriVariables = new HashMap<>();

		uriVariables.put("productId", order.getProductId());
		uriVariables.put("quantity", order.getQuantity());

		restTemplate.exchange(updateURL, HttpMethod.PUT, new HttpEntity<>(new HttpHeaders()), Void.class, uriVariables);
		return status;

	}
	
	


	public String orderCircutiFallback(Exception ex) {
		return "Service is not available";

	}

	/*
	 * public void produceMessage() { kafkaTemplate.sendDefault(1, "test");
	 * 
	 * }
	 */
	


}
