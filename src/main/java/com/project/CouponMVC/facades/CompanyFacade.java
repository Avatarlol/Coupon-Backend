package com.project.CouponMVC.facades;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.project.CouponMVC.entities.Company;
import com.project.CouponMVC.entities.Coupon;
import com.project.CouponMVC.enums.Category;
import com.project.CouponMVC.enums.ClientType;
import com.project.CouponMVC.exceptions.ConditionsNotMet;
import com.project.CouponMVC.exceptions.IncorrectCredentials;

@Service
@Transactional
@Scope("singleton")
public class CompanyFacade extends ClientFacade{

	private int companyID;
	
	
	public CompanyFacade() {
	}
	
	@Override
	public ClientFacade login(String email, String password) throws IncorrectCredentials {
		Company company = coRep.findByEmailAndPassword(email, password);
		if(company==null) {
			throw new IncorrectCredentials();
		}else {
			companyID = company.getId();
			System.out.println("Logged in as "+ClientType.COMPANY+": "+company.getName()+".");
			return this;
		}
	}
	
	/**
	 * 
	 * @param id
	 * @return Coupon with the given ID. returns null if ID does not exist.
	 */
	public Coupon getCoupon(int id) {
		return couRep.getCoupon(id);
	}
	
	public void addCoupon(Coupon coupon) throws ConditionsNotMet {
			if(conditions.newCouponCondition(coupon, companyID)) {
				Company company = getCompany();
				company.addCoupon(coupon);
			}
	}
	
	public void updateCoupon(Coupon coupon) throws ConditionsNotMet {
			if(conditions.updateCouponCondition(coupon, companyID)) {
				couRep.updateCoupon(coupon);
			}
	}
	
	public void deleteCoupon(int couponID) throws ConditionsNotMet {
			if(conditions.deleteCouponCondition(couponID, companyID)) {
				Company company = getCompany();
				Coupon coupon = getCoupon(couponID);
				company.removeCoupon(coupon);
			}
	}
	
	public Company getCompany() {
		Company company = coRep.getCompany(companyID);
		return company;
	}
	
	public List<Coupon> getAllCoupons() {
		Company company = getCompany();
		List<Coupon> coupons = company.getCoupons();
		return coupons;
	}
	
	public List<Coupon> getAllCoupons(Category category) {
		List<Coupon> coupons = getAllCoupons();
		coupons = coupons.stream()
				 .filter(c -> c.getCategory().equals(category))
				 .collect(Collectors.toList());
		return coupons;
	}
	
	public List<Coupon> getAllCoupons(double maxPrice) {
		List<Coupon> coupons = getAllCoupons();
		coupons = coupons.stream()
				 .filter(c -> c.getPrice()<=maxPrice)
				 .collect(Collectors.toList());
		return coupons;
	}
	
}
