package com.qishiyi.domain;
@SuppressWarnings("all")
public class CartItem {
	private String itemId;//CartItemId,��Ϊ��ɾ������item��ʱ����Ҫ����id��ɾ��
	private Product product; //����pid��ͼƬ����Ʒ���ơ��۸�
	private int buyNumber; //�������
	private double totalPrice; //�ܼ�
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
