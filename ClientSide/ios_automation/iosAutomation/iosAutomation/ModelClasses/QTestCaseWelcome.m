#import "QTestCaseWelcome.h"
#import "StringConstants.h"
// Shorthand for simple blocks
#define λ(decl, expr) (^(decl) { return (expr); })

// nil → NSNull conversion for JSON dictionaries
static id NSNullify(id _Nullable x) {
    return (x == nil || x == NSNull.null) ? NSNull.null : x;
}

NS_ASSUME_NONNULL_BEGIN

#pragma mark - Private model interfaces

@interface QTestCaseWelcome (JSONConversion)
+ (instancetype)fromJSONDictionary:(NSDictionary *)dict;
- (NSDictionary *)JSONDictionary;
@end

@interface QTestCaseServerExecutionDetail (JSONConversion)
+ (instancetype)fromJSONDictionary:(NSDictionary *)dict;
- (NSDictionary *)JSONDictionary;
@end

@interface QTestCaseTestjobexecution (JSONConversion)
+ (instancetype)fromJSONDictionary:(NSDictionary *)dict;
- (NSDictionary *)JSONDictionary;
@end

@interface QTestCaseExecution (JSONConversion)
+ (instancetype)fromJSONDictionary:(NSDictionary *)dict;
- (NSDictionary *)JSONDictionary;
@end

static id map(id collection, id (^f)(id value)) {
    id result = nil;
    if ([collection isKindOfClass:NSArray.class]) {
        result = [NSMutableArray arrayWithCapacity:[collection count]];
        for (id x in collection) [result addObject:f(x)];
    } else if ([collection isKindOfClass:NSDictionary.class]) {
        result = [NSMutableDictionary dictionaryWithCapacity:[collection count]];
        for (id key in collection) [result setObject:f([collection objectForKey:key]) forKey:key];
    }
    return result;
}

#pragma mark - JSON serialization

QTestCaseWelcome *_Nullable QTestCaseWelcomeFromData(NSData *data, NSError **error)
{
    @try {
        NSLog(@"%@",[[NSString alloc]initWithData:data encoding:NSUTF8StringEncoding]);
        id json = [NSJSONSerialization JSONObjectWithData:data options:0 error:error];
        return *error ? nil : [QTestCaseWelcome fromJSONDictionary:json];
    } @catch (NSException *exception) {
        *error = [NSError errorWithDomain:@"JSONSerialization" code:-1 userInfo:@{ @"exception": exception }];
        return nil;
    }
}

QTestCaseWelcome *_Nullable QTestCaseWelcomeFromJSON(NSString *json, NSStringEncoding encoding, NSError **error)
{
    return QTestCaseWelcomeFromData([json dataUsingEncoding:encoding], error);
}

NSData *_Nullable QTestCaseWelcomeToData(QTestCaseWelcome *welcome, NSError **error)
{
    @try {
        id json = [welcome JSONDictionary];
        NSData *data = [NSJSONSerialization dataWithJSONObject:json options:kNilOptions error:error];
        return *error ? nil : data;
    } @catch (NSException *exception) {
        *error = [NSError errorWithDomain:@"JSONSerialization" code:-1 userInfo:@{ @"exception": exception }];
        return nil;
    }
}

NSString *_Nullable QTestCaseWelcomeToJSON(QTestCaseWelcome *welcome, NSStringEncoding encoding, NSError **error)
{
    NSData *data = QTestCaseWelcomeToData(welcome, error);
    return data ? [[NSString alloc] initWithData:data encoding:encoding] : nil;
}

@implementation QTestCaseWelcome
+ (NSDictionary<NSString *, NSString *> *)properties
{
    static NSDictionary<NSString *, NSString *> *properties;
    return properties = properties ? properties : @{
        @"device_avail_code": @"deviceAvailCode",
        @"job_avail_code": @"jobAvailCode",
        @"server_execution_details": @"serverExecutionDetails",
        @"response_code": @"responseCode",
        @"error_code": @"errorCode",
    };
}

+ (_Nullable instancetype)fromData:(NSData *)data error:(NSError *_Nullable *)error
{
    return QTestCaseWelcomeFromData(data, error);
}

+ (_Nullable instancetype)fromJSON:(NSString *)json encoding:(NSStringEncoding)encoding error:(NSError *_Nullable *)error
{
    return QTestCaseWelcomeFromJSON(json, encoding, error);
}

