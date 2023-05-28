package com.aet.service;

import java.util.List;

import com.aet.entity.Order;
import com.aet.model.OrderRequest;

public interface OrderService {

	long placeOrder(OrderRequest orderRequest)throws Exception;
	Order getOrderDetails(long id);
	String cancelOrder(long id);
	List<Order> getDetails();

}
