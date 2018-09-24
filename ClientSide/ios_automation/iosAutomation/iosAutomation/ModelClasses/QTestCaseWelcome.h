// To parse this JSON:
//
//   NSError *error;
//   QTestCaseWelcome *welcome = [QTestCaseWelcome fromJSON:json encoding:NSUTF8Encoding error:&error];

#import <Foundation/Foundation.h>

@class QTestCaseWelcome;
@class QTestCaseServerExecutionDetail;
@class QTestCaseTestjobexecution;
@class QTestCaseExecution;

NS_ASSUME_NONNULL_BEGIN

#pragma mark - Object interfaces

@interface QTestCaseWelcome : NSObject
@property (nonatomic, assign) NSInteger deviceAvailCode;
@property (nonatomic, assign) NSInteger jobAvailCode;
@property (nonatomic, copy)   NSArray<QTestCaseServerExecutionDetail *> *serverExecutionDetails;
@property (nonatomic, assign) NSInteger responseCode;
@property (nonatomic, assign) NSInteger errorCode;

+ (_Nullable instancetype)fromJSON:(NSString *)json encoding:(NSStringEncoding)encoding error:(NSError *_Nullable *)error;
+ (_Nullable instancetype)fromData:(NSData *)data error:(NSError *_Nullable *)error;
- (NSString *_Nullable)toJSON:(NSStringEncoding)encoding error:(NSError *_Nullable *)error;
- (NSData *_Nullable)toData:(NSError *_Nullable *)error;

@end

@interface QTestCaseServerExecutionDetail : NSObject
@property (nonatomic, assign) NSInteger serverID;
@property (nonatomic, assign) NSInteger gmPort;
@property (nonatomic, assign) NSInteger sdkPort;
@property (nonatomic, assign) NSInteger verifyPort;
@property (nonatomic, assign) NSInteger apiPort;
@property (nonatomic, copy)   NSString *ipAddress;
@property (nonatomic, copy)   NSString *osVersion;
@property (nonatomic, copy)   NSString *consoleUser;
@property (nonatomic, copy)   NSString *consolePassword;
@property (nonatomic, copy)   NSString *enterpriseID;
@property (nonatomic, copy)   NSString *enterpriseUser;
@property (nonatomic, copy)   NSString *enterprisePassword;
@property (nonatomic, copy)   NSString *serverUser;
@property (nonatomic, copy)   NSString *serverPassword;
@property (nonatomic, copy)   NSString *agentInfo;
@property (nonatomic, copy)   NSArray<QTestCaseTestjobexecution *> *testjobexecutions;
@end

@interface QTestCaseTestjobexecution : NSObject
@property (nonatomic, assign) NSInteger testjobID;
@property (nonatomic, copy)   NSString *testJobDescription;
@property (nonatomic, copy)   NSString *createdTime;
@property (nonatomic, copy)   NSString *updatedTime;
@property (nonatomic, copy)   NSString *status;
@property (nonatomic, assign) NSInteger serverID;
@property (nonatomic, assign) NSInteger libID;
@property (nonatomic, assign) NSInteger autoCreateOnNewDevice;
@property (nonatomic, assign) NSInteger testRunID;
@property (nonatomic, copy)   NSArray<QTestCaseExecution *> *executions;
@end

@interface QTestCaseExecution : NSObject

/*enum FeatureNameTestCases {
    VERIFY_API,
    Initialization_on_Mobile,
    SERVICE_INFO,
    SERVICE_ACCESS,
    INFO_GETTERS,
    DATA_PRIVACY
    
};*/


@property (nonatomic, assign) NSInteger executionID;
@property (nonatomic, assign) NSInteger testrunID;
@property (nonatomic, assign) NSInteger testcaseID;
@property (nonatomic, assign) NSInteger featureID;
@property (nonatomic, copy)   NSString *featureName;
@property (nonatomic, copy)   NSString *testcaseName;
@property (nonatomic, copy)   NSString *testcaseDesc;
@property (nonatomic, assign) NSInteger deviceID;
@property (nonatomic, assign) NSInteger testjobID;
@property (nonatomic, assign) NSInteger errorCode;
@property (nonatomic, copy)   NSString *errorMessage;
// @property (nonatomic) enum FeatureNameTestCases featureNameTestCases;
- (NSDictionary *)JSONDictionary;
@end

NS_ASSUME_NONNULL_END
