package br.com.marketplace.exception;

/**
 * Created by thiago-dpf on 24/01/17.
 */
public class EmptyUserListException extends MarketplaceBusinessException {

	private static final long serialVersionUID = -1404419766008157704L;

	public EmptyUserListException() { super(); }

    public EmptyUserListException(String message) {
        super(message);
    }
}
