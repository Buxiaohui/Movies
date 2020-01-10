package com.buxiaohui.movies.movies;

import org.jetbrains.annotations.NotNull;

import com.buxiaohui.movies.Config;
import com.buxiaohui.movies.R;
import com.buxiaohui.movies.contract.BasePanelView;
import com.buxiaohui.movies.movies.component.MovieBannerComponent;
import com.buxiaohui.movies.movies.contract.MoviesContract;
import com.buxiaohui.movies.movies.model.MovieBannerModel;
import com.buxiaohui.movies.widgets.PullZoomScrollView;
import com.google.android.material.tabs.TabLayout;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class MoviesFragment extends BasePanelView implements MoviesContract.View {
    private static final String TAG = "MoviesFragment";
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
    private MovieBannerComponent mMovieBannerComponent;

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
        mPullZoomScrollView
                .setMaxHeight(getContext().getResources().getDimensionPixelSize(R.dimen.bannner_container_max_height));
        mPullZoomScrollView
                .setMinHeight(getContext().getResources().getDimensionPixelSize(R.dimen.bannner_container_min_height));
        mPullZoomScrollView.setOnSizeChangeListener(new PullZoomScrollView.OnSizeChangeListener() {
            @Override
            public void onSizeChange(int height, @FloatRange(from = 0.0f, to = 1.0f) float progress) {
                Log.d(TAG, "onSizeChange,height:" + height + ",progress:" + progress);
                mMovieBannerComponent.onSizeChange(height, progress);
            }
        });
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
        mMovieBannerComponent = new MovieBannerComponent(new MovieBannerComponent.BannerCallback() {
            @Override
            public ViewPager getBannerView() {
                return mBannerViewPager;
            }

            @Override
            public Context getCtx() {
                return MoviesFragment.this.getContext();
            }

            @Override
            public int request() {
                if (mPresenter != null) {
                    return mPresenter.request();
                }
                return Config.RequestActionRet.UNKNOWN;
            }
        });
        mMovieBannerComponent.initBannerSection();
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
