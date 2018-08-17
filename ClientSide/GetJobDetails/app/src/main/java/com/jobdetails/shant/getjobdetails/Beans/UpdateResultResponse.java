package com.jobdetails.shant.getjobdetails.Beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateResultResponse {

    @SerializedName("response_code")
    @Expose
    private Integer responseCode;
    @SerializedName("error_code")
    @Expose
    private Integer errorCode;

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

}
