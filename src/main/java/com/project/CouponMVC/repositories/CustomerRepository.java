/**
 * 
 */
package com.project.CouponMVC.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.CouponMVC.entities.Customer;
import com.project.CouponMVC.exceptions.ConditionsNotMet;
import com.project.CouponMVC.repositories.interfaces.CustomerRepositoryInt;

public interface CustomerRepository extends JpaRepository<Customer, Integer>, CustomerRepositoryInt{

	public Customer findByEmailAndPassword(String email, String password);
	
	/**
	 * checks if a customer with both email and password exists.
	 * 
	 * @return returns true if atleast one exists otherwise returns false.
	 */
	@Deprecated(since = "6-11-2021")
	public default boolean isCustomerExists(String email, String password) {
		return existsByEmailAndPassword(email, password);
	}
	
	/**
	 * checks if customer's id exists in database.
	 * if true prints error message otherwise adds customer to database.
	 * @param customer
	 */
	public default Customer addCustomer(Customer customer) {
		if(!existsById(customer.getId())) {
			Customer newCustomer = save(customer);
			System.out.println("Customer added.");
			return newCustomer;
		}else {
			System.out.println("Can't add a customer that already exists.");
			return customer;
		}
	}
	
	/**
	 * checks if customer's id exists in database.
	 * if true updates customer otherwise prints error message.
	 * @param company
	 */
	public default Customer updateCustomer(Customer customer) {
		if(!existsById(customer.getId())) {
			System.out.println("Customer doesn't exist in the databases.");
			return customer;
		}else {
			Customer updatedCustomer = save(customer);
			System.out.println("Customer updated.");
			return updatedCustomer;
		}
	}
	
	/**
	 * deletes customer with given <code>id</code> from database.
	 */
	public default void deleteCustomer (int id) {
		if(existsById(id)) {
			deleteById(id);
			System.out.println("Customer deleted.");
		}else {
			System.out.println("Customer ID '"+id+"' doesn't exist in database.");
		}
	}
	
	/**
	 * gets a customer from database.
	 * @return <li>returns customer with the given <code>id</code>.
	 * <li>returns null if no customer with <code>id</code> given exists.
	 */
	public default Customer getCustomer(int id) {
		if(existsById(id)) {
			return findById(id).get();
		}else {
			return null;
		}
	}
	
	/**
	 * @return <li>returns an arraylist with all customers from the database.
	 * 		   <li>returns an empty arraylist if none exist.
	 */
	public default List<Customer> getAllCustomers() {
		List<Customer> customers = findAll();
		return customers;
	}
	
	// Booleans
	
	public boolean existsById(int id);
	
	public boolean existsByEmail(String email);
	
	public boolean existsByEmailAndPassword(String email, String password);
	
	// Conditions
	
	public default boolean newCustomerCondition(Customer customer) throws ConditionsNotMet {
		
		boolean isCustomerNotExist = !existsById(customer.getId());
		boolean isEmailNotUsed = !existsByEmail(customer.getEmail());
		
		if(isCustomerNotExist && isEmailNotUsed) {
			return true;
		}else {
			String msg = "";
			if(!isCustomerNotExist) msg += "Customer ID already exists.\n";
			if(!isEmailNotUsed) msg += "Email already in use.\n";
			throw new ConditionsNotMet(msg);
		}
	}
	
	public default boolean updateCustomerCondition(Customer customer) throws ConditionsNotMet {
		
		boolean isCustomerExist = existsById(customer.getId());
		
		if(isCustomerExist) {
			return true;
		}else {
			String msg = "";
			if(!isCustomerExist) msg += "Customer doesn't exist.\n";
			throw new ConditionsNotMet(msg);
		}
	}
	
	public default boolean deleteCustomerCondition(int id) throws ConditionsNotMet {
		
		boolean isCustomerExist = existsById(id);
		
		if(isCustomerExist) {
			return true;
		}else {
			String msg = "";
			if(!isCustomerExist) msg += "Customer doesn't exist.\n";
			throw new ConditionsNotMet(msg);
		}
	}
	
}
