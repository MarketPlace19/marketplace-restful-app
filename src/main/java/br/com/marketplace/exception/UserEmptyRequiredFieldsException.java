package br.com.marketplace.exception;

public class UserEmptyRequiredFieldsException extends MarketplaceBusinessException {

	private static final long serialVersionUID = -1705063713900703664L;
	
	public UserEmptyRequiredFieldsException() {}
	
	public UserEmptyRequiredFieldsException(String message) {
		super(message);
	}

}
