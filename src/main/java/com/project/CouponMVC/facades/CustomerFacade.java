package com.project.CouponMVC.facades;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.project.CouponMVC.entities.Coupon;
import com.project.CouponMVC.entities.Customer;
import com.project.CouponMVC.enums.Category;
import com.project.CouponMVC.enums.ClientType;
import com.project.CouponMVC.exceptions.ConditionsNotMet;
import com.project.CouponMVC.exceptions.IncorrectCredentials;

@Service
@Transactional
@Scope("singleton")
public class CustomerFacade extends ClientFacade{

	private int customerID;

	
	public CustomerFacade() {
	}
 
	@Override
	public ClientFacade login(String email, String password) throws IncorrectCredentials {
		Customer customer = cuRep.findByEmailAndPassword(email, password);
		if(customer==null) {
			throw new IncorrectCredentials();
		}else {
			customerID = customer.getId();
			System.out.println("Logged in as "+ClientType.CUSTOMER+": "+customer.getFirstname()+".");
			return this;
		}
	}
	
	public void purchaseCoupon(Coupon coupon) throws ConditionsNotMet {
			if(conditions.purchaseCouponCondition(coupon, customerID)) {
				Customer customer = getCustomer();
				customer.buyCoupon(coupon);
				couRep.updateCoupon(coupon);
			}
	}

	public Customer getCustomer() {
		Customer customer = cuRep.getCustomer(customerID);
		return customer;
	}
	
	public List<Coupon> getAllPurchasedCoupons(){
		Customer customer = getCustomer();
		List<Coupon> coupons = customer.getCoupons();
		return coupons;
	}
	
	public List<Coupon> getAllPurchasedCoupons(Category category) {
		List<Coupon> coupons = getAllPurchasedCoupons();
		coupons = coupons.stream()
				 .filter(c -> c.getCategory().equals(category))
				 .collect(Collectors.toList());
		return coupons;
	}
	
	public List<Coupon> getAllPurchasedCoupons(double maxPrice) {
		List<Coupon> coupons = getAllPurchasedCoupons();
		coupons = coupons.stream()
				 .filter(c -> c.getPrice()<=maxPrice)
				 .collect(Collectors.toList());
		return coupons;
	}
	
	// A function for tests.
	// * used to display coupons list to customer before purchase
	public List<Coupon> getAllCoupons(){
		
		List<Coupon> coupons = couRep.getAllCoupons();
		
		return coupons;
	}
	
}
