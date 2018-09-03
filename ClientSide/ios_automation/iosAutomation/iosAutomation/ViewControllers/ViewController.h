//
//  ViewController.h
//  iosAutomation
//
//  Created by cps on 26/07/18.
//  Copyright © 2018 cps. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "RDNA.h"
#import "QTestCaseWelcome.h"
#import "WPHTTPRequestOperationManager.h"


typedef NS_ENUM(NSInteger,USER_SESSION_API) {
    ENROLL_USER = 0,
    ENROLL_USER_DEVICE,
    GET_USER_STATUS,
};

typedef NS_ENUM(NSInteger,ACTIVE_USER) {
    checkuser = 0,
    act,
    Pass,
    devbind,
};

typedef NS_ENUM(NSInteger,INITIALIZATION_ON_MOBILE) {
    INIT = 0,
    INIT_FAIL_WRONG_APPID,
    INIT_FAIL_WRONG_PORT,
    TERMINATE,
    
};

typedef NS_ENUM(NSInteger,SERVICE_INFO) {
    GET_SERVICE_BY_SERVICE_NAME=0,
    GET_SERVICE_BY_WRONG_SERVICE_NAME,
    GET_SERVICE_BY_TARGET_COORDINATE,
    GET_SERVICE_BY_TARGET_COORDINATE_WRONG_IP,
    GET_SERVICE_BY_TARGET_COORDINATE_WRONG_PORT,
    GET_SERVICE_BY_TARGET_COORDINATE_WRONG_IP_AND_PORT,
    SERVICE_ACCESS_START_STOPPED_SERVICE,
    SERVICE_ACCESS_START_WRONG_SERVICE_NAME,
    SERVICE_ACCESS_START_RUNNING_SERVICE,
    SERVICE_ACCESS_STOP_WRONG_SERVICE_NAME,
    SERVICE_ACCESS_STOP_RUNNING_SERVICE,
    SERVICE_ACCESS_STOP_STOPPED_SERVICE,
    SERVICE_ACCESS_STOP_ALL_SERVICE,
    SERVICE_ACCESS_START_ALL_SERVICE,
    GET_ALL_SERVICES,
};

typedef NS_ENUM(NSInteger,INFO_GETTERS) {
    GET_DEFAULT_CIPHER_SPEC=0,
    GET_DEFAULT_CIPHER_SALT,
    GET_API_SDK_VERSION,
    GET_ERROR_CODE,
    GET_SESSION_ID,
    GET_DEVICE_ID,
    GET_AGENT_ID,
};

typedef NS_ENUM(NSInteger,DATA_PRIVACY) {
    ENCRYPT_DATA_PACKET = 0,
    ENCRYPT_DATA_PACKET_EMPTY_CIPHERSPEC,
    ENCRYPT_DATA_PACKET_GET_EMPTY_CIPHERSALT,
    DECRYPT_DATA_PACKET,
    DECRYPT_DATA_PACKET_EMPTY_CIPHERSPEC,
    DECRYPT_DATA_PACKET_EMPTY_CIPHERSALT,
    DECRYPT_DATA_PACKET_GET_EMPTY_PLAINTEXT,
};



@class AppDelegate;
@interface ViewController : UIViewController <RDNACallbacks>
{
    id<RDNACallbacks> clientCallbacks;  //RDNACallbacks object.
    
    
    
}
@property (weak, nonatomic) IBOutlet UILabel *passedTestCount;
@property (weak, nonatomic) IBOutlet UILabel *failedTestCount;
@property (weak, nonatomic) IBOutlet UILabel *totalTestCount;
@property (weak, nonatomic) IBOutlet UILabel *completeTestCount;
@property (weak, nonatomic) IBOutlet UILabel *CannotTestCount;
@property (strong, nonatomic) QTestCaseTestjobexecution *testjobexecutions;
@property (strong, nonatomic) QTestCaseExecution *executions;
@property (nonatomic) enum USER_SESSION_API userSessionApi;
@property (nonatomic) enum ACTIVE_USER activeUser;
@property (nonatomic) enum INITIALIZATION_ON_MOBILE initializationOnMobile;
@property (nonatomic) enum SERVICE_INFO serviceInfo;
@property (nonatomic) enum INFO_GETTERS infoGetters;
@property (nonatomic) enum DATA_PRIVACY dataPrivacy;
@property (nonatomic, strong) WPHTTPRequestOperationManager *manager;
@property (nonatomic,strong)NSArray *statusChallenges;
@property (nonatomic,strong) AppDelegate* appDelegate;



 typedef void(^myCompletion)(BOOL);
@end

