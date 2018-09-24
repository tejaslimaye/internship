//
//  Utility.h
//  API_SDK_SAMPLE_V1
//
//  Created by Uniken India pvt ltd.
//  Copyright © 2015 Uniken India pvt ltd. All rights reserved.
//

#import <Foundation/Foundation.h>
//#import "ConnectionProfile.h"
#import "RDNA.h"
@interface Utility : NSObject
+ (NSString*)trimmingStringWithString:(NSString*)string;
@end

@interface PasswordPolicy : NSObject
@property (nonatomic, assign) int minChar;
@property (nonatomic, assign) int maxChar;
@property (nonatomic, assign) BOOL must_have_digit;
@property (nonatomic, assign) BOOL must_have_special_char;
@property (nonatomic, assign) BOOL must_have_uppercase;

@end

@interface UserSessionState : NSObject

+(UserSessionState*)getSharedInstance;
@property (nonatomic, assign) BOOL isRDNAIntilized;
@property (nonatomic, assign) BOOL isRememberUser;
@property (nonatomic, assign) BOOL isLoginToDashboard;
@property (nonatomic, assign) uint16_t proxyPort;
@property (nonatomic, copy) NSString *userID;
@property (nonatomic, copy) NSString *mainPageUrl;
@property (nonatomic, copy) PasswordPolicy *passwordPolicy;
@property (nonatomic, copy) NSArray *autoStartedServices;
//@property (nonatomic, strong) ConnProfile *connectionProfile;
@property (nonatomic, assign) BOOL isForgetPasswordFlow;
- (void)parseConfig:(NSString *)jsonString withurlMicro:(NSDictionary*)dictMicro;
- (PasswordPolicy *)parsePasswordPolicy:(NSDictionary *)passPolicyDictionary;
-(BOOL) checkQuestionDuplication:(RDNAChallenge*) challenge withString:(NSString*)str;
@end

