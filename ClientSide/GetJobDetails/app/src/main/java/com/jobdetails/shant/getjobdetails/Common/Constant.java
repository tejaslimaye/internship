package com.jobdetails.shant.getjobdetails.Common;


import com.jobdetails.shant.getjobdetails.Beans.ExecutionBean;
import com.jobdetails.shant.getjobdetails.Beans.Response;
import com.jobdetails.shant.getjobdetails.Beans.ResultResponseCode;
import com.jobdetails.shant.getjobdetails.Beans.ServerBean;
import com.jobdetails.shant.getjobdetails.Beans.TestJobBean;
import com.uniken.rdna.RDNA;

public class Constant {
    public static ResultResponseCode r = new ResultResponseCode();

    public final static String resultPass = "PASSED";
    public final static String resultFail = "FAILED";
    public final static String resultCanNotTest = "CANNOT_TEST";

    public final static String mainURL = "http://34.235.131.241";
    public final static String urlPortNo = ":8080";
    public final static String enrollUserPort = ":9080";



}
