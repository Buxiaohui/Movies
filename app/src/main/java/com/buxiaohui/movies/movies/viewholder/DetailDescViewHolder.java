package com.buxiaohui.movies.movies.viewholder;

import com.buxiaohui.movies.R;
import com.google.android.material.tabs.TabLayout;

import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * unused
 */
public class DetailDescViewHolder extends RecyclerView.ViewHolder {
    private TabLayout tabLayout;

    public DetailDescViewHolder(@NonNull View itemView) {
        super(itemView);
        this.tabLayout = itemView.findViewById(R.id.tab_layout);
        this.tabLayout.addTab(tabLayout.newTab().setText("OverView"));
        this.tabLayout.addTab(tabLayout.newTab().setText("Rate movie"));
    }
}
