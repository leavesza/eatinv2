package com.eatin.eatinv2.Callback;

import com.eatin.eatinv2.Model.BestDealModel;
import com.eatin.eatinv2.Model.PopularCategoryModel;

import java.util.List;

public interface IBestDealCallbackListener {
    void onBestDealLoadSuccess(List<BestDealModel> bestDealModels);
    void onBestDealLoadFailed(String message);
}
