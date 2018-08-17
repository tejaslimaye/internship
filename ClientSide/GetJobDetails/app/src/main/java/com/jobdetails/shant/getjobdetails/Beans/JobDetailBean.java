package com.jobdetails.shant.getjobdetails.Beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JobDetailBean {


    @SerializedName("device_avail_code")
    @Expose
    private Integer deviceAvailCode;
    @SerializedName("job_avail_code")
    @Expose
    private Integer jobAvailCode;
    @SerializedName("server_execution_details")
    @Expose
    private List<ServerExecutionDetail> serverExecutionDetails = null;
    @SerializedName("response_code")
    @Expose
    private Integer responseCode;
    @SerializedName("error_code")
    @Expose
    private Integer errorCode;

    public Integer getDeviceAvailCode() {
        return deviceAvailCode;
    }

    public void setDeviceAvailCode(Integer deviceAvailCode) {
        this.deviceAvailCode = deviceAvailCode;
    }

    public Integer getJobAvailCode() {
        return jobAvailCode;
    }

    public void setJobAvailCode(Integer jobAvailCode) {
        this.jobAvailCode = jobAvailCode;
    }

    public List<ServerExecutionDetail> getServerExecutionDetails() {
        return serverExecutionDetails;
    }

    public void setServerExecutionDetails(List<ServerExecutionDetail> serverExecutionDetails) {
        this.serverExecutionDetails = serverExecutionDetails;
    }

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

    public class Execution {

        @SerializedName("execution_id")
        @Expose
        private Integer executionId;
        @SerializedName("response_code")
        @Expose
        private Integer responseId;
        @SerializedName("error_code")
        @Expose
        private Integer errorCode;

        public Integer getResponseId() {
            return responseId;
        }

        public void setResponseId(Integer responseId) {
            this.responseId = responseId;
        }

        public Integer getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(Integer errorCode) {
            this.errorCode = errorCode;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        @SerializedName("error_message")
        @Expose
        private String errorMessage;
        @SerializedName("testrun_id")
        @Expose
        private Integer testrunId;
        @SerializedName("testcase_id")
        @Expose
        private Integer testcaseId;
        @SerializedName("feature_id")
        @Expose
        private Integer featureId;
        @SerializedName("feature_name")
        @Expose
        private String featureName;
        @SerializedName("testcase_name")
        @Expose
        private String testcaseName;
        @SerializedName("testcase_desc")
        @Expose
        private String testcaseDesc;
        @SerializedName("device_id")
        @Expose
        private Integer deviceId;
        @SerializedName("testjob_id")
        @Expose
        private Integer testjobId;

        public Integer getExecutionId() {
            return executionId;
        }

        public void setExecutionId(Integer executionId) {
            this.executionId = executionId;
        }

        public Integer getTestrunId() {
            return testrunId;
        }

        public void setTestrunId(Integer testrunId) {
            this.testrunId = testrunId;
        }

        public Integer getTestcaseId() {
            return testcaseId;
        }

        public void setTestcaseId(Integer testcaseId) {
            this.testcaseId = testcaseId;
        }

        public Integer getFeatureId() {
            return featureId;
        }

        public void setFeatureId(Integer featureId) {
            this.featureId = featureId;
        }

        public String getFeatureName() {
            return featureName;
        }

        public void setFeatureName(String featureName) {
            this.featureName = featureName;
        }

        public String getTestcaseName() {
            return testcaseName;
        }

        public void setTestcaseName(String testcaseName) {
            this.testcaseName = testcaseName;
        }

        public String getTestcaseDesc() {
            return testcaseDesc;
        }

        public void setTestcaseDesc(String testcaseDesc) {
            this.testcaseDesc = testcaseDesc;
        }

        public Integer getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(Integer deviceId) {
            this.deviceId = deviceId;
        }

        public Integer getTestjobId() {
            return testjobId;
        }

        public void setTestjobId(Integer testjobId) {
            this.testjobId = testjobId;
        }

    }

    public class ServerExecutionDetail {

        @SerializedName("server_id")
        @Expose
        private Integer serverId;
        @SerializedName("gm_port")
        @Expose
        private Integer gmPort;
        @SerializedName("sdk_port")
        @Expose
        private Integer sdkPort;
        @SerializedName("verify_port")
        @Expose
        private Integer verifyPort;
        @SerializedName("api_port")
        @Expose
        private Integer apiPort;
        @SerializedName("ip_address")
        @Expose
        private String ipAddress;
        @SerializedName("os_version")
        @Expose
        private String osVersion;
        @SerializedName("console_user")
        @Expose
        private String consoleUser;
        @SerializedName("console_password")
        @Expose
        private String consolePassword;
        @SerializedName("enterprise_id")
        @Expose
        private String enterpriseId;
        @SerializedName("enterprise_user")
        @Expose
        private String enterpriseUser;
        @SerializedName("enterprise_password")
        @Expose
        private String enterprisePassword;
        @SerializedName("server_user")
        @Expose
        private String serverUser;
        @SerializedName("server_password")
        @Expose
        private String serverPassword;
        @SerializedName("agent_info")
        @Expose
        private String agentInfo;
        @SerializedName("testjobexecutions")
        @Expose
        private List<Testjobexecution> testjobexecutions = null;

        public Integer getServerId() {
            return serverId;
        }

        public void setServerId(Integer serverId) {
            this.serverId = serverId;
        }

        public Integer getGmPort() {
            return gmPort;
        }

        public void setGmPort(Integer gmPort) {
            this.gmPort = gmPort;
        }

        public Integer getSdkPort() {
            return sdkPort;
        }

        public void setSdkPort(Integer sdkPort) {
            this.sdkPort = sdkPort;
        }

        public Integer getVerifyPort() {
            return verifyPort;
        }

        public void setVerifyPort(Integer verifyPort) {
            this.verifyPort = verifyPort;
        }

        public Integer getApiPort() {
            return apiPort;
        }

        public void setApiPort(Integer apiPort) {
            this.apiPort = apiPort;
        }

        public String getIpAddress() {
            return ipAddress;
        }

        public void setIpAddress(String ipAddress) {
            this.ipAddress = ipAddress;
        }

        public String getOsVersion() {
            return osVersion;
        }

        public void setOsVersion(String osVersion) {
            this.osVersion = osVersion;
        }

        public String getConsoleUser() {
            return consoleUser;
        }

        public void setConsoleUser(String consoleUser) {
            this.consoleUser = consoleUser;
        }

        public String getConsolePassword() {
            return consolePassword;
        }

        public void setConsolePassword(String consolePassword) {
            this.consolePassword = consolePassword;
        }

        public String getEnterpriseId() {
            return enterpriseId;
        }

        public void setEnterpriseId(String enterpriseId) {
            this.enterpriseId = enterpriseId;
        }

        public String getEnterpriseUser() {
            return enterpriseUser;
        }

        public void setEnterpriseUser(String enterpriseUser) {
            this.enterpriseUser = enterpriseUser;
        }

        public String getEnterprisePassword() {
            return enterprisePassword;
        }

        public void setEnterprisePassword(String enterprisePassword) {
            this.enterprisePassword = enterprisePassword;
        }

        public String getServerUser() {
            return serverUser;
        }

        public void setServerUser(String serverUser) {
            this.serverUser = serverUser;
        }

        public String getServerPassword() {
            return serverPassword;
        }

        public void setServerPassword(String serverPassword) {
            this.serverPassword = serverPassword;
        }

        public String getAgentInfo() {
            return agentInfo;
        }

        public void setAgentInfo(String agentInfo) {
            this.agentInfo = agentInfo;
        }

        public List<Testjobexecution> getTestjobexecutions() {
            return testjobexecutions;
        }

        public void setTestjobexecutions(List<Testjobexecution> testjobexecutions) {
            this.testjobexecutions = testjobexecutions;
        }

    }


    public class Testjobexecution {

        @SerializedName("testjob_id")
        @Expose
        private Integer testjobId;
        @SerializedName("test_job_description")
        @Expose
        private String testJobDescription;
        @SerializedName("created_time")
        @Expose
        private String createdTime;
        @SerializedName("updated_time")
        @Expose
        private String updatedTime;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("server_id")
        @Expose
        private Integer serverId;
        @SerializedName("lib_id")
        @Expose
        private Integer libId;
        @SerializedName("auto_create_on_new_device")
        @Expose
        private Integer autoCreateOnNewDevice;
        @SerializedName("test_run_id")
        @Expose
        private Integer testRunId;
        @SerializedName("executions")
        @Expose
        private List<Execution> executions = null;

        public Integer getTestjobId() {
            return testjobId;
        }

        public void setTestjobId(Integer testjobId) {
            this.testjobId = testjobId;
        }

        public String getTestJobDescription() {
            return testJobDescription;
        }

        public void setTestJobDescription(String testJobDescription) {
            this.testJobDescription = testJobDescription;
        }

        public String getCreatedTime() {
            return createdTime;
        }

        public void setCreatedTime(String createdTime) {
            this.createdTime = createdTime;
        }

        public String getUpdatedTime() {
            return updatedTime;
        }

        public void setUpdatedTime(String updatedTime) {
            this.updatedTime = updatedTime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Integer getServerId() {
            return serverId;
        }

        public void setServerId(Integer serverId) {
            this.serverId = serverId;
        }

        public Integer getLibId() {
            return libId;
        }

        public void setLibId(Integer libId) {
            this.libId = libId;
        }

        public Integer getAutoCreateOnNewDevice() {
            return autoCreateOnNewDevice;
        }

        public void setAutoCreateOnNewDevice(Integer autoCreateOnNewDevice) {
            this.autoCreateOnNewDevice = autoCreateOnNewDevice;
        }

        public Integer getTestRunId() {
            return testRunId;
        }

        public void setTestRunId(Integer testRunId) {
            this.testRunId = testRunId;
        }

        public List<Execution> getExecutions() {
            return executions;
        }

        public void setExecutions(List<Execution> executions) {
            this.executions = executions;
        }

    }

}
