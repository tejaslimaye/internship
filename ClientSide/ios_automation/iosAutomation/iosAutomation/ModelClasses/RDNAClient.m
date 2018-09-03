//
//  RDNAClient.m
//  API_SDK_SAMPLE_V1
//
//  Created by Uniken India pvt ltd.
//  Copyright © 2015 Uniken India pvt ltd. All rights reserved.
//

#import "RDNAClient.h"
#import <UIKit/UIKit.h>
#import "AppDelegate.h"



#define IS_OS_8_OR_LATER ([[[UIDevice currentDevice] systemVersion] floatValue] >= 8.0)


@interface RDNAClient(){
  
  dispatch_semaphore_t semaphore;
  RDNAIWACreds *rdnaIWACredsObj;
  id<RDNACallbacks> clientCallbacks;  //RDNACallbacks object.
  NSMutableArray *getAllServiceArray; //RDNA Service Array.
  CLLocationManager *locationManagerObject;//LocationManager
  RDNA *rdnaObject;  //This is the public RDNA object which will be initialized in the initializeRDNA
}

@end

@implementation RDNAClient
- (instancetype)init {
  
  self = [super init];
  if (self) {
  }
  return self;
}

- (int) initializeRDNA {
  
  int errorID = 0;
 
    

    
    clientCallbacks = self;
    RDNA *rdna;
       /* errorID = [RDNA initialize:&rdna AgentInfo:connectionProfile.relid Callbacks:clientCallbacks GatewayHost:connectionProfile.host GatewayPort:connectionProfile.port CipherSpec:kRdnaCipherSpecs CipherSalt:kRdnaCipherSalt ProxySettings:ppxy RDNASSLCertificate:NULL DNSServerList:nil RDNALoggingLevel:RDNA_LOG_VERBOSE AppContext:self];*/
        rdnaObject = rdna;
  return errorID;
}


/**
 * @brief This method is invoked by RDNA when there is error in retrieving location.
 */
- (int)ShowLocationDailogue {
  
  NSLog(@"Location Callback of client called");
  return 0;
}




/**
 * @brief This method is used for get current session id on server
 */
-(NSString*)RDNAGetSessionID{
  NSMutableString *strSessionID = [[NSMutableString alloc]init];
  [rdnaObject getSessionID:&strSessionID];
  return strSessionID;
}

#pragma mark CallBacks
/**
 * @brief This method is an implementation of the RDNACLientCallbacks
 * This method is invoked by the RDNA when the RDNA initialize API is invoked.
 * It returns the RDNAStatusInit class object.
 */
- (int)onInitializeCompleted:(RDNAStatusInit *)status {
  return 1;
}

/**
 * @brief This method is an implementation of the RDNACLientCallbacks
 * This method is invoked by the RDNA when the RDNA pause API is invoked by the client
 * It returns the RDNAStatusInit class object.
 */
- (int)onPauseRuntime:(RDNAStatusPauseRuntime *)status {

  return 1;
}

/**
 * @brief This method is an implementation of the RDNACLientCallbacks
 * This method is invoked by the RDNA when the RDNA resume API is invoked by the client
 * It returns the RDNAStatusInit class object.
 */
- (int)onResumeRuntime:(RDNAStatusResumeRuntime *)status {
  return 1;
}

/**
 * @brief This method is an implementation of the RDNACLientCallbacks
 * This method is invoked by the RDNA when the RDNA terminate API is invoked by the client
 * It returns the RDNAStatusInit class object.
 * If the errorCode obtained in RDNAStatusTerminate structure is zero then the  rdnaobject should be deallocated.
 */
- (int)onTerminate:(RDNAStatusTerminate *)status {
  return 1;
}

/**
 * @brief This method is an implementation of the RDNACLientCallbacks
 * This method is invoked by the RDNA when the RDNA check challenges API is invoked by the client
 * It returns the RDNAStatusCheckChallengesResponse class object.
 */
- (int)onCheckChallengeResponseStatus:(RDNAStatusCheckChallengeResponse *) status {
  return 1;
}

/**
 * @brief This method is an implementation of the RDNACLientCallbacks
 * This method is invoked by the RDNA when the RDNA post login authentication challenges API is invoked by the client
 * It returns the RDNAStatusGetPostLoginChallenges class object.
 */
- (int)onGetPostLoginChallenges:(RDNAStatusGetPostLoginChallenges *)status {
  return 1;
}

