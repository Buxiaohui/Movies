package com.buxiaohui.movies.movies;

import org.jetbrains.annotations.NotNull;

import com.buxiaohui.movies.R;
import com.buxiaohui.movies.contract.BasePanelView;
import com.buxiaohui.movies.movies.contract.MoviesContract;
import com.buxiaohui.movies.movies.model.ActorModel;
import com.buxiaohui.movies.movies.model.BannerImgMode;
import com.buxiaohui.movies.movies.model.MovieBannerModel;
import com.buxiaohui.movies.utils.UIUtils;
import com.buxiaohui.movies.widgets.PullZoomScrollView;
import com.google.android.material.tabs.TabLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

public class MoviesFragment extends BasePanelView implements MoviesContract.View {
    private View mRootView;
    private MoviesContract.Presenter mPresenter;
    private TextView mNameTv;
    private TextView mGenreTv;

    private TabLayout mDescTabLayout;
    private ViewPager mDescViewPager;

    private ViewPager mBannerViewPager;

    private PullZoomScrollView mPullZoomScrollView;
    private MoviesOverviewFragment mOverviewFragment;
    private MoviesRateFragment mRateFragment;

    @Override
    public void setPresenter(MoviesContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public View getRootView() {
        return mRootView;
    }

    @Override
    public void onSuccess(MovieBannerModel movieBannerModel) {
        mOverviewFragment.onSuccess(movieBannerModel);
        mRateFragment.onSuccess(movieBannerModel);
        mNameTv.setText(movieBannerModel.getTitle());
        String s = getDurationString(movieBannerModel);
        mGenreTv.setText(s + " | " + movieBannerModel.getGenre());
    }

    @NotNull
    private String getDurationString(MovieBannerModel movieBannerModel) {
        int minutes = 0;
        try {
            String runtimeStr = movieBannerModel.getRuntime();
            if (runtimeStr.contains("min")) {
                runtimeStr = runtimeStr.replace("min", "");
            }
            if (runtimeStr.contains(" ")) {
                runtimeStr = runtimeStr.replace(" ", "");
            }
            minutes = Integer.parseInt(runtimeStr);
        } catch (Exception e) {

        }
        int h = minutes / 60;
        int m = minutes % 60;
        String s = "";
        if (h > 0) {
            s += h;
            s += "h";
            s += " ";

        }
        s += m;
        s += "min";
        return s;
    }

    @Override
    public void onFailure(String desc) {
        mOverviewFragment.onFailure(desc);
        mRateFragment.onFailure(desc);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_movie_tab, container, false);
        return mRootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // find
        mBannerViewPager = view.findViewById(R.id.banner_pager);

        mNameTv = view.findViewById(R.id.name);
        mGenreTv = view.findViewById(R.id.genre);

        mPullZoomScrollView = view.findViewById(R.id.content_scrollview);
        mPullZoomScrollView.setMaxHeight(UIUtils.dip2px(this.getContext(), 400));
        mPullZoomScrollView.setMinHeight(UIUtils.dip2px(this.getContext(), 300));


        mDescTabLayout = view.findViewById(R.id.tab_layout);
        mDescViewPager = view.findViewById(R.id.tab_viewpager);

        // set
        initDescSection();
        initBannerSection();
        if (mPresenter != null) {
            mPresenter.request();
        }
    }

    private void initBannerSection() {
        final ArrayList<BannerImgMode> bannerList = new ArrayList<>();
        bannerList.add(new BannerImgMode());
        bannerList.add(new BannerImgMode());
        bannerList.add(new BannerImgMode());
        bannerList.add(new BannerImgMode());
        bannerList.add(new BannerImgMode());
        bannerList.add(new BannerImgMode());
        bannerList.add(new BannerImgMode());
        mBannerViewPager.setOffscreenPageLimit(4);
        mBannerViewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            final float DEFAULT_MIN_SCALE = 0.65f;
            float mMinScale = DEFAULT_MIN_SCALE;
            final float DEFAULT_CENTER = 0.5f;

            @Override
            public void transformPage(@NonNull View view, float position) {

                int pageWidth = view.getWidth();
                int pageHeight = view.getHeight();

                view.setPivotY(pageHeight / 2);
                view.setPivotX(pageWidth / 2);
                if (position < -1) { // [-Infinity,-1)
                    // This page is way off-screen to the left.
                    view.setScaleX(mMinScale);
                    view.setScaleY(mMinScale);
                    view.setPivotX(pageWidth);
                } else if (position <= 1) { // [-1,1]
                    // Modify the default slide transition to shrink the page as well
                    if (position < 0) { //1-2:1[0,-1] ;2-1:1[-1,0]
                        float scaleFactor = (1 + position) * (1 - mMinScale) + mMinScale;
                        view.setScaleX(scaleFactor);
                        view.setScaleY(scaleFactor);

                        view.setPivotX(pageWidth * (DEFAULT_CENTER + (DEFAULT_CENTER * -position)));

                    } else { //1-2:2[1,0] ;2-1:2[0,1]

                        float scaleFactor = (1 - position) * (1 - mMinScale) + mMinScale;
                        view.setScaleX(scaleFactor);
                        view.setScaleY(scaleFactor);
                        view.setPivotX(pageWidth * ((1 - position) * DEFAULT_CENTER));
                    }


                } else { // (1,+Infinity]
                    view.setPivotX(0);
                    view.setScaleX(mMinScale);
                    view.setScaleY(mMinScale);

                }
            }
        });
        mBannerViewPager.setAdapter(new

                                            PagerAdapter() {
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
                                                    ImageView image = new ImageView(container.getContext());
                                                    image.setImageResource(bannerList.get(position).getImgResId());
                                                    image.setScaleType(ImageView.ScaleType.FIT_XY);
                                                    ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(200, 200);
                                                    image.setLayoutParams(layoutParams);
                                                    ((ViewPager) container).addView(image);
                                                    Log.d("instantiateItem", "position:" + position);
                                                    return image;
                                                }
                                            });
    }

    private void initDescSection() {
        mDescTabLayout.addTab(mDescTabLayout.newTab().setText(R.string.overview), true);
        mDescTabLayout.addTab(mDescTabLayout.newTab().setText(R.string.rate_movie), false);
        mOverviewFragment = new MoviesOverviewFragment();
        mRateFragment = new MoviesRateFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ParamsKey.BASE_DATA, this.mPresenter.getData());
        mDescViewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {

            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return mOverviewFragment;
                    case 1:
                        return mRateFragment;
                    default:
                        return null;
                }
            }
        });
        mDescTabLayout.setupWithViewPager(mDescViewPager);
        // after setupWithViewPager
        mDescTabLayout.getTabAt(0).setText(R.string.overview);
        mDescTabLayout.getTabAt(1).setText(R.string.rate_movie);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    interface ParamsKey {
        String BASE_DATA = "movie_model";
    }
}
