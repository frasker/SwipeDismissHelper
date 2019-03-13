/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 */

package com.frasker.swipedismiss.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.frasker.swipedismiss.SwipeDismissHelper;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        new SwipeDismissHelper(this);
    }
}
