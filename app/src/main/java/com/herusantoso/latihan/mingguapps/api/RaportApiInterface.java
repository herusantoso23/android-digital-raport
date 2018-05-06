package com.herusantoso.latihan.mingguapps.api;

import com.herusantoso.latihan.mingguapps.model.ResultMessage;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by santoso on 5/6/18.
 */

public interface RaportApiInterface {

    @Headers("Content-Type: application/json")
    @GET("digital_raport/raport/raport.php")
    Call<ResultMessage> getRaport(
            @Query(value = "nis") String nis,
            @Query(value = "semester") String semester);
}
