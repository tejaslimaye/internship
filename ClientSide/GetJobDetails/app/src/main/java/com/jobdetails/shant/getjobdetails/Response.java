package com.jobdetails.shant.getjobdetails;

import java.util.List;

/**
 * Created by shant on 29-06-2018.
 */

public class Response {

    private int device_avail_code;
    private int job_avail_code;
    private int response_code;
    private int error_code;
    public List<TestExecutionDetailsBean> test_execution_details;

    public int getDevice_avail_code() {
        return device_avail_code;
    }

    public int getJob_avail_code() {
        return job_avail_code;
    }

    public int getResponse_code() {
        return response_code;
    }

    public int getError_code() {
        return error_code;
    }

    public List<TestExecutionDetailsBean> getTest_execution_details() {
        return test_execution_details;
    }

    public class TestExecutionDetailsBean {

        private int execution_id;
        private int testrun_id;
        private int testcase_id;
        private int feature_id;
        private String feature_name;
        private String testcase_name;
        private String testcase_desc;
        private int device_id;
        private int testjob_id;

        public int getExecution_id() {
            return execution_id;
        }

        public int getTestrun_id() {
            return testrun_id;
        }

        public int getTestcase_id() {
            return testcase_id;
        }

        public int getFeature_id() {
            return feature_id;
        }

        public String getFeature_name() {
            return feature_name;
        }

        public String getTestcase_name() {
            return testcase_name;
        }

        public String getTestcase_desc() {
            return testcase_desc;
        }

        public int getDevice_id() {
            return device_id;
        }

        public int getTestjob_id() {
            return testjob_id;
        }
    }
}
