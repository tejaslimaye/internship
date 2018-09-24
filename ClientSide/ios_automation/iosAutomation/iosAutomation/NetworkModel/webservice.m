//
//  webservice.m
//  jsondemo post
//
//  Created by Yogesh Patel on 08/11/17.
//  Copyright © 2017 Yogesh Patel. All rights reserved.
//

#import "webservice.h"
#import "TestEnrollUser.h"
@implementation webservice
+(void)executequery:(NSString *)strurl strprameter:(NSDictionary *)prameter withblock:(void (^)(QTestCaseWelcome *, NSError *))block
{
    //Step:-1 Session Create
    NSError *error;
   NSURLSessionConfiguration *defaultconfiguration = [NSURLSessionConfiguration defaultSessionConfiguration];//New Session
    NSURLSession *session = [NSURLSession sessionWithConfiguration:defaultconfiguration delegate:nil delegateQueue:[NSOperationQueue mainQueue]];//Queue is Stroing and retrieve data FIFO
    
    NSURL *urlrequest = [NSURL URLWithString:strurl];
    
    NSMutableURLRequest*mutablerequest = [NSMutableURLRequest requestWithURL:urlrequest];
    
   //NSString *str=[NSString stringWithFormat:@"json data is %@", prameter];
   // NSData *postData = [str dataUsingEncoding:NSUTF8StringEncoding];
    
    
    NSData *postData = [NSJSONSerialization dataWithJSONObject:prameter options:kNilOptions error:&error];
    NSString *receivedDataString = [[NSString alloc] initWithData:postData encoding:NSUTF8StringEncoding];
    [mutablerequest setHTTPBody:postData];
    [mutablerequest addValue:@"application/json" forHTTPHeaderField:@"Content-Type"];
    [mutablerequest addValue:@"application/json" forHTTPHeaderField:@"Accept"];
     [mutablerequest addValue:receivedDataString forHTTPHeaderField:@"data"];
   
    //[mutablerequest addValue:@"application/x-www-form-urlencoded" forHTTPHeaderField:@"Content-Type"];
    [mutablerequest setHTTPMethod:@"POST"];
    
    
   // NSURLRequest*urlRequest = [NSURLRequest requestWithURL:urlrequest];
   NSURLSessionDataTask * task = [session dataTaskWithRequest:mutablerequest completionHandler:^(NSData *  data, NSURLResponse * response, NSError *  error) {
        if (data!=nil)
        {
            NSLog(@"Response %@", data);
            NSError *error;
            QTestCaseWelcome *testCaseWelcome  =[QTestCaseWelcome fromData:data error:&error];
            block(testCaseWelcome,error);//Data is NSDATA and Error is NSERROR
        }
        else
        {
            NSLog(@"error");
            block(nil,error);
        }
    }];
    [task resume];
}

+(void)executequeryForPostingToServer:(NSString *)strurl strprameter:(NSDictionary *)prameter withblock:(void (^)(NSData  *, NSError *))block
{
    //Step:-1 Session Create
    NSError *error;
    NSURLSessionConfiguration *defaultconfiguration = [NSURLSessionConfiguration defaultSessionConfiguration];//New Session
    NSURLSession *session = [NSURLSession sessionWithConfiguration:defaultconfiguration delegate:nil delegateQueue:[NSOperationQueue mainQueue]];//Queue is Stroing and retrieve data FIFO
    
    NSURL *urlrequest = [NSURL URLWithString:strurl];
    
    NSMutableURLRequest*mutablerequest = [NSMutableURLRequest requestWithURL:urlrequest];
    NSData *postData = [NSJSONSerialization dataWithJSONObject:prameter options:kNilOptions error:&error];
    NSString *receivedDataString = [[NSString alloc] initWithData:postData encoding:NSUTF8StringEncoding];
    [mutablerequest setHTTPBody:postData];
    [mutablerequest addValue:@"application/json" forHTTPHeaderField:@"Content-Type"];
    [mutablerequest addValue:@"application/json" forHTTPHeaderField:@"Accept"];
    [mutablerequest addValue:receivedDataString forHTTPHeaderField:@"data"];
    //[mutablerequest addValue:@"application/x-www-form-urlencoded" forHTTPHeaderField:@"Content-Type"];
    [mutablerequest setHTTPMethod:@"POST"];
    NSURLSessionDataTask * task = [session dataTaskWithRequest:mutablerequest completionHandler:^(NSData *  data, NSURLResponse * response, NSError *  error) {
        if (data!=nil)
        {
            NSLog(@"Response %@", data);
            NSString *str = [[NSString alloc]initWithData:data encoding:NSUTF8StringEncoding];
            NSLog(@"str :- %@",str);
            NSError *error;
            block(data,error);//Data is NSDATA and Error is NSERROR
        }
        else
        {
            NSLog(@"error");
            block(nil,error);
        }
    }];
    [task resume];
}


