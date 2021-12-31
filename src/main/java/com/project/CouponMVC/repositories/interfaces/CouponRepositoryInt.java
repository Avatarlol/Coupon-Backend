package com.project.CouponMVC.repositories.interfaces;

import java.util.List;

import com.project.CouponMVC.entities.Coupon;

public abstract interface CouponRepositoryInt {

	@Deprecated(since = "05-11-2021\n Reason: Different way of updating coupon ; update company that owns that to apply any coupon changes.")
	public Coupon addCoupon(Coupon coupon);
	
	public Coupon updateCoupon(Coupon coupon);
	
	@Deprecated(since = "05-11-2021\n Reason: Different way of updating coupon ; update company that owns that to apply any coupon changes.")
	public void deleteCoupon(int couponID);
	
	public Coupon getCoupon(int id);
	
	public List<Coupon> getAllCoupons();
	
}
