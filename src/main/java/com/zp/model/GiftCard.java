package com.zp.model;

/**
 * 赠卡
 * 
 * @author zp
 * @date 2019年3月5日
 */
public class GiftCard {
	private String id;
	private double amount;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public GiftCard(String id, double amount) {
		this.id = id;
		this.amount = amount;
	}

	public GiftCard() {
	}

	@Override
	public String toString() {
		return "赠卡 [卡号=" + id + ", 金额=" + amount + "]";
	}
}
