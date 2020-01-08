package com.buxiaohui.movies.widgets;

import com.buxiaohui.movies.BuildConfig;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ScrollView;

public class PullZoomScrollView extends ScrollView {
    private static final float DEFAULT_SCROLL_FACTOR = 0.5f;
    private static final String TAG = "PullZoomScrollView";
    private static final boolean DEBUG = BuildConfig.DEBUG;

    /**
     * 被放大或缩小的View
     */
    private View mZoomView;
    private int mZoomViewWidth;
    private int mZoomViewHeight;
    /**
     * 记录第一次按下的位置
     */
    private float mFirstY;
    /**
     * 是否正在缩放
     */
    private boolean mIsMoving;
    /**
     * 缩放系数，缩放系数越大，变化的越大
     */
    private float mZoomSpeedFactor = DEFAULT_SCROLL_FACTOR;
    /**
     * 最小滑动距离
     */
    private int mTouchSlop;
    private int mMinHeight;
    private int mMaxHeight;

    public PullZoomScrollView(Context context) {
        super(context);
        setFillViewport(true);
    }

    public PullZoomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFillViewport(true);
    }

    public PullZoomScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFillViewport(true);
    }

    public void setMinHeight(int minHeight) {
        this.mMinHeight = minHeight;
    }

    public void setMaxHeight(int maxHeight) {
        this.mMaxHeight = maxHeight;
    }

    public void setZoomView(View v) {
        this.mZoomView = v;
    }

    public void setZoomSpeedFactor(float factor) {
        this.mZoomSpeedFactor = factor;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    private void init() {
        setFillViewport(true);
        mTouchSlop = ViewConfiguration.get(this.getContext()).getScaledTouchSlop();
        if (getChildAt(0) != null) {
            ViewGroup vg = (ViewGroup) getChildAt(0);
            if (vg.getChildAt(0) != null) {
                mZoomView = vg.getChildAt(0);
            }
        } else {
            throw new IllegalStateException("no child view");
        }
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(isTopMaxHeight()){

        }
//        if (mZoomViewWidth <= 0 || mZoomViewHeight <= 0) {
//            mZoomViewWidth = mZoomView.getMeasuredWidth();
//            mZoomViewHeight = mZoomView.getMeasuredHeight();
//        }
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                mFirstY = ev.getY();
//                break;
//            case MotionEvent.ACTION_UP:
//                mIsMoving = false;
//                break;
//            case MotionEvent.ACTION_MOVE:
//                if(){}
////                if (getScrollY() == 0) {
////                    if (DEBUG) {
////                        Log.e(TAG, "onTouchEvent,已经在顶部，不滑动!!!");
////                    }
////                    return super.onTouchEvent(ev);
////                }
//                int distance = (int) ((ev.getY() - mFirstY));
//                if (Math.abs(distance) < mTouchSlop) {
//                    if (DEBUG) {
//                        Log.e(TAG, "onTouchEvent,滑动距离太小!!!");
//                    }
//                    return super.onTouchEvent(ev);
//                }
//
//                // 处理放大
//                mIsMoving = true;
//                updateZoomProgress(distance);
//                return true; // 返回true表示已经完成触摸事件，不再处理
//        }
        return super.onTouchEvent(ev);
    }


    public void updateZoomProgress(float progress) {
        if (mZoomViewWidth <= 0 || mZoomViewHeight <= 0 || mZoomView == null) {
            if (DEBUG) {
                Log.e(TAG, "updateZoomProgress,return!!!");
            }
            return;
        }
        ViewGroup.LayoutParams lp = mZoomView.getLayoutParams();
        // TODO
    }

    private boolean isTopMaxHeight() {
        ViewGroup.LayoutParams lp = mZoomView.getLayoutParams();
        return lp.height == mMaxHeight;
    }

    private boolean isTopMinHeight() {
        ViewGroup.LayoutParams lp = mZoomView.getLayoutParams();
        return lp.height == mMinHeight;
    }
}
