package com.jobdetails.shant.getjobdetails;

import java.util.ArrayList;
import java.util.List;

/**
 * Created for Json parsing got from server as response containing test case details .
 */

public class Response {

    private int device_avail_code;
    private int job_avail_code;
    private int response_code;
    private int error_code;
    public List<TestExecutionDetailsBean> test_execution_details;

    public Response() {
        this.device_avail_code = device_avail_code;
        this.job_avail_code = job_avail_code;
        this.response_code = response_code;
        this.error_code = error_code;
        this.test_execution_details = test_execution_details;
    }

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

        public TestExecutionDetailsBean(int execution_id, int testrun_id, int testcase_id, int feature_id, String feature_name, String testcase_name, String testcase_desc, int device_id, int testjob_id) {
            this.execution_id = execution_id;
            this.testrun_id = testrun_id;
            this.testcase_id = testcase_id;
            this.feature_id = feature_id;
            this.feature_name = feature_name;
            this.testcase_name = testcase_name;
            this.testcase_desc = testcase_desc;
            this.device_id = device_id;
            this.testjob_id = testjob_id;
        }



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
