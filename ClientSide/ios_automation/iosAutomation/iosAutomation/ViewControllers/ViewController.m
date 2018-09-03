//
//  ViewController.m
//  iosAutomation
//
//  Created by cps on 26/07/18.
//  Copyright © 2018 cps. All rights reserved.
//

#import "ViewController.h"
#import "webservice.h"
#import "QTestCaseWelcome.h"
#import "RDNAInitialize.h"
#import "StringConstants.h"
#import "QTestCaseResultUpdate.h"
#import "QTDeviceInfo.h"
#import "TestEnrollUser.h"
#import "QTestEnrolluserResult.h"
#import "UIStateMachine.h"
#import "AppDelegate.h"
#import "Utility.h"
#import <netdb.h>
#include <arpa/inet.h>
@interface ViewController ()
{
    NSString *mainstr;
    int countForPassTest;
    int countForFailTest;
    int countCompleted;
    int countCannotTest;
    int Port;
    int totalTestCount;
    NSString * agentInfo;
    NSString * Host;
    RDNAInitialize *rdnaInitialize;
    RDNA *rdnaObject;  //This is the public RDNA object which will be initialized in the initializeRDNA
    // NSString * const agentInfo;
    QTestCaseResultUpdate *TestCaseResultUpdate;
    TestEnrollUser *testEnrolluser;
    QTDeviceInfo *deviceinfo;
    QTestEnrolluserResult *enrolluser;
    NSString *endTime;
    NSString *startTime;
    NSString *agentWronInfo;
    NSArray *_testjobexecutionsArray;
    int wrongPort;
    int terminate;
    __block dispatch_semaphore_t semaphore;
    int CountForInit;
    int index;
    NSArray *testServices;
    BOOL firstTimeCallInitialize;
    NSMutableData *responseData;
    int ServicePort;
    RDNAChallenge *checkUserChallenge;
    
    
    id<LogOffCallbacks> logOffCallback; //this is delegate object for respond to logoff operation.
    id<GetAllChallengesCallbacks> getAllChallengesCallback; //this is delegate object for respond to get all challenges operation.
    id<GetPostLoginAuthenticationChallengesCallback>getPostLoginAuthenticationChallenges; //this is delegate object for respond to post login authentication challenge operation.
    id<GetRegisteredDevicesCallback> getRegisteredDevicesCallback; //this is delegate object for respond to registered device operation.
}


@property (nonatomic, assign) APIType currentAPIType;



@end

@implementation ViewController
- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view, typically from a nib.
    countForPassTest = 0;
    countForFailTest = 0;
    countCompleted =  0;
    totalTestCount = 0;
    countCannotTest = 0;
    index = -1;
    firstTimeCallInitialize = false;
    Port = 4443;
    Host = @"18.206.222.73";
    agentInfo = @"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";
    
    self.passedTestCount.text = [NSString stringWithFormat:@"%d",countForPassTest];
    self.failedTestCount.text = [NSString stringWithFormat:@"%d",countForFailTest];
    self.CannotTestCount.text = [NSString stringWithFormat:@"%d",countCannotTest];
    
    TestCaseResultUpdate = [[QTestCaseResultUpdate alloc]init];
    testEnrolluser = [[TestEnrollUser alloc]init];
    deviceinfo = [[QTDeviceInfo alloc]init];
    //  semaphore = dispatch_semaphore_create(0);
    testServices = [[NSArray alloc]init];
    /* rdnaInitialize = [[RDNAInitialize alloc]init];
     rdnaInitialize.host = @"10.0.5.21";
     rdnaInitialize.agentInfo = @"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"; */
    
    // [self doSomethingWithTheJson];
    
    
    int status = [self initializeRDNA:Port andHost:Host andAgentInfo:agentInfo];
    RDNAStatusInit *rdnaStatusinit = [[RDNAStatusInit alloc]init];
    rdnaStatusinit.errCode = status;
    if (rdnaStatusinit.errCode == 0) {
        // firstTimeCallInitialize = true;
        startTime = [self calculateTime];
        NSLog(@"startTime :- %@",startTime);
        
        
    }else{
    }
    
    
    
    
    
    
    
    
    
    
    // [self requestdata];
}


- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (int) initializeRDNA :(int)port andHost:(NSString *)host andAgentInfo:(NSString *)agentInfo {
    __block int errorID = 0;
    //dispatch_async(dispatch_get_global_queue( DISPATCH_QUEUE_PRIORITY_DEFAULT, 0), ^(void){
    RDNA *rdna;
    self->clientCallbacks = self;
    errorID = [RDNA initialize:&rdna AgentInfo:agentInfo Callbacks:self->clientCallbacks GatewayHost:host GatewayPort:port CipherSpec:nil CipherSalt:nil ProxySettings:nil RDNASSLCertificate:NULL DNSServerList:nil RDNALoggingLevel:RDNA_LOG_VERBOSE AppContext:self];
    //Background Thread
    if (self->rdnaObject == nil) {
        self->rdnaObject =rdna;
    }
    
    // });
    return errorID;
    
    
}








- (NSArray *)fetchAllServices {
    
    NSArray *allServiceArray;
    int errorgetAllServiceArray = [rdnaObject getAllServices:&allServiceArray];
    if (errorgetAllServiceArray == 0) {
        // countForPassTest = countForPassTest +1;
        // [self passTestCase:countForPassTest];
        
    }else{
        //countForFailTest = countForFailTest+1;
        // [self FailTestCase:countForFailTest];
        
    }
    NSLog(@"service error status code :- %d",errorgetAllServiceArray);
    
    
    
    return [allServiceArray copy];
    //  [getAllServiceArray addObjectsFromArray:getAllServiceArray];
    //  return errorgetAllServiceArray;
}

-(void)requestdata
{
    NSString *sdkVersion = [RDNA getSDKVersion];
    NSLog(@"sdkVersion :- %@",sdkVersion);
    
    mainstr = [NSString stringWithFormat:@"http://34.235.131.241:8080/automation/getJobDetails.htm"];
    // mainstr = [NSString stringWithFormat:@"http://10.0.0.135:8080/automation/getJobDetails.htm"];
    
    //NSDictionary *mapData = [deviceinfo JSONDictionary];
    //NSLog(@"mapData :- %@",mapData);
    
    NSDictionary *mapData = @{
                              @"serial_num":@"my phone 458",
                              @"device_model":@"iphone",
                              @"device_os":@"ios",
                              @"os_version":@"123",
                              @"build_id":@"buildID",
                              @"manufacturer":@"mannufacturer1",
                              @"brand":@"brand1",
                              @"library_version":sdkVersion
                              };
    
    /* NSDictionary *mapData = @{
     @"serial_num":@"my phone 353",
     @"device_model":[[UIDevice currentDevice]model],
     @"device_os":[[UIDevice currentDevice]systemName],
     @"os_version":[[UIDevice currentDevice]systemVersion],
     @"build_id":[[NSBundle mainBundle] objectForInfoDictionaryKey:@"CFBundleVersion"],
     @"manufacturer":@"Apple",
     @"brand":@"brand1",
     @"library_version":sdkVersion
     };*/
    
    
    //NSLog(@"mapData :- %@",mapData);
    
    [webservice executequery:mainstr strprameter:mapData withblock:^(QTestCaseWelcome * welcome, NSError *error) {
        NSLog(@"Data: %@", welcome);
        
        if (welcome!=nil)
        {
            QTestCaseTestjobexecution *testjobexecutions;
            //  QTestCaseWelcome *welcome = [self JSONFromFile];
            for (QTestCaseServerExecutionDetail *serverExecutionDetails in welcome.serverExecutionDetails) {
                //rdnaInitialize.port = (int)serverExecutionDetails.sdkPort;
                self->Port = (int)serverExecutionDetails.sdkPort;
                self->agentInfo = serverExecutionDetails.agentInfo;
                self->Host = serverExecutionDetails.ipAddress;
                NSLog(@"port :- %d",self->Port);
                for (testjobexecutions in serverExecutionDetails.testjobexecutions ) {
                    self.testjobexecutions = testjobexecutions;
                    NSLog(@"testjobexecutions :- %@",self.testjobexecutions);
                    dispatch_async(dispatch_get_main_queue(), ^(void){
                        //Run UI Updates
                        self.totalTestCount.text = [NSString stringWithFormat:@"%lu",(unsigned long)self->_testjobexecutions.executions.count];
                        self->totalTestCount = [self.totalTestCount.text intValue];
                    });
                    self->_testjobexecutionsArray =self->_testjobexecutions.executions;
                    [self testExecute];
                    
                }
                
            }
        }
    }];
    
    /* HardCoded JSON */
    // [self doSomethingWithTheJson];
    
}



