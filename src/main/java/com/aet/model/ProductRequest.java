package com.aet.model;

public class ProductRequest {
	
	private String productName;
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	private long price;
	private long quantity;
	
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
	public long getQuantity() {
		return quantity;
	}
	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}
	@Override
	public String toString() {
		return "ProductRequest [productName=" + productName + ", price=" + price + ", quantity=" + quantity + "]";
	}
	
	

}
