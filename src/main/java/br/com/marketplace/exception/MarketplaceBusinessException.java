package br.com.marketplace.exception;

public class MarketplaceBusinessException extends RuntimeException {

	private static final long serialVersionUID = 4195148806788430106L;
	
	public MarketplaceBusinessException() {
		super();
	}

	public MarketplaceBusinessException(String message) {
		super(message);
	}
}
