package com.jobdetails.shant.getjobdetails.Common;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.uniken.rdna.RDNA;

public class CallBacks implements RDNA.RDNACallbacks {

    Application mainApplication;
    public CallBacks(Application application){this.mainApplication = application;};
    String TAG="";


    @Override
    public int onInitializeCompleted(RDNA.RDNAStatusInit rdnaStatusInit) {
        return 0;
    }

    @Override
    public Context getDeviceContext() {
        return null;
    }

    @Override
    public String getDeviceToken() {
        return null;
    }

    @Override
    public int onGetNotifications(RDNA.RDNAStatusGetNotifications rdnaStatusGetNotifications) {
        return 0;
    }

    @Override
    public int onUpdateNotification(RDNA.RDNAStatusUpdateNotification rdnaStatusUpdateNotification) {
        return 0;
    }

    @Override
    public int onTerminate(RDNA.RDNAStatusTerminate rdnaStatusTerminate) {
        return 0;
    }

    @Override
    public int onPauseRuntime(RDNA.RDNAStatusPause rdnaStatusPause) {
        return 0;
    }

    @Override
    public int onResumeRuntime(RDNA.RDNAStatusResume rdnaStatusResume) {
        return 0;
    }

    @Override
    public int onConfigReceived(RDNA.RDNAStatusGetConfig rdnaStatusGetConfig) {
        return 0;
    }

    @Override
    public int onCheckChallengeResponseStatus(RDNA.RDNAStatusCheckChallengeResponse rdnaStatusCheckChallengeResponse) {
        return 0;
    }

    @Override
    public int onGetAllChallengeStatus(RDNA.RDNAStatusGetAllChallenges rdnaStatusGetAllChallenges) {
        return 0;
    }

    @Override
    public int onUpdateChallengeStatus(RDNA.RDNAStatusUpdateChallenges rdnaStatusUpdateChallenges) {
        return 0;
    }

    @Override
    public int onGetPostLoginChallenges(RDNA.RDNAStatusGetPostLoginChallenges rdnaStatusGetPostLoginChallenges) {
        return 0;
    }

    @Override
    public int onLogOff(RDNA.RDNAStatusLogOff rdnaStatusLogOff) {
        return 0;
    }

    @Override
    public RDNA.RDNAIWACreds getCredentials(String s) {
        return null;
    }

    @Override
    public String getApplicationName() {
        return null;
    }

    @Override
    public String getApplicationVersion() {
        return null;
    }

    @Override
    public int onGetRegistredDeviceDetails(RDNA.RDNAStatusGetRegisteredDeviceDetails rdnaStatusGetRegisteredDeviceDetails) {
        return 0;
    }

    @Override
    public int onUpdateDeviceDetails(RDNA.RDNAStatusUpdateDeviceDetails rdnaStatusUpdateDeviceDetails) {
        return 0;
    }

    @Override
    public int onGetNotificationsHistory(RDNA.RDNAStatusGetNotificationHistory rdnaStatusGetNotificationHistory) {
        return 0;
    }

    @Override
    public int onSessionTimeout(String s) {
        return 0;
    }

    @Override
    public int onSdkLogPrintRequest(RDNA.RDNALoggingLevel rdnaLoggingLevel, String s) {
        return 0;
    }

    @Override
    public int onSecurityThreat(String s) {
        return 0;
    }
}
