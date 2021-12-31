package com.project.CouponMVC.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.CouponMVC.entities.Customer;
import com.project.CouponMVC.exceptions.ConditionsNotMet;
import com.project.CouponMVC.facades.AdminFacade;

@RestController
@RequestMapping("admin")
public class AdminController {

	@Autowired
	AdminFacade admin;
	
	@GetMapping("NewCustomer")
	public ResponseEntity<?> newCustomer(String firstname, String lastname, String email, String password){
		
		try {
		
			if(firstname==null || firstname==null || firstname==null || firstname==null) 
				return new ResponseEntity<String>("all fields are required!" , HttpStatus.BAD_REQUEST);
			
			Customer customer = new Customer();
			customer.setFirstname(firstname);
			customer.setLastname(lastname);
			customer.setEmail(email);
			customer.setPassword(password);
		
			admin.addCustomer(customer);
			return new ResponseEntity<Customer>(customer, HttpStatus.OK);
			
		}catch (/*ConditionsNotMet*/Exception e) { //TODO change back to ConditionsNotMet
			return new ResponseEntity<String>(e.getMessage() , HttpStatus.BAD_REQUEST);
		}
	}
	
}
