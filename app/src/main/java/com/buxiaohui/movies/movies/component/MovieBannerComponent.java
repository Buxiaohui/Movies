package com.buxiaohui.movies.movies.component;

import java.util.ArrayList;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.buxiaohui.movies.R;
import com.buxiaohui.movies.movies.model.BannerImgMode;
import com.buxiaohui.movies.utils.LogUtils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

/**
 * banner
 */
public class MovieBannerComponent {
    public static final float DEFAULT_MIN_SCALE = 0.65f;
    /**
     * <边缘的page最大高度> 与 <主page最大高度>的比
     */
    private static final String TAG = "MovieBannerComponent";
    private static final float DEFAULT_CENTER = 0.5f;

    private int mBannerCardHeight;
    /**
     * page_width
     */
    private int mBannerPageMaxWidth;
    /**
     * Banner最小高度
     */
    private int mBannerMinHeight;
    /**
     * Banner最大高度
     */
    private int mBannerMaxHeight;
    /**
     * 边缘的page最大高度
     */
    private int mEdgePageMaxHeight;
    /**
     * 主page最大高度
     */
    private int mMainPageMaxHeight;

    /**
     * page最小高度
     */
    private int mPageMinHeight;

    private int mScreenWidth;
    private int mScreenHeight;

    private ViewPager mBannerViewPager;
    private Context mCtx;
    private BannerCallback mCallback;
    private MovieSizeConfig mSizeConfig; // TODO 尺寸定义全部挪到MovieSizeConfig

    private float mAspectRatio;
    /**
     * 根据滑动距离做动画
     *
     * @param height
     * viewPager's height
     * @param progress
     * from = 0.0f(minHeight), to = 1.0f (maxHeight)
     */
    private float mCurProgress = 1f;

    private int mMarginLRPX;

