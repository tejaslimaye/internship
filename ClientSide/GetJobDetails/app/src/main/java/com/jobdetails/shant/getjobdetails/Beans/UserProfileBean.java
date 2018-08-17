package com.jobdetails.shant.getjobdetails.Beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserProfileBean {

    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("actCode")
    @Expose
    private String actCode;
    @SerializedName("groupName")
    @Expose
    private String groupName;
    @SerializedName("emailId")
    @Expose
    private String emailId;
    @SerializedName("mobNum")
    @Expose
    private String mobNum;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("isRelIdVerifyEnabled")
    @Expose
    private Boolean isRelIdVerifyEnabled;
    @SerializedName("sessionId")
    @Expose
    private String sessionId;
    @SerializedName("apiversion")
    @Expose
    private String apiversion;

    public UserProfileBean(){

    }

    /**
     *
     * @param lastName
     * @param username
     * @param emailId
     * @param groupName
     * @param sessionId
     * @param mobNum
     * @param userId
     * @param actCode
     * @param firstName
     * @param password
     * @param apiversion
     * @param isRelIdVerifyEnabled
     */
    public UserProfileBean(String firstName, String lastName, String userId, String actCode, String groupName, String emailId, String mobNum, String username, String password, Boolean isRelIdVerifyEnabled, String sessionId, String apiversion) {
//        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.userId = userId;
        this.actCode = actCode;
        this.groupName = groupName;
        this.emailId = emailId;
        this.mobNum = mobNum;
        this.username = username;
        this.password = password;
        this.isRelIdVerifyEnabled = isRelIdVerifyEnabled;
        this.sessionId = sessionId;
        this.apiversion = apiversion;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getActCode() {
        return actCode;
    }

    public void setActCode(String actCode) {
        this.actCode = actCode;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getMobNum() {
        return mobNum;
    }

    public void setMobNum(String mobNum) {
        this.mobNum = mobNum;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getIsRelIdVerifyEnabled() {
        return isRelIdVerifyEnabled;
    }

    public void setIsRelIdVerifyEnabled(Boolean isRelIdVerifyEnabled) {
        this.isRelIdVerifyEnabled = isRelIdVerifyEnabled;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getApiversion() {
        return apiversion;
    }

    public void setApiversion(String apiversion) {
        this.apiversion = apiversion;
    }


}
