package com.jobdetails.shant.getjobdetails.Protocol;

public interface CountUpdater {

    void updateCount(int totalCases, int completedCases, int passCases, int failedCases, int notTetstedCases, String progress);
}
