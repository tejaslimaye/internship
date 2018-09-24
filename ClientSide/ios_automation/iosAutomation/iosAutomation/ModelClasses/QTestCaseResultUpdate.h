// To parse this JSON:
//
//   NSError *error;
//   QTestCaseResultUpdate *resultUpdate = [QTestCaseResultUpdate fromJSON:json encoding:NSUTF8Encoding error:&error];

#import <Foundation/Foundation.h>

@class QTestCaseResultUpdate;

NS_ASSUME_NONNULL_BEGIN

#pragma mark - Object interfaces

@interface QTestCaseResultUpdate : NSObject
@property (nonatomic, assign) NSInteger executionID;
@property (nonatomic, copy)   NSString *executionResult;
@property (nonatomic, copy)   NSString *startTime;
@property (nonatomic, copy)   NSString *endTime;
@property (nonatomic, copy)   NSString *paramsUsed;
@property (nonatomic, copy)   NSString *resultData;

+ (_Nullable instancetype)fromJSON:(NSString *)json encoding:(NSStringEncoding)encoding error:(NSError *_Nullable *)error;
+ (_Nullable instancetype)fromData:(NSData *)data error:(NSError *_Nullable *)error;
- (NSString *_Nullable)toJSON:(NSStringEncoding)encoding error:(NSError *_Nullable *)error;
- (NSData *_Nullable)toData:(NSError *_Nullable *)error;
@end

NS_ASSUME_NONNULL_END
