//
//  TestEnrollUser.m
//  iosAutomation
//
//  Created by cps on 16/08/18.
//  Copyright © 2018 cps. All rights reserved.
//

#import "TestEnrollUser.h"

@implementation TestEnrollUser

-(instancetype)initWithDict:(NSDictionary *)dict{
    self = [super init];
    if(self)
    {
        self.firstName = dict[@"firstName"];
        self.lastName = dict[@"lastName"];
        self.userId = dict[@"userId"];
        self.actCode = dict[@"actCode"];
        self.groupName = dict[@"groupName"];
        self.emailId = dict[@"emailId"];
        self.mobNum = dict[@"mobNum"];
        self.username = dict[@"username"];
        self.password = dict[@"password"];
        self.isRelIdVerifyEnabled = dict[@"isRelIdVerifyEnabled"];
        self.sessionId = dict[@"sessionId"];
        self.apiversion = dict[@"apiversion"];
    
    }
    return self;
}
@end
