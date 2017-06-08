package br.com.marketplace.service.impl;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ResourceBundle;

public abstract class GenericServiceImpl implements Serializable {

	private static final long serialVersionUID = -1864140545583159135L;
	
	protected static final String EXCEPTION_MESSAGES = "main.resources.exceptionMessages";
	
	protected static String formatSearchParam(String param) {
		return "%" + param + "%";
	}
	
	protected static String getMessage(String property, String key, Object... params) {
		ResourceBundle rb = ResourceBundle.getBundle(property);
		return (params.length > 0) ? MessageFormat.format(rb.getString(key), params) : rb.getString(key);
	}

}
