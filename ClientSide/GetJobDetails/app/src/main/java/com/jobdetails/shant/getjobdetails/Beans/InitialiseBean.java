package com.jobdetails.shant.getjobdetails.Beans;

/**
 * Created for declaring Mobile Details to be sent to server as http request
 */

public class InitialiseBean {

    private String serial_num;
    private String device_model;
    private String device_os;
    private String os_version;

    public String getSerial_num() {
        return serial_num;
    }

    public String getDevice_model() {
        return device_model;
    }

    public String getDevice_os() {
        return device_os;
    }

    public String getOs_version() {
        return os_version;
    }

    public String getBuild_id() {
        return build_id;
    }

    private String build_id;
    private String manufacturer;
    private String brand;
    private String library_version;

    public InitialiseBean(String serial_num, String device_model, String device_os, String os_version, String build_id, String manufacturer, String brand, String library_version)
    {
        this.serial_num=serial_num;
        this.device_model=device_model;
        this.device_os=device_os;
        this.os_version=os_version;
        this.build_id=build_id;
        this.manufacturer=manufacturer;
        this.brand=brand;
        this.library_version=library_version;

    }
}
