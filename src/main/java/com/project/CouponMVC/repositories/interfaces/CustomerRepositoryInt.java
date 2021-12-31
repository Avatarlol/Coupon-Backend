package com.project.CouponMVC.repositories.interfaces;


import java.util.List;

import com.project.CouponMVC.entities.Customer;

public abstract interface CustomerRepositoryInt {

//	public boolean isCustomerExists(String email, String password);
	
	public Customer findByEmailAndPassword(String email, String password);
	
	public Customer addCustomer(Customer customer);
	
	public Customer updateCustomer(Customer customer);
	
	public void deleteCustomer (int id);
	
	public Customer getCustomer(int id);
	
	public List<Customer> getAllCustomers();
}