    private ViewPager.PageTransformer mScaleAnimTransformer = new ViewPager.PageTransformer() {

        @Override
        public void transformPage(@NonNull View view, float position) {
            ((TextView) view.findViewById(R.id.desc)).setText("" + (Integer) view.getTag() + "::" + position);
            int pageWidth = view.getWidth();
            int pageHeight = view.getHeight();
            View innerContainer = view.findViewById(R.id.inner_container);
            if ((int) view.getTag() == 1) {
                LogUtils.d(TAG, "transformPage,setPivotX:" + pageWidth / 2);
                LogUtils.d(TAG, "transformPage,setPivotY:" + pageHeight / 2);
                LogUtils.d(TAG, "transformPage,mBannerViewPager.getPageMargin():" + mBannerViewPager.getPageMargin());
            }
            view.setPivotX(pageWidth / 2);
            view.setPivotY(pageHeight / 2);
            RelativeLayout.LayoutParams layoutParams =
                    (RelativeLayout.LayoutParams) innerContainer.getLayoutParams();
            boolean isHigher = layoutParams.height >= mMainPageMaxHeight * DEFAULT_MIN_SCALE;
            LogUtils.d(TAG, "transformPage,isHigher:" + isHigher);
            if (layoutParams.height >= mMainPageMaxHeight * DEFAULT_MIN_SCALE) {
                layoutParams.height = mBannerCardHeight;
                layoutParams.width = (int) (mBannerCardHeight * mAspectRatio);
            }
            innerContainer.setLayoutParams(layoutParams);

            // 横向的 节点1之前
            boolean isBeforeP1 = mBannerCardHeight >= (int) (mMainPageMaxHeight * DEFAULT_MIN_SCALE);
            LogUtils.d(TAG, "transformPage,isBeforeP1:" + isBeforeP1);
            if (!isBeforeP1) {
                if (position < -1) { // [-Infinity,-1)
                    view.setScaleX(DEFAULT_MIN_SCALE);
                    view.setScaleY(DEFAULT_MIN_SCALE);
                } else if (position < 0) { // [-1,0)
                    float scaleFactor = (1 + position) * (1 - DEFAULT_MIN_SCALE) + DEFAULT_MIN_SCALE;
                    view.setScaleX(scaleFactor);
                    view.setScaleY(scaleFactor);
                } else if (position <= 1) { // (0,1]
                    float scaleFactor = (1 - position) * (1 - DEFAULT_MIN_SCALE) + DEFAULT_MIN_SCALE;
                    view.setScaleX(scaleFactor);
                    view.setScaleY(scaleFactor);
                } else { // (1,+Infinity]
                    view.setScaleX(DEFAULT_MIN_SCALE);
                    view.setScaleY(DEFAULT_MIN_SCALE);
                }
            } else {
                LinearLayout.LayoutParams pagerLayoutParams =
                        (LinearLayout.LayoutParams) mBannerViewPager.getLayoutParams();
                int newLRMargin = (int) (((mBannerPageMaxWidth - layoutParams.width) * 0.5f) + mMarginLRPX);
                // TODO 改为padding方式，避免屏幕边缘无法滑动viewpager
                // mBannerViewPager.setPadding(newLRMargin,0,newLRMargin,0);
                pagerLayoutParams.leftMargin = newLRMargin;
                pagerLayoutParams.rightMargin = newLRMargin;
                float curBannerCardHeight = (mBannerMaxHeight - mPageMinHeight) * mCurProgress + mPageMinHeight;
                float s = (mMainPageMaxHeight * 1.0f * DEFAULT_MIN_SCALE) / (curBannerCardHeight * 1.0f);
                LogUtils.d(TAG, "transformPage,s:" + s + ",curBannerCardHeight:" + curBannerCardHeight);
                if (position < -1) { // [-Infinity,-1)
                    view.setScaleX(s);
                    view.setScaleY(s);
                } else if (position < 0) { // [-1,0)
                    float scaleFactor = (1 + position) * (1 - s) + s;
                    view.setScaleX(scaleFactor);
                    view.setScaleY(scaleFactor);
                    // TODO 水平滑动
//                     view.setTranslationX(-((mBannerPageMaxWidth - layoutParams.width) >> 1));
                } else if (position == 0) { // 0
                    float scaleFactor = (1 - position) * (1 - s) + s;
                    view.setScaleX(scaleFactor);
                    view.setScaleY(scaleFactor);
                } else if (position <= 1) { // (0,1]
                    float scaleFactor = (1 - position) * (1 - s) + s;
                    view.setScaleX(scaleFactor);
                    view.setScaleY(scaleFactor);
                    // TODO 水平滑动
//                     view.setTranslationX(((mBannerPageMaxWidth - layoutParams.width) >> 1));
                } else { // (1,+Infinity]
                    view.setScaleX(s);
                    view.setScaleY(s);
                }
            }

        }
    };

    public MovieBannerComponent(@NonNull BannerCallback callback) {
        mCtx = callback.getCtx();
        mCallback = callback;
        // init size
        mSizeConfig = new MovieSizeConfig(mCtx);
        mScreenWidth = mSizeConfig.getScreenWidth();
        mScreenHeight = mSizeConfig.getScreenHeight();
        mBannerMaxHeight = mSizeConfig.getMaxHeight();
        mBannerMinHeight = (int) (mBannerMaxHeight * DEFAULT_MIN_SCALE);

        mMainPageMaxHeight = mBannerMaxHeight;
        mEdgePageMaxHeight = (int) (mBannerMaxHeight * DEFAULT_MIN_SCALE);
        mPageMinHeight = mBannerMinHeight;
        mBannerCardHeight = mBannerMaxHeight;
        mMarginLRPX = mSizeConfig.getMarginLR();
        mBannerPageMaxWidth = (mScreenWidth - (mMarginLRPX << 1));
        mAspectRatio = mBannerPageMaxWidth * 1f / mMainPageMaxHeight * 1f;
        mBannerViewPager = callback.getBannerView();

    }