+ (instancetype)fromJSONDictionary:(NSDictionary *)dict
{
    return dict ? [[QTestCaseWelcome alloc] initWithJSONDictionary:dict] : nil;
}

- (instancetype)initWithJSONDictionary:(NSDictionary *)dict
{
    if (self = [super init]) {
        [self setValuesForKeysWithDictionary:dict];
        _serverExecutionDetails = map(_serverExecutionDetails, λ(id x, [QTestCaseServerExecutionDetail fromJSONDictionary:x]));
    }
    return self;
}

- (void)setValue:(nullable id)value forKey:(NSString *)key
{
    id resolved = QTestCaseWelcome.properties[key];
    if (resolved) [super setValue:value forKey:resolved];
}

- (NSDictionary *)JSONDictionary
{
    id dict = [[self dictionaryWithValuesForKeys:QTestCaseWelcome.properties.allValues] mutableCopy];

    // Rewrite property names that differ in JSON
    for (id jsonName in QTestCaseWelcome.properties) {
        id propertyName = QTestCaseWelcome.properties[jsonName];
        if (![jsonName isEqualToString:propertyName]) {
            dict[jsonName] = dict[propertyName];
            [dict removeObjectForKey:propertyName];
        }
    }

    // Map values that need translation
    [dict addEntriesFromDictionary:@{
        @"server_execution_details": map(_serverExecutionDetails, λ(id x, [x JSONDictionary])),
    }];

    return dict;
}

- (NSData *_Nullable)toData:(NSError *_Nullable *)error
{
    return QTestCaseWelcomeToData(self, error);
}

- (NSString *_Nullable)toJSON:(NSStringEncoding)encoding error:(NSError *_Nullable *)error
{
    return QTestCaseWelcomeToJSON(self, encoding, error);
}
@end

@implementation QTestCaseServerExecutionDetail
+ (NSDictionary<NSString *, NSString *> *)properties
{
    static NSDictionary<NSString *, NSString *> *properties;
    return properties = properties ? properties : @{
        @"server_id": @"serverID",
        @"gm_port": @"gmPort",
        @"sdk_port": @"sdkPort",
        @"verify_port": @"verifyPort",
        @"api_port": @"apiPort",
        @"ip_address": @"ipAddress",
        @"os_version": @"osVersion",
        @"console_user": @"consoleUser",
        @"console_password": @"consolePassword",
        @"enterprise_id": @"enterpriseID",
        @"enterprise_user": @"enterpriseUser",
        @"enterprise_password": @"enterprisePassword",
        @"server_user": @"serverUser",
        @"server_password": @"serverPassword",
        @"agent_info": @"agentInfo",
        @"testjobexecutions": @"testjobexecutions",
    };
}

+ (instancetype)fromJSONDictionary:(NSDictionary *)dict
{
    return dict ? [[QTestCaseServerExecutionDetail alloc] initWithJSONDictionary:dict] : nil;
}

- (instancetype)initWithJSONDictionary:(NSDictionary *)dict
{
    if (self = [super init]) {
        [self setValuesForKeysWithDictionary:dict];
        _testjobexecutions = map(_testjobexecutions, λ(id x, [QTestCaseTestjobexecution fromJSONDictionary:x]));
    }
    return self;
}

- (void)setValue:(nullable id)value forKey:(NSString *)key
{
    id resolved = QTestCaseServerExecutionDetail.properties[key];
    if (resolved) [super setValue:value forKey:resolved];
}

- (NSDictionary *)JSONDictionary
{
    id dict = [[self dictionaryWithValuesForKeys:QTestCaseServerExecutionDetail.properties.allValues] mutableCopy];

    // Rewrite property names that differ in JSON
    for (id jsonName in QTestCaseServerExecutionDetail.properties) {
        id propertyName = QTestCaseServerExecutionDetail.properties[jsonName];
        if (![jsonName isEqualToString:propertyName]) {
            dict[jsonName] = dict[propertyName];
            [dict removeObjectForKey:propertyName];
        }
    }

    // Map values that need translation
    [dict addEntriesFromDictionary:@{
        @"testjobexecutions": map(_testjobexecutions, λ(id x, [x JSONDictionary])),
    }];

    return dict;
}
@end

