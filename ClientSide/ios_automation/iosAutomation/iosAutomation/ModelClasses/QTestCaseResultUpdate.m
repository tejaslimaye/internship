#import "QTestCaseResultUpdate.h"

// Shorthand for simple blocks
#define λ(decl, expr) (^(decl) { return (expr); })

// nil → NSNull conversion for JSON dictionaries
static id NSNullify(id _Nullable x) {
    return (x == nil || x == NSNull.null) ? NSNull.null : x;
}

NS_ASSUME_NONNULL_BEGIN

#pragma mark - Private model interfaces

@interface QTestCaseResultUpdate (JSONConversion)
+ (instancetype)fromJSONDictionary:(NSDictionary *)dict;
- (NSDictionary *)JSONDictionary;
@end

#pragma mark - JSON serialization

QTestCaseResultUpdate *_Nullable QTestCaseResultUpdateFromData(NSData *data, NSError **error)
{
    @try {
        id json = [NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingAllowFragments error:error];
        return *error ? nil : [QTestCaseResultUpdate fromJSONDictionary:json];
    } @catch (NSException *exception) {
        *error = [NSError errorWithDomain:@"JSONSerialization" code:-1 userInfo:@{ @"exception": exception }];
        return nil;
    }
}

QTestCaseResultUpdate *_Nullable QTestCaseResultUpdateFromJSON(NSString *json, NSStringEncoding encoding, NSError **error)
{
    return QTestCaseResultUpdateFromData([json dataUsingEncoding:encoding], error);
}

NSData *_Nullable QTestCaseResultUpdateToData(QTestCaseResultUpdate *resultUpdate, NSError **error)
{
    @try {
        id json = [resultUpdate JSONDictionary];
        NSData *data = [NSJSONSerialization dataWithJSONObject:json options:kNilOptions error:error];
        return *error ? nil : data;
    } @catch (NSException *exception) {
        *error = [NSError errorWithDomain:@"JSONSerialization" code:-1 userInfo:@{ @"exception": exception }];
        return nil;
    }
}

NSString *_Nullable QTestCaseResultUpdateToJSON(QTestCaseResultUpdate *resultUpdate, NSStringEncoding encoding, NSError **error)
{
    NSData *data = QTestCaseResultUpdateToData(resultUpdate, error);
    return data ? [[NSString alloc] initWithData:data encoding:encoding] : nil;
}

@implementation QTestCaseResultUpdate
+ (NSDictionary<NSString *, NSString *> *)properties
{
    static NSDictionary<NSString *, NSString *> *properties;
    return properties = properties ? properties : @{
        @"execution_id": @"executionID",
        @"execution_result": @"executionResult",
        @"start_time": @"startTime",
        @"end_time": @"endTime",
        @"params_used": @"paramsUsed",
        @"result_data": @"resultData",
    };
}

+ (_Nullable instancetype)fromData:(NSData *)data error:(NSError *_Nullable *)error
{
    return QTestCaseResultUpdateFromData(data, error);
}

+ (_Nullable instancetype)fromJSON:(NSString *)json encoding:(NSStringEncoding)encoding error:(NSError *_Nullable *)error
{
    return QTestCaseResultUpdateFromJSON(json, encoding, error);
}

+ (instancetype)fromJSONDictionary:(NSDictionary *)dict
{
    return dict ? [[QTestCaseResultUpdate alloc] initWithJSONDictionary:dict] : nil;
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
    id resolved = QTestCaseResultUpdate.properties[key];
    if (resolved) [super setValue:value forKey:resolved];
}

- (NSDictionary *)JSONDictionary
{
    id dict = [[self dictionaryWithValuesForKeys:QTestCaseResultUpdate.properties.allValues] mutableCopy];

    // Rewrite property names that differ in JSON
    for (id jsonName in QTestCaseResultUpdate.properties) {
        id propertyName = QTestCaseResultUpdate.properties[jsonName];
        if (![jsonName isEqualToString:propertyName]) {
            dict[jsonName] = dict[propertyName];
            [dict removeObjectForKey:propertyName];
        }
    }

    return dict;
}

- (NSData *_Nullable)toData:(NSError *_Nullable *)error
{
    return QTestCaseResultUpdateToData(self, error);
}

- (NSString *_Nullable)toJSON:(NSStringEncoding)encoding error:(NSError *_Nullable *)error
{
    return QTestCaseResultUpdateToJSON(self, encoding, error);
}
@end

NS_ASSUME_NONNULL_END