-(void)testExecute{
    index++;
    if(index < _testjobexecutionsArray.count){
        NSLog(@"index :- %d",index);
        self.executions = [_testjobexecutionsArray objectAtIndex:index];
        if ([self.executions.featureName isEqualToString:@"Initialization on Mobile"]) {
            if ([self.executions.testcaseName isEqualToString:@"INIT"]) {
                self->TestCaseResultUpdate.executionID = self->_executions.executionID;
                NSLog(@"self->TestCaseResultUpdate.executionID :- %ld",self->TestCaseResultUpdate.executionID);
                NSString *testCaseName = self->_executions.testcaseName;
                [self checkTestCaseMethodInit:testCaseName];
                
            }
            else if ([self.executions.testcaseName isEqualToString:@"INIT_FAIL_WRONG_APPID"])
            {
                self->TestCaseResultUpdate.executionID = self->_executions.executionID;
                NSLog(@"self->TestCaseResultUpdate.executionID :- %ld",self->TestCaseResultUpdate.executionID);
                NSString *testCaseName = self->_executions.testcaseName;
                [self checkTestCaseMethodInit:testCaseName];
               
                
            }else if ([self.executions.testcaseName isEqualToString:@"INIT_FAIL_WRONG_PORT"])
            {
                self->TestCaseResultUpdate.executionID = self->_executions.executionID;
                NSLog(@"self->TestCaseResultUpdate.executionID :- %ld",self->TestCaseResultUpdate.executionID);
                NSString *testCaseName = self->_executions.testcaseName;
                [self checkTestCaseMethodInit:testCaseName];
                
            }
            else if ([self.executions.testcaseName isEqualToString:@"TERMINATE"])
            {
                self->TestCaseResultUpdate.executionID = self->_executions.executionID;
                NSLog(@"self->TestCaseResultUpdate.executionID :- %ld",self->TestCaseResultUpdate.executionID);
                NSString *testCaseName = self->_executions.testcaseName;
                [self checkTestCaseMethodInit:testCaseName];
                
            }
            
            
            else{
                NSLog(@"TestCaseName In Initialization on Mobile :- %@",self.executions.testcaseName);
                startTime = [self calculateTime];
                endTime = [self calculateTime];
                NSDictionary *dictCannotTest = @{
                                                 @"execution_id" :[NSNumber numberWithInteger:self->_executions.executionID],
                                                 @"execution_result":@"CANNOT_TEST",
                                                 @"start_time":self->startTime,
                                                 @"end_time":self->endTime,
                                                 @"params_used":@"",
                                                 @"result_data":@""
                                                 };
                
                NSLog(@"dictPass :- %@",dictCannotTest);
                    self->countCannotTest = self->countCannotTest +1 ;
                [self requestUpdateResultCannotTestCount:dictCannotTest WithCannotTestCount:self->countCannotTest];
                
                //
            }
            
        }
        else  if ([self.executions.featureName isEqualToString:@"SERVICE_INFO"]) {
            if ([self.executions.testcaseName isEqualToString:@"GET_ALL_SERVICES"]) {
                self->TestCaseResultUpdate.executionID = self->_executions.executionID;
                NSLog(@"self->TestCaseResultUpdate.executionID For GET_ALL_SERVICES :- %ld",self->TestCaseResultUpdate.executionID);
                NSString *testCaseName = self.executions.testcaseName;
                [self checkServiceInfoTestCases:testCaseName withRDNAService:testServices];
            }
            
            
            else if ([self.executions.testcaseName isEqualToString:@"GET_SERVICE_BY_SERVICE_NAME"]) {
                self->TestCaseResultUpdate.executionID = self->_executions.executionID;
                NSLog(@"self->TestCaseResultUpdate.executionID For GET_SERVIICE_BY_SERVICE_NAME :- %ld",self->TestCaseResultUpdate.executionID);
                NSString *testCaseName = self.executions.testcaseName;
                [self checkServiceInfoTestCases:testCaseName withRDNAService:testServices];
            }
            else if ([self.executions.testcaseName isEqualToString:@"GET_SERVICE_BY_WRONG_SERVICE_NAME"]) {
                self->TestCaseResultUpdate.executionID = self->_executions.executionID;
                NSLog(@"self->TestCaseResultUpdate.executionID For GET_SERVIICE_BY_WRONG_SERVICE_NAME :- %ld",self->TestCaseResultUpdate.executionID);
                NSString *testCaseName = self.executions.testcaseName;
                [self checkServiceInfoTestCases:testCaseName withRDNAService:testServices];
            }
            else if ([self.executions.testcaseName isEqualToString:@"GET_SERVICE_BY_TARGET_COORDINATE"])
            {
                self->TestCaseResultUpdate.executionID = self->_executions.executionID;
                NSLog(@"self->TestCaseResultUpdate.executionID For  :- %ld",self->TestCaseResultUpdate.executionID);
                NSString *testCaseName = self.executions.testcaseName;
                [self checkServiceInfoTestCases:testCaseName withRDNAService:testServices];
                
            }
            else if ([self.executions.testcaseName isEqualToString:@"GET_SERVICE_BY_TARGET_COORDINATE_WRONG_IP"])
            {
                self->TestCaseResultUpdate.executionID = self->_executions.executionID;
                NSLog(@"self->TestCaseResultUpdate.executionID :- %ld",self->TestCaseResultUpdate.executionID);
                NSString *testCaseName = self.executions.testcaseName;
                [self checkServiceInfoTestCases:testCaseName withRDNAService:testServices];
                
            }
            else if ([self.executions.testcaseName isEqualToString:@"GET_SERVICE_BY_TARGET_COORDINATE_WRONG_PORT"])
            {
                self->TestCaseResultUpdate.executionID = self->_executions.executionID;
                NSLog(@"self->TestCaseResultUpdate.executionID :- %ld",self->TestCaseResultUpdate.executionID);
                NSString *testCaseName = self.executions.testcaseName;
                [self checkServiceInfoTestCases:testCaseName withRDNAService:testServices];
                
            }
            else if ([self.executions.testcaseName isEqualToString:@"GET_SERVICE_BY_TARGET_COORDINATE_WRONG_IP_AND_PORT"])
            {
                self->TestCaseResultUpdate.executionID = self->_executions.executionID;
                NSLog(@"self->TestCaseResultUpdate.executionID :- %ld",self->TestCaseResultUpdate.executionID);
                NSString *testCaseName = self.executions.testcaseName;
                [self checkServiceInfoTestCases:testCaseName withRDNAService:testServices];
                
            }
        }else if ([self.executions.featureName isEqualToString:@"SERVICE_ACCESS"])
        {
            if ([self.executions.testcaseName isEqualToString:@"SERVICE_ACCESS_START_WRONG_SERVICE_NAME"])
            {
                self->TestCaseResultUpdate.executionID = self->_executions.executionID;
                NSLog(@"self->TestCaseResultUpdate.executionID :- %ld",self->TestCaseResultUpdate.executionID);
                NSString *testCaseName = self.executions.testcaseName;
                [self checkServiceInfoTestCases:testCaseName withRDNAService:nil];
                
            }
            else if ([self.executions.testcaseName isEqualToString:@"SERVICE_ACCESS_START_RUNNING_SERVICE"])
            {
                self->TestCaseResultUpdate.executionID = self->_executions.executionID;
                NSLog(@"self->TestCaseResultUpdate.executionID :- %ld",self->TestCaseResultUpdate.executionID);
                NSString *testCaseName = self.executions.testcaseName;
                [self checkServiceInfoTestCases:testCaseName withRDNAService:testServices];
            }
            else if ([self.executions.testcaseName isEqualToString:@"SERVICE_ACCESS_START_STOPPED_SERVICE"])
            {
                self->TestCaseResultUpdate.executionID = self->_executions.executionID;
                NSLog(@"self->TestCaseResultUpdate.executionID :- %ld",self->TestCaseResultUpdate.executionID);
                NSString *testCaseName = self.executions.testcaseName;
                [self checkServiceInfoTestCases:testCaseName withRDNAService:testServices];
            }
            else if ([self.executions.testcaseName isEqualToString:@"SERVICE_ACCESS_STOP_WRONG_SERVICE_NAME"])
            {
                self->TestCaseResultUpdate.executionID = self->_executions.executionID;
                NSLog(@"self->TestCaseResultUpdate.executionID :- %ld",self->TestCaseResultUpdate.executionID);
                NSString *testCaseName = self.executions.testcaseName;
                [self checkServiceInfoTestCases:testCaseName withRDNAService:nil];
            }
            else if ([self.executions.testcaseName isEqualToString:@"SERVICE_ACCESS_STOP_RUNNING_SERVICE"])
            {
                self->TestCaseResultUpdate.executionID = self->_executions.executionID;
                NSLog(@"self->TestCaseResultUpdate.executionID :- %ld",self->TestCaseResultUpdate.executionID);
                NSString *testCaseName = self.executions.testcaseName;
                [self checkServiceInfoTestCases:testCaseName withRDNAService:testServices];
                
            }
            else if ([self.executions.testcaseName isEqualToString:@"SERVICE_ACCESS_STOP_STOPPED_SERVICE"])
            {
                self->TestCaseResultUpdate.executionID = self->_executions.executionID;
                NSLog(@"self->TestCaseResultUpdate.executionID :- %ld",self->TestCaseResultUpdate.executionID);
                NSString *testCaseName = self.executions.testcaseName;
                [self checkServiceInfoTestCases:testCaseName withRDNAService:testServices];
                
            }
            else if ([self.executions.testcaseName isEqualToString:@"SERVICE_ACCESS_START_ALL_SERVICE"])
            {
                self->TestCaseResultUpdate.executionID = self->_executions.executionID;
                NSLog(@"self->TestCaseResultUpdate.executionID :- %ld",self->TestCaseResultUpdate.executionID);
                NSString *testCaseName = self.executions.testcaseName;
                [self checkServiceInfoTestCases:testCaseName withRDNAService:testServices];
                
            }
            else if ([self.executions.testcaseName isEqualToString:@"SERVICE_ACCESS_STOP_ALL_SERVICE"])
            {
                self->TestCaseResultUpdate.executionID = self->_executions.executionID;
                NSLog(@"self->TestCaseResultUpdate.executionID :- %ld",self->TestCaseResultUpdate.executionID);
                NSString *testCaseName = self.executions.testcaseName;
                [self checkServiceInfoTestCases:testCaseName withRDNAService:testServices];
                
            }
            
            else{
                NSLog(@"TestCaseName in Service Access and Service info :- %@",self.executions.testcaseName);
                startTime = [self calculateTime];
                endTime = [self calculateTime];
                NSDictionary *dictCannotTest = @{
                                                 @"execution_id" :[NSNumber numberWithInteger:self->_executions.executionID],
                                                 @"execution_result":@"CANNOT_TEST",
                                                 @"start_time":self->startTime,
                                                 @"end_time":self->endTime,
                                                 @"params_used":@"",
                                                 @"result_data":@""
                                                 };
                
                NSLog(@"dictPass :- %@",dictCannotTest);
                
                    self->countCannotTest = self->countCannotTest +1 ;
                [self requestUpdateResultCannotTestCount:dictCannotTest WithCannotTestCount:self->countCannotTest];
                
                //
            }
        }
        else if ([self.executions.featureName isEqualToString:@"INFO_GETTERS"])
        {
            NSMutableString *str = [[NSMutableString alloc]init];
            if ([self.executions.testcaseName isEqualToString:@"GET_AGENT_ID"])
            {
                self->TestCaseResultUpdate.executionID = self->_executions.executionID;
                NSLog(@"self->TestCaseResultUpdate.executionID :- %ld",self->TestCaseResultUpdate.executionID);
                
                [self checkInfoGetterTestCases:self.executions.testcaseName withMutableString:str withRDNAStatus:0];
                
                
            }else if ([self.executions.testcaseName isEqualToString:@"GET_DEVICE_ID"])
            {
                self->TestCaseResultUpdate.executionID = self->_executions.executionID;
                NSLog(@"self->TestCaseResultUpdate.executionID :- %ld",self->TestCaseResultUpdate.executionID);
                [self checkInfoGetterTestCases:self.executions.testcaseName withMutableString:str withRDNAStatus:0];
                
            }else if ([self.executions.testcaseName isEqualToString:@"GET_SESSION_ID"])
            {
                self->TestCaseResultUpdate.executionID = self->_executions.executionID;
                NSLog(@"self->TestCaseResultUpdate.executionID :- %ld",self->TestCaseResultUpdate.executionID);
                [self checkInfoGetterTestCases:self.executions.testcaseName withMutableString:str withRDNAStatus:0];
                
            }
            else if ([self.executions.testcaseName isEqualToString:@"GET_ERROR_CODE"])
            {
                self->TestCaseResultUpdate.executionID = self->_executions.executionID;
                NSLog(@"self->TestCaseResultUpdate.executionID :- %ld",self->TestCaseResultUpdate.executionID);
                [self checkInfoGetterTestCases:self.executions.testcaseName withMutableString:nil withRDNAStatus:0];
                
            }else if ([self.executions.testcaseName isEqualToString:@"GET_API_SDK_VERSION"])
            {
                startTime = [self calculateTime];
                self->TestCaseResultUpdate.executionID = self->_executions.executionID;
                NSLog(@"self->TestCaseResultUpdate.executionID :- %ld",self->TestCaseResultUpdate.executionID);
                NSString *sdkVersion = [RDNA getSDKVersion];
                NSLog(@"sdkVersion :- %@",sdkVersion);
                
                if (sdkVersion) {
                    endTime = [self calculateTime];
                    countForPassTest = countForPassTest+1;
                    [self passTestCase:countForPassTest];
                    NSDictionary *dictPass = @{
                                               @"execution_id":[NSNumber numberWithInteger:TestCaseResultUpdate.executionID],
                                               @"execution_result":@"PASSED",
                                               @"start_time":self->startTime,
                                               @"end_time":self->endTime,
                                               @"params_used":@"",
                                               @"result_data":@""
                                               };
                    
                    //dispatch_async(dispatch_get_main_queue(), ^(void){
                        [self requestUpdateResult:dictPass];
                    //});
                }else{
                    endTime = [self calculateTime];
                    countForFailTest = countForFailTest+1;
                    [self FailTestCase:countForFailTest];
                    NSDictionary *dictFail = @{
                                               @"execution_id":[NSNumber numberWithInteger:TestCaseResultUpdate.executionID],
                                               @"execution_result":@"FAILED",
                                               @"start_time":self->startTime,
                                               @"end_time":self->endTime,
                                               @"params_used":@"",
                                               @"result_data":@""
                                               };
                    
                   // dispatch_async(dispatch_get_main_queue(), ^(void){
                        [self requestUpdateResult:dictFail];
                    //});
                }
                
                
            }else if ([self.executions.testcaseName isEqualToString:@"GET_DEFAULT_CIPHER_SPEC"])
            {
                self->TestCaseResultUpdate.executionID = self->_executions.executionID;
                NSLog(@"self->TestCaseResultUpdate.executionID :- %ld",self->TestCaseResultUpdate.executionID);
                
                [self checkDataPrivacyTestCases:self.executions.testcaseName withMutableString:str];
                
            }
            else if ([self.executions.testcaseName isEqualToString:@"GET_DEFAULT_CIPHER_SALT"])
            {
                self->TestCaseResultUpdate.executionID = self->_executions.executionID;
                NSLog(@"self->TestCaseResultUpdate.executionID :- %ld",self->TestCaseResultUpdate.executionID);
                [self checkDataPrivacyTestCases:self.executions.testcaseName withMutableString:str];
                
            }
            else{
                
                NSLog(@"TestCaseName in Info Getters :- %@",self.executions.testcaseName);
                startTime = [self calculateTime];
                endTime = [self calculateTime];
                NSDictionary *dictCannotTest = @{
                                                 @"execution_id" :[NSNumber numberWithInteger:self->_executions.executionID],
                                                 @"execution_result":@"CANNOT_TEST",
                                                 @"start_time":self->startTime,
                                                 @"end_time":self->endTime,
                                                 @"params_used":@"",
                                                 @"result_data":@""
                                                 };
                
                NSLog(@"dictPass :- %@",dictCannotTest);
                    self->countCannotTest = self->countCannotTest +1 ;
                [self requestUpdateResultCannotTestCount:dictCannotTest WithCannotTestCount:self->countCannotTest];
                
                
                //
            }
            
        }
        else if ([self.executions.featureName isEqualToString:@"DATA_PRIVACY"])
        {
            NSLog(@"DATA_PRIVACY_TestCasesName :- %@",self.executions.testcaseName);
            if ([self.executions.testcaseName isEqualToString:@"ENCRYPT_DATA_PACKET"])
            {
                self->TestCaseResultUpdate.executionID = self->_executions.executionID;
                NSLog(@"self->TestCaseResultUpdate.executionID :- %ld",self->TestCaseResultUpdate.executionID);
                [self checkDataPrivacyTestCases:self.executions.testcaseName withMutableString:nil];
            }
            else if ([self.executions.testcaseName isEqualToString:@"ENCRYPT_DATA_PACKET_EMPTY_CIPHERSPEC"])
            {
                self->TestCaseResultUpdate.executionID = self->_executions.executionID;
                NSLog(@"self->TestCaseResultUpdate.executionID :- %ld",self->TestCaseResultUpdate.executionID);
                [self checkDataPrivacyTestCases:self.executions.testcaseName withMutableString:nil];
            }
            else if ([self.executions.testcaseName isEqualToString:@"ENCRYPT_DATA_PACKET_GET_EMPTY_CIPHERSALT"])
            {
                self->TestCaseResultUpdate.executionID = self->_executions.executionID;
                NSLog(@"self->TestCaseResultUpdate.executionID :- %ld",self->TestCaseResultUpdate.executionID);
                [self checkDataPrivacyTestCases:self.executions.testcaseName withMutableString:nil];
            }
            else if ([self.executions.testcaseName isEqualToString:@"DECRYPT_DATA_PACKET"])
            {
                self->TestCaseResultUpdate.executionID = self->_executions.executionID;
                NSLog(@"self->TestCaseResultUpdate.executionID :- %ld",self->TestCaseResultUpdate.executionID);
                [self checkDataPrivacyTestCases:self.executions.testcaseName withMutableString:nil];
            }
            else if ([self.executions.testcaseName isEqualToString:@"DECRYPT_DATA_PACKET_EMPTY_CIPHERSPEC"])
            {
                self->TestCaseResultUpdate.executionID = self->_executions.executionID;
                NSLog(@"self->TestCaseResultUpdate.executionID :- %ld",self->TestCaseResultUpdate.executionID);
                [self checkDataPrivacyTestCases:self.executions.testcaseName withMutableString:nil];
            }
            else if ([self.executions.testcaseName isEqualToString:@"DECRYPT_DATA_PACKET_EMPTY_CIPHERSALT"])
            {
                self->TestCaseResultUpdate.executionID = self->_executions.executionID;
                NSLog(@"self->TestCaseResultUpdate.executionID :- %ld",self->TestCaseResultUpdate.executionID);
                [self checkDataPrivacyTestCases:self.executions.testcaseName withMutableString:nil];
            }
            else if ([self.executions.testcaseName isEqualToString:@"DECRYPT_DATA_PACKET_GET_EMPTY_PLAINTEXT"])
            {
                self->TestCaseResultUpdate.executionID = self->_executions.executionID;
                NSLog(@"self->TestCaseResultUpdate.executionID :- %ld",self->TestCaseResultUpdate.executionID);
                [self checkDataPrivacyTestCases:self.executions.testcaseName withMutableString:nil];
            }
            else{
                NSLog(@"TestCaseName in DATA_PRIVACY :- %@",self.executions.testcaseName);
                startTime = [self calculateTime];
                endTime = [self calculateTime];
                NSDictionary *dictCannotTest = @{
                                                 @"execution_id" :[NSNumber numberWithInteger:self->_executions.executionID],
                                                 @"execution_result":@"CANNOT_TEST",
                                                 @"start_time":self->startTime,
                                                 @"end_time":self->endTime,
                                                 @"params_used":@"",
                                                 @"result_data":@""
                                                 };
                
                NSLog(@"dictPass :- %@",dictCannotTest);
                    self->countCannotTest = self->countCannotTest +1 ;
                [self requestUpdateResultCannotTestCount:dictCannotTest WithCannotTestCount:self->countCannotTest];
                
                
                
                
                //
            }
        }
        else if ([self.executions.featureName isEqualToString:@"USER_SESSION_API"])
        {
            if ([self.executions.testcaseName isEqualToString:@"ENROLL_USER"])
            {
                self->TestCaseResultUpdate.executionID = self->_executions.executionID;
                NSMutableString *string = [[NSMutableString alloc]init];
                int sessionId = [self->rdnaObject getSessionID:&string];
                NSLog(@"sessionId String:- %@",string);
                NSLog(@"SesionId :- %d",sessionId);
                self->testEnrolluser.sessionId = string;
                
                NSDictionary *dictEnrollUser = @{
                                                 @"firstName":@"onkar",
                                                 @"lastName":@"kulkarni",
                                                 @"userId":@"omi23",
                                                 @"actCode":@"detfjt",
                                                 @"groupName":@"group1",
                                                 @"emailId":@"onkar.kulkarni@uniken.com",
                                                 @"mobNum":@"9168086688",
                                                 @"username":@"sruser",
                    @"password":@"1e99b14aa45d6add97271f8e06adacda4e521ad98a4ed18e38cfb0715e7841d2",
                                                 @"isRelIdVerifyEnabled":@"true",
                                                 @"sessionId":self->testEnrolluser.sessionId,
                                                 @"apiversion":@"v1"
                                                 };
                    self->testEnrolluser = [[TestEnrollUser alloc]initWithDict:dictEnrollUser];
                self->enrolluser = [self requestEnrollUser:self->testEnrolluser andExecutionId:self->TestCaseResultUpdate.executionID];
                    
                 /*    dispatch_async(dispatch_get_main_queue(), ^(void){
                         
                         if (self->enrolluser.isError == NO) {
                             
                             self->endTime = [self calculateTime];
                             self->countForPassTest = self->countForPassTest+1;
                             [self passTestCase:self->countForPassTest];
                             NSDictionary *dictPass = @{
                                                        @"execution_id":[NSNumber numberWithInteger:self->TestCaseResultUpdate.executionID],
                                                        @"execution_result":@"PASSED",
                                                        @"start_time":self->startTime,
                                                        @"end_time":self->endTime,
                                                        @"params_used":@"",
                                                        @"result_data":@""
                                                        };
                             
                             dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0), ^{
                                 [self requestUpdateResult:dictPass];
                  
                             });
                         }else{
                             
                             endTime = [self calculateTime];
                             countForFailTest = countForFailTest+1;
                             [self FailTestCase:countForFailTest];
                             NSDictionary *dictFail = @{
                                                        @"execution_id":[NSNumber numberWithInteger:TestCaseResultUpdate.executionID],
                                                        @"execution_result":@"FAILED",
                                                        @"start_time":self->startTime,
                                                        @"end_time":self->endTime,
                                                        @"params_used":@"",
                                                        @"result_data":@""
                                                        };
                             
                             dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0), ^{
                                 [self requestUpdateResult:dictFail];
                  
                             });
                             
                         }
                     });*/
             
                
                
                
                
                
                
                
            }else if ([self.executions.testcaseName isEqualToString:@"ENROLL_USER_DEVICE"])
            {
                self->TestCaseResultUpdate.executionID = self->_executions.executionID;
                NSMutableString *string = [[NSMutableString alloc]init];
                int sessionId = [self->rdnaObject getSessionID:&string];
                NSLog(@"sessionId String:- %@",string);
                NSLog(@"SesionId :- %d",sessionId);
                self->testEnrolluser.sessionId = string;
                
                NSDictionary *dictEnrollUser = @{
                                                 @"userId":@"omi23",
                                                 @"actCode":@"detfjt",
                                                 @"username":@"sruser",
                    @"password":@"1e99b14aa45d6add97271f8e06adacda4e521ad98a4ed18e38cfb0715e7841d2",
                                                 @"sessionId":self->testEnrolluser.sessionId,
                                                 @"apiversion":@"v1"
                                                 };
                
                self->testEnrolluser = [[TestEnrollUser alloc]initWithDict:dictEnrollUser];
                enrolluser = [self requestEnrollUserDevice:self->testEnrolluser andExecutionId:self->TestCaseResultUpdate.executionID];
                
               /* if (enrolluser.isError == NO) {
                    
                    endTime = [self calculateTime];
                    countForPassTest = countForPassTest+1;
                    [self passTestCase:countForPassTest];
                    NSDictionary *dictPass = @{
                                               @"execution_id":[NSNumber numberWithInteger:TestCaseResultUpdate.executionID],
                                               @"execution_result":@"PASSED",
                                               @"start_time":self->startTime,
                                               @"end_time":self->endTime,
                                               @"params_used":@"",
                                               @"result_data":@""
                                               };
                    
                    dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0), ^{
                        [self requestUpdateResult:dictPass];
                
                    });
                }else{
                    
                    endTime = [self calculateTime];
                    countForFailTest = countForFailTest+1;
                    [self FailTestCase:countForFailTest];
                    NSDictionary *dictFail = @{
                                               @"execution_id":[NSNumber numberWithInteger:TestCaseResultUpdate.executionID],
                                               @"execution_result":@"FAILED",
                                               @"start_time":self->startTime,
                                               @"end_time":self->endTime,
                                               @"params_used":@"",
                                               @"result_data":@""
                                               };
                    
                    dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0), ^{
                        [self requestUpdateResult:dictFail];
                
                    });
                    
                }*/
                
                
                
            }
            
            
            
        
        else if ([self.executions.testcaseName isEqualToString:@"ENROLL_USER_STATUS"])
        {
            self->TestCaseResultUpdate.executionID = self->_executions.executionID;
            NSMutableString *string = [[NSMutableString alloc]init];
            int sessionId = [self->rdnaObject getSessionID:&string];
            NSLog(@"sessionId String:- %@",string);
            NSLog(@"SesionId :- %d",sessionId);
            self->testEnrolluser.sessionId = string;
            
            NSDictionary *dictEnrollUser = @{
                                             @"userId":@"omi23",
                                             @"username":@"sruser",
                         @"password":@"1e99b14aa45d6add97271f8e06adacda4e521ad98a4ed18e38cfb0715e7841d2",
                                              @"apiversion":@"v1"
                                             };
            
            self->testEnrolluser = [[TestEnrollUser alloc]initWithDict:dictEnrollUser];
            enrolluser = [self requestGetuserStatus:self->testEnrolluser andExecutionId:self->TestCaseResultUpdate.executionID];
            
            /* if (enrolluser.isError == NO) {
             
             endTime = [self calculateTime];
             countForPassTest = countForPassTest+1;
             [self passTestCase:countForPassTest];
             NSDictionary *dictPass = @{
             @"execution_id":[NSNumber numberWithInteger:TestCaseResultUpdate.executionID],
             @"execution_result":@"PASSED",
             @"start_time":self->startTime,
             @"end_time":self->endTime,
             @"params_used":@"",
             @"result_data":@""
             };
             
             dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0), ^{
             [self requestUpdateResult:dictPass];
             
             });
             }else{
             
             endTime = [self calculateTime];
             countForFailTest = countForFailTest+1;
             [self FailTestCase:countForFailTest];
             NSDictionary *dictFail = @{
             @"execution_id":[NSNumber numberWithInteger:TestCaseResultUpdate.executionID],
             @"execution_result":@"FAILED",
             @"start_time":self->startTime,
             @"end_time":self->endTime,
             @"params_used":@"",
             @"result_data":@""
             };
             
             dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0), ^{
             [self requestUpdateResult:dictFail];
             
             });
             
             }*/
            
            
            
        }
       else if ([self.executions.testcaseName isEqualToString:@"GET_USER_ID"])
       {
           self->TestCaseResultUpdate.executionID = self->_executions.executionID;
           NSDictionary *dictEnrollUser = @{
                                            @"userId":@"omi23",
                                            @"username":@"sruser",
                                            @"password":@"1e99b14aa45d6add97271f8e06adacda4e521ad98a4ed18e38cfb0715e7841d2",
                                            @"apiversion":@"v1"
                                            };
           
           self->testEnrolluser = [[TestEnrollUser alloc]initWithDict:dictEnrollUser];
           enrolluser = [self requestGetUserId:self->testEnrolluser andExecutionId:self->TestCaseResultUpdate.executionID];
           
       }
        
        [self calculateCompleteTestCaseCount:countForPassTest withFailCount:countForFailTest];
        
    }
        else{
            NSLog(@"TestCaseName in User Session :- %@",self.executions.testcaseName);
            startTime = [self calculateTime];
            endTime = [self calculateTime];
            NSDictionary *dictCannotTest = @{
                                             @"execution_id" :[NSNumber numberWithInteger:self->_executions.executionID],
                                             @"execution_result":@"CANNOT_TEST",
                                             @"start_time":self->startTime,
                                             @"end_time":self->endTime,
                                             @"params_used":@"",
                                             @"result_data":@""
                                             };
            
            NSLog(@"dictPass :- %@",dictCannotTest);
            dispatch_async(dispatch_get_main_queue(), ^(void){
                
                self->countCannotTest = self->countCannotTest +1 ;
                [self requestUpdateResultCannotTestCount:dictCannotTest WithCannotTestCount:self->countCannotTest];
                
            });
            
            //
        }
        
    }
}


