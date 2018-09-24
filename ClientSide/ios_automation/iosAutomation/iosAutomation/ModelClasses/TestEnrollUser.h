//
//  TestEnrollUser.h
//  iosAutomation
//
//  Created by cps on 16/08/18.
//  Copyright © 2018 cps. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface TestEnrollUser : NSObject
@property (nonatomic, copy)   NSString *firstName;
@property (nonatomic, copy)   NSString *lastName;
@property (nonatomic, copy)   NSString *userId;
@property (nonatomic, copy)   NSString *actCode;
@property (nonatomic, copy)   NSString *groupName;
@property (nonatomic, copy)   NSString *emailId;
@property (nonatomic, copy)   NSString *mobNum;
@property (nonatomic, copy)   NSString *username;
@property (nonatomic, copy)   NSString *password;
@property (nonatomic, copy)   NSString *sessionId;
@property (nonatomic, copy)   NSString *apiversion;
@property (nonatomic, copy) NSString *isRelIdVerifyEnabled;

@property (nonatomic,strong)NSDictionary *data;
-(instancetype)initWithDict:(NSDictionary *)dict;

@end
