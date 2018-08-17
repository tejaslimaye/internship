package com.jobdetails.shant.getjobdetails.Activity;


import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jobdetails.shant.getjobdetails.Beans.JobDetailBean;
import com.jobdetails.shant.getjobdetails.Common.Constant;
import com.jobdetails.shant.getjobdetails.Common.TestAPIResult;
import com.jobdetails.shant.getjobdetails.Network.APIClient;
import com.jobdetails.shant.getjobdetails.Beans.Mob_Details;
import com.jobdetails.shant.getjobdetails.Protocol.APIInterface;
import com.jobdetails.shant.getjobdetails.Protocol.CountUpdater;
import com.jobdetails.shant.getjobdetails.R;
import com.jobdetails.shant.getjobdetails.TestCaseActivity.UserSessionActivity;
import com.uniken.rdna.RDNA;

import retrofit2.Call;
import retrofit2.Callback;

import static com.jobdetails.shant.getjobdetails.Common.Constant.rdnaInitSuccess;
import static com.uniken.rdna.RDNA.Initialize;
import static com.uniken.rdna.RDNA.getSDKVersion;


public class MainActivity extends AppCompatActivity implements CountUpdater{

    public TextView totalTest;
    public TextView completedTest;
    public  TextView passedTest;
    public  TextView failedTest;
    public  TextView cannotTest;
    public static TextView featureName;
    public static TextView testName;
    public static TextView testCaseDesc;
    public static TextView startTime;
    public TextView inProgress;
    private static String TAG ="";
    public Button btn;             //For Start Test Button";
    public JobDetailBean jobDetailBeanGL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFormData();
        getTestCasesData();

    }

    /*
    * get data from server
    *
    * */
    private void getTestCasesData() {

//        String lib_version = getLibraryVersion();
        String lib_version = "1.9.1";

        Mob_Details mob_details = new Mob_Details("NEW19",Build.MODEL,"Android",Build.VERSION.RELEASE,Build.ID,Build.MANUFACTURER,Build.BRAND,lib_version);

        Log.d(TAG,"Serial = " +mob_details.getSerial_num());
        Log.d(TAG,"Model = " +mob_details.getDevice_model());
        Log.d(TAG,"OS = " +mob_details.getDevice_os());
        Log.d(TAG,"OS version  = " +mob_details.getOs_version());
        Log.d(TAG,"ID = " +mob_details.getBuild_id());
        Gson gson = new Gson();
        String json = gson.toJson(mob_details);

        APIInterface apiInterface = APIClient.getJobDetailJSON().create(APIInterface.class);
        Call<JobDetailBean> jsonAPICall = apiInterface.getTestCasesJob(mob_details);
//        Call<JobDetailBean> jsonAPICall = apiInterface.getTestCasesDummyJob();

        jsonAPICall.enqueue(new Callback<JobDetailBean>() {
            @Override
            public void onResponse(Call<JobDetailBean> call, retrofit2.Response<JobDetailBean> response) {
                JobDetailBean jobDetailBean = response.body();
                if (jobDetailBean != null) {
                    jobDetailBeanGL = jobDetailBean;
                    checkJobAvailability(jobDetailBean);
                } else {
                    Toast.makeText(MainActivity.this, "Empty Response.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<JobDetailBean> call, Throwable t) {
                Log.d("response","not received");
                Toast.makeText(MainActivity.this,"Not able to connect server.",Toast.LENGTH_LONG).show();
            }
        });

    }

    //initialize the fields
    private void initFormData(){

        btn = (Button) findViewById(R.id.add);
        totalTest=(TextView) findViewById(R.id.txtTotalValue);
        completedTest=(TextView) findViewById(R.id.txtCompletedValue);
        passedTest=(TextView) findViewById(R.id.txtPassesValue);
        failedTest = (TextView) findViewById(R.id.txtFailedValue);
        cannotTest = (TextView) findViewById(R.id.txtCannotTestValue);
        featureName = (TextView) findViewById(R.id.txtFeatureName_Value);
        testName=(TextView) findViewById(R.id.txtTestName_Value);
        testCaseDesc=(TextView) findViewById(R.id.txtTestDesc_Value);
        startTime= (TextView) findViewById(R.id.txtStartTime_Value);
        inProgress = (TextView) findViewById(R.id.txtInProgress);


    }

    public void checkJobAvailability(JobDetailBean jobDetailBean) {     // For Parsing Json response from Server

        if (jobDetailBean.getJobAvailCode() == 0) {
            inProgress.setText("No Tests Available");
        } else {
/*            TestAPIResult testResult = new TestAPIResult(this.getApplication(), MainActivity.this);
            testResult.startTestExecution(jobDetailBean);*/
            JobDetailBean.ServerExecutionDetail serverBean = jobDetailBeanGL.getServerExecutionDetails().get(0);

            String agentInfo = serverBean.getAgentInfo();
            int authGateWayPort = serverBean.getSdkPort();
            String authGateWayIP = serverBean.getIpAddress();
            APIResponseCallBacks callBacks = new APIResponseCallBacks(this.getApplication());

            RDNA.RDNALoggingLevel loggingLevel = RDNA.RDNALoggingLevel.RDNA_LOG_DEBUG;
            RDNA.RDNAStatus<RDNA> objRDNA = Initialize(agentInfo, callBacks, authGateWayIP, authGateWayPort, null, null, null, null, null, loggingLevel, this);//  obj.result.

            if(!rdnaInitSuccess){
                Constant.objRDNA = objRDNA;
            }
        }

    }


    private String getLibraryVersion() {

        String version = getSDKVersion();
        Log.d(TAG,"LibraryVersion: "+ version);
        //return version;
        return "1.0";

    }


    @Override
    public void updateCount(int totalCases, int completedCases, int passCases, int failedCases, int notTetstedCases, String progress) {

        totalTest.setText(String.valueOf(totalCases));
        completedTest.setText(String.valueOf(completedCases));
        passedTest.setText(String.valueOf(passCases));
        failedTest.setText(String.valueOf(failedCases));
        cannotTest.setText(String.valueOf(notTetstedCases));
        inProgress.setText(progress);

    }


    public class APIResponseCallBacks implements RDNA.RDNACallbacks {

        Application mainApplication;

        public APIResponseCallBacks(Application application) {
            this.mainApplication = application;
        }

        @Override
        public int onInitializeCompleted(final RDNA.RDNAStatusInit rdnaStatusInit) {
            if (rdnaStatusInit.errCode == 0) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("UI thread:", "on main thread");
                        if (rdnaStatusInit.errCode == 0) {
                            rdnaInitSuccess = true;
                        }

/*                        TestAPIResult testResult = new TestAPIResult(mainApplication, MainActivity.this);
                        testResult.startTestExecution(jobDetailBeanGL);*/


                        UserSessionActivity userSessionActivity = new UserSessionActivity();
                        userSessionActivity.testCustomEnrollUser();

                    }
                });

                return 0;
            } else {

                Log.e("Error:", "Not initialised, Error while init from SDK.");
                return 1;
            }
        }

        @Override
        public Context getDeviceContext() {
            return mainApplication.getApplicationContext();
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
}
