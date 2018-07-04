package com.jobdetails.shant.getjobdetails;

/**
 * Created by shant on 28-06-2018.
 */

public class Mob_Details {

    private String serial_num;
    private String device_model;
    private String device_os;
    private String os_version;
    private String build_id;
    private String manufacturer;
    private String brand;

    public Mob_Details(String serial_num, String device_model, String device_os, String os_version, String build_id, String manufacturer, String brand)
    {
        this.serial_num=serial_num;
        this.device_model=device_model;
        this.device_os=device_os;
        this.os_version=os_version;
        this.build_id=build_id;
        this.manufacturer=manufacturer;
        this.brand=brand;

    }
}
