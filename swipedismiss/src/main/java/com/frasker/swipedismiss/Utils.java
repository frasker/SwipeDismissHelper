/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 */

package com.frasker.swipedismiss;

import android.app.Activity;
import android.app.ActivityOptions;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Utils {
    private Utils() {
    }

    /**
     * Convert a translucent themed Activity
     * {@link android.R.attr#windowIsTranslucent} to a fullscreen opaque
     * Activity.
     * <p>
     * Call this whenever the background of a translucent Activity has changed
     * to become opaque. Doing so will allow the {@link android.view.Surface} of
     * the Activity behind to be released.
     * <p>
     * This call has no effect on non-translucent activities or on activities
     * with the {@link android.R.attr#windowIsFloating} attribute.
     */
    public static void convertActivityFromTranslucent(Activity activity) {
        try {
            Method method = Activity.class.getDeclaredMethod("convertFromTranslucent");
            method.setAccessible(true);
            method.invoke(activity);
        } catch (Throwable t) {
        }
    }

    /**
     * Convert a translucent themed Activity
     * {@link android.R.attr#windowIsTranslucent} back from opaque to
     * translucent following a call to
     * {@link #convertActivityFromTranslucent(Activity)} .
     * <p>
     * Calling this allows the Activity behind this one to be seen again. Once
     * all such Activities have been redrawn
     * <p>
     * This call has no effect on non-translucent activities or on activities
     * with the {@link android.R.attr#windowIsFloating} attribute.
     */
    public static boolean convertActivityToTranslucent(Activity activity) {
        boolean success = false;
        try {
            Class<?>[] classes = Activity.class.getDeclaredClasses();
            Class<?> translucentConversionListenerClazz = null;
            for (Class clazz : classes) {
                if (clazz.getSimpleName().contains("TranslucentConversionListener")) {
                    translucentConversionListenerClazz = clazz;
                }
            }
            Method method = Activity.class.getDeclaredMethod("convertToTranslucent",
                    translucentConversionListenerClazz);
            method.setAccessible(true);
            method.invoke(activity, new Object[]{
                    null
            });
            success = true;
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return success;
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static void convertActivityToTranslucent(Activity activity, PageTranslucentListener listener) {
        try {
            Method getActivityOptions = Activity.class.getDeclaredMethod("getActivityOptions");
            getActivityOptions.setAccessible(true);
            Object options = getActivityOptions.invoke(activity);

            Class<?>[] classes = Activity.class.getDeclaredClasses();
            Class<?> translucentConversionListenerClazz = null;
            for (Class clazz : classes) {
                if (clazz.getSimpleName().contains("TranslucentConversionListener")) {
                    translucentConversionListenerClazz = clazz;
                }
            }


            SwipeInvocationHandler myInvocationHandler = new SwipeInvocationHandler(new WeakReference<PageTranslucentListener>(listener));
            Object obj = Proxy.newProxyInstance(Activity.class.getClassLoader(), new Class[]{translucentConversionListenerClazz}, myInvocationHandler);

            Method convertToTranslucent = Activity.class.getDeclaredMethod("convertToTranslucent",
                    translucentConversionListenerClazz, ActivityOptions.class);
            convertToTranslucent.setAccessible(true);
            Log.d("SwipeInvocationHandler", "start time: " + System.currentTimeMillis());
            convertToTranslucent.invoke(activity, obj, options);
        } catch (Throwable t) {
        }
    }

    public interface PageTranslucentListener {
        void onPageTranslucent();
    }

    static class SwipeInvocationHandler implements InvocationHandler {
        private static final String TAG = "SwipeInvocationHandler";
        private WeakReference<PageTranslucentListener> listener;

        public SwipeInvocationHandler(WeakReference<PageTranslucentListener> listener) {
            this.listener = listener;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Log.d(TAG, "invoke: end time: " + System.currentTimeMillis());
            Log.d(TAG, "invoke: 被回调了");
            try {
                boolean success = (boolean) args[0];
                if (success && listener.get() != null) {
                    listener.get().onPageTranslucent();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}