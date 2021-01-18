package com.eatin.eatinv2.Callback;

import com.eatin.eatinv2.Model.Order;

public interface ILoadTimeFromFirebaseListener {
    void onLoadTimeSuccess(Order order,long estimateTimeInMs);
    void onLoadTimeFailed(String message);
}
