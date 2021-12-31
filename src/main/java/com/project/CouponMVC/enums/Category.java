package com.project.CouponMVC.enums;

public enum Category {

	RESTAURANT,
	ELECTRICITY,
	SHOPPING,
	VACATION,
	;
	
	/**
	 * 
	 * @param id
	 * @return category where the oridnal number equals to given ID. if no oridnal number equals to the ID, returns null. 
	 */
	public static Category getCategoryById(int id) {
		for (Category category : values()) {
			if(category.ordinal()==id) return category;
		}
		return null;
	}
	
}
