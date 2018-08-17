package com.jobdetails.shant.getjobdetails.Beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserErrorBean {

    @SerializedName("isError")
    @Expose
    private Boolean isError;
    @SerializedName("successMessage")
    @Expose
    private String successMessage;
    @SerializedName("errorMessage")
    @Expose
    private String errorMessage;

    public Boolean getError() {
        return isError;
    }

    public void setError(Boolean error) {
        isError = error;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
