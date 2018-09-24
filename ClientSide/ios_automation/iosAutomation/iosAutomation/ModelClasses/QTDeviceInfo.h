// To parse this JSON:
//
//   NSError *error;
//   QTDeviceInfo *deviceInfo = [QTDeviceInfo fromJSON:json encoding:NSUTF8Encoding error:&error];

#import <Foundation/Foundation.h>

@class QTDeviceInfo;

NS_ASSUME_NONNULL_BEGIN

#pragma mark - Object interfaces

@interface QTDeviceInfo : NSObject
@property (nonatomic, copy) NSString *serialNum;
@property (nonatomic, copy) NSString *deviceModel;
@property (nonatomic, copy) NSString *deviceOS;
@property (nonatomic, copy) NSString *osVersion;
@property (nonatomic, copy) NSString *buildID;
@property (nonatomic, copy) NSString *manufacturer;
@property (nonatomic, copy) NSString *brand;
@property (nonatomic, copy) NSString *libraryVersion;

+ (_Nullable instancetype)fromJSON:(NSString *)json encoding:(NSStringEncoding)encoding error:(NSError *_Nullable *)error;
+ (_Nullable instancetype)fromData:(NSData *)data error:(NSError *_Nullable *)error;
- (NSString *_Nullable)toJSON:(NSStringEncoding)encoding error:(NSError *_Nullable *)error;
- (NSData *_Nullable)toData:(NSError *_Nullable *)error;
- (NSDictionary *)JSONDictionary;
@end

NS_ASSUME_NONNULL_END
