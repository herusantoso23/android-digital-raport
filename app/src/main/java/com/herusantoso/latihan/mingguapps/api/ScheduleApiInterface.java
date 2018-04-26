package com.herusantoso.latihan.mingguapps.api;

import com.herusantoso.latihan.mingguapps.model.ResultMessage;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by santoso on 4/26/18.
 */

public interface ScheduleApiInterface {

    @Headers("Content-Type: application/json")
    @GET("digital_raport/schedule/get-all.php")
    Call<ResultMessage> getSchedule();

}
