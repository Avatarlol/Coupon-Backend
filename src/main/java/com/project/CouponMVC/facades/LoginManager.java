package com.project.CouponMVC.facades;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.CouponMVC.enums.ClientType;
import com.project.CouponMVC.exceptions.IncorrectCredentials;

@Service
public class LoginManager {

	@Autowired
	private AdminFacade adminF;
	@Autowired
	private CompanyFacade companyF;
	@Autowired
	private CustomerFacade customerF;
	
	public ClientFacade login(String email, String password, ClientType clientType) throws IncorrectCredentials {
		
		switch(clientType) {
		case ADMIN: 
			return adminF.login(email, password);
		case COMPANY:
			return companyF.login(email, password);
		case CUSTOMER:
			return customerF.login(email, password);
		default:
			return null;
		}
	}
	
}
