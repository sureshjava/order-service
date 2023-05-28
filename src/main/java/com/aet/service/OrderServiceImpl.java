package com.aet.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.aet.entity.Order;
import com.aet.entity.Product;
import com.aet.model.OrderRequest;
import com.aet.model.ProductRequest;
import com.aet.repository.OrderMongoRepository;
import com.aet.repository.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService{
	
	
	/*
	 * @Autowired private OrderRepository orderRepository;
	 */
	
	@Autowired
	private OrderMongoRepository orderMongoRepository;

	@Override
	public long placeOrder(OrderRequest orderRequest) throws Exception {
		// TODO Auto-generated method stub
		
		Order order =new Order();
		Random ran=new Random();
		int value=ran.nextInt(10, 1000);
		order.setAmount(orderRequest.getTotalAmount());
		order.setOrderDate(Instant.now());
		order.setOrderStatus("CREATED");
		order.setProductId(orderRequest.getProductId());
		order.setQuantity(orderRequest.getQuantity());
		order.setId(value);
		
		System.out.println("value : "+value);
		orderMongoRepository.save(order);
	//	orderRepository.save(order);
		return order.getId();
	}
	
	@Override
	public Order getOrderDetails(long id) {
		
		Optional<Order> order=orderMongoRepository.findById(id);
		order.get();
		return order.get();
		
	}

	@Override
	public String cancelOrder(long id) {
		
		//Order order =new Order();
		Optional<Order> order=orderMongoRepository.findById(id);
		Order replaceOrder=order.get();
		replaceOrder.setOrderStatus("CANCELLED");
		
		orderMongoRepository.save(replaceOrder);
		
		// TODO Auto-generated method stub
		return "successfully updated";
	}

	@Override
	public List<Order> getDetails() {
		// TODO Auto-generated method stub
		List<Order>list=orderMongoRepository.findAll();
		return list;
	}
	@KafkaListener(topics = "product-topic", groupId = "group-id")
public void consume(Product message){
System.out.println("message received : "+message.toString());
}


}
