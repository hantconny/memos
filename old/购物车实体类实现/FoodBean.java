package com.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class FoodBean implements Serializable {
	private String foodId;
	private String foodName;
	private String remark;
	private String foodPrice;
	private String foodImage;
	private String description;
	private String orderCount;
	private String viewCount;

	public FoodBean() {
	}

	public FoodBean(String foodId, String foodName, String remark,
			String foodPrice, String foodImage, String description,
			String orderCount, String viewCount) {
		this.foodId = foodId;
		this.foodName = foodName;
		this.remark = remark;
		this.foodPrice = foodPrice;
		this.foodImage = foodImage;
		this.description = description;
		this.orderCount = orderCount;
		this.viewCount = viewCount;
	}

	public String getFoodId() {
		return foodId;
	}

	public void setFoodId(String foodId) {
		this.foodId = foodId;
	}

	public String getFoodName() {
		return foodName;
	}

	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getFoodPrice() {
		return foodPrice;
	}

	public void setFoodPrice(String foodPrice) {
		this.foodPrice = foodPrice;
	}

	public String getFoodImage() {
		return foodImage;
	}

	public void setFoodImage(String foodImage) {
		this.foodImage = foodImage;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(String orderCount) {
		this.orderCount = orderCount;
	}

	public String getViewCount() {
		return viewCount;
	}

	public void setViewCount(String viewCount) {
		this.viewCount = viewCount;
	}

}
