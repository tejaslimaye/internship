
#import "WPHTTPRequestOperationManager.h"
//#import "WPWISEStore.h"


@interface WPHTTPRequestOperationManager ()

// Master switch to enable/disable HTTP operations (e.g. if the application's end-of-life has been detected)
@property (nonatomic) BOOL networkingEnabled;

@end


@implementation WPHTTPRequestOperationManager

- (instancetype)initWithBaseURL:(NSURL *)url
{
    self = [super initWithBaseURL:url];

    self.networkingEnabled = true;

    return self;
}

- (AFHTTPRequestOperation *)HTTPRequestOperationWithRequest:(NSURLRequest *)request
                                                    success:(void (^)(AFHTTPRequestOperation *operation, id responseObject))success
                                                    failure:(void (^)(AFHTTPRequestOperation *operation, NSError *error))failure
{
    // By default, the operation we'll return is nil.
    // If networkingEnabled is false, we won't set it
    // and no network traffic will occur.
    AFHTTPRequestOperation *operation = nil;

    // Wrap the client's success callback
    void (^successWrapper)(AFHTTPRequestOperation *operation, id responseObject);
    successWrapper = ^(AFHTTPRequestOperation *operation, id responseObject)
    {
        // Handle WISE response headers
        //[[WPWISEStore sharedStore] handleWISEResponseHeaders:operation];
        
        if (success) {
            success(operation, responseObject);
        }
       
     /*   NSError *error;
        NSData *jsonData = [NSJSONSerialization dataWithJSONObject:responseObject
                                                           options:NSJSONWritingPrettyPrinted // Pass 0 if you don't care about the readability of the generated string
                                                             error:&error];
        
        if (! jsonData) {
            NSLog(@"Got an error: %@", error);
        } else {
            NSString *jsonString = [[NSString alloc] initWithData:jsonData encoding:NSUTF8StringEncoding];
            NSLog(@"jsonString === %@", jsonString);
        }*/
       
    };

    // Wrap the client's failure callback
    void (^failureWrapper)(AFHTTPRequestOperation *operation, id responseObject);
    failureWrapper = ^(AFHTTPRequestOperation *operation, id responseObject)
    {
        // Handle WISE response headers
      //  [[WPWISEStore sharedStore] handleWISEResponseHeaders:operation];

        if (failure) {
            failure(operation, responseObject);
        }
    };

    // If networking is currently enabled, ...
    if (self.networkingEnabled) {
        // Call the superclass' method with the wrapped success/failure callbacks
        // to handle the WISE response headers.
        operation = [super HTTPRequestOperationWithRequest:request
                                                   success:successWrapper
                                                   failure:failureWrapper];
        
        
    } else {
        NSLog(@"HTTP REQUEST BLOCKED:\n%@", request);
    }

    return operation;
}

- (void)isNetworkingEnabled:(BOOL)isEnabled
{
    self.networkingEnabled = isEnabled;
}

- (AFHTTPRequestOperation *)PUT_JSON:(NSString *)URLString
                          parameters:(id)parameters
                      setAccessToken:(NSString *)accessToken
                             success:(void (^)(AFHTTPRequestOperation *operation, id responseObject))success
                             failure:(void (^)(AFHTTPRequestOperation *operation, NSError *error))failure
{
    AFHTTPRequestOperation *operation = NULL;
    NSError *error;
    NSData *jsonData = [NSJSONSerialization dataWithJSONObject:parameters
                                                       options:0
                                                         error:&error];
    
    if (!jsonData) {
        NSLog(@"Got an error while converting: %@", error);
        if (failure) {
            failure(operation, error);
        }
    } else {
        NSString *jsonStr = [[NSString alloc] initWithData:jsonData encoding:NSUTF8StringEncoding];
        
        // Create a custom HTTP request, based on our existing HTTP manager's base URL
        NSURL *baseURL = [self baseURL];
        NSURL *fullURL = [baseURL URLByAppendingPathComponent:URLString];
        NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL:fullURL];
        
        // Set post method
        [request setHTTPMethod:@"PUT"];
        
        // Set header to accept JSON request
        [request setValue:@"application/json" forHTTPHeaderField:@"Content-Type"];
        
       /* if (accessToken) {
            [request setValue:[NSString stringWithFormat:@"Bearer %@",accessToken] forHTTPHeaderField:@"Authorization"];
            [request setValue:@"Whirlpool" forHTTPHeaderField:@"brand"];
            [request setValue:@"Whirlpool" forHTTPHeaderField:@"App_Name"];
            [request setValue:@"12345" forHTTPHeaderField:@"Device_ID"];
            [request setValue:@"Appliances" forHTTPHeaderField:@"Platform"];
        }*/
        // Set the JSON-serialized data as the HTTP body
        [request setHTTPBody:[jsonStr dataUsingEncoding:NSUTF8StringEncoding]];
        
        // Get an operation for the request
        operation = [self HTTPRequestOperationWithRequest:request
                                                  success:success
                                                  failure:failure];
        
        // Start the operation
        [self.operationQueue addOperation:operation];
    }
    
    return operation;
}


- (AFHTTPRequestOperation *)POST_JSON:(NSString *)URLString
                           parameters:(id)parameters
                              success:(void (^)(AFHTTPRequestOperation *operation, id responseObject))success
                              failure:(void (^)(AFHTTPRequestOperation *operation, NSError *error))failure
{
    AFHTTPRequestOperation *operation = NULL;
    NSError *error;
    NSData *jsonData = [NSJSONSerialization dataWithJSONObject:parameters
                                                       options:0
                                                         error:&error];
    
    if (!jsonData) {
        NSLog(@"Got an error while converting: %@", error);
        if (failure) {
            failure(operation, error);
        }
    } else {
        NSString *jsonStr = [[NSString alloc] initWithData:jsonData encoding:NSUTF8StringEncoding];

        // Create a custom HTTP request, based on our existing HTTP manager's base URL
        NSURL *baseURL = [self baseURL];
        NSURL *fullURL = [baseURL URLByAppendingPathComponent:URLString];
       //NSURL *fullURL = [baseURL URLByAppendingPathComponent:@"appliances/140/Entities"];
        NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL:fullURL];
        
        // Set post method
        [request setHTTPMethod:@"POST"];
        
        // Set header to accept JSON request
        [request setValue:@"application/json" forHTTPHeaderField:@"Content-Type"];
        
        // Set the JSON-serialized data as the HTTP body
        [request setHTTPBody:[jsonStr dataUsingEncoding:NSUTF8StringEncoding]];
        
        // Get an operation for the request
        operation = [self HTTPRequestOperationWithRequest:request
                                                  success:success
                                                  failure:failure];
        
        // Start the operation
        [self.operationQueue addOperation:operation];
    }
    
    return operation;
}

@end
