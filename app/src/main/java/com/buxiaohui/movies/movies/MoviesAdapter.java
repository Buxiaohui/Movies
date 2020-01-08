package com.buxiaohui.movies.movies;

import java.util.List;

import com.buxiaohui.movies.movies.model.BaseModel;
import com.buxiaohui.movies.movies.model.MovieBannerModel;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
/**
 * unused
 */
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    private List<MovieBannerModel> mDataList;

    public MoviesAdapter(List<MovieBannerModel> list) {
        mDataList = list;
    }

    @Override

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fruit_item, parent, false);
//        ViewHolder holder = new ViewHolder(view);
//        return holder;
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BaseModel data = mDataList.get(position);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView fruitImage;
        TextView fruitName;

        public ViewHolder(View view) {
            super(view);
//            fruitImage = (ImageView) view.findViewById(R.id.fruit_image);
//            fruitName = (TextView) view.findViewById(R.id.fruitname);
        }
    }
}