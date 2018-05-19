package com.herusantoso.latihan.mingguapps;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.herusantoso.latihan.mingguapps.api.ApiClient;
import com.herusantoso.latihan.mingguapps.api.SchoolProfileApiInterface;
import com.herusantoso.latihan.mingguapps.model.ResultMessage;
import com.herusantoso.latihan.mingguapps.model.SchoolProfile;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class HomeFragment extends Fragment {
    private View myView;
    private boolean isReload = false;
    private ImageView imgSchool;
    private TextView txtCountStudent;
    private TextView txtCountUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_home, container, false);
        imgSchool = (ImageView) myView.findViewById(R.id.img_sekolah);
        txtCountStudent = (TextView) myView.findViewById(R.id.txt_jumlah_siswa);
        txtCountUser = (TextView) myView.findViewById(R.id.txt_jumlah_user);

        bindData();

        return myView;
    }

    private void bindData() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiClient.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SchoolProfileApiInterface api = retrofit.create(SchoolProfileApiInterface.class);
        Call<ResultMessage> call = api.getProfile();
        call.enqueue(new Callback<ResultMessage>() {
            @Override
            public void onResponse(Call<ResultMessage> call, Response<ResultMessage> response) {
                String message = response.body().getMessage();

                if (message.equals("1")) {
                    Gson gson = new Gson();
                    JsonArray jsonArray = gson.toJsonTree(response.body().getResult()).getAsJsonArray();
                    //json to array
                    List<SchoolProfile> schoolProfileList = new ArrayList<SchoolProfile>();
                    schoolProfileList = gson.fromJson(jsonArray.toString(), new TypeToken<ArrayList<SchoolProfile>>(){}.getType());

                    txtCountStudent.setText(schoolProfileList.get(0).getStudentCount().toString());

                    Glide.with(getContext())
                            .load(ApiClient.URL + schoolProfileList.get(0).getPicture())
                            .placeholder(R.drawable.avatar)
                            .listener(new RequestListener<String, GlideDrawable>() {
                                @Override
                                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                    if(isReload == true){
                                        bindData();
                                        isReload = false;
                                    }
                                    return false;
                                }
                            })
                            .into(imgSchool);
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), response.body().getResult().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResultMessage> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getActivity().getApplicationContext(), "Jaringan Error !", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
