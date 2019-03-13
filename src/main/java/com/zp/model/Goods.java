package com.zp.model;

/**
 * 商品类
 * 
 * @author zp
 * @date 2019年3月5日
 */
public class Goods {
	private String name;
	private double price;
	private int num;
	private boolean supportGiftCard;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public boolean isSupportGiftCard() {
		return supportGiftCard;
	}

	public void setSupportGiftCard(boolean supportGiftCard) {
		this.supportGiftCard = supportGiftCard;
	}

	public Goods() {

	}

	public Goods(String name, double price, int num, boolean supportGiftCard) {
		this.name = name;
		this.price = price;
		this.num = num;
		this.supportGiftCard = supportGiftCard;
	}
}
