package com.buxiaohui.movies.movies;

import static com.buxiaohui.movies.movies.component.MovieBannerComponent.DEFAULT_MIN_SCALE;

import com.buxiaohui.movies.R;
import com.buxiaohui.movies.utils.UIUtils;

import android.content.Context;

public class MovieSizeConfig {
    private int marginLR;
    private int maxHeight;
    private int minHeight;
    private int pageMargin;
    private int screenWidth;
    private int screenHeight;
    private int topMaxMargin;
    private int bottomMaxMargin;

    public MovieSizeConfig(Context ctx) {
        screenWidth = UIUtils.getScreenWidth(ctx);
        screenHeight = UIUtils.getScreenHeight(ctx);

        marginLR = ctx.getResources().getDimensionPixelOffset(R.dimen.bannner_lr_margin);

        maxHeight = ctx.getResources().getDimensionPixelOffset(R.dimen.bannner_container_max_height);
        minHeight = (int) (maxHeight * DEFAULT_MIN_SCALE);

        pageMargin = ctx.getResources().getDimensionPixelOffset(R.dimen.bannner_page_margin);

        topMaxMargin = ctx.getResources().getDimensionPixelOffset(R.dimen.bannner_view_pager_top_max_margin);

        bottomMaxMargin = ctx.getResources().getDimensionPixelOffset(R.dimen.bannner_view_pager_bottom_max_margin);
    }

    public int getBottomMaxMargin() {
        return bottomMaxMargin;
    }

    public int getTopMaxMargin() {
        return topMaxMargin;
    }

    public int getMinHeight() {
        return minHeight;
    }

    public int getPageMargin() {
        return pageMargin;
    }

    public int getMarginLR() {
        return marginLR;
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }
}
