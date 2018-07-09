package com.jobdetails.shant.getjobdetails;


//Created for Displaying these 3 testcase details on main Screen
public class PrintResponse {

    private String display_ex_id;
    private String display_test_id;
    private String display_test_name;

    public PrintResponse(String display_ex_id, String display_test_id, String display_test_name) {
        this.display_ex_id = display_ex_id;
        this.display_test_id = display_test_id;
        this.display_test_name = display_test_name;
    }

    public String getDisplay_ex_id() {
        return display_ex_id;
    }

    public void setDisplay_ex_id(String display_ex_id) {
        this.display_ex_id = display_ex_id;
    }

    public String getDisplay_test_id() {
        return display_test_id;
    }

    public void setDisplay_test_id(String display_test_id) {
        this.display_test_id = display_test_id;
    }

    public String getDisplay_test_name() {
        return display_test_name;
    }

    public void setDisplay_test_name(String display_test_name) {
        this.display_test_name = display_test_name;
    }
}
