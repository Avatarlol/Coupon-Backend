package com.project.CouponMVC.repositories.interfaces;

import java.util.List;

import com.project.CouponMVC.entities.Company;

public abstract interface CompanyRepositoryInt {

//	public boolean isCompanyExists(String email, String password);
	
	public Company findByEmailAndPassword(String email, String password);
	
	public Company addCompany(Company company);

	public Company updateCompany(Company company);
	
	public void deleteCompany(int id);
	
	public Company getCompany(int id);
	
	public List<Company> getAllCompanies();
	
}
