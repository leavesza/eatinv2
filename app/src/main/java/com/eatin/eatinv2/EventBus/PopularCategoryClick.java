package com.eatin.eatinv2.EventBus;

import com.eatin.eatinv2.Model.PopularCategoryModel;

public class PopularCategoryClick {
    private PopularCategoryModel popularCategoryModel;

    public PopularCategoryModel getPopularCategoryModel() {
        return popularCategoryModel;
    }

    public void setPopularCategoryModel(PopularCategoryModel popularCategoryModel) {
        this.popularCategoryModel = popularCategoryModel;
    }

    public PopularCategoryClick(PopularCategoryModel popularCategoryModel) {
        this.popularCategoryModel = popularCategoryModel;
    }
}
