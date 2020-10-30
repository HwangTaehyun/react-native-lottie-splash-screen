//  Dynamic.swift
//  Created by Taehyun Hwang on 2020/10/29.

import UIKit
import Foundation
import Lottie

@objc class Dynamic: NSObject {

  @objc func createAnimationView(rootView: UIView, lottieName: String) -> AnimationView {
    let animationView = AnimationView(name: lottieName)
    animationView.frame = rootView.frame // 애니메이션뷰의 크기 설정
    animationView.center = rootView.center
    animationView.backgroundColor = UIColor.red;
    return animationView;
  }

  @objc func play(animationView: AnimationView) {
    animationView.play();
  }
}

