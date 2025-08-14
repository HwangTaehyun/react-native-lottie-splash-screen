# react-native-lottie-splash-screen

[![Download](https://img.shields.io/badge/Download-latest-ff69b4.svg) ](https://www.npmjs.com/package/react-native-lottie-splash-screen)
[ ![PRs Welcome](https://img.shields.io/badge/PRs-Welcome-brightgreen.svg)](https://github.com/HwangTaehyun/react-native-lottie-splash-screen/pulls)
[ ![react-native-lottie-splash-screen release](https://img.shields.io/github/release/HwangTaehyun/react-native-lottie-splash-screen.svg?maxAge=2592000?style=flat-square)](https://github.com/crazycodeboy/GitHubPopular/releases)
[![License MIT](http://img.shields.io/badge/license-MIT-orange.svg?style=flat)](https://raw.githubusercontent.com/crazycodeboy/react-native-check-box/master/LICENSE)

Fork of [react-native-splash-screen](https://github.com/crazycodeboy/react-native-splash-screen) and add implement for animation splash screen using airbnb lottie files.

Works on IOS and Android.

## Content

- [Examples](#examples)
- [Installation](#installation)
- [Getting started](#getting-started)
- [API](#api)
- [Contribution](#contribution)

## Examples

You can run [examples](https://github.com/HwangTaehyun/react-native-lottie-splash-screen/tree/master/examples) in this project

![react-native-lottie-splash-screen-Android](screenshot/Lottie-Splash-Screen-Android.gif)
![react-native-lottie-splash-screen-iOS](screenshot/Lottie-Splash-Screen-IOS.gif)

## Installation

### Versions compatibilities
| React Native | react-native-lottie-splash-screen |
|---|---|
| >= 0.7x | 2.x |
| < 0.70 | 1.x |
#### Warning: Version 2.x has no backward compatibility. You need to follow the new setup instructions.

## React-Native >= 0.70
### First step (Download):
Run `yarn add lottie-react-native@6.7.2 react-native-lottie-splash-screen`

### Second step (Plugin Installation):

The package is [automatically linked](https://github.com/react-native-community/cli/blob/master/docs/autolinking.md) when building the app. All you need to do is:

```bash
cd ios && pod install
```

For android, the package will be linked automatically on build.

### Third step (Adding Splash Screen To Native Code):

**Android:**

1. Update the `MainActivity.kt` file to use `react-native-lottie-splash-screen` via the following changes:

```kotlin
// React-Native >= 0.70
// MainActivity.kt file contents
package com.examples

import com.facebook.react.ReactActivity
import com.facebook.react.ReactActivityDelegate
import com.facebook.react.defaults.DefaultNewArchitectureEntryPoint.fabricEnabled
import com.facebook.react.defaults.DefaultReactActivityDelegate

import org.devio.rn.splashscreen.SplashScreen // Add this Line to your project
import android.os.Bundle

class MainActivity : ReactActivity() {

  /* Add this function to your project */
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    SplashScreen.show(this, R.style.SplashScreen_SplashTheme, R.id.lottie)
    // Skip waiting for the Lottie animation to finish
    // To make the splash screen wait for the Lottie animation to finish set it to false
    SplashScreen.setAnimationFinished(true)
  }
  /* End */


  override fun getMainComponentName(): String = "examples"

  override fun createReactActivityDelegate(): ReactActivityDelegate =
      DefaultReactActivityDelegate(this, mainComponentName, fabricEnabled)

}

```

2. Update `styles.xml` file in `app/src/main/res/values` and add these styles:

```xml
<resources>
    <style name="AppTheme" parent="Theme.AppCompat.DayNight.NoActionBar">
        <item name="android:editTextBackground">@drawable/rn_edit_text_material</item>
    </style>
    <!-- Copy these lines to you project. -->
    <style name="SplashScreen_SplashAnimation">
        <item name="android:windowExitAnimation">@android:anim/fade_out</item>
    </style>

    <style name="SplashScreen_SplashTheme" parent="Theme.AppCompat.NoActionBar">
        <item name="android:windowAnimationStyle">@style/SplashScreen_SplashAnimation</item>
    </style>
    <!-- End of copy -->
</resources>

```

**iOS:**

1. In Xcode open the `ios` folder > in Project Explorer> right click on your project name > `New File...` menu > select `Swift File` > Next > Type `Dynamic.swift` as the file name and select your project in `Targets` > Create.
Copy these codes into `Dynamic.swift` file:

```swift
// React-Native >= 0.70
// Dynamic.swift file content
import UIKit
import Foundation
import Lottie

@objc class Dynamic: NSObject {

  @objc func createAnimationView(rootView: UIView, lottieName: String) -> LottieAnimationView {
    let animationView = LottieAnimationView(name: lottieName)
    animationView.frame = rootView.frame
    animationView.center = rootView.center
    animationView.backgroundColor = UIColor.white;
    return animationView;
  }

  @objc func play(animationView: LottieAnimationView) {
    animationView.play(
      completion: { (success) in
        RNSplashScreen.setAnimationFinished(true)
      }
    );
  }
}


```

2. Xcode asks you to create the bridge header. Accept it. The content of `[your-project-name]-Bridging-Header.h` would be like this:

```objc
// React-Native >= 0.70
// [your-project-name]-Bridging-Header.h file content


//  [your-project-name]-Bridging-Header.h

#ifndef [your-project-name]_Bridging_Header_h
#define [your-project-name]_Bridging_Header_h

#import "RNSplashScreen.h" // here

#endif /* [your-project-name]_Bridging_Header_h */


```

3. Update `AppDelegate.mm` with the following additions: (for react-native@0.71 proceed to 4.1)

```obj-c
// React-Native >= 0.70
// AppDelegate.mm file content

#import "AppDelegate.h"
#import <React/RCTBundleURLProvider.h>

#import "RNSplashScreen.h" // Copy This line to your project
#import "[your-project-name]-Swift.h" // Replace [your-project-name] with your project name

@implementation AppDelegate

// REPLACE application METHOD FROM HERE

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
  [FIRApp configure];
  self.moduleName = @"[your-project-name]"; // Replace [your-project-name] with your project name
  self.initialProps = @{};
  // return [super application:application didFinishLaunchingWithOptions:launchOptions]; //This will be assigned as success instead
 
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
    // Skip waiting for the Lottie animation to finish
    // To make the splash screen wait for the Lottie animation to finish set it to false
    [RNSplashScreen setAnimationFinished:true];
  }
 
  return success;
}
// END OF CHANGES

- (NSURL *)sourceURLForBridge:(RCTBridge *)bridge
{
...
```

<details>
 <summary>For React Native version 0.69 or older</summary>

### First step(Download):

Run `yarn add lottie-ios@3.2.3 react-native-lottie-splash-screen@1.1.2`

### Second step(Plugin Installation):

### React Native v0.60+

The package is [automatically linked](https://github.com/react-native-community/cli/blob/master/docs/autolinking.md) when building the app. All you need to do is:

```bash
cd ios && pod install
```

For android, the package will be linked automatically on build.

<details>
 <summary>For React Native version 0.59 or older</summary>

### React Native <= v0.59

```bash
// React-Native <= 0.59

react-native link react-native-lottie-splash-screen
```

If you don't want to use the methods above, you can always do Manual installation.

</details>

### Manual installation

**Android (React-Native < 0.70):**

1. In your `android/settings.gradle` file, make the following additions:

```java
// React-Native < 0.70

include ':react-native-lottie-splash-screen'
project(':react-native-lottie-splash-screen').projectDir = new File(rootProject.projectDir, '../node_modules/react-native-lottie-splash-screen/android')
```

2. In your android/app/build.gradle file, add the `:react-native-lottie-splash-screen` project as a compile-time dependency:

```java
// React-Native < 0.70

...
dependencies {
    ...
    implementation project(':react-native-lottie-splash-screen')
}
```

3. Update the MainApplication.java file to use `react-native-lottie-splash-screen` via the following changes:

```java
// React-Native < 0.70

// react-native-lottie-splash-screen >= 0.3.1
import org.devio.rn.splashscreen.SplashScreenReactPackage;
// react-native-lottie-splash-screen < 0.3.1
import com.cboy.rn.splashscreen.SplashScreenReactPackage;

public class MainApplication extends Application implements ReactApplication {

    private final ReactNativeHost mReactNativeHost = new ReactNativeHost(this) {
        @Override
        public boolean getUseDeveloperSupport() {
            return BuildConfig.DEBUG;
        }

        @Override
        protected List<ReactPackage> getPackages() {
            return Arrays.<ReactPackage>asList(
                    new MainReactPackage(),
            new SplashScreenReactPackage()  //here
            );
        }
    };

    @Override
    public ReactNativeHost getReactNativeHost() {
        return mReactNativeHost;
    }
}
```

**iOS: (React-Native < 0.70)**

1. `cd ios`
2. `run pod install`

> OR

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-lottie-splash-screen` and add `SplashScreen.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libSplashScreen.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. To fix `'RNSplashScreen.h' file not found`, you have to select your project → Build Settings → Search Paths → Header Search Paths to add:

   `$(SRCROOT)/../node_modules/react-native-lottie-splash-screen/ios`

### Third step(Plugin Configuration):

**Android:**

Update the `MainActivity.java` to use `react-native-lottie-splash-screen` via the following changes:

```java
// React-Native < 0.70
import android.os.Bundle;
import com.facebook.react.ReactActivity;
import org.devio.rn.splashscreen.SplashScreen; // here
import android.os.Bundle;

public class MainActivity extends ReactActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    SplashScreen.show(this, R.id.lottie); // here
    SplashScreen.setAnimationFinished(true); // If you want the animation dialog to be forced to close when hide is called, use this code
    super.onCreate(savedInstanceState);
    // ...other code
  }
}
```

**iOS:**

1. Create `Dynamic.swift` with the following contents:

```swift
// React-Native < 0.70
import UIKit
import Foundation
import Lottie

@objc class Dynamic: NSObject {

  @objc func createAnimationView(rootView: UIView, lottieName: String) -> AnimationView {
    let animationView = AnimationView(name: lottieName)
    animationView.frame = rootView.frame
    animationView.center = rootView.center
    animationView.backgroundColor = UIColor.white;
    return animationView;
  }

  @objc func play(animationView: AnimationView) {
    animationView.play(
      completion: { (success) in
        RNSplashScreen.setAnimationFinished(true)
      }
    );
  }
}
```

2. Create `[your-project-name]-Bridging-Header.h` with the following contents:

```objc
//  HyperMoney-Bridging-Header.h

#ifndef HyperMoney_Bridging_Header_h
#define HyperMoney_Bridging_Header_h

#import "RNSplashScreen.h" // here

#endif /* HyperMoney_Bridging_Header_h */

```

3. To use swift file in AppDelegate.m, follow next step.

[https://developer.apple.com/documentation/swift/imported_c_and_objective-c_apis/importing_swift_into_objective-c](https://developer.apple.com/documentation/swift/imported_c_and_objective-c_apis/importing_swift_into_objective-c)

> Import Swift code into Objective-C within the same framework:\
> \
> Under Build Settings, in Packaging, make sure the Defines Module setting for that framework target is set to Yes.\
> \
> Import the Swift code from that framework target into any Objective-C .m file within that target using this syntax and substituting the appropriate names:

4. Update `AppDelegate.mm` with the following additions: (for react-native@0.71 proceed to 4.1)

```obj-c
// React-Native < 0.70

#import "AppDelegate.h"

#import <React/RCTBridge.h>
#import <React/RCTBundleURLProvider.h>
#import <React/RCTRootView.h>
#import "RNSplashScreen.h" // here

#import "HyperMoney-Swift.h" // here, change project name to yours

@implementation AppDelegate

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{

  // ...other code

  /* here */
  UIViewController *rootViewController = [UIViewController new];

  rootViewController.view = rootView;

  self.window.rootViewController = rootViewController;
  [self.window makeKeyAndVisible];

  Dynamic *t = [Dynamic new];
  UIView *animationUIView = (UIView *)[t createAnimationViewWithRootView:rootView lottieName:@"loading"]; // change lottieName to your lottie files name
  animationUIView.backgroundColor = [UIColor whiteColor]; // change backgroundColor

  // register LottieSplashScreen to RNSplashScreen
  [RNSplashScreen showLottieSplash:animationUIView inRootView:rootView];

  // casting UIView type to AnimationView type
  AnimationView *animationView = (AnimationView *) animationUIView;

  // play
  [t playWithAnimationView:animationView];

  // If you want the animation layout to be forced to remove when hide is called, use this code
  [RNSplashScreen setAnimationFinished:true];

  /* here */

  return YES;
}
```
4.1  For React-Native version 0.71, in `AppDelegate.mm` rootView is no longer here. We need access to the rootView.

```
 - (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
  self.moduleName = @"YOUR_PROJECT_NAME";
  // You can add your custom initial props in the dictionary below.
  // They will be passed down to the ViewController used by React Native.
  self.initialProps = @{};
  // return [super application:application didFinishLaunchingWithOptions:launchOptions]; //This will be assigned as success instead
 
 BOOL success = [super application:application didFinishLaunchingWithOptions:launchOptions];
 
  if (success) {
    //This is where we will put the logic to get access to rootview
    UIView *rootView = self.window.rootViewController.view;
    
    rootView.backgroundColor = [UIColor whiteColor]; // change with your desired backgroundColor
 
    Dynamic *t = [Dynamic new];
    UIView *animationUIView = (UIView *)[t createAnimationViewWithRootView:rootView lottieName:@"logo_animated"]; // change lottieName to your lottie files name
 
    // register LottieSplashScreen to RNSplashScreen
    [RNSplashScreen showLottieSplash:animationUIView inRootView:rootView];
    // casting UIView type to AnimationView type
    AnimationView *animationView = (AnimationView *) animationUIView;
    // play
    [t playWithAnimationView:animationView];
    // If you want the animation layout to be forced to remove when hide is called, use this code
    [RNSplashScreen setAnimationFinished:true];
  }
 
  return success;
 
}
```
4.2 If your `AppDelegate.swift` is in `swift`:

```
  let rootViewController = UIViewController()
  rootViewController.view = rootView
  rootWindow.rootViewController = rootViewController
  rootWindow.makeKeyAndVisible()

  // start
  let t = Dynamic()
  let animationUIView: UIView = t.createAnimationView(rootView: rootView, lottieName:"Your_lottie_animation")

  RNSplashScreen.showLottieSplash(animationUIView, inRootView: rootView)
  
  animationUIView.frame = rootView.frame
  t.play(animationView: animationUIView as! AnimationView)
  RNSplashScreen.setAnimationFinished(true)
  // end

  return true
```

</details>

## Getting started

Import `react-native-lottie-splash-screen` in your JS file.

`import SplashScreen from 'react-native-lottie-splash-screen'`

### Android:

Create a file called `launch_screen.xml` in `app/src/main/res/layout` (create the `layout`-folder if it doesn't exist). Next, locate your lottie files in `app/src/main/res/raw` (loading.json in this example). The contents of the file should be the following:

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    tools:context=".MainActivity"
    android:orientation="vertical"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@color/white"
  >
    <com.airbnb.lottie.LottieAnimationView
        android:id="@+id/lottie"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:lottie_rawRes="@raw/loading"    <--  Your file name
        app:lottie_autoPlay="true"
        app:lottie_loop="false"
        />
</LinearLayout>
```

Customize your launch screen by creating a `launch_screen.png`-file and placing it in an appropriate `drawable`-folder. Android automatically scales drawable, so you do not necessarily need to provide images for all phone densities.
You can create splash screens in the following folders:

- `drawable-ldpi`
- `drawable-mdpi`
- `drawable-hdpi`
- `drawable-xhdpi`
- `drawable-xxhdpi`
- `drawable-xxxhdpi`

### iOS

Drag your lottie files to Xcode Project. Click Finish. That's all.

![](screenshot/2022-07-09-16-40-46.png)
![](screenshot/2022-07-09-16-41-45.png)

## Expo bare workflow!!

It's really annoying to resolve issues with expo bare workflow because I do not use this 🥲. But a lot of developers want to use this project to expo bare workflow. So, If you managed to this, please star and share this project! That's a really big energy to me! :rocket::rocket::rocket:

1. You should use XCode 14.2 because there's some build issue.

- https://github.com/expo/expo/issues/20777

2. If you had the following issue, you will resolve it with this [strategy](https://github.com/HwangTaehyun/react-native-lottie-splash-screen/discussions/64).

```
"Cannot find 'RNSplashScreen' in scope - Build iOS error" issue in Dynamic.swift #
```

3. You should insert the following code to AppDelegate.mm. Refer to this [issue](https://github.com/expo/expo/issues/17705)

```m
#import "RNSplashScreen.h" // here
#import "ExpoModulesCore-Swift.h" // here
#import "ExpoLSSTestApp-Swift.h" // here, change project name to yours
```

4. If you want to test in release mode, you
   should add this line to scripts in your package.json file. Once you run this script, you can build:release in Xcode!

```js
"build:ios": "react-native bundle --entry-file='index.js' --bundle-output='./ios/main.jsbundle' --dev=false --platform='ios'"
j
```

## Usage

Use like so:

When the app is finished loading, hide the LottieSplashScreen.

The contents of the App.js may be the following:

```js
import React, { useEffect } from "react";
import LottieSplashScreen from "react-native-lottie-splash-screen";
import RootNavigator from "@navi/RootNavigator";

const App = () => {
  useEffect(() => {
    // Hide Lottie splash screen immediately; works when setAnimationFinished is true
    LottieSplashScreen.hide(); // here
  }, []);
  return <RootNavigator />;
};

export default App;
```

## API

| Method | Type     | Optional | Description                |
| ------ | -------- | -------- | -------------------------- |
| hide() | function | false    | Close lottie splash screen |

## :sparkling_heart: Support the project

I open-source almost everything I can and try to reply to everyone needing help using these projects. Obviously,
this takes time. You can use this service for free.

However, if you are using this project and are happy with it or just want to encourage me to continue creating stuff, there are a few ways you can do it:

-   Starring and sharing the project :rocket:
-   You can make one-time donations via buymeacoffee. I'll probably buy a coffee! :coffee:

[!["Buy Me A Coffee"](https://www.buymeacoffee.com/assets/img/custom_images/orange_img.png)](https://www.buymeacoffee.com/eeht17173)

Thanks! :heart:

## Contribution

Issues are welcome. Please add a screenshot of you bug and a code snippet. Quickest way to solve issue is to reproduce it in one of the examples.

Pull requests are welcome. If you want to change the API or do something big it is best to create an issue and discuss it first.

---

**[MIT Licensed](https://github.com/HwangTaehyun/react-native-lottie-splash-screen/blob/master/LICENSE)**
