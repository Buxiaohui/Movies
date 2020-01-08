package com.buxiaohui.movies.movies;

import java.util.List;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.buxiaohui.movies.R;
import com.buxiaohui.movies.movies.model.ActorModel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;

/**
 * unused
 */
public class MoviesOverviewCastAdapter extends RecyclerView.Adapter<MoviesOverviewCastAdapter.ViewHolder> {

    private List<ActorModel> mDataList;

    public MoviesOverviewCastAdapter(List<ActorModel> list) {
        mDataList = list;
    }

    public void setDataList(List<ActorModel> dataList) {
        this.mDataList = dataList;
        notifyDataSetChanged();
    }

    @Override

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_movies_overview_cast, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ActorModel data = mDataList.get(position);
        RequestOptions options = new RequestOptions().error(R.drawable.img_load_failure).bitmapTransform(new RoundedCorners(30));//图片圆角为30
        Glide.with(holder.itemView.getContext()).load(data.getImgUrl()).apply(options).into(holder.imgView);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgView;

        public ViewHolder(View view) {
            super(view);
            imgView = (ImageView) view.findViewById(R.id.img);
        }
    }
}