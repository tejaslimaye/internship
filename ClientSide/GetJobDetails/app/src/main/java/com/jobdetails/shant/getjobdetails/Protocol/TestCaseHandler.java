package com.jobdetails.shant.getjobdetails.Protocol;

public interface TestCaseHandler {

   void testCaseExecuted(int testCaseProgress, int failedCount, int passedCount, int notTestCount, String caseResult);

}