+(void)executequeryForEnrolluser:(NSString *)strurl portNumber :(int) port strEnrolluser:(TestEnrollUser *)enrollUser withblock:(void (^)(NSData  *, NSError *))block
{
    NSString* proxyHost = @"127.0.0.1";
    NSNumber* proxyPort = [NSNumber numberWithInt: port];
    
    // Create an NSURLSessionConfiguration that uses the proxy
    NSDictionary *proxyDict = @{
                                @"HTTPEnable"  : [NSNumber numberWithInt:1],
                                (NSString *)kCFStreamPropertyHTTPProxyHost  : proxyHost,
                                (NSString *)kCFStreamPropertyHTTPProxyPort  : proxyPort,
                                
                                @"HTTPSEnable" : [NSNumber numberWithInt:1],
                                (NSString *)kCFStreamPropertyHTTPSProxyHost : proxyHost,
                                (NSString *)kCFStreamPropertyHTTPSProxyPort : proxyPort,
                                };
    
    NSURLSessionConfiguration *configuration = [NSURLSessionConfiguration ephemeralSessionConfiguration];
    configuration.connectionProxyDictionary = proxyDict;
    
    
    
    //Step:-1 Session Create
    NSError *error;
    NSURLSessionConfiguration *defaultconfiguration = [NSURLSessionConfiguration defaultSessionConfiguration];//New Session
    NSURLSession *session = [NSURLSession sessionWithConfiguration:defaultconfiguration delegate:nil delegateQueue:[NSOperationQueue mainQueue]];//Queue is Stroing and retrieve data FIFO
    
    NSURL *urlrequest = [NSURL URLWithString:strurl];
    
    NSMutableURLRequest*mutablerequest = [NSMutableURLRequest requestWithURL:urlrequest];
   // NSData *postData = [NSJSONSerialization dataWithJSONObject:prameter options:kNilOptions error:&error];
    //NSString *receivedDataString = [[NSString alloc] initWithData:postData encoding:NSUTF8StringEncoding];
    //[mutablerequest setHTTPBody:postData];
   // [mutablerequest addValue:@"application/json" forHTTPHeaderField:@"Content-Type"];
    //[mutablerequest addValue:@"application/json" forHTTPHeaderField:@"Accept"];
    //[mutablerequest addValue:receivedDataString forHTTPHeaderField:@"data"];
    
//    @"firstName":@"nikhil",
//    @"lastName":@"kanawde",
//    @"userId":@"nikhil1",
//    @"actCode":@"detfjt",
//    @"groupName":@"group1",
//    @"emailId":@"nikhil.kanawade@uniken.com",
//    @"mobNum":@"9898989898",
//    @"username":@"sruser",
//    @"password":@"1e99b14aa45d6add97271f8e06adacda4e521ad98a4ed18e38cfb0715e7841d2",
//    @"isRelIdVerifyEnabled":@"true",
//    @"sessionId":self->testEnrolluser.sessionId,
//    @"apiversion":@"v1"
    [mutablerequest addValue:@"application/x-www-form-urlencoded" forHTTPHeaderField:@"Content-Type"];
    [mutablerequest addValue:enrollUser.firstName forHTTPHeaderField:@"firstName"];
    [mutablerequest addValue:enrollUser.lastName forHTTPHeaderField:@"lastName"];
    [mutablerequest addValue:enrollUser.userId forHTTPHeaderField:@"userId"];
    [mutablerequest addValue:enrollUser.actCode forHTTPHeaderField:@"actCode"];
    [mutablerequest addValue:enrollUser.groupName forHTTPHeaderField:@"groupName"];
    [mutablerequest addValue:enrollUser.emailId forHTTPHeaderField:@"emailId"];
    [mutablerequest addValue:enrollUser.mobNum forHTTPHeaderField:@"mobNum"];
    [mutablerequest addValue:enrollUser.username forHTTPHeaderField:@"username"];
    [mutablerequest addValue:enrollUser.password forHTTPHeaderField:@"password"];
    [mutablerequest addValue:enrollUser.isRelIdVerifyEnabled forHTTPHeaderField:@"isRelIdVerifyEnabled"];
    [mutablerequest addValue:enrollUser.sessionId forHTTPHeaderField:@"sessionId"];
    [mutablerequest addValue:enrollUser.apiversion forHTTPHeaderField:@"apiversion"];
    
    [mutablerequest setHTTPMethod:@"POST"];
    NSURLSessionDataTask * task = [session dataTaskWithRequest:mutablerequest completionHandler:^(NSData *  data, NSURLResponse * response, NSError *  error) {
        if (data!=nil)
        {
            NSLog(@"Response %@", data);
            NSString *str = [[NSString alloc]initWithData:data encoding:NSUTF8StringEncoding];
            NSLog(@"str :- %@",str);
            NSError *error;
            block(data,error);//Data is NSDATA and Error is NSERROR
        }
        else
        {
            NSLog(@"error :- %@",[error description]);
            block(nil,error);
        }
    }];
    [task resume];
}

