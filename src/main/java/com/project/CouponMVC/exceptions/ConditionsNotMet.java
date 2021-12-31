package com.project.CouponMVC.exceptions;

public class ConditionsNotMet extends CustomException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6717681522679091436L;

	private static final String DEFAULT_ERROR_MESSAGE = "Conditions not met.\n";
	
	public ConditionsNotMet(String message) {
		super(DEFAULT_ERROR_MESSAGE + message);
	}

}
