//
//  webservice.h
//  jsondemo post
//
//  Created by Yogesh Patel on 08/11/17.
//  Copyright © 2017 Yogesh Patel. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "QTestCaseWelcome.h"
@class TestEnrollUser;
@interface webservice : NSObject
//+ instance Globally -"ViewController me used "
//+ second Web View
//argumentname type objectname
+(void)executequery:(NSString *)strurl strprameter:(NSDictionary *)prameter withblock:(void (^)(QTestCaseWelcome *, NSError *))block;
+(void)executequeryForPostingToServer:(NSString *)strurl strprameter:(NSDictionary *)prameter withblock:(void (^)(NSData  *, NSError *))block;


/* User Session API's */
+(void)executequeryForEnrolluser:(NSString *)strurl portNumber :(int) port strEnrolluser:(TestEnrollUser *)enrollUser withblock:(void (^)(NSData  *, NSError *))block;
+(void)executequeryForEnrolluserDevice:(NSString *)strurl portNumber :(int) port strEnrolluser:(TestEnrollUser *)enrollUser withblock:(void (^)(NSData  *, NSError *))block;
+(void)executequeryForGetUserStatus:(NSString *)strurl portNumber :(int) port strEnrolluser:(TestEnrollUser *)enrollUser withblock:(void (^)(NSData  *, NSError *))block;
+(void)executequeryForGetUserId:(NSString *)strurl portNumber :(int) port strEnrolluser:(TestEnrollUser *)enrollUser withblock:(void (^)(NSData  *, NSError *))block;
@end
