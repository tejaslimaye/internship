//
//  RDNAInitialize.m
//  iosAutomation
//
//  Created by cps on 31/07/18.
//  Copyright © 2018 cps. All rights reserved.
//

#import "RDNAInitialize.h"

@implementation RDNAInitialize

+ (instancetype)sharedInitialize
{
    static RDNAInitialize *sharedInitialize = nil;
    static dispatch_once_t Initialize;
    dispatch_once(&Initialize, ^{
        sharedInitialize = [[RDNAInitialize alloc] init];
    });
    return sharedInitialize;
}


- (int) initializeRDNA :(int)port andHost:(NSString *)host andAgentInfo:(NSString *)agentInfo {
    __block int errorID = 0;
    dispatch_async(dispatch_get_global_queue( DISPATCH_QUEUE_PRIORITY_DEFAULT, 0), ^(void){
        RDNA *rdna;
        errorID = [RDNA initialize:&rdna AgentInfo:agentInfo Callbacks:self->clientCallbacks GatewayHost:host GatewayPort:port CipherSpec:nil CipherSalt:nil ProxySettings:nil RDNASSLCertificate:NULL DNSServerList:nil RDNALoggingLevel:RDNA_NO_LOGS AppContext:self];
        //Background Thread
    });
    return errorID;
}




@end
