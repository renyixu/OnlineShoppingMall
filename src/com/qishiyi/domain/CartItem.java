package com.qishiyi.domain;
@SuppressWarnings("all")
public class CartItem {
	private String itemId;//CartItemId,因为在删除单个item的时候需要根据id来删除
	private Product product; //包含pid、图片、商品名称、价格
	private int buyNumber; //买的数量
	private double totalPrice; //总价
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getBuyNumber() {
		return buyNumber;
	}
	public void setBuyNumber(int buyNumber) {
		this.buyNumber = buyNumber;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

}
