package com.buxiaohui.movies.movies.component;

import java.util.ArrayList;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.buxiaohui.movies.R;
import com.buxiaohui.movies.movies.model.BannerImgMode;
import com.buxiaohui.movies.utils.UIUtils;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

/**
 * banner
 */
public class MovieBannerComponent {
    /**
     * <边缘的page最大高度> 与 <主page最大高度>的比
     */
    private static final float DEFAULT_MIN_SCALE = 0.65f;
    private static final float DEFAULT_CENTER = 0.5f;

    private int mBannerCardHeight;
    private int mBannerCardWidth;
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

    public MovieBannerComponent(@NonNull BannerCallback callback) {
        mCtx = callback.getCtx();
        mCallback = callback;
        mScreenWidth = UIUtils.getScreenWidth(mCtx);
        mScreenHeight = UIUtils.getScreenHeight(mCtx);
        mBannerMaxHeight = mCtx.getResources().getDimensionPixelSize(R.dimen.bannner_container_max_height);
        mBannerMinHeight = mCtx.getResources().getDimensionPixelSize(R.dimen.bannner_container_min_height);
        mBannerViewPager = callback.getBannerView();
        mMainPageMaxHeight = mBannerMaxHeight;
        mEdgePageMaxHeight = (int) (mBannerMaxHeight * DEFAULT_MIN_SCALE);
        mPageMinHeight = mBannerMinHeight;

        mBannerCardWidth = (mScreenWidth >> 1);
        mBannerCardHeight = mBannerMaxHeight;

    }

    public void onSizeChange(int height, @FloatRange(from = 0.0f, to = 1.0f) float progress) {
        // TODO 改变顶部样式
        /**
         * 向上：
         * 当midPageSize > edgePageSie   执行midPage缩小
         * 当midPageSize = leftRightPageSie   执行整体的缩小
         */

        /**
         * 向下：
         * 当midPageSize > leftRightPageSie   执行整体放大
         * 当midPageSize = leftRightPageSie   执行整体的缩小
         */
    }

    /**
     * 构造了banner的假数据
     */
    public void initBannerSection() {
        final ArrayList<BannerImgMode> bannerList = new ArrayList<>();
        String imgUrl = "https://m.media-amazon"
                + ".com/images/M/MV5BNGVjNWI4ZGUtNzE0MS00YTJmLWE0ZDctN2ZiYTk2YmI3NTYyXkEyXkFqcGdeQXVyMTkxNjUyNQ@@"
                + "._V1_SX300.jpg";
        bannerList.add(new BannerImgMode(imgUrl));
        bannerList.add(new BannerImgMode(imgUrl));
        bannerList.add(new BannerImgMode(imgUrl));
        bannerList.add(new BannerImgMode(imgUrl));
        bannerList.add(new BannerImgMode(imgUrl));
        bannerList.add(new BannerImgMode(imgUrl));
        bannerList.add(new BannerImgMode(imgUrl));
        mBannerViewPager.setOffscreenPageLimit(4);
        mBannerViewPager.setPageTransformer(false, new ViewPager.PageTransformer() {

            @Override
            public void transformPage(@NonNull View view, float position) {
                ((TextView) view.findViewById(R.id.desc)).setText("" + (Integer) view.getTag() + "::" + position);
                int pageWidth = view.getWidth();
                int pageHeight = view.getHeight();

                view.setPivotY(pageHeight / 2);
                view.setPivotX(pageWidth / 2);
                if (position < -1) { // [-Infinity,-1)
                    view.setScaleX(DEFAULT_MIN_SCALE);
                    view.setScaleY(DEFAULT_MIN_SCALE);
                } else if (position < 0) {
                    float scaleFactor = (1 + position) * (1 - DEFAULT_MIN_SCALE) + DEFAULT_MIN_SCALE;
                    view.setScaleX(scaleFactor);
                    view.setScaleY(scaleFactor);
                } else if (position <= 1) {
                    float scaleFactor = (1 - position) * (1 - DEFAULT_MIN_SCALE) + DEFAULT_MIN_SCALE;
                    view.setScaleX(scaleFactor);
                    view.setScaleY(scaleFactor);
                } else { // (1,+Infinity]
                    view.setScaleX(DEFAULT_MIN_SCALE);
                    view.setScaleY(DEFAULT_MIN_SCALE);
                }
            }
        });
        mBannerViewPager.setAdapter(new PagerAdapter() {
            int screenWidth = UIUtils.getScreenWidth(mBannerViewPager.getContext());

            @Override
            public int getCount() {
                return bannerList.size(); // test
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
                View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_movie_banner, null);
                ImageView image = view.findViewById(R.id.img);
                ViewGroup.LayoutParams layoutParams =
                        new ViewGroup.LayoutParams(mBannerCardWidth, mBannerCardHeight);
                view.setLayoutParams(layoutParams);
                RequestOptions
                        options = new RequestOptions().error(R.drawable.img_load_failure)
                        .bitmapTransform(new RoundedCorners(30));//图片圆角为30
                Glide.with(image.getContext()).load(bannerList.get(position).getImgUrl()).apply(options).into(image);
                view.setTag(position);
                ((ViewPager) container).addView(view);
                Log.d("instantiateItem", "position:" + position);
                return view;
            }
        });
        mBannerViewPager.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

            }
        });
        // 假设已经有了banner数据
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
    }

    public interface BannerCallback {
        ViewPager getBannerView();

        Context getCtx();

        int request();
    }
}