@implementation QTestCaseTestjobexecution
+ (NSDictionary<NSString *, NSString *> *)properties
{
    static NSDictionary<NSString *, NSString *> *properties;
    return properties = properties ? properties : @{
        @"testjob_id": @"testjobID",
        @"test_job_description": @"testJobDescription",
        @"created_time": @"createdTime",
        @"updated_time": @"updatedTime",
        @"status": @"status",
        @"server_id": @"serverID",
        @"lib_id": @"libID",
        @"auto_create_on_new_device": @"autoCreateOnNewDevice",
        @"test_run_id": @"testRunID",
        @"executions": @"executions",
    };
}

+ (instancetype)fromJSONDictionary:(NSDictionary *)dict
{
    return dict ? [[QTestCaseTestjobexecution alloc] initWithJSONDictionary:dict] : nil;
}

- (instancetype)initWithJSONDictionary:(NSDictionary *)dict
{
    if (self = [super init]) {
        [self setValuesForKeysWithDictionary:dict];
        _executions = map(_executions, λ(id x, [QTestCaseExecution fromJSONDictionary:x]));
    }
    return self;
}

- (void)setValue:(nullable id)value forKey:(NSString *)key
{
    id resolved = QTestCaseTestjobexecution.properties[key];
    if (resolved) [super setValue:value forKey:resolved];
}

- (NSDictionary *)JSONDictionary
{
    id dict = [[self dictionaryWithValuesForKeys:QTestCaseTestjobexecution.properties.allValues] mutableCopy];

    // Rewrite property names that differ in JSON
    for (id jsonName in QTestCaseTestjobexecution.properties) {
        id propertyName = QTestCaseTestjobexecution.properties[jsonName];
        if (![jsonName isEqualToString:propertyName]) {
            dict[jsonName] = dict[propertyName];
            [dict removeObjectForKey:propertyName];
        }
    }

    // Map values that need translation
    [dict addEntriesFromDictionary:@{
        @"executions": map(_executions, λ(id x, [x JSONDictionary])),
    }];

    return dict;
}
@end

@implementation QTestCaseExecution
+ (NSDictionary<NSString *, NSString *> *)properties
{
    static NSDictionary<NSString *, NSString *> *properties;
    return properties = properties ? properties : @{
        @"execution_id": @"executionID",
        @"testrun_id": @"testrunID",
        @"testcase_id": @"testcaseID",
        @"feature_id": @"featureID",
        @"feature_name": @"featureName",
        @"testcase_name": @"testcaseName",
        @"testcase_desc": @"testcaseDesc",
        @"device_id": @"deviceID",
        @"testjob_id": @"testjobID",
        @"error_code":@"errorCode",
        @"error_message":@"errorMessage"
    };
}

+ (instancetype)fromJSONDictionary:(NSDictionary *)dict
{
    return dict ? [[QTestCaseExecution alloc] initWithJSONDictionary:dict] : nil;
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
    id resolved = QTestCaseExecution.properties[key];
    if (resolved) [super setValue:value forKey:resolved];
}

- (NSDictionary *)JSONDictionary
{
    id dict = [[self dictionaryWithValuesForKeys:QTestCaseExecution.properties.allValues] mutableCopy];

    // Rewrite property names that differ in JSON
    for (id jsonName in QTestCaseExecution.properties) {
        id propertyName = QTestCaseExecution.properties[jsonName];
        if (![jsonName isEqualToString:propertyName]) {
            dict[jsonName] = dict[propertyName];
            [dict removeObjectForKey:propertyName];
        }
    }

    return dict;
}

/* - (NSString *)featureStringForValue:(enum FeatureNameTestCases)featureValue {
    NSString *datatypeStr;
    
    switch (featureValue) {
        case VERIFY_API:
            datatypeStr = kverify_api;
            break;
            
        case Initialization_on_Mobile:
            datatypeStr = kinitialization_on_mobile;
            break;
            
        case SERVICE_INFO:
            datatypeStr = kservice_info;
            break;
            
        case SERVICE_ACCESS:
            datatypeStr = kservice_access;
            break;
            
        case DATA_PRIVACY:
            datatypeStr = kdata_privacy;
            break;
            
            
        default:
            datatypeStr = @"";
    }
    
    return datatypeStr;
}*/


@end

NS_ASSUME_NONNULL_END
