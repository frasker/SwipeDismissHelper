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
        new SwipeDismissHelper(this).enable();

        // 支持自己重写滑动效果，默认是android 自带效果，滑动 + 透明度
//        final View content = getWindow().getDecorView();
//        new SwipeDismissHelper(this, new SwipeDismissLayout.OnSwipeProgressChangedListener() {
//            @Override
//            public void onSwipeProgressChanged(float translate) {
//                content.setX(translate);
//                content.setAlpha(progressToAlpha(translate / content.getWidth()));
//            }
//
//            @Override
//            public void onSwipeCancelled() {
//                content.setX(0);
//                content.setAlpha(1.0f);
//            }
//        }).enable();
    }


    private float progressToAlpha(float progress) {
        return 1 - progress * progress * progress;
    }
}