- (void)doSomethingWithTheJson
{
    QTestCaseTestjobexecution *testjobexecutions;
    QTestCaseWelcome *welcome = [self JSONFromFile];
    for (QTestCaseServerExecutionDetail *serverExecutionDetails in welcome.serverExecutionDetails) {
        //rdnaInitialize.port = (int)serverExecutionDetails.sdkPort;
        Port = (int)serverExecutionDetails.sdkPort;
        agentInfo = serverExecutionDetails.agentInfo;
        Host = serverExecutionDetails.ipAddress;
        NSLog(@"port :- %d",Port);
        for (testjobexecutions in serverExecutionDetails.testjobexecutions ) {
            self.testjobexecutions = testjobexecutions;
            NSLog(@"testjobexecutions :- %@",self.testjobexecutions);
            dispatch_async(dispatch_get_main_queue(), ^(void){
                //Run UI Updates
                self.totalTestCount.text = [NSString stringWithFormat:@"%lu",(unsigned long)self->_testjobexecutions.executions.count];
                self->totalTestCount = (int)self.totalTestCount.text;
            });
            
            self->_testjobexecutionsArray =self->_testjobexecutions.executions;
            
        }
        
    }
    
}

- (QTestCaseWelcome *)JSONFromFile
{
    NSString *path = [[NSBundle mainBundle] pathForResource:@"Sample" ofType:@"json"];
    NSData *data = [NSData dataWithContentsOfFile:path];
    
    NSError *error;
    QTestCaseWelcome *testCaseWelcome  =[QTestCaseWelcome fromData:data error:&error];
    if(error){
        NSLog(@"%@",error.description);
        return nil;
    }else{
        return testCaseWelcome;
    }
}

/* - (NSString *)checkTestCases : (enum InitializeTestCases )InitializeTestCases
 {
 NSString *InitializeTestCase;
 switch (InitializeTestCases) {
 case INIT:
 InitializeTestCase = kLinit;
 break;
 
 case INIT_FAIL_WRONG_APPID:
 InitializeTestCase = kLinit_Fail_Wrong_Appid;
 break;
 
 case INIT_FAIL_WRONG_PORT:
 InitializeTestCase = kinit_Fail_Wrong_Port;
 break;
 }
 return InitializeTestCase;
 }*/



#pragma mark initializationRelatedTestCases

- (void)checkTestCaseMethodInit :(NSString *)testCaseName
{
    int status;
    RDNAStatusInit *rdnaStatusinit;
    if ([testCaseName isEqualToString:@"INIT"]) {
        
        startTime = [self calculateTime];
        NSLog(@"startTime :- %@",startTime);
        NSLog(@"CurrentThreadSynch :- %@", [NSThread currentThread]);
       if (rdnaObject) {
            rdnaObject = nil;
            status = [self initializeRDNA:Port andHost:Host andAgentInfo:agentInfo];
            rdnaStatusinit = [[RDNAStatusInit alloc]init];
            rdnaStatusinit.errCode = status;
        }
        NSLog(@"Initialize Object :- %@",rdnaObject);
       // status = [self initializeRDNA:Port andHost:Host andAgentInfo:agentInfo];
       // rdnaStatusinit = [[RDNAStatusInit alloc]init];
       // rdnaStatusinit.errCode = status;
        
        if (rdnaStatusinit.errCode == 0) {
            firstTimeCallInitialize = true;
        }else{
            endTime = [self calculateTime];
            countForFailTest =countForFailTest+1;
            NSDictionary *dictFail = @{
                                       @"execution_id" :[NSNumber numberWithInteger:TestCaseResultUpdate.executionID],
                                       @"execution_result":@"FAILED",
                                       @"start_time":startTime,
                                       @"end_time":endTime,
                                       @"params_used":@"",
                                       @"result_data":@""
                                       };
            
            NSLog(@"dictPass :- %@",dictFail);
            
            [self requestUpdateResult:dictFail];
            
            
            
            
        }
    }else if ([testCaseName isEqualToString:@"INIT_FAIL_WRONG_APPID"])
    {
       NSLog(@"Initialize Object :- %@",rdnaObject);
        startTime = [self calculateTime];
        NSLog(@"startTime :- %@",startTime);
        // dispatch_after(dispatch_time(DISPATCH_TIME_NOW, 1 * NSEC_PER_SEC), dispatch_get_main_queue(), ^{
        //agentInfo =
        agentInfo = nil;
        agentInfo = @"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";
        
        agentWronInfo = agentInfo;
        NSLog(@"CurrentThreadSynch :- %@", [NSThread currentThread]);
        
        if (rdnaObject) {
            //terminate = [rdnaObject terminate];
            rdnaObject = nil;
            status = [self initializeRDNA:Port andHost:Host andAgentInfo:agentWronInfo];
            rdnaStatusinit = [[RDNAStatusInit alloc]init];
            rdnaStatusinit.errCode = status;
        }
        
        //status = [self initializeRDNA:Port andHost:Host andAgentInfo:agentInfo];
        //rdnaStatusinit = [[RDNAStatusInit alloc]init];
        //rdnaStatusinit.errCode = status;
        if (rdnaStatusinit.errCode == 0) {
            
            
        }else{
            endTime = [self calculateTime];
            countForFailTest =countForFailTest+1;
            NSDictionary *dictFail = @{
                                       @"execution_id" :[NSNumber numberWithInteger:TestCaseResultUpdate.executionID],
                                       @"execution_result":@"FAILED",
                                       @"start_time":startTime,
                                       @"end_time":endTime,
                                       @"params_used":@"",
                                       @"result_data":@""
                                       };
            
            NSLog(@"dictPass :- %@",dictFail);
            
            [self requestUpdateResult:dictFail];
            
        }
        // });
        
        
        
    }else if ([testCaseName isEqualToString:@"INIT_FAIL_WRONG_PORT"])
    {
        NSLog(@"Initialize Object :- %@",rdnaObject);
        startTime = [self calculateTime];
        wrongPort = Port +1;
        NSLog(@"startTime :- %@",startTime);
        NSLog(@"CurrentThreadSynch :- %@", [NSThread currentThread]);
       if (rdnaObject) {
            rdnaObject = nil;
            status = [self initializeRDNA:wrongPort andHost:Host andAgentInfo:agentInfo];
            rdnaStatusinit = [[RDNAStatusInit alloc]init];
            rdnaStatusinit.errCode = status;
        }
        //status = [self initializeRDNA:wrongPort andHost:Host andAgentInfo:agentInfo];
        //rdnaStatusinit = [[RDNAStatusInit alloc]init];
       // rdnaStatusinit.errCode = status;
        
        if (rdnaStatusinit.errCode == 0) {
            
        }
        else{
            endTime = [self calculateTime];
            countForFailTest =countForFailTest+1;
            NSDictionary *dictFail = @{
                                       @"execution_id" :[NSNumber numberWithInteger:TestCaseResultUpdate.executionID],
                                       @"execution_result":@"FAILED",
                                       @"start_time":startTime,
                                       @"end_time":endTime,
                                       @"params_used":@"",
                                       @"result_data":@""
                                       };
            
            NSLog(@"dictPass :- %@",dictFail);
            
            [self requestUpdateResult:dictFail];
            
        }
        
    }
    else if ([testCaseName isEqualToString:@"TERMINATE"])
    {
        self->terminate = [self->rdnaObject terminate];
        if (self->terminate == 0) {
            
        }else
        {
            endTime = [self calculateTime];
            countForFailTest =countForFailTest+1;
            NSDictionary *dictFail = @{
                                       @"execution_id" :[NSNumber numberWithInteger:TestCaseResultUpdate.executionID],
                                       @"execution_result":@"FAILED",
                                       @"start_time":startTime,
                                       @"end_time":endTime,
                                       @"params_used":@"",
                                       @"result_data":@""
                                       };
            
            NSLog(@"dictPass :- %@",dictFail);
            
            [self requestUpdateResult:dictFail];
        }
        
            
    }
    
    [self calculateCompleteTestCaseCount:countForPassTest withFailCount:countForFailTest];
    
}


- (void)performOperationWithAttributeAndWait:(NSString *)attribute
{
    //create a semaphore
    __block dispatch_semaphore_t semaphore = dispatch_semaphore_create(0);
    
    void (^privateFinish)(NSString *attribute) = ^(NSString *attribute) {
        NSLog(@"Finished Process: %@",attribute);
        // Stop blocking our thread and go on :-)
        dispatch_semaphore_signal(semaphore);
    };
    
    dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0), ^{
        NSLog(@"Started Process: %f",CFAbsoluteTimeGetCurrent());
        [NSThread sleepForTimeInterval:2]; // Simulate a process that takes time
        privateFinish(attribute);
    });
    
}



#pragma mark RDNACallbacks methods

- (NSString *)getApplicationName {
    return @"test";
}

- (NSString *)getApplicationVersion {
    return @"1.1";
}

- (CLLocationManager *)getLocationManager {
    return nil;
}


- (int)onTerminate:(RDNAStatusTerminate *)status {
    
    status.errCode = terminate;
    
    if (status.errCode == 0) {
        rdnaObject = nil;
        endTime = [self calculateTime];
        countForPassTest = countForPassTest +1;
        [self passTestCase:countForPassTest];
        
        NSDictionary *dictPass = @{
                                   @"execution_id":[NSNumber numberWithInteger:TestCaseResultUpdate.executionID],
                                   @"execution_result":@"PASSED",
                                   @"start_time":self->startTime,
                                   @"end_time":self->endTime,
                                   @"params_used":@"",
                                   @"result_data":@""
                                   };
        [self requestUpdateResult:dictPass];
        
    }else
    {
        endTime = [self calculateTime];
        countForFailTest = countForFailTest +1;
        [self FailTestCase:countForFailTest];
        
        NSDictionary *dictFail = @{
                                   @"execution_id":[NSNumber numberWithInteger:TestCaseResultUpdate.executionID],
                                   @"execution_result":@"FAILED",
                                   @"start_time":self->startTime,
                                   @"end_time":self->endTime,
                                   @"params_used":@"",
                                   @"result_data":@""
                                   };
        [self requestUpdateResult:dictFail];
    }
    
    return 1;
}

/**
 * @brief This method is an implementation of the RDNACLientCallbacks
 * This method is invoked by the RDNA when the RDNA check challenges API is invoked by the client
 * It returns the RDNAStatusCheckChallengesResponse class object.
 */
- (int)onCheckChallengeResponseStatus:(RDNAStatusCheckChallengeResponse *) status {
    
    NSLog(@"onCheckChallengeResponseStatus called -> errorcode : %d",status.errCode);
    if (status.errCode == 0) {
        
        NSLog(@"RDNAResponseStatus message : %@",status.status.message);
        
        NSLog(@"The status of challenge : %ld",(long)status.status.statusCode);
        
        if (status.services.count > 0) {
            [UserSessionState getSharedInstance].autoStartedServices = [NSArray arrayWithArray:status.services];
        }else{
            [UserSessionState getSharedInstance].autoStartedServices = nil;
        }
        [self InitializeState:[[StatusInfo alloc] initWithChalleges:status.challenges withChallengeStatus:status.status withAPIFlag:CHECKCHALLENGES] withUpdateFlag:true];
    }else{
        NSLog(@"RDNA_METH_CHECK_CREDENTIAL errorcode : %d",status.errCode);
        // [self performNotificationWithObject:[NSNumber numberWithInt:status.errCode]];
    }
    return 1;
}

