package br.com.marketplace.exception;

/**
 * Created by thiago-dpf on 17/01/17.
 */
public class UserNotFoundException extends MarketplaceBusinessException {

	private static final long serialVersionUID = 1263871605447923763L;
	
	public UserNotFoundException() {
		super();
	}
	
	public UserNotFoundException(String message) {
		super(message);
	}


}
