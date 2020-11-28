package com.eatin.eatinv2.Remote;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ICloudFunctions {
    @GET("helloworld")
    Observable<ResponseBody> getCustomToken(@Query("access_token") String accessToken);
}
