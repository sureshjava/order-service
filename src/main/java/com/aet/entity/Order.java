package com.aet.entity;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/*@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="ORDER_DETAILS")*/
@Document
public class Order {

	
	/*
	 * @Id
	 * 
	 * @GeneratedValue(strategy = GenerationType.AUTO)
	 */
	@Id
	private long id;
	
	//@Column(name="PRODUCT_ID")
	private long productId;
	
	//@Column(name="QUANTITY")
	private long quantity;
	
	//@Column(name="ORDER_DATE")
	private Instant orderDate;
	
	//@Column(name="STATUS")
	private String orderStatus;
	
	//@Column(name="TOTAL_AMOUNT")
	private long amount;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	public Instant getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Instant orderDate) {
		this.orderDate = orderDate;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}
	
	
}
