//
//  UIStateMachin.m
//  AdvanceAPIClient
//
//  Created by Uniken India pvt ltd.
//  Copyright © 2015 Uniken India pvt ltd. All rights reserved.
//

#import "UIStateMachine.h"
@interface UIStateMachine()
{
  int challengeCount;
  int challengeCounter;
}
@end
@implementation UIStateMachine

- (id)init{
  
  if (self) {
    
  }
  return self;
}

+(UIStateMachine *)getSharedInstance{
  
  __strong static UIStateMachine *sharedUIStateMachin = nil;
  static dispatch_once_t onceToken1;
  dispatch_once(&onceToken1, ^{
    sharedUIStateMachin = [[self alloc] init];
  });
  return sharedUIStateMachin;
}

-(void)setChallenge:(NSArray*)challenges{
  
  challengeCounter = 0;
  self.challeges = [challenges mutableCopy];
}

-(RDNAChallenge*)getNextChallenge{
  
  if (self.challeges == nil || [self.challeges count] <= 0) {
    return nil;
  }  else if(self.challeges.count == challengeCounter){
    return nil;
  }else{
    RDNAChallenge* challenge = [self.challeges objectAtIndex:challengeCounter];
    challengeCounter++;
    return challenge;
  }
}

-(void)switchToPreviousChallenge{
  
  if (challengeCounter >  0) {
    challengeCounter -- ;
  }
  
}
-(BOOL)allChallengesDone{
  
  if (challengeCounter >= self.challeges.count ) {
    return YES;
  }else{
    return NO;
  }
}

-(void)updateChallenge:(RDNAChallenge*)challenge{
  
  [self.challeges replaceObjectAtIndex:[self.challeges indexOfObject:challenge] withObject:challenge];
}

-(NSArray*)getChalleges{
  
  return self.challeges;
}
-(RDNAChallenge*)getCurrentChallenge{
  
  RDNAChallenge *chlg;
  if ((challengeCounter - 1) >= 0) {
    chlg = [self.challeges objectAtIndex:challengeCounter - 1];
  }else{
    chlg =  [self.challeges objectAtIndex:challengeCounter];
  }
  return chlg;
  
}

-(void)setInitialChallenge:(NSArray*)challenges{
  
  self.initialChalleges = [challenges mutableCopy];
}

-(NSArray*)getInitialChallenge{
  
  return self.initialChalleges;
}

-(void)setAllChallenge:(NSArray*)challenges{
  
  self.allChalleges = [challenges mutableCopy];
}

-(RDNAChallenge*)getChallengeWithName:(NSString*)challengeName{
  
  for (int i = 0; i < self.allChalleges.count; i++) {
    RDNAChallenge* tempChallenge = [self.allChalleges objectAtIndex:i];
    if ([tempChallenge.name isEqualToString:challengeName]) {
      return tempChallenge;
    }
  }
  return nil;
}


- (NSMutableArray *)getChallengesWithName:(NSString *)challengeName{
  
  NSMutableArray *updateChlgArray = [[NSMutableArray alloc]init];
  for (int i = 0; i < self.allChalleges.count; i++) {
    RDNAChallenge* tempChallenge = [self.allChalleges objectAtIndex:i];
    if ([tempChallenge.name isEqualToString:challengeName]) {
      [updateChlgArray addObject:tempChallenge];
    }
  }
  return updateChlgArray;
}

- (BOOL)isFirstChallenge{
  
  if (challengeCounter == 1) {
    return YES;
  }else{
    return NO;
  }
}
@end

@implementation StatusInfo

- (id)init{
  
  self = [super init];
  if (self)  {
    
    self.challeges = nil;
    self.challengeStatus = nil;
    
    self.apiType = 0;
  }
  
  return self;
}


- (id)initWithChalleges:(NSArray *)challenges withChallengeStatus:(RDNAResponseStatus *)challengeStatus withAPIFlag:(APIType)apiType {
  self = [super init];
  if (self) {
    self.challeges = challenges;
    self.challengeStatus = challengeStatus;
    self.apiType = apiType;
  }
  return self;
}

@end
