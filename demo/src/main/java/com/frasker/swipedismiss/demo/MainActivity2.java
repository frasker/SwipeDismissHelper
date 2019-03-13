/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 */

package com.frasker.swipedismiss.demo;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.frasker.swipedismiss.SwipeDismissHelper;
import com.frasker.swipedismiss.SwipeDismissLayout;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        new SwipeDismissHelper(this);

        // 支持自己重写滑动效果，默认是android 自带效果，滑动 + 透明度
//        final View content = getWindow().getDecorView();
//        final Drawable background = content.getBackground();
//        new SwipeDismissHelper(this, new SwipeDismissLayout.OnSwipeProgressChangedListener() {
//            @Override
//            public void onSwipeProgressChanged(float translate) {
//                int scrollX = (int) translate;
//                content.scrollTo(-scrollX, 0);
//                if (scrollX == 0) {
//                    content.setBackgroundDrawable(background);
//                } else {
//                    content.setBackgroundColor(Color.TRANSPARENT);
//                }
//                content.setAlpha(progressToAlpha(translate / content.getWidth()));
//            }
//
//            @Override
//            public void onSwipeCancelled() {
//                content.scrollTo(0, 0);
//                content.setBackgroundDrawable(background);
//                content.setAlpha(1.0f);
//            }
//        });
    }


    private float progressToAlpha(float progress) {
        return 1 - progress * progress * progress;
    }
}
