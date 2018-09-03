//
//  Utility.m
//  API_SDK_SAMPLE_V1
//
//  Created by Uniken India pvt ltd.
//  Copyright © 2015 Uniken India pvt ltd. All rights reserved.
//

#import "Utility.h"
#import <CommonCrypto/CommonDigest.h>
#import "UIStateMachine.h"
@implementation Utility

- (id)init
{
  self = [super init];
  if (self) {
    
  }
  return self;
}
+(NSString*)trimmingStringWithString:(NSString*)string{
  return [string stringByTrimmingCharactersInSet:
          [NSCharacterSet whitespaceCharacterSet]];
}

@end

@implementation PasswordPolicy

- (id)init
{
  self = [super init];
  if (self) {
    self.minChar = 4;
    self.maxChar = 8;
    self.must_have_digit = YES;
    self.must_have_special_char = YES;
    self.must_have_uppercase = YES;
  }
  return self;
}

@end

@implementation UserSessionState

+(UserSessionState*)getSharedInstance{
  
  __strong static UserSessionState *sharedSessionState = nil;
  static dispatch_once_t onceToken1;
  dispatch_once(&onceToken1, ^{
    sharedSessionState = [[self alloc] init];
    
  });
  return sharedSessionState;
}

- (void)parseConfig:(NSString *)jsonString withurlMicro:(NSDictionary*)dictMicro{
  
  NSError *jsonError;
  NSData *objectData = [jsonString dataUsingEncoding:NSUTF8StringEncoding];
  NSDictionary *json = [NSJSONSerialization JSONObjectWithData:objectData
                                                       options:NSJSONReadingMutableContainers
                                                         error:&jsonError];
  
  NSArray *arrConfig = [json valueForKey:@"config"];
  
  for (NSDictionary *obj in arrConfig) {
    if([[obj valueForKey:@"key"] isEqualToString:@"main_page_url"]){
  
      self.mainPageUrl = [self replaceUrlMicro:[obj valueForKey:@"value"] microDetails:dictMicro];
    }else
      if([[obj valueForKey:@"key"] isEqualToString:@"password_policy"]){
      self.passwordPolicy = [self parsePasswordPolicy:[obj valueForKey:@"value"]];
    }
  }
   //self.passwordPolicy = [self parsePasswordPolicy:[json valueForKey:@"password_policy"]];
  
}

-(NSString*)replaceUrlMicro:(NSString*)url  microDetails:(NSDictionary*)dictMicro{
  
  for (NSString* key in dictMicro) {
    id value = [dictMicro objectForKey:key];
    url = [url stringByReplacingOccurrencesOfString:key withString:value];
  }
  return url;
}




- (PasswordPolicy *)parsePasswordPolicy:(NSDictionary *)passPolicyDictionary {
  
  //  NSString *passPolicyJsonStringtemp = @"{\"minChar\":\"4\",\"maxChar\":\"10\",\"must_have_digit\":\"1\", \"must_have_special_char\":\"1\",\"must_have_uppercase\":\"\1\"}"; //Test Policy json
  //  NSError *jsonError;
  //  NSData *objectData = [passPolicyJsonStringtemp dataUsingEncoding:NSUTF8StringEncoding];
  //  NSDictionary *json = [NSJSONSerialization JSONObjectWithData:objectData
  //                                                       options:NSJSONReadingMutableContainers
  //                                                         error:&jsonError];
  PasswordPolicy *passwordPolicy = [UserSessionState getSharedInstance].passwordPolicy;
  passwordPolicy.minChar = (int)[passPolicyDictionary valueForKey:@"minChar"];
  passwordPolicy.maxChar = (int)[passPolicyDictionary valueForKey:@"maxChar"];
  passwordPolicy.must_have_digit = (BOOL)[passPolicyDictionary valueForKey:@"must_have_digit"];
  passwordPolicy.must_have_special_char = (BOOL)[passPolicyDictionary valueForKey:@"must_have_special_char"];
  passwordPolicy.must_have_uppercase = (BOOL)[passPolicyDictionary valueForKey:@"must_have_uppercase"];
  
  return passwordPolicy;
}

- (BOOL)checkQuestionDuplication:(RDNAChallenge*) challenge withString:(NSString*)str{
  
  NSMutableArray *tempArray = [[NSMutableArray alloc] init];
  for (int j = 0; j < [[[UIStateMachine getSharedInstance] getChalleges] indexOfObject:challenge]; j++) {
    [tempArray addObject:[[[UIStateMachine getSharedInstance] getChalleges] objectAtIndex:j]];
  }
  
  
  if (tempArray.count > 0) {
    
    for (int i = 0; i < tempArray.count; i++) {
      RDNAChallenge* tempChallenge = [tempArray objectAtIndex:i];
      if (tempChallenge.responseKey.length > 0) {
        if ([tempChallenge.responseKey isEqualToString:str]) {
          return YES;
        }
      }
    }
    
  }else{
    return NO;
  }
  return NO;
}

@end
