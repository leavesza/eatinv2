package com.eatin.eatinv2.ui.view_orders;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.eatin.eatinv2.Model.Order;

import java.util.List;

public class ViewOrderViewModel extends ViewModel {

    private MutableLiveData<List<Order>> mutableLiveDataOrderList;

    public ViewOrderViewModel(MutableLiveData<List<Order>> mutableLiveDataOrderList) {
        this.mutableLiveDataOrderList = mutableLiveDataOrderList;
    }

    public MutableLiveData<List<Order>> getMutableLiveDataOrderList() {
        return mutableLiveDataOrderList;
    }

    public void setMutableLiveDataOrderList(List<Order> orderList) {
        mutableLiveDataOrderList.setValue(orderList);

    }
}