/**
 * @brief This method is an implementation of the RDNACLientCallbacks
 * This method is invoked by the RDNA when the RDNA registred device details API is invoked by the client
 * It returns the RDNAStatusGetRegisteredDeviceDetails class object.
 */
- (int)onGetRegistredDeviceDetails:(RDNAStatusGetRegisteredDeviceDetails *)status {
  return 1;
}

/**
 * @brief This method is an implementation of the RDNACLientCallbacks
 * This method is invoked by the RDNA when the RDNA get all challenges API is invoked by the client
 * It returns the RDNAStatusGetAllChallenges class object.
 */
- (int)onGetAllChallengeStatus:(RDNAStatusGetAllChallenges *) status {
  
  return 1;
}



/**
 * @brief This method is an implementation of the RDNACLientCallbacks
 * This method is invoked by the RDNA when the RDNA update challenge(s) API is invoked by the client
 * It returns the RDNAStatusUpdateChallenge class object.
 */
- (int)onUpdateChallengeStatus:(RDNAStatusUpdateChallenges *) status {

    return 1;
}

/**
 * @brief This method is an implementation of the RDNACLientCallbacks
 * This method is invoked by the RDNA when the RDNA update device details API is invoked by the client
 * It returns the RDNAStatusUpdateDeviceDetails class object.
 */
- (int)onUpdateDeviceDetails:(RDNAStatusUpdateDeviceDetails *)status {
  return 1;
}

/**
 * @brief This method is an implementation of the RDNACLientCallbacks
 * This method is invoked by the RDNA when the RDNA forget password API is invoked by the client
 * It returns the RDNAStatusForgotPassword class object.
 */
- (int)onForgotPasswordStatus:(RDNAStatusForgotPassword *)status {
  return 1;
}

/**
 * @brief This method is an implementation of the RDNACLientCallbacks
 * This method is invoked by the RDNA when the RDNA user logoff API is invoked by the client
 * It returns the RDNAStatusLogOff class object.
 */
- (int)onLogOff: (RDNAStatusLogOff *)status {
  return 1;
}


/**
 * @brief This method is an implementation of the RDNACLientCallbacks
 * This method is invoked by the RDNA when the RDNA getConfig API is invoked by the client
 * It returns the RDNAStatusGetConfig structure.
 * If the errorCode obtained in RDNAStatusGetConfig class object is zero.
 */
- (int) onConfigReceived:(RDNAStatusGetConfig *)status {
  return 1;
}


/**
 * @brief This method is an implementation of the RDNACLientCallbacks
 * This method is invoked by the RDNA when web content needs to authentication
 * It returns the RDNAStatusLogOff class object.
 */
- (RDNAIWACreds *)getCredentials:(NSString *)domainUrl {
  
  rdnaIWACredsObj  = [[RDNAIWACreds alloc] init];;
  return rdnaIWACredsObj;
}


/**
 * @brief This method is an implementation of the RDNACLientCallbacks
 * This method is invoked by the RDNA when session not valid.
 */
-(int)onSessionTimeout:(NSString*)status{
  return 1;
}



/**
 * @brief This method is used to fetch the currently running services.
 */
- (NSArray *)fetchAllServices {
  
  NSArray *allServiceArray;
  int errorgetAllServiceArray = [rdnaObject getAllServices:&allServiceArray];
  return [allServiceArray copy];
  //  [getAllServiceArray addObjectsFromArray:getAllServiceArray];
  //  return errorgetAllServiceArray;
}

/**
 * @brief This method is used to fetch the application fingerprint.
 */
- (NSString *)getApplicationFingerprint {
  
  return @"test";
}

/**
 * @brief This method is used to fetch the application varsion.
 */
- (NSString *)getApplicationVersion {
  
  NSString *version = [RDNA getSDKVersion];
  return version;
}

/**
 * @brief This method is used to fetch the application name.
 */
- (NSString *)getApplicationName {
  
  NSDictionary *infoDictionary = [[NSBundle mainBundle]infoDictionary];
  NSString *AppName = infoDictionary[(NSString *)kCFBundleNameKey];
  return AppName;
  
}

/**
 * @brief This method extracts the actual euRelId from the cryptic string.
 */
- (NSString *)unpackEndUserRelID:(NSString *)euRelId {
  
  return @"relid";
}

@end
