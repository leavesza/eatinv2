package com.eatin.eatinv2.EventBus;

import com.eatin.eatinv2.Model.BestDealModel;

public class BestDealItemClick {
    private BestDealModel bestDealModel;

    public BestDealModel getBestDealModel() {
        return bestDealModel;
    }

    public void setBestDealModel(BestDealModel bestDealModel) {
        this.bestDealModel = bestDealModel;
    }

    public BestDealItemClick(BestDealModel bestDealModel) {
        this.bestDealModel = bestDealModel;
    }
}
