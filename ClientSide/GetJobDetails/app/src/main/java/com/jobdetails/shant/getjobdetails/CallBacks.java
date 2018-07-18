package com.jobdetails.shant.getjobdetails;

import android.app.Application;
import android.util.Log;

import com.uniken.rdna.RDNA;

public class CallBacks implements RDNA.RDNACallbacks {

    Application mainApplication;
    public CallBacks(Application application){this.mainApplication = application;};
    String TAG="";
    @Override
    public int onInitializeCompleted(String s) {
        Log.d(TAG,"STRING IN onInitializeCompleted: "+ s);
        return 0;
    }

    @Override
    public Object getDeviceContext() {
        Log.d(TAG,"IN getDeviceContext: ");
        return mainApplication.getApplicationContext();
    }

    @Override
    public int onTerminate(String s) {
        Log.d(TAG,"STRING IN onTerminate: "+s);
        return 0;
    }

    @Override
    public int onPauseRuntime(String s) {
        Log.d(TAG,"STRING IN onPauseRuntime: "+s);
        return 0;
    }

    @Override
    public int onResumeRuntime(String s) {
        Log.d(TAG,"STRING IN onResumeRuntime: "+s);
        return 0;
    }

    @Override
    public int onConfigReceived(String s) {
        Log.d(TAG,"STRING IN onConfigReceived: "+s);
        return 0;
    }

    @Override
    public int onCheckChallengeResponseStatus(String s) {
        Log.d(TAG,"STRING IN oonCheckChallengeResponseStatus: "+s);
        return 0;
    }

    @Override
    public int onGetAllChallengeStatus(String s) {
        Log.d(TAG,"STRING IN onGetAllChallengeStatus: "+s);
        return 0;
    }

    @Override
    public int onUpdateChallengeStatus(String s) {
        Log.d(TAG,"STRING IN onUpdateChallengeStatus: "+s);
        return 0;
    }

    @Override
    public int onForgotPasswordStatus(String s) {
        Log.d(TAG,"STRING IN onForgotPasswordStatus: "+s);
        return 0;
    }

    @Override
    public int onLogOff(String s) {
        Log.d(TAG,"STRING IN onLogOff: "+s);
        return 0;
    }

    @Override
    public RDNA.RDNAIWACreds getCredentials(String s) {
        Log.d(TAG,"STRING IN getCredentials: "+s);
        return null;
    }

    @Override
    public String getApplicationName() {
        Log.d(TAG," IN getApplicationName ");
        return "TESTING AUTOMATION";
    }

    @Override
    public String getApplicationVersion() {
        Log.d(TAG,"IN getApplicationVersion ");
        return "1.0";
    }

    @Override
    public int onGetPostLoginChallenges(String s) {
        Log.d(TAG,"STRING IN onGetPostLoginChallenges: "+s);
        return 0;
    }

    @Override
    public int onGetRegistredDeviceDetails(String s) {
        Log.d(TAG,"STRING IN onGetRegistredDeviceDetails: "+s);
        return 0;
    }

    @Override
    public int onUpdateDeviceDetails(String s) {
        Log.d(TAG,"STRING IN onUpdateDeviceDetails: "+s);
        return 0;
    }

    @Override
    public String getDeviceToken() {
        Log.d(TAG,"IN getDeviceToken");
        return null;
    }

    @Override
    public int onGetNotifications(String s) {
        Log.d(TAG,"STRING IN onGetNotifications: "+s);
        return 0;
    }

    @Override
    public int onUpdateNotification(String s) {
        Log.d(TAG,"STRING IN onUpdateNotification: "+s);
        return 0;
    }

    @Override
    public int onGetNotificationsHistory(String s) {
        Log.d(TAG,"STRING IN onGetNotificationsHistory: "+s);
        return 0;
    }

    @Override
    public int onSessionTimeout(String s) {
        Log.d(TAG,"STRING IN onSessionTimeout: "+s);
        return 0;
    }

    @Override
    public int onSdkLogPrintRequest(RDNA.RDNALoggingLevel rdnaLoggingLevel, String s) {
        Log.d(TAG,"STRING IN onSdkLogPrintRequest: "+rdnaLoggingLevel);
        Log.d(TAG,"STRING IN onSdkLogPrintRequest: "+s);
        return 0;
    }
}
