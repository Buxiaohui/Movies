package com.buxiaohui.movies.widgets;

import com.buxiaohui.movies.BuildConfig;
import com.buxiaohui.movies.Config;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
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
    private int mLastX;
    private int mLastY;
    private int mLastXIntercept;
    private int mLastYIntercept;
    private GestureDetector mGestureDetector;
    private OnSizeChangeListener mOnSizeChangeListener;

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
        // mGestureDetector = new GestureDetector(this.getContext(), new YScrollDetector());
        setFillViewport(true);
        // mTouchSlop = ViewConfiguration.get(this.getContext()).getScaledTouchSlop();
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
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (DEBUG) {
            Log.d(TAG, "onInterceptTouchEvent,scrollview");
        }
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        boolean intercept = false;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (DEBUG) {
                    Log.d(TAG, "onInterceptTouchEvent,按下");
                }
                mLastX = x;
                mLastY = y;
                mLastXIntercept = x;
                mLastYIntercept = y;
                return super.onInterceptTouchEvent(ev);
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastXIntercept;
                int deltaY = y - mLastYIntercept;
                if (DEBUG) {
                    Log.d(TAG, "onInterceptTouchEvent,滑动");
                    Log.d(TAG, "onInterceptTouchEvent,当前Y:" + y + ",上次Y:" + mLastYIntercept);
                    Log.d(TAG, "onInterceptTouchEvent,当前X:" + x + ",上次X:" + mLastXIntercept);
                    Log.d(TAG, "onInterceptTouchEvent,Math.abs(deltaX):" + Math.abs(deltaX));
                    Log.d(TAG, "onInterceptTouchEvent,Math.abs(deltaY):" + Math.abs(deltaY));
                    Log.d(TAG, "onInterceptTouchEvent,getScrollY():" + getScrollY());
                    Log.d(TAG, "onInterceptTouchEvent,mTouchSlop:" + mTouchSlop);
                }
                boolean isYDirectionMove = Math.abs(deltaX) < Math.abs(deltaY);
                intercept = isYDirectionMove;
                mLastX = x;
                mLastY = y;
                mLastXIntercept = x;
                mLastYIntercept = y;
                return intercept;
            case MotionEvent.ACTION_UP:
                if (DEBUG) {
                    Log.d(TAG, "onInterceptTouchEvent,抬起");
                }
                mLastX = x;
                mLastY = y;
                mLastXIntercept = x;
                mLastYIntercept = y;
                return super.onInterceptTouchEvent(ev);
            default:
                mLastX = x;
                mLastY = y;
                mLastXIntercept = x;
                mLastYIntercept = y;
                return super.onInterceptTouchEvent(ev);
        }
    }

    private boolean isTouchInView(View view, MotionEvent event) {
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        int[] local = new int[2];
        view.getLocationOnScreen(local);
        int subVX = local[0];
        int subVY = local[1];
        int subWidth = view.getWidth();
        int subHeight = view.getHeight();
        if (x > subVX && x < subVX + subWidth && y > subVY && y < subVY + subHeight) {
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        boolean consume = false;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;
                int direction = Config.MoveDirection.UNKNOWN;
                if (deltaY > 0) {
                    direction = Config.MoveDirection.BOTTOM;
                }
                if (deltaY <= 0) {
                    direction = Config.MoveDirection.TOP;
                }
                if (DEBUG) {
                    Log.d(TAG, "onTouchEvent,deltaY:" + deltaY + ",direction:" + getDirectionDesc(direction));
                }

                if (direction == Config.MoveDirection.BOTTOM) {
                    if (isTopMaxHeight()) {
                        if (DEBUG) {
                            Log.d(TAG, "onTouchEvent,向下滑动，已经最大");
                        }
                        consume = true;
                    } else {
                        if (DEBUG) {
                            Log.d(TAG, "onTouchEvent,向下滑动，放大");
                        }
                        if (zoom(deltaY, Config.MoveDirection.BOTTOM)) {
                            consume = true;
                        }
                    }
                } else if (direction == Config.MoveDirection.TOP) {
                    if (isTopMinHeight()) {
                        if (DEBUG) {
                            Log.d(TAG, "onTouchEvent,向上滑动，已经最小");
                        }
                        consume = true;
                    } else {
                        if (DEBUG) {
                            Log.d(TAG, "onTouchEvent,向上滑动，缩小");
                        }
                        if (zoom(deltaY, Config.MoveDirection.TOP)) {
                            consume = true;
                        }
                    }
                }

                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        mLastX = x;
        mLastY = y;
        if (consume) {
            Log.d(TAG, "onTouchEvent,消费掉滑动事件 " + getActionDesc(ev));
            return true;
        } else {
            Log.d(TAG, "onTouchEvent,继续处理滑动事件 " + getActionDesc(ev));
            return super.onTouchEvent(ev);
        }
    }

    /**
     * TestOnly
     */
    private String getActionDesc(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return "按下";
            case MotionEvent.ACTION_MOVE:
                return "滑动";
            case MotionEvent.ACTION_UP:
                return "抬起";
            case MotionEvent.ACTION_CANCEL:
                return "取消";
        }
        return "other";
    }

    /**
     * TestOnly
     */
    private String getDirectionDesc(int direction) {
        switch (direction) {
            case Config.MoveDirection.RIGHT:
                return "向右";
            case Config.MoveDirection.LEFT:
                return "向左";
            case Config.MoveDirection.BOTTOM:
                return "向下";
            case Config.MoveDirection.TOP:
                return "向上";
        }
        return "other";
    }

    /**
     * unused
     * TestOnly
     */
    private int getDirection(float dx, float dy) {
        if (Math.abs(dx) > Math.abs(dy)) {
            // X轴移动
            return dx > 0 ? Config.MoveDirection.RIGHT : Config.MoveDirection.LEFT;
        } else {
            // Y轴移动
            return dy > 0 ? Config.MoveDirection.BOTTOM : Config.MoveDirection.TOP;
        }
    }

    public boolean zoom(float deltaY, int direction) {
        ViewGroup.LayoutParams lp = mZoomView.getLayoutParams();
        if (DEBUG) {
            Log.d(TAG, "zoom,--before---lp.height:" + lp.height);
        }
        if (direction == Config.MoveDirection.BOTTOM) {
            if (lp.height >= mMaxHeight) {
                return false;
            }
        }
        if (direction == Config.MoveDirection.TOP) {
            if (lp.height <= mMinHeight) {
                return false;
            }
        }
        lp.height += (deltaY * 0.5);
        if (lp.height > mMaxHeight) {
            lp.height = mMaxHeight;
        }
        if (lp.height < mMinHeight) {
            lp.height = mMinHeight;
        }
        if (DEBUG) {
            Log.d(TAG, "zoom,---after---lp.height:" + lp.height);
        }
        mZoomView.setLayoutParams(lp);
        requestLayout();
        if (mOnSizeChangeListener != null) {
            mOnSizeChangeListener
                    .onSizeChange(lp.height, (lp.height - mMinHeight) * 1.0f / (mMaxHeight - mMinHeight) * 1.0f);
        }
        return true;
    }

    private boolean isTopMaxHeight() {
        ViewGroup.LayoutParams lp = mZoomView.getLayoutParams();
        if (DEBUG) {
            Log.e(TAG, "isTopMaxHeight,lp.height:" + lp.height + ",mMaxHeight:" + mMaxHeight);
        }
        return lp.height >= mMaxHeight;
    }

    private boolean isTopMinHeight() {
        ViewGroup.LayoutParams lp = mZoomView.getLayoutParams();
        if (DEBUG) {
            Log.e(TAG, "isTopMinHeight,lp.height:" + lp.height + ",mMinHeight:" + mMinHeight);
        }
        return lp.height <= mMinHeight;
    }

    public void setOnSizeChangeListener(OnSizeChangeListener listener) {
        mOnSizeChangeListener = listener;
    }

    public interface OnSizeChangeListener {
        void onSizeChange(int height, float progress);
    }

    /**
     * unused
     * TestOnly
     */
    private class YScrollDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {
            if (Math.abs(distanceY) > 0) {
                return true;
            }
            return false;
        }
    }
}
