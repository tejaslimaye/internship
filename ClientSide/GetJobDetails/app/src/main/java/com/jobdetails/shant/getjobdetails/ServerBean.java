package com.jobdetails.shant.getjobdetails;

import java.util.ArrayList;

class ServerBean {

    private int server_id;

    public int getServer_id() {
        return server_id;
    }

    public void setServer_id(int server_id) {
        this.server_id = server_id;
    }

    private int gm_port;
    private int sdk_port;
    private int verify_port;
    private int api_port;
    private String ip_address;
    private String os_version;
    private String console_user;
    private String console_password;
    private String enterprise_id;


    private String enterprise_user;
    private String enterprise_password;
    private String server_user;
    private String server_password;
    private String agent_info;

    ArrayList<TestJobBean> testjobexecutions = new ArrayList<TestJobBean>();

    public ArrayList<TestJobBean> getTestJobExecutions() {
        return testjobexecutions;
    }

    public void setTestJobExecutions(ArrayList<TestJobBean> executions) {
        this.testjobexecutions = executions;
    }

    public int getGm_port(){
        return gm_port;
    }

    public void setGm_port(int gm_port){
        this.gm_port=gm_port;
    }

    public int getSdk_port(){
        return sdk_port;
    }

    public void setSdk_port(int sdk_port){
        this.sdk_port=sdk_port;
    }

    public int getVerify_port(){
        return verify_port;
    }

    public void setVerify_port(int verify_port){
        this.verify_port=verify_port;
    }

    public int getApi_port(){
        return api_port;
    }

    public void setApi_port(int api_port){
        this.api_port=api_port;
    }

    public String getIp_address(){
        return ip_address;
    }

    public void setIp_address(String ip_address){
        this.ip_address=ip_address;
    }

    public String getOs_version(){
        return os_version;
    }

    public void setOs_version(String os_version){
        this.os_version=os_version;
    }

    public String getConsole_user(){
        return console_user;
    }

    public void setConsole_user(String console_user){
        this.console_user=console_user;
    }

    public String getConsole_password(){
        return console_password;
    }

    public void setConsole_password(String console_password){
        this.console_password=console_password;
    }

    public String getEnterprise_id() {
        return enterprise_id;
    }

    public void setEnterprise_id(String enterprise_id) {
        this.enterprise_id = enterprise_id;
    }

    public String getEnterprise_user(){
        return enterprise_user;
    }

    public void setEnterprise_user(String enterprise_user){
        this.enterprise_user=enterprise_user;
    }

    public String getEnterprise_password(){
        return enterprise_password;
    }

    public void setEnterprise_password(String enterprise_password){
        this.enterprise_password=enterprise_password;
    }

    public String getServer_user(){
        return server_user;
    }

    public void setServer_user(String server_user){
        this.server_user=server_user;
    }

    public String getServer_password(){
        return server_password;
    }

    public void setServer_password(String server_password){
        this.server_password=server_password;
    }

    public String getAgent_info(){
        return agent_info;
    }

    public void setAgent_info(String agent_info){
        this.agent_info=agent_info;
    }
}
