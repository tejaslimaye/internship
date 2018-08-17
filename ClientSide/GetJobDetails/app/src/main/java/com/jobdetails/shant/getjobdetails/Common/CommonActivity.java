package com.jobdetails.shant.getjobdetails.Common;

import com.jobdetails.shant.getjobdetails.Protocol.TestCaseHandler;

public class CommonActivity {


    public void updateResult(TestCaseHandler testCaseHandler, String resultStat, int passedCount, int failedCount, int canNotTestCount){
        testCaseHandler.testCaseExecuted(1,failedCount,passedCount,canNotTestCount, resultStat);
    }


    public void updateCanNotTest(TestCaseHandler testCaseHandler){
        updateResult(testCaseHandler, Constant.resultCanNotTest, 0, 0, 1);
    }

    public void updatePassedTest(TestCaseHandler testCaseHandler){
        updateResult(testCaseHandler, Constant.resultPass, 1, 0, 0);
    }

    public void updateFailedTest(TestCaseHandler testCaseHandler){
        updateResult(testCaseHandler, Constant.resultFail, 0, 1, 0);
    }

    public void resultChecker(TestCaseHandler testCaseHandler, int responseCode, int expectedCode){

        if(responseCode == expectedCode) {
            updatePassedTest(testCaseHandler);
        }else {
            updateFailedTest(testCaseHandler);
        }

    }
}
