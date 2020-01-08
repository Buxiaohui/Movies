package com.buxiaohui.movies.main;

import java.util.ArrayList;

import android.os.Bundle;

import com.buxiaohui.movies.R;
import com.buxiaohui.movies.movies.MoviesFragment;
import com.buxiaohui.movies.movies.presenter.MoviesPresenter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private MainPagerAdapter mPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager = findViewById(R.id.page_pager);
        mPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
        ArrayList<MainPageModel> list = new ArrayList<>();
        MoviesFragment moviesFragment = new MoviesFragment();
        MoviesFragment moviesFragment1 = new MoviesFragment();
        MoviesFragment moviesFragment2 = new MoviesFragment();
        MoviesPresenter moviesPresenter = new MoviesPresenter();
        MoviesPresenter moviesPresenter1 = new MoviesPresenter();
        MoviesPresenter moviesPresenter2 = new MoviesPresenter();
        moviesPresenter.onCreate();
        moviesPresenter1.onCreate();
        moviesPresenter2.onCreate();
        moviesPresenter.bindView(moviesFragment);
        moviesPresenter1.bindView(moviesFragment1);
        moviesPresenter2.bindView(moviesFragment2);
        list.add(new MainPageModel("Cafe",moviesFragment));
        list.add(new MainPageModel("Movies",moviesFragment1));
        list.add(new MainPageModel("Cache",moviesFragment2));
        mPagerAdapter.setDataList(list);
        mViewPager.setAdapter(mPagerAdapter);
    }
}
