package com.project.CouponMVC.facades;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.CouponMVC.entities.Company;
import com.project.CouponMVC.entities.Coupon;
import com.project.CouponMVC.entities.Customer;
import com.project.CouponMVC.exceptions.ConditionsNotMet;
import com.project.CouponMVC.repositories.CompanyRepository;
import com.project.CouponMVC.repositories.CouponRepository;
import com.project.CouponMVC.repositories.CustomerRepository;

@Service
public class Conditions {

	@Autowired
	private CompanyRepository coRep;
	@Autowired
	private CustomerRepository cuRep;
	@Autowired
	private CouponRepository couRep;
	
	
	public Conditions() {
		super();
	}

	//Company

	public boolean newCompanyCondition(Company company) throws ConditionsNotMet{
		return coRep.newCompanyCondition(company);
	}
	
	public boolean updateCompanyCondition(Company company) throws ConditionsNotMet {
		return coRep.updateCompanyCondition(company);
	}
	
	public boolean deleteCompanyCondition(int companyID) throws ConditionsNotMet {
		return coRep.deleteCompanyCondition(companyID);
	}
	
	//Customer

	public boolean newCustomerCondition(Customer customer) throws ConditionsNotMet {
		return cuRep.newCustomerCondition(customer);
	}
	
	public boolean updateCustomerCondition(Customer customer) throws ConditionsNotMet {
		return cuRep.updateCustomerCondition(customer);
	}
	
	public boolean deleteCustomerCondition(int id) throws ConditionsNotMet {
		return cuRep.deleteCustomerCondition(id);
	}
	
	//Coupons
	
	public boolean newCouponCondition(Coupon coupon, int companyID) throws ConditionsNotMet{
		return couRep.newCouponCondition(coupon, companyID);
	}
	
	public boolean updateCouponCondition(Coupon coupon, int companyID) throws ConditionsNotMet{
		return couRep.updateCouponCondition(coupon, companyID);
	}
	
	public boolean deleteCouponCondition(int couponID, int companyID) throws ConditionsNotMet {
		return couRep.deleteCouponCondition(couponID, companyID);
	}
	
	public boolean purchaseCouponCondition(Coupon coupon, int customerID) throws ConditionsNotMet {
		return couRep.purchaseCouponCondition(coupon, customerID);
	}
	
}
