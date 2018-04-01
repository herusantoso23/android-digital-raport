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

public interface StudentApiInterface {

    @Headers("Content-Type: application/json")
    @POST("digital_raport/registrasi/registrasi.php")
    Call<ResultMessage> register(@Body Student student);

    @Headers("Content-Type: application/json")
    @POST("digital_raport/auth/login.php")
    Call<ResultMessage> login(@Body Login login);

    @Headers("Content-Type: application/json")
    @GET("digital_raport/student/get-profile.php")
    Call<ResultMessage> getProfile(@Query(value = "nis") String nis);

    @Headers("Content-Type: application/json")
    @POST("digital_raport/student/update-profile.php")
    Call<ResultMessage> updateProfile(@Body StudentUpdate studentUpdate);

    @Headers("Content-Type: application/json")
    @POST("digital_raport/student/change-password.php")
    Call<ResultMessage> changePass(
            @Body StudentChangePassword studentChangePassword);
}
