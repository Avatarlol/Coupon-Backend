package com.project.CouponMVC.exceptions;

public class InvalidInput extends CustomException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8178472305841884574L;

	private static final String DEFAULT_ERROR_MESSAGE = "Conditions not met.\n";
	
	public InvalidInput(String message) {
		super(DEFAULT_ERROR_MESSAGE+message);
	}

}
