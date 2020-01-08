package com.buxiaohui.movies.main;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class MainPagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<MainPageModel> mDataList;
    private boolean mNewInstanceOnPageChange = true;

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setNewInstanceOnPageChange(boolean newInstanceOnPageChange) {
        this.mNewInstanceOnPageChange = newInstanceOnPageChange;
    }

    public void setDataList(ArrayList<MainPageModel> dataList) {
        this.mDataList = dataList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (mDataList == null) {
            return 0;
        }
        return mDataList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (mDataList == null || mDataList.size() <= position || position < 0) {
            return super.getPageTitle(position);
        }
        MainPageModel model = mDataList.get(position);
        return model.getTitle();
    }

    @Override
    public Fragment getItem(int position) {
        if (mDataList == null || mDataList.size() <= position || position < 0) {
            return null;
        }
        MainPageModel model = mDataList.get(position);
        if (mNewInstanceOnPageChange && model.getClazz() != null) {
            Class clazz = model.getClazz();
            try {
                Fragment f= (Fragment) clazz.newInstance();
                return f;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        } else {
            return model.getFragment();
        }
        return null;
    }
}