+(void)executequeryForEnrolluserDevice:(NSString *)strurl portNumber :(int) port strEnrolluser:(TestEnrollUser *)enrollUser withblock:(void (^)(NSData  *, NSError *))block
{
    NSString* proxyHost = @"127.0.0.1";
    NSNumber* proxyPort = [NSNumber numberWithInt: port];
    
    // Create an NSURLSessionConfiguration that uses the proxy
    NSDictionary *proxyDict = @{
                                @"HTTPEnable"  : [NSNumber numberWithInt:1],
                                (NSString *)kCFStreamPropertyHTTPProxyHost  : proxyHost,
                                (NSString *)kCFStreamPropertyHTTPProxyPort  : proxyPort,
                                
                                @"HTTPSEnable" : [NSNumber numberWithInt:1],
                                (NSString *)kCFStreamPropertyHTTPSProxyHost : proxyHost,
                                (NSString *)kCFStreamPropertyHTTPSProxyPort : proxyPort,
                                };
    
    NSURLSessionConfiguration *configuration = [NSURLSessionConfiguration ephemeralSessionConfiguration];
    configuration.connectionProxyDictionary = proxyDict;
    
    
    
    //Step:-1 Session Create
    NSError *error;
    NSURLSessionConfiguration *defaultconfiguration = [NSURLSessionConfiguration defaultSessionConfiguration];//New Session
    NSURLSession *session = [NSURLSession sessionWithConfiguration:defaultconfiguration delegate:nil delegateQueue:[NSOperationQueue mainQueue]];//Queue is Stroing and retrieve data FIFO
    
    NSURL *urlrequest = [NSURL URLWithString:strurl];
    
    NSMutableURLRequest*mutablerequest = [NSMutableURLRequest requestWithURL:urlrequest];
    // NSData *postData = [NSJSONSerialization dataWithJSONObject:prameter options:kNilOptions error:&error];
    //NSString *receivedDataString = [[NSString alloc] initWithData:postData encoding:NSUTF8StringEncoding];
    //[mutablerequest setHTTPBody:postData];
    // [mutablerequest addValue:@"application/json" forHTTPHeaderField:@"Content-Type"];
    //[mutablerequest addValue:@"application/json" forHTTPHeaderField:@"Accept"];
    //[mutablerequest addValue:receivedDataString forHTTPHeaderField:@"data"];
    
    //    @"firstName":@"nikhil",
    //    @"lastName":@"kanawde",
    //    @"userId":@"nikhil1",
    //    @"actCode":@"detfjt",
    //    @"groupName":@"group1",
    //    @"emailId":@"nikhil.kanawade@uniken.com",
    //    @"mobNum":@"9898989898",
    //    @"username":@"sruser",
    //    @"password":@"1e99b14aa45d6add97271f8e06adacda4e521ad98a4ed18e38cfb0715e7841d2",
    //    @"isRelIdVerifyEnabled":@"true",
    //    @"sessionId":self->testEnrolluser.sessionId,
    //    @"apiversion":@"v1"
    [mutablerequest addValue:@"application/x-www-form-urlencoded" forHTTPHeaderField:@"Content-Type"];
    
    [mutablerequest addValue:enrollUser.userId forHTTPHeaderField:@"userId"];
    [mutablerequest addValue:enrollUser.actCode forHTTPHeaderField:@"actCode"];
    [mutablerequest addValue:enrollUser.username forHTTPHeaderField:@"username"];
    [mutablerequest addValue:enrollUser.password forHTTPHeaderField:@"password"];
    [mutablerequest addValue:enrollUser.sessionId forHTTPHeaderField:@"sessionId"];
    [mutablerequest addValue:enrollUser.apiversion forHTTPHeaderField:@"apiversion"];
    
    [mutablerequest setHTTPMethod:@"POST"];
    NSURLSessionDataTask * task = [session dataTaskWithRequest:mutablerequest completionHandler:^(NSData *  data, NSURLResponse * response, NSError *  error) {
        if (data!=nil)
        {
            NSLog(@"Response %@", data);
            NSString *str = [[NSString alloc]initWithData:data encoding:NSUTF8StringEncoding];
            NSLog(@"str :- %@",str);
            NSError *error;
            block(data,error);//Data is NSDATA and Error is NSERROR
        }
        else
        {
            NSLog(@"error :- %@",[error description]);
            block(nil,error);
        }
    }];
    [task resume];
}

