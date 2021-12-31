package com.project.CouponMVC.exceptions;

public class IncorrectCredentials extends CustomException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2686282213049349037L;
	
	private static final String ERROR_MESSAGE = "Login credentials incorrect.\n";
	
	public IncorrectCredentials() {
		super(ERROR_MESSAGE);
	}
	
}
