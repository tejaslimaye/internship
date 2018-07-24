package com.uniken.automation.beans;

public class DeviceBean{
	private int device_id;
	private String device_model;
	private String device_os;
	private String os_version;
	private String build_id;
	private String serial_num;
	private String brand;
	private String manufacturer;
	private String library_version;
	
	
	public String getLibrary_version() {
		return library_version;
	}

	public void setLibrary_version(String library_version) {
		this.library_version = library_version;
	}
	
	public int getDevice_Id()
	{
		return device_id;
	}
	
	public void setDeviceId(int id)
	{
		device_id = id;
	}

	public String getDevice_model(){
		return device_model;
	}

	public void setDevice_model(String device_model){
		this.device_model=device_model;
	}

	public String getDevice_os(){
		return device_os;
	}

	public void setDevice_os(String device_os){
		this.device_os=device_os;
	}

	public String getOs_version(){
		return os_version;
	}

	public void setOs_version(String os_version){
		this.os_version=os_version;
	}

	public String getBuild_id(){
		return build_id;
	}

	public void setBuild_id(String build_id){
		this.build_id=build_id;
	}

	public String getSerial_num(){
		return serial_num;
	}

	public void setSerial_num(String serial_num){
		this.serial_num=serial_num;
	}

	public String getBrand(){
		return brand;
	}

	public void setBrand(String brand){
		this.brand=brand;
	}

	public String getManufacturer(){
		return manufacturer;
	}

	public void setManufacturer(String manufacturer){
		this.manufacturer=manufacturer;
	}
	
	
	
	
}