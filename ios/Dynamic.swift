//  Dynamic.swift
//  Created by Taehyun Hwang on 2020/10/29.

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

