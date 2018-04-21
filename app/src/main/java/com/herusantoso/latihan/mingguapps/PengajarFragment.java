package com.herusantoso.latihan.mingguapps;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.herusantoso.latihan.mingguapps.adapter.LecturerRecyclerviewAdapter;
import com.herusantoso.latihan.mingguapps.api.ApiClient;
import com.herusantoso.latihan.mingguapps.api.LecturerApiInterface;
import com.herusantoso.latihan.mingguapps.model.Lecturer;
import com.herusantoso.latihan.mingguapps.model.ResultMessage;
import com.herusantoso.latihan.mingguapps.model.Student;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PengajarFragment extends Fragment {
    private View myView;

    private List<Lecturer> lecturers = new ArrayList<>();
    private LecturerRecyclerviewAdapter viewAdapter;

    private ProgressBar progressBar;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_pengajar, container, false);

        progressBar = (ProgressBar) myView.findViewById(R.id.progress_bar);
        recyclerView = (RecyclerView) myView.findViewById(R.id.recycleView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(viewAdapter);

        bindData();

        return myView;
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    private void bindData(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiClient.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LecturerApiInterface api = retrofit.create(LecturerApiInterface.class);
        Call<ResultMessage> call = api.getAll();

        call.enqueue(new Callback<ResultMessage>() {
            @Override
            public void onResponse(Call<ResultMessage> call, Response<ResultMessage> response) {
                String value = response.body().getMessage();
                progressBar.setVisibility(View.GONE);

                if(value.equals("1")){
                    Gson gson = new Gson();
                    JsonArray jsonArray = gson.toJsonTree(response.body().getResult()).getAsJsonArray();

                    lecturers = gson.fromJson(jsonArray.toString(), new TypeToken<ArrayList<Lecturer>>(){}.getType());

                    viewAdapter = new LecturerRecyclerviewAdapter(getActivity(), lecturers);
                    recyclerView.setAdapter(viewAdapter);
                }
            }

            @Override
            public void onFailure(Call<ResultMessage> call, Throwable t) {
                t.printStackTrace();
                progressBar.setVisibility(myView.GONE);
                Toast.makeText(getActivity().getApplicationContext(), "Jaringan error", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
