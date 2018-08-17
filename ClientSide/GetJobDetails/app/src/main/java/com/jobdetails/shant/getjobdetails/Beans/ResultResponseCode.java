package com.jobdetails.shant.getjobdetails.Beans;

public class ResultResponseCode {


    /**
     Created for declaring final response from server after sending it the result of completion of test cases
     */

    private int response_code;
    private int error_code;
    private String error_message;

    public String getResponse_code() {
        return(String.valueOf(response_code));
    }


    public String getError_code() {
        return String.valueOf(error_code);
    }



    public String getError_message() {
        return error_message;
    }


}