- (int)onInitializeCompleted:(RDNAStatusInit *)status {
    
    // NSMutableString *str = [[NSMutableString alloc]init];
    dispatch_async(dispatch_get_global_queue( DISPATCH_QUEUE_PRIORITY_DEFAULT, 0), ^(void){
        NSLog(@"\n\n $$$$$ Notify runtime status of client called reason {%ld : %d} $$$$ \n\n",(long)[RDNA getErrorInfo:status.errCode],status.errCode);
        
        
        
        if (status.errCode == 0) {
            
            NSLog(@"CurrentThread :- %@", [NSThread currentThread]);
            self.statusChallenges = status.challenges;
            [self requestdata];
            self->endTime = [self calculateTime];
            NSLog(@"endTime :- %@",self->endTime);
            self->testServices = [self fetchAllServices];
            NSLog(@"service :- %@",self->testServices);
            
            
            
            
            __block RDNAService *service;
            __block RDNAPort *portInfo;
            NSString *host;
            for (int i =0; i<self->testServices.count; i++) {
                service = [self->testServices objectAtIndex:i];
                portInfo = service.portInfo;
                self->ServicePort = portInfo.port;
                NSLog(@"Service Prt :- %d",self->ServicePort);
                host =service.targetHNIP;
            }
            NSLog(@"Test Case Executed Successfully.");
            if (self->firstTimeCallInitialize == true) {
                self->countForPassTest = self->countForPassTest +1 ;
                [self passTestCase:self->countForPassTest];
                NSDictionary *dictPass = @{
                                           @"execution_id" :[NSNumber numberWithInteger:self->TestCaseResultUpdate.executionID],
                                           @"execution_result":@"PASSED",
                                           @"start_time":self->startTime,
                                           @"end_time":self->endTime,
                                           @"params_used":@"",
                                           @"result_data":@""
                                           };
                
                NSLog(@"dictPass :- %@",dictPass);
                dispatch_async(dispatch_get_main_queue(), ^(void){
                    [self requestUpdateResult:dictPass];
                    
                });
            }
            
            
            
            
            /* [self requestUpdateResultTestCase:dictPass withblock:^(BOOL finished) {
             if (finished) {
             
             }
             }];*/
            
            
            //            for (self->_executions in self.testjobexecutions.executions) {
            //                if ([self.executions.featureName isEqualToString:@"SERVICE_INFO"]) {
            //
            //                    if ([self.executions.testcaseName isEqualToString:@"GET_SERVIICE_BY_SERVICE_NAME"]) {
            //                        self->TestCaseResultUpdate.executionID = self->_executions.executionID;
            //                        NSLog(@"self->TestCaseResultUpdate.executionID For GET_SERVIICE_BY_SERVICE_NAME :- %ld",self->TestCaseResultUpdate.executionID);
            //                        NSString *testCaseName = self.executions.testcaseName;
            //                      //  [self checkServiceInfoTestCases:testCaseName withRDNAService:services];
            //                    }
            //                    else if ([self.executions.testcaseName isEqualToString:@"GET_SERVIICE_BY_WRONG_SERVICE_NAME"]) {
            //                        self->TestCaseResultUpdate.executionID = self->_executions.executionID;
            //                        NSLog(@"self->TestCaseResultUpdate.executionID For GET_SERVIICE_BY_WRONG_SERVICE_NAME :- %ld",self->TestCaseResultUpdate.executionID);
            //                        NSString *testCaseName = self.executions.testcaseName;
            //                       // [self checkServiceInfoTestCases:testCaseName withRDNAService:services];
            //                    }
            //                    else if ([self.executions.testcaseName isEqualToString:@"GET_SERVICE_BY_TARGET_COORDINATE"])
            //                    {
            //                        self->TestCaseResultUpdate.executionID = self->_executions.executionID;
            //                        NSLog(@"self->TestCaseResultUpdate.executionID For  :- %ld",self->TestCaseResultUpdate.executionID);
            //                        NSString *testCaseName = self.executions.testcaseName;
            //                       // [self checkServiceInfoTestCases:testCaseName withRDNAService:services];
            //
            //                    }
            //                    else if ([self.executions.testcaseName isEqualToString:@"GET_SERVICE_BY_TARGET_COORDINATE_WRONG_IP"])
            //                    {
            //                        self->TestCaseResultUpdate.executionID = self->_executions.executionID;
            //                        NSLog(@"self->TestCaseResultUpdate.executionID :- %ld",self->TestCaseResultUpdate.executionID);
            //                        NSString *testCaseName = self.executions.testcaseName;
            //                       // [self checkServiceInfoTestCases:testCaseName withRDNAService:services];
            //
            //                    }
            //                    else if ([self.executions.testcaseName isEqualToString:@"GET_SERVICE_BY_TARGET_COORDINATE_WRONG_PORT"])
            //                    {
            //                        self->TestCaseResultUpdate.executionID = self->_executions.executionID;
            //                        NSLog(@"self->TestCaseResultUpdate.executionID :- %ld",self->TestCaseResultUpdate.executionID);
            //                        NSString *testCaseName = self.executions.testcaseName;
            //                      //  [self checkServiceInfoTestCases:testCaseName withRDNAService:services];
            //
            //                    }
            //                    else if ([self.executions.testcaseName isEqualToString:@"GET_SERVICE_BY_TARGET_COORDINATE_WRONG_IP_AND_PORT"])
            //                    {
            //                        self->TestCaseResultUpdate.executionID = self->_executions.executionID;
            //                        NSLog(@"self->TestCaseResultUpdate.executionID :- %ld",self->TestCaseResultUpdate.executionID);
            //                        NSString *testCaseName = self.executions.testcaseName;
            //                       // [self checkServiceInfoTestCases:testCaseName withRDNAService:services];
            //
            //                    }
            //
            //                    /* serviceAccessStart  */
            //                    // int serviceAccessStart = [self->rdnaObject serviceAccessStart:service];
            //                    // NSLog(@"serviceAccessStart :- %d",serviceAccessStart);
            //
            //                    /* serviceAccessStop  */
            //                    // int serviceAccessStop = [self->rdnaObject serviceAccessStop:service];
            //                    // NSLog(@"serviceAccessStop :- %d",serviceAccessStop);
            //
            //                    /* serviceAccessStop  */
            //                    // int serviceAccessStopNew = [self->rdnaObject serviceAccessStop:service];
            //                    // NSLog(@"serviceAccessStop :- %d",serviceAccessStopNew);
            //
            //                    /* serviceAccessStart  */
            //                    // int serviceAccessStartNew = [self->rdnaObject serviceAccessStart:service];
            //                    // NSLog(@"serviceAccessStart :- %d",serviceAccessStartNew);
            //
            //
            //                    /* serviceAccessStartAll */
            //                    // int serviceAccessStartAll = [self->rdnaObject serviceAccessStartAll];
            //                    // NSLog(@"serviceAccessStartAll :- %d",serviceAccessStartAll);
            //
            //
            //
            //
            //
            //
            //                    // NSLog(@"Test Case Executed Successfully.");
            //
            //
            //
            //                }else if ([self.executions.featureName isEqualToString:@"SERVICE_ACCESS"])
            //                {
            //                    if ([self.executions.testcaseName isEqualToString:@"SERVICE_ACCESS_START_WRONG_SERVICE_NAME"])
            //                    {
            //                        self->TestCaseResultUpdate.executionID = self->_executions.executionID;
            //                        NSLog(@"self->TestCaseResultUpdate.executionID :- %ld",self->TestCaseResultUpdate.executionID);
            //
            //                    }else if ([self.executions.testcaseName isEqualToString:@"SERVICE_ACCESS_START_RUNNING_SERVICE"])
            //                    {
            //                        self->TestCaseResultUpdate.executionID = self->_executions.executionID;
            //                        NSLog(@"self->TestCaseResultUpdate.executionID :- %ld",self->TestCaseResultUpdate.executionID);
            //
            //                    }else if ([self.executions.testcaseName isEqualToString:@"SERVICE_ACCESS_START_STOPPED_SERVICE"])
            //                    {
            //                        self->TestCaseResultUpdate.executionID = self->_executions.executionID;
            //                        NSLog(@"self->TestCaseResultUpdate.executionID :- %ld",self->TestCaseResultUpdate.executionID);
            //
            //                    }
            //                    else if ([self.executions.testcaseName isEqualToString:@"SERVICE_ACCESS_STOP_WRONG_SERVICE_NAME"])
            //                    {
            //                        self->TestCaseResultUpdate.executionID = self->_executions.executionID;
            //                        NSLog(@"self->TestCaseResultUpdate.executionID :- %ld",self->TestCaseResultUpdate.executionID);
            //
            //                    }
            //                    else if ([self.executions.testcaseName isEqualToString:@"SERVICE_ACCESS_STOP_RUNNING_SERVICE"])
            //                    {
            //                        self->TestCaseResultUpdate.executionID = self->_executions.executionID;
            //                        NSLog(@"self->TestCaseResultUpdate.executionID :- %ld",self->TestCaseResultUpdate.executionID);
            //
            //                    }
            //                    else if ([self.executions.testcaseName isEqualToString:@"SERVICE_ACCESS_STOP_STOPPED_SERVICE"])
            //                    {
            //                        self->TestCaseResultUpdate.executionID = self->_executions.executionID;
            //                        NSLog(@"self->TestCaseResultUpdate.executionID :- %ld",self->TestCaseResultUpdate.executionID);
            //
            //                    }
            //
            //
            //                }else if ([self.executions.featureName isEqualToString:@"INFO_GETTERS"])
            //                {
            //                    if ([self.executions.testcaseName isEqualToString:@"GET_AGENT_ID"])
            //                    {
            //                        self->TestCaseResultUpdate.executionID = self->_executions.executionID;
            //                        NSLog(@"self->TestCaseResultUpdate.executionID :- %ld",self->TestCaseResultUpdate.executionID);
            //
            //                        [self checkInfoGetterTestCases:self.executions.testcaseName withMutableString:str withRDNAStatus:0];
            //
            //
            //                    }else if ([self.executions.testcaseName isEqualToString:@"GET_DEVICE_ID"])
            //                    {
            //                        self->TestCaseResultUpdate.executionID = self->_executions.executionID;
            //                        NSLog(@"self->TestCaseResultUpdate.executionID :- %ld",self->TestCaseResultUpdate.executionID);
            //                      [self checkInfoGetterTestCases:self.executions.testcaseName withMutableString:str withRDNAStatus:0];
            //
            //                    }else if ([self.executions.testcaseName isEqualToString:@"GET_SESSION_ID"])
            //                    {
            //                        self->TestCaseResultUpdate.executionID = self->_executions.executionID;
            //                        NSLog(@"self->TestCaseResultUpdate.executionID :- %ld",self->TestCaseResultUpdate.executionID);
            //                         [self checkInfoGetterTestCases:self.executions.testcaseName withMutableString:str withRDNAStatus:0];
            //
            //                    }
            //                    else if ([self.executions.testcaseName isEqualToString:@"GET_ERROR_CODE"])
            //                    {
            //                        self->TestCaseResultUpdate.executionID = self->_executions.executionID;
            //                        NSLog(@"self->TestCaseResultUpdate.executionID :- %ld",self->TestCaseResultUpdate.executionID);
            //                         [self checkInfoGetterTestCases:self.executions.testcaseName withMutableString:str withRDNAStatus:status.errCode];
            //
            //                    }else if ([self.executions.testcaseName isEqualToString:@"GET_API_SDK_VERSION"])
            //                    {
            //                        self->TestCaseResultUpdate.executionID = self->_executions.executionID;
            //                        NSLog(@"self->TestCaseResultUpdate.executionID :- %ld",self->TestCaseResultUpdate.executionID);
            //                        NSString *sdkVersion = [RDNA getSDKVersion];
            //                        NSLog(@"sdkVersion :- %@",sdkVersion);
            //                    }
            //
            //                }
            //
            //                else if ([self.executions.featureName isEqualToString:@"DATA_PRIVACY"])
            //                {
            //                    if ([self.executions.testcaseName isEqualToString:@"GET_DEFAULT_CIPHER_SPEC"])
            //                    {
            //                        self->TestCaseResultUpdate.executionID = self->_executions.executionID;
            //                        NSLog(@"self->TestCaseResultUpdate.executionID :- %ld",self->TestCaseResultUpdate.executionID);
            //
            //                       // [self checkDataPrivacyTestCases:self.executions.testcaseName withMutableString:str];
            //
            //
            //                    }else if ([self.executions.testcaseName isEqualToString:@"GET_DEVICE_ID"])
            //                    {
            //                        self->TestCaseResultUpdate.executionID = self->_executions.executionID;
            //                        NSLog(@"self->TestCaseResultUpdate.executionID :- %ld",self->TestCaseResultUpdate.executionID);
            //                       // [self checkDataPrivacyTestCases:self.executions.testcaseName withMutableString:str];
            //
            //                    }
            //
            //
            //
            //
            //                }
            //
            //
            //
            //
            //
            //            }
            
            // self->terminate = [self->rdnaObject terminate];
            //  if (self->terminate == 0) {
            
            //}else
            //{
            
            // }
        }
        else
        {
            NSLog(@"Test Case Failed in Asynch");
            self->rdnaObject = nil;
            self->countForFailTest = self->countForFailTest +1 ;
            [self FailTestCase:self->countForFailTest];
            NSDictionary *dictFail = @{
                                       @"execution_id" :[NSNumber numberWithInteger:self->TestCaseResultUpdate.executionID],
                                       @"execution_result":@"FAILED",
                                       @"start_time":self->startTime,
                                       @"end_time":self->endTime,
                                       @"params_used":@"",
                                       @"result_data":@""
                                       };
            
            NSLog(@"dictPass :- %@",dictFail);
            [self requestUpdateResult:dictFail];
            
            
        }
    });
    
    
    return 1;
    
    
    
}





#pragma mark SERVICE_INFO and SERVICE_ACCESS  Related TestCases

