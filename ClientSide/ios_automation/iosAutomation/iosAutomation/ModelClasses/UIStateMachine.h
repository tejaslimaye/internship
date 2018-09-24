//
//  UIStateMachin.h
//  AdvanceAPIClient
//
//  Created by Uniken India pvt ltd.
//  Copyright © 2015 Uniken India pvt ltd. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "RDNA.h"
#import "RDNAConstants.h"


@protocol NotificationCallbacks
@required
- (void)onGetNotification:(RDNAStatusGetNotifications*) statusGetNotification;
- (void)onUpdateNotification:(RDNAStatusUpdateChallenges*) statusUpdateNotification;
- (void)onGetNotificationsHistory:(RDNAStatusGetNotificationHistory*) statusNotificationHistory;
@end


@protocol LogOffCallbacks

@required
- (void)logOff:(int)errorCode withChallenges:(NSArray *)challenges;
@end

@protocol GetAllChallengesCallbacks

@required
- (void)GetAllChallengesWithErrorCode:(int)errorCode andErrorMessage:(NSString *)errorMessage;
@end

@protocol GetPostLoginAuthenticationChallengesCallback
- (void)getPostLoginAuthenticationChallengesWithErrorCode:(int)errorCode andErrorMessage:(NSString *)errorMessage;
@end

@protocol GetRegisteredDevicesCallback
- (void)GetRegisteredDevices:(RDNAStatusGetRegisteredDeviceDetails *)status withErrorCode:(int)errorCode andErrorMessage:(NSString *)errorMessage;
@end

@protocol UpdateDeviceDetailsCallback
- (void)UpdateDeviceDetails:(RDNAStatusGetRegisteredDeviceDetails *)status withErrorCode:(int)errorCode andErrorMEssage:(NSString *)errorMessage;
@end

@interface StatusInfo : NSObject

typedef NS_ENUM(NSInteger, APIType) {
  CHECKCHALLENGES = 0,
  UPDATECHALLENGES,
  POSTLOGINCHALLENGES,
};

@property (nonatomic, strong) NSArray *challeges;
@property (nonatomic, strong) RDNAResponseStatus *challengeStatus;
@property (nonatomic) APIType apiType;

//- (id)initWithChalleges:(NSArray *)challenges withChallengeStatus:(RDNAChallengeStatus *)challengeStatus;

- (id)initWithChalleges:(NSArray *)challenges withChallengeStatus:(RDNAResponseStatus *)challengeStatus withAPIFlag:(APIType)apiType;

@end

@protocol TerminateCallbacks

@required
- (void)terminate:(int)errorCode;
@end

@protocol ConfigurationCallbacks

@required
- (void)config:(NSString*)strConfig withErrorCode:(int)errorCode;
@end

@interface UIStateMachine : NSObject

@property (nonatomic, strong) NSMutableArray *challeges;
@property (nonatomic, strong) NSMutableArray *initialChalleges;
@property (nonatomic, strong) NSMutableArray *allChalleges;
@property (nonatomic, strong) NSArray *arraySegues;
@property (nonatomic, assign) int resetUICounter;

@property (nonatomic, strong) NSMutableArray *registeredDevices;

/**
* @brief This method returns the singleton object of UIStateMachine class .
*/
+ (UIStateMachine *)getSharedInstance;

/**
 * @brief This method set challenges from onInitialseComplete and onChallengeReceived .
 */
- (void)setChallenge:(NSArray *)challenges;

/**
 * @brief This method return the next Challenge from challenges array.
 */
- (RDNAChallenge *)getNextChallenge;

/**
 * @brief This method tells us all challenges done or not.
 */
- (BOOL)allChallengesDone;

/**
 * @brief This method update perticular challenge from saved challenges.
 */
- (void)updateChallenge:(RDNAChallenge *)challenge;


/**
 * @brief This method returns all saved challenges.
 */
- (NSArray *)getChalleges;

/**
 * @brief This method makes the incrementChallenge counter to previuos challenge.
*this method will generally used go back to one previous screen.
 */
- (void)switchToPreviousChallenge;

/**
 * @brief This method returns current challenge for complete.
 */
- (RDNAChallenge *)getCurrentChallenge;

/**
 * @brief This method save the initial challenges.
 */
- (void)setInitialChallenge:(NSArray *)challenges;

/**
 * @brief This method returns initial challenges.
 */
- (NSArray *)getInitialChallenge;


/**
 * @brief This method save all update challenges.
 */
- (void)setAllChallenge:(NSArray *)challenges;


/**
 * @brief This method returns update challenge by name from saved update challeges.
 */
- (RDNAChallenge *)getChallengeWithName:(NSString *)challengeName;


/**
 * @brief This method returns update challenge aaray by name from saved update challeges.
 */
- (NSMutableArray *)getChallengesWithName:(NSString *)challengeName;


/**
 * @brief This method returns current challenge is first or not.
 */
- (BOOL)isFirstChallenge;



@end
