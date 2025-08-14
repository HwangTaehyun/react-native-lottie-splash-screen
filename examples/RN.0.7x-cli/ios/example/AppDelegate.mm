#import "AppDelegate.h"

#import <React/RCTBundleURLProvider.h>

#import "RNSplashScreen.h" // Copy This line to your project
#import "example-Swift.h" // Replace [your-project-name] with your project name

@implementation AppDelegate

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
  self.moduleName = @"example";
  // You can add your custom initial props in the dictionary below.
  // They will be passed down to the ViewController used by React Native.
  self.initialProps = @{};

  BOOL success = [super application:application didFinishLaunchingWithOptions:launchOptions];
  
  if (success) {
    //This is where we will put the logic to get access to rootview
    UIView *rootView = self.window.rootViewController.view;
    
    rootView.backgroundColor = [UIColor whiteColor]; // change with your desired backgroundColor

    Dynamic *t = [Dynamic new];
    UIView *animationUIView = (UIView *)[t createAnimationViewWithRootView:rootView lottieName:@"loading"]; // change lottieName to your lottie files name
    animationUIView.layer.zPosition = 1;

    // register LottieSplashScreen to RNSplashScreen
    [RNSplashScreen showLottieSplash:animationUIView inRootView:rootView];
    // casting UIView type to AnimationView type
    LottieAnimationView *animationView = (LottieAnimationView *) animationUIView;
    // play
    [t playWithAnimationView:animationView];
    // Make the splash screen wait for the Lottie animation to finish
    // To skip waiting for the Lottie animation to finish set it to true
    [RNSplashScreen setAnimationFinished:false];
  }
  
  return success;
}

- (NSURL *)sourceURLForBridge:(RCTBridge *)bridge
{
  return [self bundleURL];
}

- (NSURL *)bundleURL
{
#if DEBUG
  return [[RCTBundleURLProvider sharedSettings] jsBundleURLForBundleRoot:@"index"];
#else
  return [[NSBundle mainBundle] URLForResource:@"main" withExtension:@"jsbundle"];
#endif
}

@end
