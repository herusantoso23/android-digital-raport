package com.herusantoso.latihan.mingguapps.api;

import com.herusantoso.latihan.mingguapps.model.Login;
import com.herusantoso.latihan.mingguapps.model.ResultMessage;
import com.herusantoso.latihan.mingguapps.model.Student;
import com.herusantoso.latihan.mingguapps.model.StudentChangePassword;
import com.herusantoso.latihan.mingguapps.model.StudentUpdate;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by santoso on 3/31/18.
 */

public interface LecturerApiInterface {

    @Headers("Content-Type: application/json")
    @GET("digital_raport/lecturer/get-all.php")
    Call<ResultMessage> getAll();

    @Headers("Content-Type: application/json")
    @GET("digital_raport/lecturer/get-detail.php")
    Call<ResultMessage> getDetail(@Query("nip") String nip);

}
