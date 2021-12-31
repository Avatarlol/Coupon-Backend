package com.project.CouponMVC.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.CouponMVC.enums.ClientType;
import com.project.CouponMVC.exceptions.IncorrectCredentials;
import com.project.CouponMVC.facades.AdminFacade;
import com.project.CouponMVC.facades.CompanyFacade;
import com.project.CouponMVC.facades.CustomerFacade;
import com.project.CouponMVC.facades.LoginManager;

@RestController
@RequestMapping("login")
public class LoginController {

	@Autowired
	private LoginManager lm;
	
	@GetMapping()
	public ResponseEntity<?> login(String email, String password, ClientType clientType){
		
		try {
			switch (clientType) {
			case ADMIN:
				AdminFacade admin = (AdminFacade)lm.login(email, password, clientType);
				return new ResponseEntity<AdminFacade>(admin, HttpStatus.OK);
			case COMPANY:
				CompanyFacade company = (CompanyFacade)lm.login(email, password, clientType);
				return new ResponseEntity<CompanyFacade>(company, HttpStatus.OK);
			case CUSTOMER:
				CustomerFacade customer = (CustomerFacade)lm.login(email, password, clientType);
				return new ResponseEntity<CustomerFacade>(customer, HttpStatus.OK);
			default:
				break;
			}
		}catch (IncorrectCredentials e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

		return null;
	}
	
	
}
