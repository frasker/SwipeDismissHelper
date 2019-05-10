package com.frasker.swipedismiss;

import android.app.Activity;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * author: created by lvmo on 2019/3/13
 * email: lvmo@baidu.com
 */
public class SwipeDismissHelper {
    private static final String TAG = "SwipeDismissHelper";
    private Activity mActivity;
    private SwipeDismissLayout swipeDismissLayout;
    private SwipeDismissLayout.OnSwipeProgressChangedListener mListener;
    private boolean enabled = false;
    private static final int[] LAYOUT_ATTRS = new int[]{
            android.R.attr.windowIsTranslucent
    };

    public SwipeDismissHelper(@NonNull Activity activity) {
        this.mActivity = activity;
    }

    public SwipeDismissHelper(@NonNull Activity activity, SwipeDismissLayout.OnSwipeProgressChangedListener listener) {
        this.mActivity = activity;
        this.mListener = listener;
    }

    public void enable() {
        if (!enabled) {
            enabled = true;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                initSwipeLayout();
            } else {
                Log.e(TAG, "SwipeDismissHelper only support api19 and after");
            }
        }
    }

    public void setDismissable(boolean dismissable){
        swipeDismissLayout.setDismissable(dismissable);
    }

    private void initSwipeLayout() {
        final TypedArray a = mActivity.getTheme().obtainStyledAttributes(LAYOUT_ATTRS);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            boolean windowIsTranslucent = a.getBoolean(0, false);
            a.recycle();
            if (!windowIsTranslucent) {
                Log.e(TAG, "you must set android:windowIsTranslucent = true to enable SwipeDismissHelper before api21");
                return;
            }
        }
        final ViewGroup windowContentView = (ViewGroup) mActivity.getWindow().findViewById(android.R.id.content);
        if (windowContentView != null) {
            final LayoutInflater inflater = LayoutInflater.from(mActivity);
            swipeDismissLayout = (SwipeDismissLayout) inflater.inflate(R.layout.swipe_dismiss_content, null);
            // There might be Views already added to the Window's content view so we need to
            // migrate them to our content view
            while (windowContentView.getChildCount() > 0) {
                final View child = windowContentView.getChildAt(0);
                windowContentView.removeViewAt(0);
                swipeDismissLayout.addView(child);
            }
            // Change our content FrameLayout to use the android.R.id.content id.
            // Useful for fragments.
            windowContentView.setId(View.NO_ID);
            swipeDismissLayout.setId(android.R.id.content);

            windowContentView.addView(swipeDismissLayout, 0);

            final View content = mActivity.getWindow().getDecorView();
            mActivity.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            content.setBackgroundColor(Color.TRANSPARENT);
            swipeDismissLayout.setOnDismissedListener(new SwipeDismissLayout.OnDismissedListener() {
                @Override
                public void onDismissed(SwipeDismissLayout layout) {
                    dispatchOnWindowDismissed(false /*finishTask*/, true /*suppressWindowTransition*/);
                }
            });

            if (mListener != null) {
                swipeDismissLayout.setOnSwipeProgressChangedListener(mListener);
            } else {
                swipeDismissLayout.setOnSwipeProgressChangedListener(
                        new SwipeDismissLayout.OnSwipeProgressChangedListener() {
                            @Override
                            public void onSwipeProgressChanged(float translate) {
                                content.setX(translate);
                                content.setAlpha(progressToAlpha(translate / content.getWidth()));
                            }

                            @Override
                            public void onSwipeCancelled() {
                                content.setX(0);
                                content.setAlpha(1.0f);
                            }
                        });
            }
        }


    }

    private float progressToAlpha(float progress) {
        return 1 - progress * progress * progress;
    }

    private void dispatchOnWindowDismissed(boolean finishTask, boolean suppressWindowTransition) {
        mActivity.finish();
        if (suppressWindowTransition) {
            mActivity.overridePendingTransition(0, 0);
        }
    }
}