- (void)checkServiceInfoTestCases :(NSString *)testCaseName withRDNAService:(NSArray *)rdnaServices
{
    
    __block RDNAService *service;
    int port = 0;
    NSString *host;
    for (int i =0; i<rdnaServices.count; i++) {
        service = [rdnaServices objectAtIndex:i];
        port = service.targetPort;
        host =service.targetHNIP;
    }
    
    if ([testCaseName isEqualToString:@"GET_SERVICE_BY_SERVICE_NAME"]) {
        
        startTime = [self calculateTime];
        /* Get ServiceByServiceName */
        
        //  dispatch_async(dispatch_get_global_queue( DISPATCH_QUEUE_PRIORITY_DEFAULT, 0), ^(void){
        
        int servicestatus = [self->rdnaObject getServiceByServiceName:service.serviceName ServiceInfo:&service];
        
        if (servicestatus == 0) {
            NSLog(@"Test Case Passed For GET_SERVICE_BY_SERVICE_NAME");
            self->endTime = [self calculateTime];
            self->countForPassTest = countForPassTest +1;
            [self passTestCase:countForPassTest];
            
            NSDictionary *dictPass = @{
                                       @"execution_id":[NSNumber numberWithInteger:TestCaseResultUpdate.executionID],
                                       @"execution_result":@"PASSED",
                                       @"start_time":self->startTime,
                                       @"end_time":self->endTime,
                                       @"params_used":@"",
                                       @"result_data":@""
                                       };
            
            if (![NSJSONSerialization isValidJSONObject:dictPass]) {
                NSLog(@"Cannot make valid JSON");
            } else {
                NSLog(@"Vaild Json!");
            }
            [self requestUpdateResult:dictPass];
        }else{
            NSLog(@"Test Case Failed For GET_SERVICE_BY_SERVICE_NAME");
            self->endTime = [self calculateTime];
            self->countForFailTest = self->countForFailTest+1;
            [self FailTestCase:self->countForFailTest];
            
            NSDictionary *dictFail = @{
                                       @"execution_id":[NSNumber numberWithInteger:self->TestCaseResultUpdate.executionID],
                                       @"execution_result":@"FAILED",
                                       @"start_time":self->startTime,
                                       @"end_time":self->endTime,
                                       @"params_used":@"",
                                       @"result_data":@""
                                       };
            
            if (![NSJSONSerialization isValidJSONObject:dictFail]) {
                NSLog(@"Cannot make valid JSON");
            } else {
                NSLog(@"Vaild Json!");
            }
            
            [self requestUpdateResult:dictFail];
        }
        
        NSLog(@"servicestatus :- %d",servicestatus);
        // });
        
        
        
    }
    else if ([self.executions.testcaseName isEqualToString:@"GET_SERVICE_BY_WRONG_SERVICE_NAME"]) {
        
        startTime = [self calculateTime];
        /* Get ServiceByServiceName */
        int servicestatus = [self->rdnaObject getServiceByServiceName:@"xyz" ServiceInfo:&service];
        
        if (servicestatus > 0) {
            NSLog(@"Test Case Passed For GET_SERVICE_BY_WRONG_SERVICE_NAME");
            endTime = [self calculateTime];
            countForPassTest = countForPassTest +1;
            [self passTestCase:countForPassTest];
            NSDictionary *dictPass = @{
                                       @"execution_id":[NSNumber numberWithInteger:TestCaseResultUpdate.executionID],
                                       @"execution_result":@"PASSED",
                                       @"start_time":self->startTime,
                                       @"end_time":self->endTime,
                                       @"params_used":@"",
                                       @"result_data":@""
                                       };
            
            if (![NSJSONSerialization isValidJSONObject:dictPass]) {
                NSLog(@"Cannot make valid JSON");
            } else {
                NSLog(@"Vaild Json!");
            }
            [self requestUpdateResult:dictPass];
            
        }else{
            NSLog(@"Test Case Failed For GET_SERVICE_BY_WRONG_SERVICE_NAME");
            endTime = [self calculateTime];
            countForFailTest = countForFailTest+1;
            [self FailTestCase:countForFailTest];
            
            NSDictionary *dictFail = @{
                                       @"execution_id":[NSNumber numberWithInteger:TestCaseResultUpdate.executionID],
                                       @"execution_result":@"FAILED",
                                       @"start_time":self->startTime,
                                       @"end_time":self->endTime,
                                       @"params_used":@"",
                                       @"result_data":@""
                                       };
            [self requestUpdateResult:dictFail];
        }
        
        NSLog(@"servicestatus :- %d",servicestatus);
        
    }
    else if ([self.executions.testcaseName isEqualToString:@"GET_SERVICE_BY_TARGET_COORDINATE"])
    {
        startTime = [self calculateTime];
        /* Get getServiceByTargetCoordinate */
        int serviceByTargetCoordinate = [self->rdnaObject getServiceByTargetCoordinate:host TargetPort:port ServicesInfo:&rdnaServices];
        
        if (serviceByTargetCoordinate == 0) {
            NSLog(@"Test Case Passed For GET_SERVICE_BY_TARGET_COORDINATE");
            endTime = [self calculateTime];
            countForPassTest = countForPassTest +1;
            [self passTestCase:countForPassTest];
            
            NSDictionary *dictPass = @{
                                       @"execution_id":[NSNumber numberWithInteger:TestCaseResultUpdate.executionID],
                                       @"execution_result":@"PASSED",
                                       @"start_time":self->startTime,
                                       @"end_time":self->endTime,
                                       @"params_used":@"",
                                       @"result_data":@""
                                       };
            [self requestUpdateResult:dictPass];
            
        }else{
            NSLog(@"Test Case Failed For GET_SERVICE_BY_TARGET_COORDINATE");
            endTime = [self calculateTime];
            countForFailTest = countForFailTest+1;
            [self FailTestCase:countForFailTest];
            
            NSDictionary *dictFail = @{
                                       @"execution_id":[NSNumber numberWithInteger:TestCaseResultUpdate.executionID],
                                       @"execution_result":@"FAILED",
                                       @"start_time":self->startTime,
                                       @"end_time":self->endTime,
                                       @"params_used":@"",
                                       @"result_data":@""
                                       };
            
            [self requestUpdateResult:dictFail];
            
            
        }
        
        
        NSLog(@"serviceByTargetCoordinate :- %d",serviceByTargetCoordinate);
        
        
    }
    else if ([self.executions.testcaseName isEqualToString:@"GET_SERVICE_BY_TARGET_COORDINATE_WRONG_IP"])
    {
        startTime = [self calculateTime];
        
        /* Get getServiceByTargetCoordinate */
        int serviceByTargetCoordinate = [self->rdnaObject getServiceByTargetCoordinate:nil TargetPort:port ServicesInfo:&rdnaServices];
        
        if (serviceByTargetCoordinate > 0) {
            NSLog(@"Test Case Passed For GET_SERVICE_BY_TARGET_COORDINATE_WRONG_IP");
            endTime = [self calculateTime];
            countForPassTest = countForPassTest +1;
            [self passTestCase:countForPassTest];
            
            NSDictionary *dictPass = @{
                                       @"execution_id":[NSNumber numberWithInteger:TestCaseResultUpdate.executionID],
                                       @"execution_result":@"PASSED",
                                       @"start_time":self->startTime,
                                       @"end_time":self->endTime,
                                       @"params_used":@"",
                                       @"result_data":@""
                                       };
            [self requestUpdateResult:dictPass];
            
        }else{
            NSLog(@"Test Case Failed For GET_SERVICE_BY_TARGET_COORDINATE_WRONG_IP");
            endTime = [self calculateTime];
            countForFailTest = countForFailTest+1;
            [self FailTestCase:countForFailTest];
            
            NSDictionary *dictFail = @{
                                       @"execution_id":[NSNumber numberWithInteger:TestCaseResultUpdate.executionID],
                                       @"execution_result":@"FAILED",
                                       @"start_time":self->startTime,
                                       @"end_time":self->endTime,
                                       @"params_used":@"",
                                       @"result_data":@""
                                       };
            [self requestUpdateResult:dictFail];
            
            
        }
        
        
        NSLog(@"serviceByTargetCoordinate :- %d",serviceByTargetCoordinate);
        
        
    }
    else if ([self.executions.testcaseName isEqualToString:@"GET_SERVICE_BY_TARGET_COORDINATE_WRONG_PORT"])
    {
        startTime = [self calculateTime];
        
        /* Get getServiceByTargetCoordinate */
        int serviceByTargetCoordinate = [self->rdnaObject getServiceByTargetCoordinate:host TargetPort:0 ServicesInfo:&rdnaServices];
        
        if (serviceByTargetCoordinate > 0) {
            NSLog(@"Test Case Passed For GET_SERVICE_BY_TARGET_COORDINATE_WRONG_PORT");
            endTime = [self calculateTime];
            countForPassTest = countForPassTest +1;
            [self passTestCase:countForPassTest];
            
            NSDictionary *dictPass = @{
                                       @"execution_id":[NSNumber numberWithInteger:TestCaseResultUpdate.executionID],
                                       @"execution_result":@"PASSED",
                                       @"start_time":self->startTime,
                                       @"end_time":self->endTime,
                                       @"params_used":@"",
                                       @"result_data":@""
                                       };
            [self requestUpdateResult:dictPass];
            
        }else{
            NSLog(@"Test Case Failed For GET_SERVICE_BY_TARGET_COORDINATE_WRONG_PORT");
            endTime = [self calculateTime];
            countForFailTest = countForFailTest+1;
            [self FailTestCase:countForFailTest];
            
            NSDictionary *dictFail = @{
                                       @"execution_id":[NSNumber numberWithInteger:TestCaseResultUpdate.executionID],
                                       @"execution_result":@"FAILED",
                                       @"start_time":self->startTime,
                                       @"end_time":self->endTime,
                                       @"params_used":@"",
                                       @"result_data":@""
                                       };
            [self requestUpdateResult:dictFail];
        }
        
        
        NSLog(@"serviceByTargetCoordinate :- %d",serviceByTargetCoordinate);
        
        
    }
    else if ([self.executions.testcaseName isEqualToString:@"GET_SERVICE_BY_TARGET_COORDINATE_WRONG_IP_AND_PORT"])
    {
        startTime = [self calculateTime];
        
        /* Get getServiceByTargetCoordinate */
        int serviceByTargetCoordinate = [self->rdnaObject getServiceByTargetCoordinate:nil TargetPort:0 ServicesInfo:&rdnaServices];
        
        if (serviceByTargetCoordinate > 0) {
            NSLog(@"Test Case Passed For GET_SERVICE_BY_TARGET_COORDINATE_WRONG_IP_AND_PORT");
            endTime = [self calculateTime];
            countForPassTest = countForPassTest +1;
            [self passTestCase:countForPassTest];
            
            NSDictionary *dictPass = @{
                                       @"execution_id":[NSNumber numberWithInteger:TestCaseResultUpdate.executionID],
                                       @"execution_result":@"PASSED",
                                       @"start_time":self->startTime,
                                       @"end_time":self->endTime,
                                       @"params_used":@"",
                                       @"result_data":@""
                                       };
            [self requestUpdateResult:dictPass];
            
            
        }else{
            NSLog(@"Test Case Failed For GET_SERVICE_BY_TARGET_COORDINATE_WRONG_IP_AND_PORT");
            endTime = [self calculateTime];
            countForFailTest = countForFailTest+1;
            [self FailTestCase:countForFailTest];
            NSDictionary *dictFail = @{
                                       @"execution_id":[NSNumber numberWithInteger:TestCaseResultUpdate.executionID],
                                       @"execution_result":@"FAILED",
                                       @"start_time":self->startTime,
                                       @"end_time":self->endTime,
                                       @"params_used":@"",
                                       @"result_data":@""
                                       };
            [self requestUpdateResult:dictFail];
            
            
        }
        
        
        NSLog(@"serviceByTargetCoordinate :- %d",serviceByTargetCoordinate);
        
        
    }
    
    
    /* Service Access */
    
    else if ([self.executions.testcaseName isEqualToString:@"SERVICE_ACCESS_START_STOPPED_SERVICE"])
    {
        TestCaseResultUpdate.executionID = self.executions.executionID;
        startTime = [self calculateTime];
        /* serviceAccessStop  */
        int serviceAccessStop = [self->rdnaObject serviceAccessStop:service];
        NSLog(@"serviceAccessStop :- %d",serviceAccessStop);
        
        if (serviceAccessStop == 0) {
            NSLog(@"Test Case Passed For SERVICE_ACCESS_START_STOPPED_SERVICE");
            endTime = [self calculateTime];
            countForPassTest = countForPassTest +1;
            [self passTestCase:countForPassTest];
            
            NSDictionary *dictPass = @{
                                       @"execution_id":[NSNumber numberWithInteger:TestCaseResultUpdate.executionID],
                                       @"execution_result":@"PASSED",
                                       @"start_time":self->startTime,
                                       @"end_time":self->endTime,
                                       @"params_used":@"",
                                       @"result_data":@""
                                       };
            [self requestUpdateResult:dictPass];
            
            
        }else{
            NSLog(@"Test Case Failed For SERVICE_ACCESS_START_STOPPED_SERVICE");
            endTime = [self calculateTime];
            countForFailTest = countForFailTest+1;
            [self FailTestCase:countForFailTest];
            NSDictionary *dictFail = @{
                                       @"execution_id":[NSNumber numberWithInteger:TestCaseResultUpdate.executionID],
                                       @"execution_result":@"FAILED",
                                       @"start_time":self->startTime,
                                       @"end_time":self->endTime,
                                       @"params_used":@"",
                                       @"result_data":@""
                                       };
            [self requestUpdateResult:dictFail];
            
            
        }
        
        
        
    }
    else if ([self.executions.testcaseName isEqualToString:@"SERVICE_ACCESS_START_WRONG_SERVICE_NAME"])
    {
        TestCaseResultUpdate.executionID = self.executions.executionID;
        startTime = [self calculateTime];
        /* serviceAccessStop  */
        int serviceAccessStartWrongServiceName = [self->rdnaObject serviceAccessStart:nil];
        NSLog(@"serviceAccessStop :- %d",serviceAccessStartWrongServiceName);
        
        if (serviceAccessStartWrongServiceName > 0) {
            NSLog(@"Test Case Passed For SERVICE_ACCESS_START_WRONG_SERVICE_NAME");
            endTime = [self calculateTime];
            countForPassTest = countForPassTest +1;
            [self passTestCase:countForPassTest];
            
            NSDictionary *dictPass = @{
                                       @"execution_id":[NSNumber numberWithInteger:TestCaseResultUpdate.executionID],
                                       @"execution_result":@"PASSED",
                                       @"start_time":self->startTime,
                                       @"end_time":self->endTime,
                                       @"params_used":@"",
                                       @"result_data":@""
                                       };
            [self requestUpdateResult:dictPass];
            
            
        }else{
             NSLog(@"Test Case Failed For SERVICE_ACCESS_START_WRONG_SERVICE_NAME");
            endTime = [self calculateTime];
            countForFailTest = countForFailTest+1;
            [self FailTestCase:countForFailTest];
            NSDictionary *dictFail = @{
                                       @"execution_id":[NSNumber numberWithInteger:TestCaseResultUpdate.executionID],
                                       @"execution_result":@"FAILED",
                                       @"start_time":self->startTime,
                                       @"end_time":self->endTime,
                                       @"params_used":@"",
                                       @"result_data":@""
                                       };
            [self requestUpdateResult:dictFail];
            
            
        }
        
        
        
    }
    else if ([self.executions.testcaseName isEqualToString:@"SERVICE_ACCESS_START_RUNNING_SERVICE"])
    {
        TestCaseResultUpdate.executionID = self.executions.executionID;
        startTime = [self calculateTime];
        int serviceAccessStart = [self->rdnaObject serviceAccessStart:service];
        NSLog(@"serviceAccessStart :- %d",serviceAccessStart);
        if (serviceAccessStart == 0) {
            NSLog(@"Test Case Passed For SERVICE_ACCESS_START_RUNNING_SERVICE");
            endTime = [self calculateTime];
            countForPassTest = countForPassTest +1;
            [self passTestCase:countForPassTest];
            
            NSDictionary *dictPass = @{
                                       @"execution_id":[NSNumber numberWithInteger:TestCaseResultUpdate.executionID],
                                       @"execution_result":@"PASSED",
                                       @"start_time":self->startTime,
                                       @"end_time":self->endTime,
                                       @"params_used":@"",
                                       @"result_data":@""
                                       };
            [self requestUpdateResult:dictPass];
            
            
        }else{
            NSLog(@"Test Case Failed For SERVICE_ACCESS_START_RUNNING_SERVICE");
            endTime = [self calculateTime];
            countForFailTest = countForFailTest+1;
            [self FailTestCase:countForFailTest];
            NSDictionary *dictFail = @{
                                       @"execution_id":[NSNumber numberWithInteger:TestCaseResultUpdate.executionID],
                                       @"execution_result":@"FAILED",
                                       @"start_time":self->startTime,
                                       @"end_time":self->endTime,
                                       @"params_used":@"",
                                       @"result_data":@""
                                       };
            [self requestUpdateResult:dictFail];
            
            
        }
        
        
    }
    
    else if ([self.executions.testcaseName isEqualToString:@"SERVICE_ACCESS_STOP_WRONG_SERVICE_NAME"])
    {
        TestCaseResultUpdate.executionID = self.executions.executionID;
        startTime = [self calculateTime];
        int serviceAccessStopWrongServiceName = [self->rdnaObject serviceAccessStop:nil];
        NSLog(@"serviceAccessStart :- %d",serviceAccessStopWrongServiceName);
        if (serviceAccessStopWrongServiceName!= 0) {
            NSLog(@"Test Case Passed For SERVICE_ACCESS_STOP_WRONG_SERVICE_NAME");
            endTime = [self calculateTime];
            countForPassTest = countForPassTest +1;
            [self passTestCase:countForPassTest];
            
            NSDictionary *dictPass = @{
                                       @"execution_id":[NSNumber numberWithInteger:TestCaseResultUpdate.executionID],
                                       @"execution_result":@"PASSED",
                                       @"start_time":self->startTime,
                                       @"end_time":self->endTime,
                                       @"params_used":@"",
                                       @"result_data":@""
                                       };
            [self requestUpdateResult:dictPass];
            
            
        }else{
            endTime = [self calculateTime];
            countForFailTest = countForFailTest+1;
            [self FailTestCase:countForFailTest];
            NSDictionary *dictFail = @{
                                       @"execution_id":[NSNumber numberWithInteger:TestCaseResultUpdate.executionID],
                                       @"execution_result":@"FAILED",
                                       @"start_time":self->startTime,
                                       @"end_time":self->endTime,
                                       @"params_used":@"",
                                       @"result_data":@""
                                       };
            [self requestUpdateResult:dictFail];
            
            
        }
        
        
    }
    
    else if ([self.executions.testcaseName isEqualToString:@"SERVICE_ACCESS_STOP_RUNNING_SERVICE"])
    {
        TestCaseResultUpdate.executionID = self.executions.executionID;
        startTime = [self calculateTime];
        int serviceAccessStopRunningService = [self->rdnaObject serviceAccessStop:service];
        NSLog(@"serviceAccessStart :- %d",serviceAccessStopRunningService);
        if (serviceAccessStopRunningService == 0) {
            NSLog(@"Test Case Passed For SERVICE_ACCESS_STOP_RUNNING_SERVICE");
            endTime = [self calculateTime];
            countForPassTest = countForPassTest +1;
            [self passTestCase:countForPassTest];
            
            NSDictionary *dictPass = @{
                                       @"execution_id":[NSNumber numberWithInteger:TestCaseResultUpdate.executionID],
                                       @"execution_result":@"PASSED",
                                       @"start_time":self->startTime,
                                       @"end_time":self->endTime,
                                       @"params_used":@"",
                                       @"result_data":@""
                                       };
            [self requestUpdateResult:dictPass];
            
            
        }else{
            NSLog(@"Test Case Failed For SERVICE_ACCESS_STOP_RUNNING_SERVICE");
            endTime = [self calculateTime];
            countForFailTest = countForFailTest+1;
            [self FailTestCase:countForFailTest];
            NSDictionary *dictFail = @{
                                       @"execution_id":[NSNumber numberWithInteger:TestCaseResultUpdate.executionID],
                                       @"execution_result":@"FAILED",
                                       @"start_time":self->startTime,
                                       @"end_time":self->endTime,
                                       @"params_used":@"",
                                       @"result_data":@""
                                       };
            [self requestUpdateResult:dictFail];
            
            
        }
        
        
    }
    else if ([self.executions.testcaseName isEqualToString:@"SERVICE_ACCESS_STOP_STOPPED_SERVICE"])
    {
        TestCaseResultUpdate.executionID = self.executions.executionID;
        startTime = [self calculateTime];
        int serviceAccessStopStoppedService = [self->rdnaObject serviceAccessStop:service];
        NSLog(@"serviceAccessStart :- %d",serviceAccessStopStoppedService);
        if (serviceAccessStopStoppedService == 0) {
            NSLog(@"Test Case Passed For SERVICE_ACCESS_STOP_STOPPED_SERVICE");
            endTime = [self calculateTime];
            countForPassTest = countForPassTest +1;
            [self passTestCase:countForPassTest];
            
            NSDictionary *dictPass = @{
                                       @"execution_id":[NSNumber numberWithInteger:TestCaseResultUpdate.executionID],
                                       @"execution_result":@"PASSED",
                                       @"start_time":self->startTime,
                                       @"end_time":self->endTime,
                                       @"params_used":@"",
                                       @"result_data":@""
                                       };
            [self requestUpdateResult:dictPass];
            
            
        }else{
             NSLog(@"Test Case Failed For SERVICE_ACCESS_STOP_STOPPED_SERVICE");
            endTime = [self calculateTime];
            countForFailTest = countForFailTest+1;
            [self FailTestCase:countForFailTest];
            NSDictionary *dictFail = @{
                                       @"execution_id":[NSNumber numberWithInteger:TestCaseResultUpdate.executionID],
                                       @"execution_result":@"FAILED",
                                       @"start_time":self->startTime,
                                       @"end_time":self->endTime,
                                       @"params_used":@"",
                                       @"result_data":@""
                                       };
            [self requestUpdateResult:dictFail];
            
            
        }
        
        
    }
    
    
    else if ([self.executions.testcaseName isEqualToString:@"SERVICE_ACCESS_STOP_ALL_SERVICE"])
    {
        TestCaseResultUpdate.executionID = self.executions.executionID;
        startTime = [self calculateTime];
        int serviceAccessStart = [self->rdnaObject serviceAccessStopAll];
        NSLog(@"serviceAccessStart :- %d",serviceAccessStart);
        if (serviceAccessStart == 0) {
            NSLog(@"Test Case Passed For SERVICE_ACCESS_STOP_ALL_SERVICE");
            endTime = [self calculateTime];
            countForPassTest = countForPassTest +1;
            [self passTestCase:countForPassTest];
            
            NSDictionary *dictPass = @{
                                       @"execution_id":[NSNumber numberWithInteger:TestCaseResultUpdate.executionID],
                                       @"execution_result":@"PASSED",
                                       @"start_time":self->startTime,
                                       @"end_time":self->endTime,
                                       @"params_used":@"",
                                       @"result_data":@""
                                       };
            [self requestUpdateResult:dictPass];
            
            
        }else{
            NSLog(@"Test Case Failed For SERVICE_ACCESS_STOP_ALL_SERVICE");
            endTime = [self calculateTime];
            countForFailTest = countForFailTest+1;
            [self FailTestCase:countForFailTest];
            NSDictionary *dictFail = @{
                                       @"execution_id":[NSNumber numberWithInteger:TestCaseResultUpdate.executionID],
                                       @"execution_result":@"FAILED",
                                       @"start_time":self->startTime,
                                       @"end_time":self->endTime,
                                       @"params_used":@"",
                                       @"result_data":@""
                                       };
            [self requestUpdateResult:dictFail];
            
            
        }
        
        
    }
    
    else if ([self.executions.testcaseName isEqualToString:@"SERVICE_ACCESS_START_ALL_SERVICE"])
    {
        TestCaseResultUpdate.executionID = self.executions.executionID;
        startTime = [self calculateTime];
        int serviceAccessStart = [self->rdnaObject serviceAccessStartAll];
        NSLog(@"serviceAccessStart :- %d",serviceAccessStart);
        if (serviceAccessStart == 0) {
            NSLog(@"Test Case Passed For SERVICE_ACCESS_START_ALL_SERVICE");
            endTime = [self calculateTime];
            countForPassTest = countForPassTest +1;
            [self passTestCase:countForPassTest];
            
            NSDictionary *dictPass = @{
                                       @"execution_id":[NSNumber numberWithInteger:TestCaseResultUpdate.executionID],
                                       @"execution_result":@"PASSED",
                                       @"start_time":self->startTime,
                                       @"end_time":self->endTime,
                                       @"params_used":@"",
                                       @"result_data":@""
                                       };
            [self requestUpdateResult:dictPass];
            
            
        }else{
            NSLog(@"Test Case Failed For SERVICE_ACCESS_START_ALL_SERVICE");
            endTime = [self calculateTime];
            countForFailTest = countForFailTest+1;
            [self FailTestCase:countForFailTest];
            NSDictionary *dictFail = @{
                                       @"execution_id":[NSNumber numberWithInteger:TestCaseResultUpdate.executionID],
                                       @"execution_result":@"FAILED",
                                       @"start_time":self->startTime,
                                       @"end_time":self->endTime,
                                       @"params_used":@"",
                                       @"result_data":@""
                                       };
            [self requestUpdateResult:dictFail];
            
            
        }
        
        
    }
    else{
        
        endTime = [self calculateTime];
        countForFailTest = countForFailTest+1;
        [self FailTestCase:countForFailTest];
        NSDictionary *dictFail = @{
                                   @"execution_id":[NSNumber numberWithInteger:TestCaseResultUpdate.executionID],
                                   @"execution_result":@"FAILED",
                                   @"start_time":self->startTime,
                                   @"end_time":self->endTime,
                                   @"params_used":@"",
                                   @"result_data":@""
                                   };
        dispatch_async(dispatch_get_main_queue(), ^(void){
            [self requestUpdateResult:dictFail];
            
        });
    }
    
    
    [self calculateCompleteTestCaseCount:countForPassTest withFailCount:countForFailTest];
}








#pragma mark Info getters

-(void)checkInfoGetterTestCases :(NSString *)testCaseName withMutableString:(NSMutableString *)string withRDNAStatus:(int)status
{
    if ([self.executions.testcaseName isEqualToString:@"GET_AGENT_ID"])
    {
        startTime = [self calculateTime];
        int errAgentId = [self->rdnaObject getAgentID:&string];
        NSLog(@"agentId :- %d",errAgentId);
        NSLog(@"agentStr :- %@",string);
        
        if (errAgentId == 0 && string.length > 0) {
            NSLog(@"Test Case Passed For GET_AGENT_ID");
            endTime = [self calculateTime];
            countForPassTest = countForPassTest +1;
            [self passTestCase:countForPassTest];
            
            NSDictionary *dictPass = @{
                                       @"execution_id":[NSNumber numberWithInteger:TestCaseResultUpdate.executionID],
                                       @"execution_result":@"PASSED",
                                       @"start_time":self->startTime,
                                       @"end_time":self->endTime,
                                       @"params_used":@"",
                                       @"result_data":@""
                                       };
            [self requestUpdateResult:dictPass];
            
            
        }else{
             NSLog(@"Test Case Failed For GET_AGENT_ID");
            endTime = [self calculateTime];
            countForFailTest = countForFailTest+1;
            [self FailTestCase:countForFailTest];
            NSDictionary *dictFail = @{
                                       @"execution_id":[NSNumber numberWithInteger:TestCaseResultUpdate.executionID],
                                       @"execution_result":@"FAILED",
                                       @"start_time":self->startTime,
                                       @"end_time":self->endTime,
                                       @"params_used":@"",
                                       @"result_data":@""
                                       };
            [self requestUpdateResult:dictFail];
            
            
        }
        
        
        
    }else if ([self.executions.testcaseName isEqualToString:@"GET_DEVICE_ID"])
    {
        startTime = [self calculateTime];
        
        int errDeviceId = [self->rdnaObject getDeviceID:&string];
        NSLog(@"deviceId :- %d",errDeviceId);
        NSLog(@"deviceStr :- %@",string);
        
        if (errDeviceId == 0 && string.length > 0) {
             NSLog(@"Test Case Passed For GET_DEVICE_ID");
            endTime = [self calculateTime];
            countForPassTest = countForPassTest +1;
            [self passTestCase:countForPassTest];
            
            NSDictionary *dictPass = @{
                                       @"execution_id":[NSNumber numberWithInteger:TestCaseResultUpdate.executionID],
                                       @"execution_result":@"PASSED",
                                       @"start_time":self->startTime,
                                       @"end_time":self->endTime,
                                       @"params_used":@"",
                                       @"result_data":@""
                                       };
            [self requestUpdateResult:dictPass];
            
            
        }else{
             NSLog(@"Test Case Failed For GET_DEVICE_ID");
            endTime = [self calculateTime];
            countForFailTest = countForFailTest+1;
            [self FailTestCase:countForFailTest];
            NSDictionary *dictFail = @{
                                       @"execution_id":[NSNumber numberWithInteger:TestCaseResultUpdate.executionID],
                                       @"execution_result":@"FAILED",
                                       @"start_time":self->startTime,
                                       @"end_time":self->endTime,
                                       @"params_used":@"",
                                       @"result_data":@""
                                       };
            [self requestUpdateResult:dictFail];
            
            
        }
        
        
        
    }else if ([self.executions.testcaseName isEqualToString:@"GET_SESSION_ID"])
    {
        startTime = [self calculateTime];
        int sessionId = [self->rdnaObject getSessionID:&string];
        NSLog(@"sessionId :- %d",sessionId);
        NSLog(@"sessionStr :- %@",string);
        
        if (sessionId == 0) {
             NSLog(@"Test Case Passed For GET_SESSION_ID");
            endTime = [self calculateTime];
            countForPassTest = countForPassTest +1;
            [self passTestCase:countForPassTest];
            
            NSDictionary *dictPass = @{
                                       @"execution_id":[NSNumber numberWithInteger:TestCaseResultUpdate.executionID],
                                       @"execution_result":@"PASSED",
                                       @"start_time":self->startTime,
                                       @"end_time":self->endTime,
                                       @"params_used":@"",
                                       @"result_data":@""
                                       };
            [self requestUpdateResult:dictPass];
            
            
        }else{
             NSLog(@"Test Case Failed For GET_SESSION_ID");
            endTime = [self calculateTime];
            countForFailTest = countForFailTest+1;
            [self FailTestCase:countForFailTest];
            NSDictionary *dictFail = @{
                                       @"execution_id":[NSNumber numberWithInteger:TestCaseResultUpdate.executionID],
                                       @"execution_result":@"FAILED",
                                       @"start_time":self->startTime,
                                       @"end_time":self->endTime,
                                       @"params_used":@"",
                                       @"result_data":@""
                                       };
            [self requestUpdateResult:dictFail];
            
            
        }
        
    }
    else if ([self.executions.testcaseName isEqualToString:@"GET_ERROR_CODE"])
    {
        
        startTime = [self calculateTime];
        int errorId = [RDNA getErrorInfo:status];
        NSLog(@"errorId :- %d",errorId);
        
        if (errorId == 0) {
             NSLog(@"Test Case Passed For GET_ERROR_CODE");
            endTime = [self calculateTime];
            countForPassTest = countForPassTest +1;
            [self passTestCase:countForPassTest];
            
            NSDictionary *dictPass = @{
                                       @"execution_id":[NSNumber numberWithInteger:TestCaseResultUpdate.executionID],
                                       @"execution_result":@"PASSED",
                                       @"start_time":self->startTime,
                                       @"end_time":self->endTime,
                                       @"params_used":@"",
                                       @"result_data":@""
                                       };
            [self requestUpdateResult:dictPass];
            
            
        }else{
            NSLog(@"Test Case Failed For GET_ERROR_CODE");
            endTime = [self calculateTime];
            countForFailTest = countForFailTest+1;
            [self FailTestCase:countForFailTest];
            NSDictionary *dictFail = @{
                                       @"execution_id":[NSNumber numberWithInteger:TestCaseResultUpdate.executionID],
                                       @"execution_result":@"FAILED",
                                       @"start_time":self->startTime,
                                       @"end_time":self->endTime,
                                       @"params_used":@"",
                                       @"result_data":@""
                                       };
            [self requestUpdateResult:dictFail];
            
            
        }
        
        
    }else if ([self.executions.testcaseName isEqualToString:@"GET_API_SDK_VERSION"])
    {
        startTime = [self calculateTime];
        NSString *sdkVersion = [RDNA getSDKVersion];
        NSLog(@"sdkVersion :- %@",sdkVersion);
        
    }
    
    
    [self calculateCompleteTestCaseCount:countForPassTest withFailCount:countForFailTest];
    // [self calculateCanNotTestCaseCount:(int)self.testjobexecutions.executions.count withCompleteCount:countCompleted];
    
}


