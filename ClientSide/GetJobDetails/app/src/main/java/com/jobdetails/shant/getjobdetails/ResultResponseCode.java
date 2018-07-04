package com.jobdetails.shant.getjobdetails;

public class ResultResponseCode {


    /**
     * response_code : 1
     * error_code : <error_code>
     * error_message : <error_message>
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
