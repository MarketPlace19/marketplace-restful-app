package br.com.marketplace.entity;

import javax.persistence.Transient;

/**
 * Created by thiago-dpf on 17/01/17.
 */
public abstract class ErrorMessageEntity {

    @Transient
    protected String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
