package com.eatin.eatinv2.Callback;

import com.eatin.eatinv2.Model.BestDealModel;
import com.eatin.eatinv2.Model.CategoryModel;

import java.util.List;

public interface ICategoryCallbackListener {
    void onCategoryLoadSuccess(List<CategoryModel> categoryModelList);
    void onCategoryLoadFailed(String message);
}
