package com.entity;

import java.io.Serializable;

public class CartItem implements Serializable {
	private FoodBean foodBean;
	private String number;

	public CartItem() {
		super();
		foodBean = new FoodBean();
	}
	
	public CartItem(FoodBean foodBean, String number) {
		super();
		this.foodBean = foodBean;
		this.number = number;
	}

	public FoodBean getFoodBean() {
		return foodBean;
	}

	public void setFoodBean(FoodBean foodBean) {
		this.foodBean = foodBean;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

}
