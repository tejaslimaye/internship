//
//  RDNAClient.h
//  API_SDK_SAMPLE_V1
//
//  Created by Uniken India pvt ltd.
//  Copyright © 2015 Uniken India pvt ltd. All rights reserved.
//

/**
 * @brief This Class contians the implementation of RDNA.
 */

#import <Foundation/Foundation.h>
#import "UIStateMachine.h"


@interface RDNAClient : NSObject <RDNACallbacks>
/**
 * @brief This method initilizes the RDNA with the provided host, port, cipher specs, cipher salt, proxy settings.
 * It also returns an RDNA object which needs to be used futher when we want to invoke any api of the rdna client.
 */
- (int)initializeRDNA;


/**
 * @brief This method is used to pause the rdna client execution.
 * In return it provides an context object which should be saved in the client and used when user wants to resume the
 * rdna client execution.
 * The resume API should be used in cases when user moves the app from foreground to background.
 */
- (int)pauseRDNA;


/**
 * @brief This method is used to resume the rdna client execution.
 * The savd context that is obtained in the pause API should be passed in the resume API
 */
- (int)resumeRDNA;


/**
 * @brief This method is used to fetch the currently running services.
 */
- (NSArray *)fetchAllServices;


/**
 * @brief This method is invoked when application is moved into background.
 */
- (void)EnterForeground;


/**
 * @brief This method is invoked when application is moved into background.
 */
- (void)EnterBackground;


/**
 * @brief This method is used to Encrypt the data packet. Here the input parameter is plain NSdata and returns the
 * encrypted NSdata.
 */
- (NSData*)encryptDataPacket:(NSData *)plainData;

/**
 * @brief This method is used to Decrypt the data packet. Here the input parameter is encrypted NSdata and returns the
 * plain NSdata.
 */
- (NSData*)decryptDataPacket:(NSData *)encryptedData;

/**
 * @brief This method is used ti set the reponse in the response array of the given challenge with its appropraite key.
 */
- (int)RDNAClientSetReponse:(NSObject *)response andKey:(NSString *)key forChallenge:(RDNAChallenge *)challenge;

- (int)RDNAClientResetChallenge;

/**
 * @brief This method is used for get current session id on server
 */
-(NSString*)RDNAGetSessionID;

/**
 * @brief This method checks for any pending challenges and then invokes the CheckChallenges api of the RDNA for the
 * given userID. Challenges response is obtained in the callBack i.e onChallengeRecieved function.
 */
- (int)RDNAClientCheckChallenges:(NSArray *)challengeArray forUserID:(NSString *)userID;

/**
 * @brief This method checks for any pending challenges and then invokes the updateChallenges api of the RDNA for the
 * given userID. Challenges response is obtained in the callBack i.e onChallengeRecieved function.
 */
- (int)RDNAClientUpdateChallenges:(NSArray *)challengeArray forUserID:(NSString *)userID;

- (int)RDNAClientPostLoginAuthChallenges:(NSArray *)challengeArray forUserID:(NSString *)userID;


/**
 * @brief This method is used to validate the password as per the password policy obtained.
 */
- (BOOL)validatePassword:(NSString *)password;

/**
 * @brief This method invokes the forgetPassword api of the RDNA for the given userID.
 * Challenges response is obtained in the callBack i.e onChallengeRecieved function.
 */
- (int)RDNAClientForgetPasswordForUserID:(NSString *)userID;


/**
 * @brief This method invokes the get api of the RDNA GetNotification with given parameter.
 * Challenges response is obtained in the callBack i.e onGetNotifications function.
 */
- (int)RDNAClientGetNotificationWithRecountCount:(int)recordCount withStartIndex:(int)startIndex withEnterpriseID:(NSString*)enterpriseID withStartDate:(NSString*)startDate withEndDate:(NSString*)endDate withCallback:(id) getNotificationCallback;


/**
 * @brief This method invokes the get api of the RDNA updateNotification with given parameter.
 * Challenges response is obtained in the callBack i.e onUpdateNotification function.
 */
- (int)RDNAClientUpdateNotification:(NSString*)notificationID withResponse:(NSString*)response withCallback:(id) updateNotificationCallback;

- (int)RDNAClientGetNotificationHistory:(int)recordCount withStartIndex:(int)startIndex withEnterpriseID:(NSString*)enterpriseID withStartDate:(NSString*)startDate withEndDate:(NSString*)endDate withNotificationStatus:(NSString*)notificationStatus withActionPerformed:(NSString*)actionPerformed withKeywordSearch:(NSString*)keywordSearch withDeviceID:(NSString*)deviceID withCallback:(id) getNotificationHistoryCallback;


/**
 * @brief This method invokes the convert integer number errorcode into enum string
 */
+ (NSString *) RDNAClientEnumConvertToString :(int)errorCode;



- (void)getDevicesState:(NSMutableArray *)devices;

@end
