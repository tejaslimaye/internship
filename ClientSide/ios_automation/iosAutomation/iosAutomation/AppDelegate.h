//
//  AppDelegate.h
//  iosAutomation
//
//  Created by cps on 26/07/18.
//  Copyright © 2018 cps. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "RDNAClient.h"
@interface AppDelegate : UIResponder <UIApplicationDelegate>

@property (strong, nonatomic) UIWindow *window;
@property (strong, nonatomic) NSString* deviceToken;
@property(nonatomic,strong)RDNAClient *rdnaclient;
@end