#pragma mark Data privacy Methods

-(void)checkDataPrivacyTestCases :(NSString *)testCaseName withMutableString:(NSMutableString *)string
{
    if ([self.executions.testcaseName isEqualToString:@"GET_DEFAULT_CIPHER_SPEC"])
    {
        startTime = [self calculateTime];
        int cipherSpec = [rdnaObject getDefaultCipherSpec:&string];
        NSLog(@"cipherSpec :- %d",cipherSpec);
        NSLog(@"cipherSpecString :- %@",string);
        
        if (cipherSpec == 0) {
            NSLog(@"Test Case Passed For GET_DEFAULT_CIPHER_SPEC");
            endTime = [self calculateTime];
            countForPassTest = countForPassTest +1;
            [self passTestCase:countForPassTest];
            
            NSDictionary *dictPass = @{
                                       @"execution_id":[NSNumber numberWithInteger:TestCaseResultUpdate.executionID],
                                       @"execution_result":@"PASSED",
                                       @"start_time":self->startTime,
                                       @"end_time":self->endTime,
                                       @"params_used":@"",
                                       @"result_data":@""
                                       };
            
            [self requestUpdateResult:dictPass];
            
            
        }else{
            NSLog(@"Test Case Failed For GET_DEFAULT_CIPHER_SPEC");
            endTime = [self calculateTime];
            countForFailTest = countForFailTest+1;
            [self FailTestCase:countForFailTest];
            NSDictionary *dictFail = @{
                                       @"execution_id":[NSNumber numberWithInteger:TestCaseResultUpdate.executionID],
                                       @"execution_result":@"FAILED",
                                       @"start_time":self->startTime,
                                       @"end_time":self->endTime,
                                       @"params_used":@"",
                                       @"result_data":@""
                                       };
            [self requestUpdateResult:dictFail];
        }
        
        
        
        
    }else if ([self.executions.testcaseName isEqualToString:@"GET_DEFAULT_CIPHER_SALT"])
    {
        startTime = [self calculateTime];
        int cipherSalt = [rdnaObject getDefaultCipherSalt:&string];
        NSLog(@"cipherSalt :- %d",cipherSalt);
        NSLog(@"cipherSaltString :- %@",string);
        
        if (cipherSalt == 0) {
            NSLog(@"Test Case Passed For GET_DEFAULT_CIPHER_SALT");
            endTime = [self calculateTime];
            countForPassTest = countForPassTest +1;
            [self passTestCase:countForPassTest];
            
            NSDictionary *dictPass = @{
                                       @"execution_id":[NSNumber numberWithInteger:TestCaseResultUpdate.executionID],
                                       @"execution_result":@"PASSED",
                                       @"start_time":self->startTime,
                                       @"end_time":self->endTime,
                                       @"params_used":@"",
                                       @"result_data":@""
                                       };
            [self requestUpdateResult:dictPass];
            
            
        }else{
             NSLog(@"Test Case Failed For GET_DEFAULT_CIPHER_SALT");
            endTime = [self calculateTime];
            countForFailTest = countForFailTest+1;
            [self FailTestCase:countForFailTest];
            NSDictionary *dictFail = @{
                                       @"execution_id":[NSNumber numberWithInteger:TestCaseResultUpdate.executionID],
                                       @"execution_result":@"FAILED",
                                       @"start_time":self->startTime,
                                       @"end_time":self->endTime,
                                       @"params_used":@"",
                                       @"result_data":@""
                                       };
            [self requestUpdateResult:dictFail];
        }
        
        
    }
    else if ([self.executions.testcaseName isEqualToString:@"ENCRYPT_DATA_PACKET"])
    {
       
        startTime = [self calculateTime];
        // NSString *strRespoData = @"uniken123";
        // NSMutableData *respoData = [[strRespoData dataUsingEncoding:NSUTF8StringEncoding]mutableCopy];
        NSMutableData *respoData = nil;
        NSString *strData = @"uniken";
        NSData *data = [strData dataUsingEncoding:NSUTF8StringEncoding];
        int errForEncryptDataPacket = 0;
        if (rdnaObject) {
            errForEncryptDataPacket = [rdnaObject encryptDataPacket:RDNA_PRIVACY_SCOPE_DEVICE CipherSpec:@"AES/256/CFB/PKCS7Padding" CipherSalt:@"Rel-ID-Secure-IV" From:data Into:&respoData];
            responseData = respoData;
        }
        
        
        
        if (errForEncryptDataPacket == 0) {
             NSLog(@"Test Case Passed For ENCRYPT_DATA_PACKET");
            endTime = [self calculateTime];
            countForPassTest = countForPassTest +1;
            [self passTestCase:countForPassTest];
            
            NSDictionary *dictPass = @{
                                       @"execution_id":[NSNumber numberWithInteger:TestCaseResultUpdate.executionID],
                                       @"execution_result":@"PASSED",
                                       @"start_time":self->startTime,
                                       @"end_time":self->endTime,
                                       @"params_used":@"",
                                       @"result_data":@""
                                       };
            [self requestUpdateResult:dictPass];
            
            
        }else{
             NSLog(@"Test Case Failed For ENCRYPT_DATA_PACKET");
            endTime = [self calculateTime];
            countForFailTest = countForFailTest+1;
            [self FailTestCase:countForFailTest];
            NSDictionary *dictFail = @{
                                       @"execution_id":[NSNumber numberWithInteger:TestCaseResultUpdate.executionID],
                                       @"execution_result":@"FAILED",
                                       @"start_time":self->startTime,
                                       @"end_time":self->endTime,
                                       @"params_used":@"",
                                       @"result_data":@""
                                       };
            [self requestUpdateResult:dictFail];
        }
    }
    else if ([self.executions.testcaseName isEqualToString:@"ENCRYPT_DATA_PACKET_EMPTY_CIPHERSPEC"])
    {
        
        startTime = [self calculateTime];
        NSString *strRespoData = @"uniken123";
        NSMutableData *respoData = [[strRespoData dataUsingEncoding:NSUTF8StringEncoding]mutableCopy];
        NSString *strData = @"uniken";
        NSData *data = [strData dataUsingEncoding:NSUTF8StringEncoding];
        int errForEncryptDataPacket = 0;
        RDNAStatusInit *rdnaStatus = [[RDNAStatusInit alloc]init];
        if (rdnaObject) {
            errForEncryptDataPacket = [rdnaObject encryptDataPacket:RDNA_PRIVACY_SCOPE_DEVICE CipherSpec:@"onkar" CipherSalt:@"Rel-ID-Secure-IV" From:data Into:&respoData];
            rdnaStatus.errCode = errForEncryptDataPacket;
        }
        
        
        
        if (errForEncryptDataPacket > 0) {
             NSLog(@"Test Case Passed For ENCRYPT_DATA_PACKET_EMPTY_CIPHERSPEC");
            endTime = [self calculateTime];
            countForPassTest = countForPassTest +1;
            [self passTestCase:countForPassTest];
            
            NSDictionary *dictPass = @{
                                       @"execution_id":[NSNumber numberWithInteger:TestCaseResultUpdate.executionID],
                                       @"execution_result":@"PASSED",
                                       @"start_time":self->startTime,
                                       @"end_time":self->endTime,
                                       @"params_used":@"",
                                       @"result_data":@""
                                       };
            [self requestUpdateResult:dictPass];
            
            
        }else{
            NSLog(@"\n\n $$$$$ Notify runtime status of client called reason {%ld : %@} $$$$ \n\n",(long)[RDNA getErrorInfo:rdnaStatus.errCode],rdnaStatus.description);
            
             NSLog(@"Test Case Failed For ENCRYPT_DATA_PACKET_EMPTY_CIPHERSPEC");
            
            endTime = [self calculateTime];
            countForFailTest = countForFailTest+1;
            [self FailTestCase:countForFailTest];
            NSDictionary *dictFail = @{
                                       @"execution_id":[NSNumber numberWithInteger:TestCaseResultUpdate.executionID],
                                       @"execution_result":@"FAILED",
                                       @"start_time":self->startTime,
                                       @"end_time":self->endTime,
                                       @"params_used":@"",
                                       @"result_data":@""
                                       };
            [self requestUpdateResult:dictFail];
        }
    }
    else if ([self.executions.testcaseName isEqualToString:@"ENCRYPT_DATA_PACKET_GET_EMPTY_CIPHERSALT"])
    {
        
        startTime = [self calculateTime];
        NSString *strRespoData = @"uniken123";
        NSMutableData *respoData = [[strRespoData dataUsingEncoding:NSUTF8StringEncoding]mutableCopy];
        NSString *strData = @"uniken";
        NSData *data = [strData dataUsingEncoding:NSUTF8StringEncoding];
        int errForEncryptDataPacket = 0;
        RDNAStatusInit *rdnaStatus = [[RDNAStatusInit alloc]init];
        if (rdnaObject) {
            errForEncryptDataPacket = [rdnaObject encryptDataPacket:RDNA_PRIVACY_SCOPE_DEVICE CipherSpec:@"AES/256/CFB/PKCS7Padding" CipherSalt:@"onkar1211213213213213123213213123213213" From:data Into:&respoData];
            rdnaStatus.errCode = errForEncryptDataPacket;
        }
        
        
        
        if (errForEncryptDataPacket > 0) {
            
             NSLog(@"Test Case Passed For ENCRYPT_DATA_PACKET_GET_EMPTY_CIPHERSALT");
            endTime = [self calculateTime];
            countForPassTest = countForPassTest +1;
            [self passTestCase:countForPassTest];
            
            NSDictionary *dictPass = @{
                                       @"execution_id":[NSNumber numberWithInteger:TestCaseResultUpdate.executionID],
                                       @"execution_result":@"PASSED",
                                       @"start_time":self->startTime,
                                       @"end_time":self->endTime,
                                       @"params_used":@"",
                                       @"result_data":@""
                                       };
            [self requestUpdateResult:dictPass];
            
            
        }else{
            NSLog(@"\n\n $$$$$ Notify runtime status of client called reason {%ld : %@} $$$$ \n\n",(long)[RDNA getErrorInfo:rdnaStatus.errCode],rdnaStatus.description);
            NSLog(@"Test Case Failed For ENCRYPT_DATA_PACKET_GET_EMPTY_CIPHERSALT");
            endTime = [self calculateTime];
            countForFailTest = countForFailTest+1;
            [self FailTestCase:countForFailTest];
            NSDictionary *dictFail = @{
                                       @"execution_id":[NSNumber numberWithInteger:TestCaseResultUpdate.executionID],
                                       @"execution_result":@"FAILED",
                                       @"start_time":self->startTime,
                                       @"end_time":self->endTime,
                                       @"params_used":@"",
                                       @"result_data":@""
                                       };
            [self requestUpdateResult:dictFail];
        }
    }
    else if ([self.executions.testcaseName isEqualToString:@"ENCRYPT_DATA_PACKET_GET_EMPTY_PLAINTEXT"])
    {
        startTime = [self calculateTime];
        NSString *strRespoData = @"uniken123";
        NSMutableData *respoData = [[strRespoData dataUsingEncoding:NSUTF8StringEncoding]mutableCopy];
        // NSString *strData = @"uniken";
        // NSData *data = [strData dataUsingEncoding:NSUTF8StringEncoding];
        int errForEncryptDataPacket = 0;
        RDNAStatusInit *rdnaStatus = [[RDNAStatusInit alloc]init];
        if (rdnaObject) {
            errForEncryptDataPacket = [rdnaObject encryptDataPacket:RDNA_PRIVACY_SCOPE_DEVICE CipherSpec:@"AES/256/CFB/PKCS7Padding" CipherSalt:@"Rel-ID-Secure-IV" From:nil Into:&respoData];
            rdnaStatus.errCode = errForEncryptDataPacket;
        }
        
        
        
        if (errForEncryptDataPacket > 0) {
            NSLog(@"Test Case Passed For ENCRYPT_DATA_PACKET_GET_EMPTY_PLAINTEXT");
            endTime = [self calculateTime];
            countForPassTest = countForPassTest +1;
            [self passTestCase:countForPassTest];
            
            NSDictionary *dictPass = @{
                                       @"execution_id":[NSNumber numberWithInteger:TestCaseResultUpdate.executionID],
                                       @"execution_result":@"PASSED",
                                       @"start_time":self->startTime,
                                       @"end_time":self->endTime,
                                       @"params_used":@"",
                                       @"result_data":@""
                                       };
            [self requestUpdateResult:dictPass];
            
            
        }else{
            
            NSLog(@"\n\n $$$$$ Notify runtime status of client called reason {%ld : %@} $$$$ \n\n",(long)[RDNA getErrorInfo:rdnaStatus.errCode],rdnaStatus.description);
            NSLog(@"Test Case Failed For ENCRYPT_DATA_PACKET_GET_EMPTY_PLAINTEXT");
            
            endTime = [self calculateTime];
            countForFailTest = countForFailTest+1;
            [self FailTestCase:countForFailTest];
            NSDictionary *dictFail = @{
                                       @"execution_id":[NSNumber numberWithInteger:TestCaseResultUpdate.executionID],
                                       @"execution_result":@"FAILED",
                                       @"start_time":self->startTime,
                                       @"end_time":self->endTime,
                                       @"params_used":@"",
                                       @"result_data":@""
                                       };
            [self requestUpdateResult:dictFail];
        }
    }
    else if ([self.executions.testcaseName isEqualToString:@"DECRYPT_DATA_PACKET"])
    {
       
        startTime = [self calculateTime];
        // NSString *strRespoData = @"uniken123";
        NSMutableData *respoData = [NSMutableData data];
        // NSString *strData = @"uniken";
        // NSData *data = [strData dataUsingEncoding:NSUTF8StringEncoding];
        int errForDecryptDataPacket = 0;
        RDNAStatusInit *rdnaStatus = [[RDNAStatusInit alloc]init];
        if (rdnaObject) {
            errForDecryptDataPacket = [rdnaObject decryptDataPacket:RDNA_PRIVACY_SCOPE_DEVICE CipherSpec:@"AES/256/CFB/PKCS7Padding" CipherSalt:@"Rel-ID-Secure-IV" From:[responseData mutableCopy] Into:&respoData];
            NSString *str = [[NSString alloc]initWithData:[respoData mutableCopy] encoding:NSUTF8StringEncoding];
            NSLog(@"Decryption Data :- %@",str);
            rdnaStatus.errCode = errForDecryptDataPacket;
        }
        
        
        
        if (errForDecryptDataPacket == 0) {
            
            NSLog(@"Test Case Passed For DECRYPT_DATA_PACKET");
            
            endTime = [self calculateTime];
            countForPassTest = countForPassTest +1;
            [self passTestCase:countForPassTest];
            
            NSDictionary *dictPass = @{
                                       @"execution_id":[NSNumber numberWithInteger:TestCaseResultUpdate.executionID],
                                       @"execution_result":@"PASSED",
                                       @"start_time":self->startTime,
                                       @"end_time":self->endTime,
                                       @"params_used":@"",
                                       @"result_data":@""
                                       };
            [self requestUpdateResult:dictPass];
            
            
        }else{
            
            NSLog(@"\n\n $$$$$ Notify runtime status of client called reason {%ld : %@} $$$$ \n\n",(long)[RDNA getErrorInfo:rdnaStatus.errCode],rdnaStatus.description);
             NSLog(@"Test Case Failed For DECRYPT_DATA_PACKET");
            
            endTime = [self calculateTime];
            countForFailTest = countForFailTest+1;
            [self FailTestCase:countForFailTest];
            NSDictionary *dictFail = @{
                                       @"execution_id":[NSNumber numberWithInteger:TestCaseResultUpdate.executionID],
                                       @"execution_result":@"FAILED",
                                       @"start_time":self->startTime,
                                       @"end_time":self->endTime,
                                       @"params_used":@"",
                                       @"result_data":@""
                                       };
            [self requestUpdateResult:dictFail];
        }
    }
    else if ([self.executions.testcaseName isEqualToString:@"DECRYPT_DATA_PACKET_EMPTY_CIPHERSPEC"])
    {
        
        startTime = [self calculateTime];
        // NSString *strRespoData = @"uniken123";
        NSMutableData *respoData = [NSMutableData data];
        // NSString *strData = @"uniken";
        // NSData *data = [strData dataUsingEncoding:NSUTF8StringEncoding];
        int errForDecryptDataPacket = 0;
        RDNAStatusInit *rdnaStatus = [[RDNAStatusInit alloc]init];
        if (rdnaObject) {
            errForDecryptDataPacket = [rdnaObject decryptDataPacket:RDNA_PRIVACY_SCOPE_DEVICE CipherSpec:@"onkar" CipherSalt:@"Rel-ID-Secure-IV" From:[responseData mutableCopy] Into:&respoData];
            NSString *str = [[NSString alloc]initWithData:[respoData mutableCopy] encoding:NSUTF8StringEncoding];
            NSLog(@"Decryption Data :- %@",str);
            rdnaStatus.errCode = errForDecryptDataPacket;
        }
        
        
        
        if (errForDecryptDataPacket > 0) {
            NSLog(@"Test Case Passed For DECRYPT_DATA_PACKET_EMPTY_CIPHERSPEC");
            endTime = [self calculateTime];
            countForPassTest = countForPassTest +1;
            [self passTestCase:countForPassTest];
            
            NSDictionary *dictPass = @{
                                       @"execution_id":[NSNumber numberWithInteger:TestCaseResultUpdate.executionID],
                                       @"execution_result":@"PASSED",
                                       @"start_time":self->startTime,
                                       @"end_time":self->endTime,
                                       @"params_used":@"",
                                       @"result_data":@""
                                       };
            [self requestUpdateResult:dictPass];
            
            
        }else{
            
            NSLog(@"\n\n $$$$$ DECRYPT_DATA_PACKET_EMPTY_CIPHERSPEC {%ld : %@} $$$$ \n\n",(long)[RDNA getErrorInfo:rdnaStatus.errCode],rdnaStatus.description);
             NSLog(@"Test Case Failed For DECRYPT_DATA_PACKET_EMPTY_CIPHERSPEC");
            endTime = [self calculateTime];
            countForFailTest = countForFailTest+1;
            [self FailTestCase:countForFailTest];
            NSDictionary *dictFail = @{
                                       @"execution_id":[NSNumber numberWithInteger:TestCaseResultUpdate.executionID],
                                       @"execution_result":@"FAILED",
                                       @"start_time":self->startTime,
                                       @"end_time":self->endTime,
                                       @"params_used":@"",
                                       @"result_data":@""
                                       };
            [self requestUpdateResult:dictFail];
        }
    }
    else if ([self.executions.testcaseName isEqualToString:@"DECRYPT_DATA_PACKET_EMPTY_CIPHERSALT"])
    {
        startTime = [self calculateTime];
        // NSString *strRespoData = @"uniken123";
        NSMutableData *respoData = [NSMutableData data];
        // NSString *strData = @"uniken";
        // NSData *data = [strData dataUsingEncoding:NSUTF8StringEncoding];
        int errForDecryptDataPacket = 0;
        RDNAStatusInit *rdnaStatus = [[RDNAStatusInit alloc]init];
        if (rdnaObject) {
            errForDecryptDataPacket = [rdnaObject decryptDataPacket:RDNA_PRIVACY_SCOPE_DEVICE CipherSpec:@"AES/256/CFB/PKCS7Padding" CipherSalt:@"Rel-ID-Secure-IV" From:[responseData mutableCopy] Into:&respoData];
            NSString *str = [[NSString alloc]initWithData:[respoData mutableCopy] encoding:NSUTF8StringEncoding];
            NSLog(@"Decryption Data :- %@",str);
            rdnaStatus.errCode = errForDecryptDataPacket;
        }
        
        
        
        if (errForDecryptDataPacket > 0) {
             NSLog(@"Test Case Passed For DECRYPT_DATA_PACKET_EMPTY_CIPHERSALT");
            
            endTime = [self calculateTime];
            countForPassTest = countForPassTest +1;
            [self passTestCase:countForPassTest];
            
            NSDictionary *dictPass = @{
                                       @"execution_id":[NSNumber numberWithInteger:TestCaseResultUpdate.executionID],
                                       @"execution_result":@"PASSED",
                                       @"start_time":self->startTime,
                                       @"end_time":self->endTime,
                                       @"params_used":@"",
                                       @"result_data":@""
                                       };
            [self requestUpdateResult:dictPass];
            
            
        }else{
            
            NSLog(@"\n\n $$$$$ DECRYPT_DATA_PACKET_EMPTY_CIPHERSALT {%ld : %@} $$$$ \n\n",(long)[RDNA getErrorInfo:rdnaStatus.errCode],rdnaStatus.description);
            NSLog(@"Test Case Failed For DECRYPT_DATA_PACKET_EMPTY_CIPHERSALT");
            
            endTime = [self calculateTime];
            countForFailTest = countForFailTest+1;
            [self FailTestCase:countForFailTest];
            NSDictionary *dictFail = @{
                                       @"execution_id":[NSNumber numberWithInteger:TestCaseResultUpdate.executionID],
                                       @"execution_result":@"FAILED",
                                       @"start_time":self->startTime,
                                       @"end_time":self->endTime,
                                       @"params_used":@"",
                                       @"result_data":@""
                                       };
            [self requestUpdateResult:dictFail];
        }
    }
    else if ([self.executions.testcaseName isEqualToString:@"DECRYPT_DATA_PACKET_GET_EMPTY_PLAINTEXT"])
    {
        startTime = [self calculateTime];
        // NSString *strRespoData = @"uniken123";
        NSMutableData *respoData = [NSMutableData data];
        // NSString *strData = @"uniken";
        // NSData *data = [strData dataUsingEncoding:NSUTF8StringEncoding];
        int errForDecryptDataPacket = 0;
        RDNAStatusInit *rdnaStatus = [[RDNAStatusInit alloc]init];
        if (rdnaObject) {
            errForDecryptDataPacket = [rdnaObject decryptDataPacket:RDNA_PRIVACY_SCOPE_DEVICE CipherSpec:@"AES/256/CFB/PKCS7Padding" CipherSalt:@"Rel-ID-Secure-IV" From:nil Into:&respoData];
            NSString *str = [[NSString alloc]initWithData:[respoData mutableCopy] encoding:NSUTF8StringEncoding];
            NSLog(@"Decryption Data :- %@",str);
            rdnaStatus.errCode = errForDecryptDataPacket;
        }
        
        
        
        if (errForDecryptDataPacket > 0) {
            NSLog(@"Test Case Passed For DECRYPT_DATA_PACKET_GET_EMPTY_PLAINTEXT");
            
            endTime = [self calculateTime];
            countForPassTest = countForPassTest +1;
            [self passTestCase:countForPassTest];
            
            NSDictionary *dictPass = @{
                                       @"execution_id":[NSNumber numberWithInteger:TestCaseResultUpdate.executionID],
                                       @"execution_result":@"PASSED",
                                       @"start_time":self->startTime,
                                       @"end_time":self->endTime,
                                       @"params_used":@"",
                                       @"result_data":@""
                                       };
            [self requestUpdateResult:dictPass];
            
            
        }else{
            
            NSLog(@"\n\n $$$$$ DECRYPT_DATA_PACKET_GET_EMPTY_PLAINTEXT {%ld : %@} $$$$ \n\n",(long)[RDNA getErrorInfo:rdnaStatus.errCode],rdnaStatus.description);
             NSLog(@"Test Case Failed For DECRYPT_DATA_PACKET_GET_EMPTY_PLAINTEXT");
            
            endTime = [self calculateTime];
            countForFailTest = countForFailTest+1;
            [self FailTestCase:countForFailTest];
            NSDictionary *dictFail = @{
                                       @"execution_id":[NSNumber numberWithInteger:TestCaseResultUpdate.executionID],
                                       @"execution_result":@"FAILED",
                                       @"start_time":self->startTime,
                                       @"end_time":self->endTime,
                                       @"params_used":@"",
                                       @"result_data":@""
                                       };
            [self requestUpdateResult:dictFail];
        }
    }
    [self calculateCompleteTestCaseCount:countForPassTest withFailCount:countForFailTest];
}


