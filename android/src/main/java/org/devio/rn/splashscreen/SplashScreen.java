package org.devio.rn.splashscreen;

import android.animation.Animator;
import android.app.Activity;
import android.app.Dialog;
import android.os.Build;
import com.airbnb.lottie.LottieAnimationView;
import java.lang.ref.WeakReference;

/**
 * SplashScreen
 * 启动屏
 * from：http://www.devio.org
 * Author:CrazyCodeBoy
 * GitHub:https://github.com/crazycodeboy
 * Email:crazycodeboy@gmail.com
 */
public class SplashScreen {
  private static Dialog mSplashDialog;
  private static WeakReference<Activity> mActivity;
  private static Boolean isAnimationFinished = false;
  private static Boolean waiting = false;

  /**
   * 打开启动屏
   */
  public static void show(final Activity activity, final int themeResId, final int lottieId) {
    if (activity == null)
      return;
    mActivity = new WeakReference<Activity>(activity);
    activity.runOnUiThread(new Runnable() {
      @Override
      public void run() {
        if (!activity.isFinishing()) {
          mSplashDialog = new Dialog(activity, themeResId);
          mSplashDialog.setContentView(R.layout.launch_screen);
          mSplashDialog.setCancelable(false);
          LottieAnimationView lottie = (LottieAnimationView) mSplashDialog.findViewById(lottieId);

          lottie.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
              System.out.println("asdf");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
              SplashScreen.setAnimationFinished(true);
            }

            @Override
            public void onAnimationCancel(Animator animation) {}

            @Override
            public void onAnimationRepeat(Animator animation) {}
          });

          if (!mSplashDialog.isShowing()) {
            mSplashDialog.show();
          }
        }
      }
    });
  }

  public static void setAnimationFinished(boolean flag) {
    if (mActivity == null) {
      return;
    }

    isAnimationFinished = flag;

    final Activity _activity = mActivity.get();

    _activity.runOnUiThread(new Runnable() {
      @Override
      public void run() {
        if (mSplashDialog != null && mSplashDialog.isShowing()) {
          boolean isDestroyed = false;

          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            isDestroyed = _activity.isDestroyed();
          }

          if (!_activity.isFinishing() && !isDestroyed && waiting) {
            mSplashDialog.dismiss();
            mSplashDialog = null;
          }
        }
      }
    });
  }

  public static void show(final Activity activity, int lottieId) {
    int resourceId = R.style.SplashScreen_SplashTheme;
    show(activity, resourceId, lottieId);
  }

  /**
   * 关闭启动屏
   */
  public static void hide(Activity activity) {
    if (activity == null) {
      if (mActivity == null) {
        return;
      }
      activity = mActivity.get();
    }

    if (activity == null)
      return;

    waiting = true;

    final Activity _activity = activity;

    _activity.runOnUiThread(new Runnable() {
      @Override
      public void run() {
        if (mSplashDialog != null && mSplashDialog.isShowing()) {
          boolean isDestroyed = false;

          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            isDestroyed = _activity.isDestroyed();
          }

          if (!_activity.isFinishing() && !isDestroyed && isAnimationFinished) {
            mSplashDialog.dismiss();
            mSplashDialog = null;
          }
        }
      }
    });
  }
}