+(void)executequeryForGetUserStatus:(NSString *)strurl portNumber :(int) port strEnrolluser:(TestEnrollUser *)enrollUser withblock:(void (^)(NSData  *, NSError *))block
{
    NSString* proxyHost = @"127.0.0.1";
    NSNumber* proxyPort = [NSNumber numberWithInt: port];
    
    // Create an NSURLSessionConfiguration that uses the proxy
    NSDictionary *proxyDict = @{
                                @"HTTPEnable"  : [NSNumber numberWithInt:1],
                                (NSString *)kCFStreamPropertyHTTPProxyHost  : proxyHost,
                                (NSString *)kCFStreamPropertyHTTPProxyPort  : proxyPort,
                                
                                @"HTTPSEnable" : [NSNumber numberWithInt:1],
                                (NSString *)kCFStreamPropertyHTTPSProxyHost : proxyHost,
                                (NSString *)kCFStreamPropertyHTTPSProxyPort : proxyPort,
                                };
    
    NSURLSessionConfiguration *configuration = [NSURLSessionConfiguration ephemeralSessionConfiguration];
    configuration.connectionProxyDictionary = proxyDict;
    
    
    
    //Step:-1 Session Create
    NSError *error;
    NSURLSessionConfiguration *defaultconfiguration = [NSURLSessionConfiguration defaultSessionConfiguration];//New Session
    NSURLSession *session = [NSURLSession sessionWithConfiguration:defaultconfiguration delegate:nil delegateQueue:[NSOperationQueue mainQueue]];//Queue is Stroing and retrieve data FIFO
    
    NSURL *urlrequest = [NSURL URLWithString:strurl];
    
    NSMutableURLRequest*mutablerequest = [NSMutableURLRequest requestWithURL:urlrequest];
    // NSData *postData = [NSJSONSerialization dataWithJSONObject:prameter options:kNilOptions error:&error];
    //NSString *receivedDataString = [[NSString alloc] initWithData:postData encoding:NSUTF8StringEncoding];
    //[mutablerequest setHTTPBody:postData];
    // [mutablerequest addValue:@"application/json" forHTTPHeaderField:@"Content-Type"];
    //[mutablerequest addValue:@"application/json" forHTTPHeaderField:@"Accept"];
    //[mutablerequest addValue:receivedDataString forHTTPHeaderField:@"data"];
    
    //    @"firstName":@"nikhil",
    //    @"lastName":@"kanawde",
    //    @"userId":@"nikhil1",
    //    @"actCode":@"detfjt",
    //    @"groupName":@"group1",
    //    @"emailId":@"nikhil.kanawade@uniken.com",
    //    @"mobNum":@"9898989898",
    //    @"username":@"sruser",
    //    @"password":@"1e99b14aa45d6add97271f8e06adacda4e521ad98a4ed18e38cfb0715e7841d2",
    //    @"isRelIdVerifyEnabled":@"true",
    //    @"sessionId":self->testEnrolluser.sessionId,
    //    @"apiversion":@"v1"
    [mutablerequest addValue:@"application/x-www-form-urlencoded" forHTTPHeaderField:@"Content-Type"];
    
    [mutablerequest addValue:enrollUser.userId forHTTPHeaderField:@"userId"];
    [mutablerequest addValue:enrollUser.actCode forHTTPHeaderField:@"actCode"];
    [mutablerequest addValue:enrollUser.username forHTTPHeaderField:@"username"];
    [mutablerequest addValue:enrollUser.password forHTTPHeaderField:@"password"];
    [mutablerequest addValue:enrollUser.sessionId forHTTPHeaderField:@"sessionId"];
    [mutablerequest addValue:enrollUser.apiversion forHTTPHeaderField:@"apiversion"];
    
    [mutablerequest setHTTPMethod:@"POST"];
    NSURLSessionDataTask * task = [session dataTaskWithRequest:mutablerequest completionHandler:^(NSData *  data, NSURLResponse * response, NSError *  error) {
        if (data!=nil)
        {
            NSLog(@"Response %@", data);
            NSString *str = [[NSString alloc]initWithData:data encoding:NSUTF8StringEncoding];
            NSLog(@"str :- %@",str);
            NSError *error;
            block(data,error);//Data is NSDATA and Error is NSERROR
        }
        else
        {
            NSLog(@"error :- %@",[error description]);
            block(nil,error);
        }
    }];
    [task resume];
}

