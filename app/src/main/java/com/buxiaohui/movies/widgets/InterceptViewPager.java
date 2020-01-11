package com.buxiaohui.movies.widgets;

import com.buxiaohui.movies.BuildConfig;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

/**
 * unused
 */
public class InterceptViewPager extends ViewPager {
    private static final String TAG = "InterceptViewPager";
    private static final boolean DEBUG = BuildConfig.DEBUG;
    private int mLastXIntercept;
    private int mLastYIntercept;
    private int mLastX;
    private int mLastY;

    public InterceptViewPager(@NonNull Context context) {
        super(context);
    }

    public InterceptViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        boolean intercepted = false;
        int x = (int) arg0.getX();
        int y = (int) arg0.getY();
        switch (arg0.getAction()) {
            case MotionEvent.ACTION_DOWN:
                intercepted = false;
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastXIntercept;
                int deltaY = y - mLastYIntercept;
                if (DEBUG) {
                    Log.d(TAG, "onInterceptTouchEvent,Math.abs(deltaX):" + Math.abs(deltaX));
                    Log.d(TAG, "onInterceptTouchEvent,Math.abs(deltaY):" + Math.abs(deltaY));
                }
                if (Math.abs(deltaX) > Math.abs(deltaY)) { // 横向滑动
                    intercepted = true;
                } else {
                    intercepted = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                intercepted = false;
                break;
            default:
                break;
        }
        mLastX = x;
        mLastY = y;
        mLastXIntercept = x;
        mLastYIntercept = y;
        if (DEBUG) {
            Log.d(TAG, "onInterceptTouchEvent,intercepted:" + intercepted);
        }
        return intercepted;
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        int x = (int) arg0.getX();
        int y = (int) arg0.getY();

        switch (arg0.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastXIntercept;
                int deltaY = y - mLastYIntercept;
                if (Math.abs(deltaX) > Math.abs(deltaY)) {// 横向滑动
                    if (DEBUG) {
                        Log.d(TAG, "onTouchEvent,内部处理滑动");
                    }
                    getParent().requestDisallowInterceptTouchEvent(true);
                } else {
                    if (DEBUG) {
                        Log.d(TAG, "onTouchEvent,不处理");
                    }
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        mLastX = x;
        mLastY = y;
        return super.onTouchEvent(arg0);
    }
}
