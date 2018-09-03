//
//  RDNAInitialize.h
//  iosAutomation
//
//  Created by cps on 31/07/18.
//  Copyright © 2018 cps. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "RDNA.h"
@interface RDNAInitialize : NSObject
{
    id<RDNACallbacks> clientCallbacks; 
}
@property (nonatomic, assign) int port;
@property (nonatomic, strong) NSString *host;
@property (nonatomic, strong) NSString *agentInfo;
+ (instancetype)sharedInitialize;
- (int) initializeRDNA :(int)port andHost:(NSString *)host andAgentInfo:(NSString *)agentInfo;

@end