+(void)executequeryForGetUserId:(NSString *)strurl portNumber :(int) port strEnrolluser:(TestEnrollUser *)enrollUser withblock:(void (^)(NSData  *, NSError *))block
{
    NSString* proxyHost = @"127.0.0.1";
    NSNumber* proxyPort = [NSNumber numberWithInt: port];
    
    // Create an NSURLSessionConfiguration that uses the proxy
    NSDictionary *proxyDict = @{
                                @"HTTPEnable"  : [NSNumber numberWithInt:1],
                                (NSString *)kCFStreamPropertyHTTPProxyHost  : proxyHost,
                                (NSString *)kCFStreamPropertyHTTPProxyPort  : proxyPort,
                                
                                @"HTTPSEnable" : [NSNumber numberWithInt:1],
                                (NSString *)kCFStreamPropertyHTTPSProxyHost : proxyHost,
                                (NSString *)kCFStreamPropertyHTTPSProxyPort : proxyPort,
                                };
    
    NSURLSessionConfiguration *configuration = [NSURLSessionConfiguration ephemeralSessionConfiguration];
    configuration.connectionProxyDictionary = proxyDict;
    
    
    
    //Step:-1 Session Create
    NSError *error;
    NSURLSessionConfiguration *defaultconfiguration = [NSURLSessionConfiguration defaultSessionConfiguration];//New Session
    NSURLSession *session = [NSURLSession sessionWithConfiguration:defaultconfiguration delegate:nil delegateQueue:[NSOperationQueue mainQueue]];//Queue is Stroing and retrieve data FIFO
    
    NSURL *urlrequest = [NSURL URLWithString:strurl];
    
    NSMutableURLRequest*mutablerequest = [NSMutableURLRequest requestWithURL:urlrequest];
    // NSData *postData = [NSJSONSerialization dataWithJSONObject:prameter options:kNilOptions error:&error];
    //NSString *receivedDataString = [[NSString alloc] initWithData:postData encoding:NSUTF8StringEncoding];
    //[mutablerequest setHTTPBody:postData];
    // [mutablerequest addValue:@"application/json" forHTTPHeaderField:@"Content-Type"];
    //[mutablerequest addValue:@"application/json" forHTTPHeaderField:@"Accept"];
    //[mutablerequest addValue:receivedDataString forHTTPHeaderField:@"data"];
    
    //    @"firstName":@"nikhil",
    //    @"lastName":@"kanawde",
    //    @"userId":@"nikhil1",
    //    @"actCode":@"detfjt",
    //    @"groupName":@"group1",
    //    @"emailId":@"nikhil.kanawade@uniken.com",
    //    @"mobNum":@"9898989898",
    //    @"username":@"sruser",
    //    @"password":@"1e99b14aa45d6add97271f8e06adacda4e521ad98a4ed18e38cfb0715e7841d2",
    //    @"isRelIdVerifyEnabled":@"true",
    //    @"sessionId":self->testEnrolluser.sessionId,
    //    @"apiversion":@"v1"
    [mutablerequest addValue:@"application/x-www-form-urlencoded" forHTTPHeaderField:@"Content-Type"];
    [mutablerequest addValue:enrollUser.username forHTTPHeaderField:@"username"];
    [mutablerequest addValue:enrollUser.password forHTTPHeaderField:@"password"];
    [mutablerequest addValue:enrollUser.sessionId forHTTPHeaderField:@"sessionId"];
    [mutablerequest addValue:enrollUser.apiversion forHTTPHeaderField:@"apiversion"];
    
    [mutablerequest setHTTPMethod:@"POST"];
    NSURLSessionDataTask * task = [session dataTaskWithRequest:mutablerequest completionHandler:^(NSData *  data, NSURLResponse * response, NSError *  error) {
        if (data!=nil)
        {
            NSLog(@"Response %@", data);
            NSString *str = [[NSString alloc]initWithData:data encoding:NSUTF8StringEncoding];
            NSLog(@"str :- %@",str);
            NSError *error;
            block(data,error);//Data is NSDATA and Error is NSERROR
        }
        else
        {
            NSLog(@"error :- %@",[error description]);
            block(nil,error);
        }
    }];
    [task resume];
}





@end
