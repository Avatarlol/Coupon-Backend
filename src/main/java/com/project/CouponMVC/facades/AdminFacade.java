package com.project.CouponMVC.facades;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.project.CouponMVC.entities.Company;
import com.project.CouponMVC.entities.Customer;
import com.project.CouponMVC.enums.ClientType;
import com.project.CouponMVC.exceptions.ConditionsNotMet;
import com.project.CouponMVC.exceptions.IncorrectCredentials;

@Service
@Transactional
public class AdminFacade extends ClientFacade{

	@Value("${admin.email}")
	private String ADMIN_EMAIL;
	@Value("${admin.password}")
	private String ADMIN_PASSWORD;
	
	public AdminFacade() {}

	@Override
	public AdminFacade login(String email, String password) throws IncorrectCredentials {
		if(email.equalsIgnoreCase(ADMIN_EMAIL) && password.equals(ADMIN_PASSWORD)) {
			System.out.println("Logged-in as "+ClientType.ADMIN+".");
			return this;
		}else {
			throw new IncorrectCredentials();
		}
	}

	
	// Company CRUD
	
	/**
	 * 
	 * @param id
	 * @return Company with the given ID. returns null if ID does not exist.
	 */
	public Company getCompany(int id) {
		return coRep.getCompany(id);
	}

	/**
	 * 
	 * @return a List of all companies from database.
	 */
	public List<Company> getAllCompanies(){
		return coRep.getAllCompanies();
	}

	/**
	 * 
	 * @param company
	 *	
	 *	Adding company into the database if conditions are met.
	 * @throws ConditionsNotMet if conditions not met.
	 */
	public void addCompany(Company company) throws ConditionsNotMet {
			if(conditions.newCompanyCondition(company)) {
				coRep.addCompany(company);
			}
	}
	
	/**
	 * 
	 * @param company
	 *	
	 *	Updating company in database if conditions are met.
	 * @throws ConditionsNotMet if conditions not met.
	 */
	public void updateCompany(Company company) throws ConditionsNotMet {
			if(conditions.updateCompanyCondition(company)) {
				coRep.updateCompany(company);
			}
	}
	
	/**
	 * 
	 * @param id
	 *	
	 *	deletes company with the given ID if conditions are met.
	 * @throws ConditionsNotMet if conditions not met.
	 */
	public void deleteCompany(int id) throws ConditionsNotMet {
			if(conditions.deleteCompanyCondition(id)) {
				coRep.deleteCompany(id);
			}
	}

	// Customer CRUD
	
	/**
	 * 
	 * @param id
	 * @return Customer with the given ID.
	 */
	public Customer getCustomer(int id) {
		return cuRep.getCustomer(id);
	}
	
	/**
	 * 
	 * @return a List of all customers from database.
	 */
	public List<Customer> getAllCustomers(){
		return cuRep.getAllCustomers();
	}
	
	/**
	 * 
	 * @param customer
	 * 
	 * Adding customer to database if conditions are met. Prints error message if conditions not met.
	 * @throws ConditionsNotMet 
	 */
	public void addCustomer(Customer customer) throws ConditionsNotMet {
				if(conditions.newCustomerCondition(customer)) {
					cuRep.addCustomer(customer);
				}
	}

	/**
	 * 
	 * @param customer
	 *	
	 *	Updating customer in database if conditions are met. Prints error message if conditions not met.
	 * @throws ConditionsNotMet 
	 */
	public void updateCustomer(Customer customer) throws ConditionsNotMet {
			if(conditions.updateCustomerCondition(customer)) {
				cuRep.updateCustomer(customer);
			}
	}

	/**
	 * 
	 * @param id
	 *	
	 *	deletes customer with the given ID if conditions are met. Prints error message if conditions not met.
	 * @throws ConditionsNotMet 
	 */
	public void deleteCustomer(int id) throws ConditionsNotMet {
			if(conditions.deleteCustomerCondition(id)) {
				cuRep.deleteCustomer(id);
			}

	}


}
