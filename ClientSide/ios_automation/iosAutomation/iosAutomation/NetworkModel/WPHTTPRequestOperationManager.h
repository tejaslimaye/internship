

#import "AFHTTPRequestOperationManager.h"

@interface WPHTTPRequestOperationManager : AFHTTPRequestOperationManager

/**
 Overloaded initializer for our networkingEnabled flag (default: true)
 */
- (instancetype)initWithBaseURL:(NSURL *)url;

/**
 Overloaded operation factory for wrapping client callbacks and applying
 the networkingEnabled switch to allow/prevent network traffic.
 */
- (AFHTTPRequestOperation *)HTTPRequestOperationWithRequest:(NSURLRequest *)request
                                                    success:(void (^)(AFHTTPRequestOperation *operation, id responseObject))success
                                                    failure:(void (^)(AFHTTPRequestOperation *operation, NSError *error))failure;

/**
 API for enabling/disabling subsequent network traffic.
 */
- (void)isNetworkingEnabled:(BOOL)isEnabled;

/**
 Alternate POST operation providing true JSON serialization for our
 WPHTTPRequestOperationManager subclass, when it's equipped with
 WPHTTPRequestSerializer (an AFHTTPRequestSerializer subclass).  Changing
 WPHTTPRequestOperationManager to use an AFJSONRequestSerializer subclass
 might break other existing code that POSTs to WISE with parameters (such
 as createAppliance() or genericAuthentication()); instead, we provide this
 API and use it where needed (e.g. setKVPDataNoNulls()).
 */
- (AFHTTPRequestOperation *)POST_JSON:(NSString *)URLString
                           parameters:(id)parameters
                              success:(void (^)(AFHTTPRequestOperation *operation, id responseObject))success
                              failure:(void (^)(AFHTTPRequestOperation *operation, NSError *error))failure;

- (AFHTTPRequestOperation *)PUT_JSON:(NSString *)URLString
                          parameters:(id)parameters
                      setAccessToken:(NSString *)accessToken
                             success:(void (^)(AFHTTPRequestOperation *operation, id responseObject))success
                             failure:(void (^)(AFHTTPRequestOperation *operation, NSError *error))failure;
@end
