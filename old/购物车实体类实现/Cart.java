package com.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Cart implements Serializable {
	private Map<String, CartItem> items;
	
	public Map<String, CartItem> getItems() {
		return items;
	}

	public void setItems(Map<String, CartItem> items) {
		this.items = items;
	}

	public Cart() {
		super();
		items = new HashMap<String, CartItem>();
	}
	
	public void addItem(FoodBean foodBean, String number){
		CartItem item = items.get(foodBean.getFoodId());
		if (null != item) {
			item.setNumber((Integer.parseInt(item.getNumber()) + Integer.parseInt(number)) + "");
		} else {
			item = new CartItem(foodBean, number);
		}
		items.put(foodBean.getFoodId(), item);
	}
	
	public void removeItem(String id){
		items.remove(id);
	}
	
	public void clearCart(){
		items.clear();
	}
}
