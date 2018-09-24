// To parse this JSON:
//
//   NSError *error;
//   QTestEnrolluserResult *testEnrolluserResult = [QTestEnrolluserResult fromJSON:json encoding:NSUTF8Encoding error:&error];

#import <Foundation/Foundation.h>

@class QTestEnrolluserResult;

NS_ASSUME_NONNULL_BEGIN

#pragma mark - Object interfaces

@interface QTestEnrolluserResult : NSObject
@property (nonatomic, assign) BOOL isError;
@property (nonatomic, copy)   NSString *successMessage;

+ (_Nullable instancetype)fromJSON:(NSString *)json encoding:(NSStringEncoding)encoding error:(NSError *_Nullable *)error;
+ (_Nullable instancetype)fromData:(NSData *)data error:(NSError *_Nullable *)error;
- (NSString *_Nullable)toJSON:(NSStringEncoding)encoding error:(NSError *_Nullable *)error;
- (NSData *_Nullable)toData:(NSError *_Nullable *)error;
@end

NS_ASSUME_NONNULL_END
