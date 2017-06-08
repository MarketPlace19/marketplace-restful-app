package br.com.marketplace.exception;

public class EmptyUserFieldsException extends MarketplaceBusinessException {

	private static final long serialVersionUID = 808902923282803423L;

	public EmptyUserFieldsException() {
		super();
	}
	
	public EmptyUserFieldsException(String message) {
		super(message);
	}
}
