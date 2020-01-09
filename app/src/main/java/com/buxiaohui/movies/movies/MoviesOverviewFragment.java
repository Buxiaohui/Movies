package com.buxiaohui.movies.movies;

import java.util.ArrayList;

import com.buxiaohui.movies.R;
import com.buxiaohui.movies.movies.model.ActorModel;
import com.buxiaohui.movies.movies.model.MovieBannerModel;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MoviesOverviewFragment extends Fragment {
    private View mRootView;
    private TextView mTitleTv;
    private TextView mStoryContentTv;
    private RecyclerView mCastRecyclerView;
    private MoviesOverviewCastAdapter mCastViewAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.layout_movies_overview, container, false);
        return mRootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // find
        mCastRecyclerView = view.findViewById(R.id.story_cast_rv);
        mTitleTv = view.findViewById(R.id.title);
        mStoryContentTv = view.findViewById(R.id.story_content);
        mTitleTv.setText(getText(R.string.story));
        ArrayList<ActorModel> list = new ArrayList<>();
        list.add(new ActorModel());
        list.add(new ActorModel());
        list.add(new ActorModel());
        list.add(new ActorModel());
        list.add(new ActorModel());
        list.add(new ActorModel());
        list.add(new ActorModel());
        mCastViewAdapter = new MoviesOverviewCastAdapter(list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mCastRecyclerView.setLayoutManager(linearLayoutManager);
        mCastRecyclerView.setAdapter(mCastViewAdapter);

    }

    public void onSuccess(MovieBannerModel movieBannerModel) {
        String story = movieBannerModel != null ? (TextUtils.isEmpty(movieBannerModel.getPlot()) ? ""
                                                           : movieBannerModel.getPlot()) : "";
        if (mStoryContentTv != null) {
            mStoryContentTv.setText(story);
        }
    }

    public void onFailure(String desc) {
        if (mStoryContentTv != null) {
            mStoryContentTv.setText(TextUtils.isEmpty(desc) ? "unknown error" : desc);
        }
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
}
