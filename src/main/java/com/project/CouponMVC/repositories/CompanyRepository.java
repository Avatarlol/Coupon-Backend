package com.project.CouponMVC.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.CouponMVC.entities.Company;
import com.project.CouponMVC.exceptions.ConditionsNotMet;
import com.project.CouponMVC.repositories.interfaces.CompanyRepositoryInt;

public interface CompanyRepository extends JpaRepository<Company, Integer>,CompanyRepositoryInt{

	public Company findByEmailAndPassword(String email, String password);
	
	/**
	 * checks if a company with both email and password exists.
	 * 
	 * @return returns true if exists otherwise returns false.
	 */
	@Deprecated(since = "6-11-2021")
	public default boolean isCompanyExists(String email, String password) {
		return existsByEmailAndPassword(email, password);
	}
	
	/**
	 * checks if company's id exists in database.
	 * if true prints error message otherwise adds company to database.
	 * @param company
	 */
	public default Company addCompany(Company company) {
		if(!existsById(company.getId())) {
			Company addedCompany = save(company);
			System.out.println("Company added.");
			return addedCompany;
		}else {
			System.out.println("If this shows up, call ifty to fix. @addCompany");
			return company;
		}
	}

	/**
	 * checks if company's id exists in database.
	 * if true updates company otherwise prints error message.
	 * @param company
	 */
	public default Company updateCompany(Company company) {
		if(!existsById(company.getId())) {
			System.out.println("If this shows up, call ifty to fix. @updateCompany");
			return company;
		}else {
			Company updatedCompany = save(company);
			System.out.println("Updated company.");
			return updatedCompany;
		}
	}
	
	/**
	 * deletes company with given <code>id</code> from database.
	 */
	public default void deleteCompany(int id) {
		if(getCompany(id)==null) {
			System.out.println("If this shows up, call ifty to fix. @deleteCompany");
		}else {
			deleteById(id);
			System.out.println("Company deleted.");
		}
	}
	
	/**
	 * gets a company from database.
	 * @return <li>returns company with the given <code>id</code>.
	 * <li>returns null if no company with <code>id</code> given exists.
	 */
	public default Company getCompany(int id) {
		if(!existsById(id)) {
			return null;
		}else {
			return findById(id).get();
		}
	}
	
	/**
	 * @return <li>returns an arraylist with all companies from the database.
	 * 		   <li>returns an empty arraylist if none exist.
	 */
	public default List<Company> getAllCompanies(){
		List<Company> companies = findAll();
		return companies;
	}
	
	//Booleans
	
	public boolean existsById(int id);
	
	public boolean existsByEmailAndPassword(String email, String password);
	
	public boolean existsByName(String name);
	
	public boolean existsByEmail(String email);
	
	
	//Conditions
	
	public default boolean newCompanyCondition(Company company) throws ConditionsNotMet{
		
		boolean isCompanyNotExist = !existsById(company.getId());
		boolean isCompanyNameAvailable = !existsByName(company.getName());
		boolean isCompanyEmailAvailable = !existsByEmail(company.getEmail());
		
		if(isCompanyNotExist && isCompanyNameAvailable && isCompanyEmailAvailable) {
			return true;
		}else {
			String msg = "";
			if(!isCompanyNotExist) msg += "Company ID already exists.\n";
			if(!isCompanyNameAvailable) msg += "Company name already in use.\n";
			if(!isCompanyEmailAvailable) msg += "Company email already in use.\n";
			throw new ConditionsNotMet(msg);
		}
	}
	
	public default boolean updateCompanyCondition(Company company) throws ConditionsNotMet{
		
		boolean isCompanyExist = existsById(company.getId());
		boolean isCompanyNameNotChanged = existsByName(company.getName());
		
		if(isCompanyExist && isCompanyNameNotChanged) {
			return true;
		}else {
			String msg = "";
			if(!isCompanyExist) msg += "Company ID doesn't exists.\n";
			if(!isCompanyNameNotChanged) msg += "Company name cannot be changed.\n";
			throw new ConditionsNotMet(msg);
		}
	}
	
	public default boolean deleteCompanyCondition(int id) throws ConditionsNotMet{
		
		boolean isCompanyExist = existsById(id);
		
		if(isCompanyExist) {
			return true;
		}else {
			String msg = "";
			if(!isCompanyExist) msg += "Company ID doesn't exists.\n";
			throw new ConditionsNotMet(msg);
		}
	}
}