-(int)onSecurityThreat:(NSString*)status{
    dispatch_async(dispatch_get_main_queue(), ^(void){
        NSLog(@"onSecurityThreat called");
    });
    return 1;
}

- (void)encodeWithCoder:(nonnull NSCoder *)aCoder {
    
}

- (void)traitCollectionDidChange:(nullable UITraitCollection *)previousTraitCollection {
    
}

- (void)preferredContentSizeDidChangeForChildContentContainer:(nonnull id<UIContentContainer>)container {
    
}

- (CGSize)sizeForChildContentContainer:(nonnull id<UIContentContainer>)container withParentContainerSize:(CGSize)parentSize {
    return CGSizeZero;
}

- (void)systemLayoutFittingSizeDidChangeForChildContentContainer:(nonnull id<UIContentContainer>)container {
    
}

- (void)viewWillTransitionToSize:(CGSize)size withTransitionCoordinator:(nonnull id<UIViewControllerTransitionCoordinator>)coordinator {
    
}

- (void)willTransitionToTraitCollection:(nonnull UITraitCollection *)newCollection withTransitionCoordinator:(nonnull id<UIViewControllerTransitionCoordinator>)coordinator {
    
}

- (void)didUpdateFocusInContext:(nonnull UIFocusUpdateContext *)context withAnimationCoordinator:(nonnull UIFocusAnimationCoordinator *)coordinator  API_AVAILABLE(ios(9.0)){
    
}

- (void)setNeedsFocusUpdate {
    
}

- (BOOL)shouldUpdateFocusInContext:(nonnull UIFocusUpdateContext *)context  API_AVAILABLE(ios(9.0)){
    return NO;
}

- (void)updateFocusIfNeeded {
    
}
-(int)onSdkLogPrintRequest:(RDNALoggingLevel)level andlogData:(NSString*)logData
{
    NSLog(@"/n  %@",logData);
    return 1;
}

#pragma mark calculateTime

- (NSString *) calculateTime
{
    /*  NSDate * now = [NSDate date];
     NSDateFormatter *outputFormatter = [[NSDateFormatter alloc] init];
     [outputFormatter setDateFormat:@"HH:mm:ss"];
     NSString *Time = [outputFormatter stringFromDate:now];
     NSLog(@"Time %@", Time);
     return Time;*/
    
    NSDateFormatter *formatter = [[NSDateFormatter alloc] init];
    //formatter.dateFormat = @"yyyy-MM-dd'T'HH:mm:ss.SSSSSSSZZZZ";
    formatter.dateFormat = @"yyyy-MM-dd HH:mm:ss";
    NSLog(@"%@", [formatter stringFromDate:[NSDate date]]);
    return [NSString stringWithFormat:@"%@",[formatter stringFromDate:[NSDate date]]];
}

- (void)requestUpdateResult :(NSDictionary *)MapData
{
    // mainstr = [NSString stringWithFormat:@"http://10.0.0.135:8080/automation/updateTestResults.htm"];
    
    mainstr = [NSString stringWithFormat:@"http://34.235.131.241:8080/automation/updateTestResults.htm"];
    // mainstr = [NSString stringWithFormat:@"http://10.0.0.135:8080/automation/updateTestResults.htm"];
    
    [webservice executequeryForPostingToServer:mainstr strprameter:MapData withblock:^(NSData *data, NSError *error) {
        if (!error) {
            NSString *str = [[NSString alloc]initWithData:data encoding:NSUTF8StringEncoding];
            NSLog(@"str :- %@",str);
            [self testExecute];
            
        }
        
    }];
    
    
    
}


- (void)requestUpdateResultPassCount :(NSDictionary *)MapData Passcount:(int)passCount
{
    // mainstr = [NSString stringWithFormat:@"http://10.0.0.135:8080/automation/updateTestResults.htm"];
    
    mainstr = [NSString stringWithFormat:@"http://34.235.131.241:8080/automation/updateTestResults.htm"];
    // mainstr = [NSString stringWithFormat:@"http://10.0.0.135:8080/automation/updateTestResults.htm"];
    
    [webservice executequeryForPostingToServer:mainstr strprameter:MapData withblock:^(NSData *data, NSError *error) {
        if (!error) {
            NSString *str = [[NSString alloc]initWithData:data encoding:NSUTF8StringEncoding];
            NSLog(@"str :- %@",str);
            
            NSLog(@"count :- %d",passCount);
            dispatch_async(dispatch_get_main_queue(), ^(void){
                //Run UI Updates
                self.passedTestCount.text = [NSString stringWithFormat:@"%d",passCount];
            });
        }
        
    }];
    
    
    
}

- (void)requestUpdateResultFailCount :(NSDictionary *)MapData Failcount:(int)failCount
{
    // mainstr = [NSString stringWithFormat:@"http://10.0.0.135:8080/automation/updateTestResults.htm"];
    
    mainstr = [NSString stringWithFormat:@"http://34.235.131.241:8080/automation/updateTestResults.htm"];
    // mainstr = [NSString stringWithFormat:@"http://10.0.0.135:8080/automation/updateTestResults.htm"];
    
    [webservice executequeryForPostingToServer:mainstr strprameter:MapData withblock:^(NSData *data, NSError *error) {
        if (!error) {
            NSString *str = [[NSString alloc]initWithData:data encoding:NSUTF8StringEncoding];
            NSLog(@"str :- %@",str);
            
            NSLog(@"count :- %d",failCount);
            dispatch_async(dispatch_get_main_queue(), ^(void){
                //Run UI Updates
                self.failedTestCount.text = [NSString stringWithFormat:@"%d",failCount];
            });
        }
        
    }];
    
    
    
}

- (void)requestUpdateResultCannotTestCount :(NSDictionary *)MapData WithCannotTestCount:(int)CannotTestCount
{
    // mainstr = [NSString stringWithFormat:@"http://10.0.0.135:8080/automation/updateTestResults.htm"];
    
    mainstr = [NSString stringWithFormat:@"http://34.235.131.241:8080/automation/updateTestResults.htm"];
    // mainstr = [NSString stringWithFormat:@"http://10.0.0.135:8080/automation/updateTestResults.htm"];
    
    [webservice executequeryForPostingToServer:mainstr strprameter:MapData withblock:^(NSData *data, NSError *error) {
        if (!error) {
            NSString *str = [[NSString alloc]initWithData:data encoding:NSUTF8StringEncoding];
            NSLog(@"str :- %@",str);
            
            NSLog(@"count :- %d",CannotTestCount);
            dispatch_async(dispatch_get_main_queue(), ^(void){
                //Run UI Updates
                self.CannotTestCount.text = [NSString stringWithFormat:@"%d",CannotTestCount];
                [self testExecute];
            });
        }
        
    }];
    
    
    
}












- (NSDictionary<NSString *, NSString *> *)properties
{
    static NSDictionary<NSString *, NSString *> *properties;
    return properties = properties ? properties : @{
                                                    @"serial_num":@"my phone 345",
                                                    @"device_model":@"hone",
                                                    @"device_os":@"ios",
                                                    @"os_version":@"123",
                                                    @"build_id":@"buildID",
                                                    @"manufacturer":@"mannufacturer1",
                                                    @"brand":@"brand1",
                                                    @"library_version":@"1.0"
                                                    };
}


- (NSDictionary *)JSONDictionary
{
    id dict = [[self dictionaryWithValuesForKeys:self.properties.allValues] mutableCopy];
    
    // Rewrite property names that differ in JSON
    for (id jsonName in self.properties) {
        id propertyName = self.properties[jsonName];
        if (![jsonName isEqualToString:propertyName]) {
            dict[jsonName] = dict[propertyName];
            [dict removeObjectForKey:propertyName];
        }
    }
    
    return dict;
}

- (void)passTestCase : (int)count
{
    NSLog(@"count :- %d",count);
     NSLog(@"CurrentThreadSynch :- %@", [NSThread currentThread]);
    dispatch_async(dispatch_get_main_queue(), ^(void){
        //Run UI Updates
        self.passedTestCount.text = [NSString stringWithFormat:@"%d",count];
    });
    
}

- (void)FailTestCase : (int)count
{
    NSLog(@"count :- %d",count);
     NSLog(@"CurrentThreadSynch :- %@", [NSThread currentThread]);
    dispatch_async(dispatch_get_main_queue(), ^(void){
        //Run UI Updates
        self.failedTestCount.text = [NSString stringWithFormat:@"%d",count];
    });
    
}

- (void)CannoTestCase : (int)count
{
    NSLog(@"count :- %d",count);
    dispatch_async(dispatch_get_main_queue(), ^(void){
        //Run UI Updates
        self.CannotTestCount.text = [NSString stringWithFormat:@"%d",count];
    });
    
}





- (void) calculateCompleteTestCaseCount :(int)passCount withFailCount:(int)failCount
{
    __block int completeTestCount = 0;
    dispatch_async(dispatch_get_main_queue(), ^(void){
        //Run UI Updates
        self.completeTestCount.text = [NSString stringWithFormat:@"%d",self->countForPassTest+self->countForFailTest];
        completeTestCount = [self.completeTestCount.text intValue];
        self->countCompleted = completeTestCount;
    });
    
}

- (void) calculateCanNotTestCaseCount :(int)totalCount withCompleteCount:(int)CompleteCount
{
    __block int CanNotTestCount = 0;
    dispatch_async(dispatch_get_main_queue(), ^(void){
        //Run UI Updates
        self.CannotTestCount.text = [NSString stringWithFormat:@"%d",totalCount-CompleteCount-1];
        CanNotTestCount = [self.CannotTestCount.text intValue];
        NSLog(@"CanNotTestCount :- %d",CanNotTestCount);
    });
    
    
}