    public void onSizeChange(int height, @FloatRange(from = 0.0f, to = 1.0f) float progress) {
        LogUtils.d(TAG, "onSizeChange,progress:" + progress + ",mBannerCardHeight:" + mBannerCardHeight);
        mCurProgress = progress;
        mBannerCardHeight = (int) ((mBannerMaxHeight - mPageMinHeight) * mCurProgress + mPageMinHeight);
        // 主动调用transformer
        invokeTransformer();

        int offset = ((mBannerPageMaxWidth - (int) (mBannerCardHeight * mAspectRatio)) >> 1);
        // TODO 产生滑动以后位移的错误
        // mBannerViewPager.setPageMargin(offset);
        LogUtils.d(TAG, "transformPage,offset:" + offset);

    }

    private void invokeTransformer() {
        if (mScaleAnimTransformer != null) {
            final int scrollX = mBannerViewPager.getScrollX();
            LogUtils.d(TAG, "invokeTransformer,scrollX:" + scrollX);
            final int childCount = mBannerViewPager.getChildCount();
            for (int i = 0; i < childCount; i++) {
                final View child = mBannerViewPager.getChildAt(i);
                final ViewPager.LayoutParams lp = (ViewPager.LayoutParams) child.getLayoutParams();

                if (lp.isDecor) {
                    continue;
                }
                final float transformPos = (float) (child.getLeft() - scrollX) / getClientWidth();

                mScaleAnimTransformer.transformPage(child, transformPos);
            }
        }
    }

    private int getClientWidth() {
        return mBannerViewPager.getMeasuredWidth() - mBannerViewPager.getPaddingLeft() - mBannerViewPager
                .getPaddingRight();
    }

    /**
     * 构造了banner的假数据
     */
    public void initBannerSection() {
        final ArrayList<BannerImgMode> bannerList = new ArrayList<>();
        /**
         * test
         */
        final String imgUrl = "https://m.media-amazon"
                + ".com/images/M/MV5BNGVjNWI4ZGUtNzE0MS00YTJmLWE0ZDctN2ZiYTk2YmI3NTYyXkEyXkFqcGdeQXVyMTkxNjUyNQ@@"
                + "._V1_SX300.jpg";
        /**
         * 本地构造banner数据
         */
        bannerList.add(new BannerImgMode(imgUrl));
        bannerList.add(new BannerImgMode(imgUrl));
        bannerList.add(new BannerImgMode(imgUrl));
        bannerList.add(new BannerImgMode(imgUrl));
        bannerList.add(new BannerImgMode(imgUrl));
        bannerList.add(new BannerImgMode(imgUrl));
        bannerList.add(new BannerImgMode(imgUrl));
        mBannerViewPager.setOffscreenPageLimit(4);
        mBannerViewPager.setPageTransformer(false, mScaleAnimTransformer);

        mBannerViewPager.setAdapter(new PagerAdapter() {

            @Override
            public int getCount() {
                return bannerList.size(); // test
            }

            @Override
            public float getPageWidth(int position) {
                return super.getPageWidth(position);
            }

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public void destroyItem(View container, int position, Object object) {
                ((ViewPager) container).removeView((View) object);
            }

            @Override
            public Object instantiateItem(View container, int position) {
                View page = LayoutInflater.from(container.getContext()).inflate(R.layout.item_movie_banner, null);
                ImageView image = page.findViewById(R.id.img);
                View innerContainer = page.findViewById(R.id.inner_container);

                image.setScaleType(ImageView.ScaleType.FIT_XY); //  test

                RelativeLayout.LayoutParams layoutParams =
                        new RelativeLayout.LayoutParams((int) (mBannerCardHeight * mAspectRatio), mBannerCardHeight);
                layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
                innerContainer.setLayoutParams(layoutParams);

                RequestOptions
                        options = new RequestOptions().error(R.drawable.img_load_failure)
                        .bitmapTransform(new RoundedCorners(30));//图片圆角为30
                Glide.with(image.getContext()).load(bannerList.get(position).getImgUrl()).apply(options).into(image);
                page.setTag(position);
                ((ViewPager) container).addView(page);
                return page;
            }
        });
        mBannerViewPager.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

            }
        });

        mBannerViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if (mCallback != null) {
                    mCallback.request();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //mBannerViewPager.setCurrentItem(3);
    }

    public interface BannerCallback {
        ViewPager getBannerView();

        Context getCtx();

        int request();
    }
}
