package com.buxiaohui.movies.movies.viewholder;

import com.buxiaohui.movies.R;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
/**
 * unused
 */
public class SimpleDescViewHolder extends RecyclerView.ViewHolder {
    private TextView nameTv;
    private TextView genreTv;

    public SimpleDescViewHolder(@NonNull View itemView) {
        super(itemView);
        nameTv = itemView.findViewById(R.id.name);
        genreTv = itemView.findViewById(R.id.genre);
    }
}