- (QTestEnrolluserResult *)requestEnrollUser:(TestEnrollUser *)enrollUser andExecutionId :(NSInteger)executionId
{
    mainstr = [NSString stringWithFormat:@"http://18.206.222.73:9080/rest/enrollUser.htm"];
    [webservice executequeryForEnrolluser:mainstr portNumber:ServicePort strEnrolluser:enrollUser withblock:^(NSData *data, NSError *error) {
        if (!error) {
            NSString *str = [[NSString alloc]initWithData:data encoding:NSUTF8StringEncoding];
            // NSLog(@"str :- %@",str);
            // [self requestEnrollUserDevice:self->testEnrolluser];
            QTestEnrolluserResult *enrolluser = [QTestEnrolluserResult fromJSON:str encoding:NSUTF8StringEncoding error:&error];
            self->enrolluser = enrolluser;
            self->endTime = [self calculateTime];
            self->countForPassTest = self->countForPassTest+1;
            [self passTestCase:self->countForPassTest];
            NSDictionary *dictPass = @{
                                       @"execution_id":[NSNumber numberWithInteger:executionId],
                                       @"execution_result":@"PASSED",
                                       @"start_time":self->startTime,
                                       @"end_time":self->endTime,
                                       @"params_used":@"",
                                       @"result_data":@""
                                       };
            
           // dispatch_async(dispatch_get_main_queue(), ^(void){
            
            
                [self requestUpdateResult:dictPass];
       
            
          RDNAResponseStatus *challengeStatus = [[RDNAResponseStatus alloc] init];
            challengeStatus.statusCode = RDNA_RESP_STATUS_SUCCESS;
            [[UIStateMachine getSharedInstance] setInitialChalleges:[self.statusChallenges mutableCopy]];
            [[UIStateMachine getSharedInstance] setChalleges:[self.statusChallenges mutableCopy]];
            [self InitializeState:[[StatusInfo alloc] initWithChalleges:self.statusChallenges withChallengeStatus:challengeStatus withAPIFlag:CHECKCHALLENGES] withUpdateFlag:NO];
            
        }else
        {
            self->endTime = [self calculateTime];
            self->countForFailTest = self->countForFailTest+1;
            [self FailTestCase:self->countForFailTest];
            NSDictionary *dictFail = @{
                                       @"execution_id":[NSNumber numberWithInteger:executionId],
                                       @"execution_result":@"FAILED",
                                       @"start_time":self->startTime,
                                       @"end_time":self->endTime,
                                       @"params_used":@"",
                                       @"result_data":@""
                                       };
            
            
            
                [self requestUpdateResult:dictFail];
       
            
        }
    }];
    return self->enrolluser;
}

- (QTestEnrolluserResult *)requestEnrollUserDevice:(TestEnrollUser *)enrollUser andExecutionId :(NSInteger)executionId
{
    mainstr = [NSString stringWithFormat:@"http://18.206.222.73:9080/rest/enrollUserDevice.htm"];
    [webservice executequeryForEnrolluserDevice:mainstr portNumber:ServicePort strEnrolluser:enrollUser withblock:^(NSData *data, NSError *error) {
        if (!error) {
            NSString *str = [[NSString alloc]initWithData:data encoding:NSUTF8StringEncoding];
            // NSLog(@"str :- %@",str);
            // [self requestEnrollUserDevice:self->testEnrolluser];
            QTestEnrolluserResult *enrolluser = [QTestEnrolluserResult fromJSON:str encoding:NSUTF8StringEncoding error:&error];
            self->enrolluser = enrolluser;
            
            self->endTime = [self calculateTime];
            self->countForPassTest = self->countForPassTest+1;
            [self passTestCase:self->countForPassTest];
            NSDictionary *dictPass = @{
                                       @"execution_id":[NSNumber numberWithInteger:executionId],
                                       @"execution_result":@"PASSED",
                                       @"start_time":self->startTime,
                                       @"end_time":self->endTime,
                                       @"params_used":@"",
                                       @"result_data":@""
                                       };
            
            [self requestUpdateResult:dictPass];
        }else{
            self->endTime = [self calculateTime];
            self->countForFailTest = self->countForFailTest+1;
            [self FailTestCase:self->countForFailTest];
            NSDictionary *dictFail = @{
                                       @"execution_id":[NSNumber numberWithInteger:executionId],
                                       @"execution_result":@"FAILED",
                                       @"start_time":self->startTime,
                                       @"end_time":self->endTime,
                                       @"params_used":@"",
                                       @"result_data":@""
                                       };
            
            [self requestUpdateResult:dictFail];
        }
    }];
    return self->enrolluser;
}

- (QTestEnrolluserResult *)requestGetuserStatus:(TestEnrollUser *)enrollUser andExecutionId :(NSInteger)executionId
{
    mainstr = [NSString stringWithFormat:@"http://18.206.222.73:9080/rest/getUserStatus.htm"];
    [webservice executequeryForGetUserStatus:mainstr portNumber:ServicePort strEnrolluser:testEnrolluser withblock:^(NSData *data, NSError *error) {
        if (!error) {
            NSString *str = [[NSString alloc]initWithData:data encoding:NSUTF8StringEncoding];
            // NSLog(@"str :- %@",str);
            // [self requestEnrollUserDevice:self->testEnrolluser];
            QTestEnrolluserResult *enrolluser = [QTestEnrolluserResult fromJSON:str encoding:NSUTF8StringEncoding error:&error];
            self->enrolluser = enrolluser;
            self->endTime = [self calculateTime];
            self->countForPassTest = self->countForPassTest+1;
            [self passTestCase:self->countForPassTest];
            NSDictionary *dictPass = @{
                                       @"execution_id":[NSNumber numberWithInteger:executionId],
                                       @"execution_result":@"PASSED",
                                       @"start_time":self->startTime,
                                       @"end_time":self->endTime,
                                       @"params_used":@"",
                                       @"result_data":@""
                                       };
            
            [self requestUpdateResult:dictPass];
        }else{
            self->endTime = [self calculateTime];
            self->countForFailTest = self->countForFailTest+1;
            [self FailTestCase:self->countForFailTest];
            NSDictionary *dictFail = @{
                                       @"execution_id":[NSNumber numberWithInteger:executionId],
                                       @"execution_result":@"FAILED",
                                       @"start_time":self->startTime,
                                       @"end_time":self->endTime,
                                       @"params_used":@"",
                                       @"result_data":@""
                                       };
            
            [self requestUpdateResult:dictFail];
        }
    }];
    return self->enrolluser;
}


- (QTestEnrolluserResult *)requestGetUserId:(TestEnrollUser *)enrollUser andExecutionId :(NSInteger)executionId
{
    mainstr = [NSString stringWithFormat:@"http://18.206.222.73:9080/rest/getUserId.htm"];
    [webservice executequeryForGetUserId:mainstr portNumber:ServicePort strEnrolluser:enrollUser withblock:^(NSData *data, NSError *error) {
        if (!error) {
            NSString *str = [[NSString alloc]initWithData:data encoding:NSUTF8StringEncoding];
            // NSLog(@"str :- %@",str);
            // [self requestEnrollUserDevice:self->testEnrolluser];
            QTestEnrolluserResult *enrolluser = [QTestEnrolluserResult fromJSON:str encoding:NSUTF8StringEncoding error:&error];
            self->enrolluser = enrolluser;
            self->endTime = [self calculateTime];
            self->countForPassTest = self->countForPassTest+1;
            [self passTestCase:self->countForPassTest];
            NSDictionary *dictPass = @{
                                       @"execution_id":[NSNumber numberWithInteger:executionId],
                                       @"execution_result":@"PASSED",
                                       @"start_time":self->startTime,
                                       @"end_time":self->endTime,
                                       @"params_used":@"",
                                       @"result_data":@""
                                       };
            
            [self requestUpdateResult:dictPass];
            
            
        }else
        {
            self->endTime = [self calculateTime];
            self->countForFailTest = self->countForFailTest+1;
            [self FailTestCase:self->countForFailTest];
            NSDictionary *dictFail = @{
                                       @"execution_id":[NSNumber numberWithInteger:executionId],
                                       @"execution_result":@"FAILED",
                                       @"start_time":self->startTime,
                                       @"end_time":self->endTime,
                                       @"params_used":@"",
                                       @"result_data":@""
                                       };
            
            [self requestUpdateResult:dictFail];
            
        }
    }];
    return self->enrolluser;
}



#pragma Mark Challenges Related Callbacks


/**
 * @brief This method used to reset the challenge for reset the flow.
 */
- (int)RDNAClientResetChallenge {
    
    int retval = 0;
    retval = [rdnaObject resetChallenge];
    UIStateMachine *stateMachine = [UIStateMachine getSharedInstance];
    [stateMachine setChallenge:[stateMachine getInitialChallenge]];
    return retval;
}

/**
 * @brief This method checks for any pending challenges and then invokes the CheckChallenges api of the RDNA for the
 * given userID. Challenges response is obtained in the callBack i.e onCheckChallengeResponseStatus function.
 */
- (int)RDNAClientCheckChallenges:(NSArray *)challengeArray forUserID:(NSString *)userID {
    if ([[UIStateMachine getSharedInstance] allChallengesDone]) {
        return   [rdnaObject checkChallengeResponse:[[UIStateMachine getSharedInstance] getChalleges] forUserID:userID];
    }else{
        RDNAResponseStatus *challengeStatus = [[RDNAResponseStatus alloc] init];
        challengeStatus.statusCode = RDNA_RESP_STATUS_SUCCESS;
        [self InitializeState:[[StatusInfo alloc] initWithChalleges:nil withChallengeStatus:challengeStatus withAPIFlag:CHECKCHALLENGES] withUpdateFlag:false];
    }
    
    return 0;
}

/**
 * @brief This method checks for any pending challenges and then invokes the updateChallenges api of the RDNA for the
 * given userID. Challenges response is obtained in the callBack i.e onCheckChallengeResponseStatus function.
 */
- (int)RDNAClientUpdateChallenges:(NSArray *)challengeArray forUserID:(NSString *)userID {
    
    // return [rdnaObject updateChallenges:challengeArray forUserID:userID];
    if ([[UIStateMachine getSharedInstance] allChallengesDone]) {
        return  [rdnaObject updateChallenges:[[UIStateMachine getSharedInstance] getChalleges] forUserID:userID];
    } else {
        RDNAResponseStatus *challengeStatus = [[RDNAResponseStatus alloc] init];
        challengeStatus.statusCode = RDNA_RESP_STATUS_SUCCESS;
        [self InitializeState:[[StatusInfo alloc] initWithChalleges:nil withChallengeStatus:challengeStatus withAPIFlag:UPDATECHALLENGES] withUpdateFlag:false];
        //      [self InitializeState:[[StatusInfo alloc] initWithChalleges:nil withChallengeStatus:challengeStatus withAPIFlag:UPDATECHALLENGES] withUpdateFlag:false];
    }
    return 0;
}

/**
 * @brief This method checks for any pending post login auth challenges and then invokes the checkChallengeResponse api of the RDNA for the
 * given userID. Challenges response is obtained in the callBack i.e onCheckChallengeResponseStatus function.
 */
- (int)RDNAClientPostLoginAuthChallenges:(NSArray *)challengeArray forUserID:(NSString *)userID {
    
    // return [rdnaObject updateChallenges:challengeArray forUserID:userID];
    if ([[UIStateMachine getSharedInstance] allChallengesDone]) {
        return  [rdnaObject checkChallengeResponse:[[UIStateMachine getSharedInstance] getChalleges] forUserID:userID];
    } else {
        RDNAResponseStatus *challengeStatus = [[RDNAResponseStatus alloc] init];
        challengeStatus.statusCode = RDNA_RESP_STATUS_SUCCESS;
        [self InitializeState:[[StatusInfo alloc] initWithChalleges:nil withChallengeStatus:challengeStatus withAPIFlag:POSTLOGINCHALLENGES] withUpdateFlag:false];
    }
    
    return 0;
}

/**
 * @brief This method invokes the logOff api of the RDNA for the given userID.
 * If logOff is successful user gets the appSession Config in the callBack.
 */
- (int)RDNAClientLogOffForUserID:(NSString *)userID withCallbackDelegate:(id<LogOffCallbacks>)_logOffCallback {
    
    logOffCallback = _logOffCallback;
    int retVal = 0;
    if (userID.length>0) {
        retVal = [rdnaObject logOff:userID];
    } else {
        retVal = RDNA_ERR_INVALID_ARGS;
    }
    return  retVal;
}

/**
 * @brief This method is used to intilize the UI State machine and perform state transition with the challenge obtained.
 */
- (void)InitializeState:(StatusInfo *)object withUpdateFlag:(BOOL)isUpdate {
    
    if (isUpdate) {
        [[UIStateMachine getSharedInstance] setChallenge:[object.challeges mutableCopy]];
    }
    
    //NSMutableDictionary *stateDetails = [[NSMutableDictionary alloc] init];
    //[stateDetails setObject:object.challengeStatus forKey:kKeyStatusData];
    //[stateDetails setObject:[NSNumber numberWithInt:object.apiType] forKey:kKeyAPIType];
    
    RDNAChallenge *getNextChallenge = [[UIStateMachine getSharedInstance] getNextChallenge];
    
    if (getNextChallenge == nil) {
        // [stateDetails setObject:[NSNull null] forKey:kKeyChallengeData];
        NSLog(@"nil");
    } else {
        RDNAChallenge *getCurrentChallenge = [[UIStateMachine getSharedInstance] getCurrentChallenge];
        if ([getCurrentChallenge.name isEqualToString:@"checkuser"]) {
            [getCurrentChallenge setResponseValue:@"omi23"];
            // [self RDNAClientCheckChallenges:[NSArray arrayWithObject:getCurrentChallenge] forUserID:@"omi23"];
        }else if ([getCurrentChallenge.name containsString:@"act"])
        {
            // [getCurrentChallenge setResponseValue:@"detfjt"];
            [getCurrentChallenge setResponseValue:@"detfjt"];
            // int errcode = [self RDNAClientCheckChallenges:[NSArray arrayWithObject:getCurrentChallenge] forUserID:@"omi23"];
            
            // if (errcode > 0) {
            //  NSLog(@"success");
            //}
            
        }
        else if ([getCurrentChallenge.name containsString:@"pass"]) {
            [getCurrentChallenge setResponseValue:@"Omi!231990"];
            
        }else if ([getCurrentChallenge.name containsString:@"devbind"])
        {
            [getCurrentChallenge setResponseValue:@"false"];
            
        }
        else if ([getCurrentChallenge.name containsString:@"devname"])
        {
            [getCurrentChallenge setResponseValue:@"ios"];
            
        }
        
        int errorCode = [self RDNAClientCheckChallenges:[NSArray arrayWithObject:getCurrentChallenge] forUserID:@"omi23"];
        if (errorCode > 0) {
            NSLog(@"fail",errorCode);
        }
        
        //else if ([getCurrentChallenge.name containsString:@"Pass"] ||[getCurrentChallenge.name containsString:@"devbind"] || [getCurrentChallenge.name containsString:@"devname"] )
        //{
        //NSMutableArray *arrayOfChallengeObject = [NSMutableArray new];
        /*  for (int i = 0; i<object.challeges.count; i++) {
         RDNAChallenge* challenge = [object.challeges objectAtIndex:i];
         if ([challenge.name containsString:@"pass"]) {
         [challenge setResponseValue:@"1e99b14aa45d6add97271f8e06adacda4e521ad98a4ed18e38cfb0715e7841d2"];
         [arrayOfChallengeObject addObject:challenge];
         }else if ([challenge.name containsString:@"devbind"])
         {
         [challenge setResponseValue:@"true"];
         [arrayOfChallengeObject addObject:challenge];
         }else if ([challenge.name containsString:@"devname"])
         {
         [arrayOfChallengeObject addObject:challenge];
         [self RDNAClientCheckChallenges:[NSArray arrayWithObject:arrayOfChallengeObject] forUserID:@"omi23"];
         }
         
         //[self RDNAClientCheckChallenges:[NSArray arrayWithObject:arrayOfChallengeObject] forUserID:@"omi23"];
         // }
         
         }*/
    }
    
}


/**
 * @brief This method is an implementation of the RDNACLientCallbacks
 * This method is invoked by the RDNA when nofication device token needed
 */
- (NSString*)getDeviceToken{
    __block AppDelegate *appDelegate;
    dispatch_async(dispatch_get_main_queue(), ^(void){
        appDelegate  = (AppDelegate*) [UIApplication sharedApplication].delegate;
        
    });
    return appDelegate.deviceToken;
}



/**
 * @brief This method invokes the GetAllChallenges api of the RDNA for the given userID. Challenges Array is obtained in
 * the callBack i.e onCheckChallengeResponseStatus function.
 */
- (int)RDNAClientGetAllChallenges:(NSString *)userID withCallback:(id<GetAllChallengesCallbacks>)getAllChallengescb {
    
    getAllChallengesCallback = getAllChallengescb ;
    int retval = 0;
    
    if (userID.length > 0) {
        retval = [rdnaObject getAllChallenges:userID];
    }else{
        retval = RDNA_ERR_INVALID_ARGS;
    }
    
    return retval;
}


/**
 * @brief This method is an implementation of the RDNACLientCallbacks
 * This method is invoked by the RDNA when the RDNA get all challenges API is invoked by the client
 * It returns the RDNAStatusGetAllChallenges class object.
 */
- (int)onGetAllChallengeStatus:(RDNAStatusGetAllChallenges *) status {
    
    if (status.errCode == 0) {
        [[UIStateMachine getSharedInstance] setAllChalleges:[status.challenges mutableCopy]];
        [getAllChallengesCallback GetAllChallengesWithErrorCode:status.status.statusCode andErrorMessage:status.status.message];
    } else {
        NSLog(@"RDNA_METH_GET_ALL_CHALLENGES errorcode : %d",status.errCode);
        // [self performNotificationWithObject:[NSNumber numberWithInt:status.errCode]];
    }
    
    return 1;
}

/**
 * @brief This method is used ti set the reponse in the response array of the given challenge with its appropraite key.
 */
- (int)RDNAClientSetReponse:(NSObject *)response andKey:(NSString*)key forChallenge:(RDNAChallenge*)challenge {
    
    // [challenge setChallengeResponseValue:response ForKey:key];
    challenge.responseValue = response;
    challenge.responseKey = key;
    [[UIStateMachine getSharedInstance] updateChallenge:challenge];
    return 1;
}


#pragma mark - Common Methos

-(int)perfomActionWithSetResponse:(NSObject *)response andKey:(NSString*)key forChallenge:(RDNAChallenge*)challenge forUserID:(NSString *)userID{
    
    int statusCode = 0;
    [self RDNAClientSetReponse:response andKey:key forChallenge:challenge];
    
    switch (self.currentAPIType) {
        case CHECKCHALLENGES:
            statusCode = [self RDNAClientCheckChallenges:[NSArray arrayWithObject:challenge] forUserID:userID];
            break;
            
        case UPDATECHALLENGES:
            statusCode = [self RDNAClientUpdateChallenges:[NSArray arrayWithObject:challenge] forUserID:userID];
            
            break;
            
        case POSTLOGINCHALLENGES:
            statusCode = [self RDNAClientPostLoginAuthChallenges:[NSArray arrayWithObject:challenge] forUserID:userID];
            break;
            
        default:
            statusCode = 1;
            break;
    }
    
    return statusCode;
}



@end
