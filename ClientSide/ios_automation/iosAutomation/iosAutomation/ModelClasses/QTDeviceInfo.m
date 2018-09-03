#import "QTDeviceInfo.h"

// Shorthand for simple blocks
#define λ(decl, expr) (^(decl) { return (expr); })

// nil → NSNull conversion for JSON dictionaries
static id NSNullify(id _Nullable x) {
    return (x == nil || x == NSNull.null) ? NSNull.null : x;
}

NS_ASSUME_NONNULL_BEGIN

#pragma mark - Private model interfaces

@interface QTDeviceInfo (JSONConversion)
+ (instancetype)fromJSONDictionary:(NSDictionary *)dict;
- (NSDictionary *)JSONDictionary;
@end

#pragma mark - JSON serialization

QTDeviceInfo *_Nullable QTDeviceInfoFromData(NSData *data, NSError **error)
{
    @try {
        id json = [NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingAllowFragments error:error];
        return *error ? nil : [QTDeviceInfo fromJSONDictionary:json];
    } @catch (NSException *exception) {
        *error = [NSError errorWithDomain:@"JSONSerialization" code:-1 userInfo:@{ @"exception": exception }];
        return nil;
    }
}

QTDeviceInfo *_Nullable QTDeviceInfoFromJSON(NSString *json, NSStringEncoding encoding, NSError **error)
{
    return QTDeviceInfoFromData([json dataUsingEncoding:encoding], error);
}

NSData *_Nullable QTDeviceInfoToData(QTDeviceInfo *deviceInfo, NSError **error)
{
    @try {
        id json = [deviceInfo JSONDictionary];
        NSData *data = [NSJSONSerialization dataWithJSONObject:json options:kNilOptions error:error];
        return *error ? nil : data;
    } @catch (NSException *exception) {
        *error = [NSError errorWithDomain:@"JSONSerialization" code:-1 userInfo:@{ @"exception": exception }];
        return nil;
    }
}

NSString *_Nullable QTDeviceInfoToJSON(QTDeviceInfo *deviceInfo, NSStringEncoding encoding, NSError **error)
{
    NSData *data = QTDeviceInfoToData(deviceInfo, error);
    return data ? [[NSString alloc] initWithData:data encoding:encoding] : nil;
}

@implementation QTDeviceInfo
+ (NSDictionary<NSString *, NSString *> *)properties
{
    static NSDictionary<NSString *, NSString *> *properties;
    return properties = properties ? properties : @{
        @"serial_num": @"serialNum",
        @"device_model": @"deviceModel",
        @"device_os": @"deviceOS",
        @"os_version": @"osVersion",
        @"build_id": @"buildID",
        @"manufacturer": @"manufacturer",
        @"brand": @"brand",
        @"library_version": @"libraryVersion",
    };
    
    
  /*  @{
      @"serial_num":@"my phone 345",
      @"device_model":@"hone",
      @"device_os":@"ios",
      @"os_version":@"123",
      @"build_id":@"buildID",
      @"manufacturer":@"mannufacturer1",
      @"brand":@"brand1",
      @"library_version":@"1.0"
      };*/
    
    
}

+ (_Nullable instancetype)fromData:(NSData *)data error:(NSError *_Nullable *)error
{
    return QTDeviceInfoFromData(data, error);
}

+ (_Nullable instancetype)fromJSON:(NSString *)json encoding:(NSStringEncoding)encoding error:(NSError *_Nullable *)error
{
    return QTDeviceInfoFromJSON(json, encoding, error);
}

+ (instancetype)fromJSONDictionary:(NSDictionary *)dict
{
    return dict ? [[QTDeviceInfo alloc] initWithJSONDictionary:dict] : nil;
}

- (instancetype)initWithJSONDictionary:(NSDictionary *)dict
{
    if (self = [super init]) {
        [self setValuesForKeysWithDictionary:dict];
    }
    return self;
}

- (void)setValue:(nullable id)value forKey:(NSString *)key
{
    id resolved = QTDeviceInfo.properties[key];
    if (resolved) [super setValue:value forKey:resolved];
}

- (NSDictionary *)JSONDictionary
{
    id dict = [[self dictionaryWithValuesForKeys:QTDeviceInfo.properties.allValues] mutableCopy];

    // Rewrite property names that differ in JSON
    for (id jsonName in QTDeviceInfo.properties) {
        id propertyName = QTDeviceInfo.properties[jsonName];
        if (![jsonName isEqualToString:propertyName]) {
            dict[jsonName] = dict[propertyName];
            [dict removeObjectForKey:propertyName];
        }
    }

    return dict;
}

- (NSData *_Nullable)toData:(NSError *_Nullable *)error
{
    return QTDeviceInfoToData(self, error);
}

- (NSString *_Nullable)toJSON:(NSStringEncoding)encoding error:(NSError *_Nullable *)error
{
    return QTDeviceInfoToJSON(self, encoding, error);
}
@end

NS_ASSUME_NONNULL_END
