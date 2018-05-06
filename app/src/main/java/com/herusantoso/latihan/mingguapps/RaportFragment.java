package com.herusantoso.latihan.mingguapps;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.herusantoso.latihan.mingguapps.adapter.LecturerRecyclerviewAdapter;
import com.herusantoso.latihan.mingguapps.api.ApiClient;
import com.herusantoso.latihan.mingguapps.api.LecturerApiInterface;
import com.herusantoso.latihan.mingguapps.api.ParameterApiInterface;
import com.herusantoso.latihan.mingguapps.api.RaportApiInterface;
import com.herusantoso.latihan.mingguapps.model.Lecturer;
import com.herusantoso.latihan.mingguapps.model.ResultMessage;
import com.herusantoso.latihan.mingguapps.model.Semester;
import com.herusantoso.latihan.mingguapps.model.Student;
import com.herusantoso.latihan.mingguapps.session.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.R.attr.country;


public class RaportFragment extends Fragment {
    private View myView;
    private Spinner spinner;
    List<String> semesterList;

    HashMap<String, String> user;
    SessionManager session;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_raport, container, false);
        semesterList = new ArrayList<>();

        session = new SessionManager(getActivity().getApplicationContext());
        user = session.getUserDetails();

        spinner = (Spinner) myView.findViewById(R.id.spinner_semester);
        spinner.setPrompt("Select your favorite Planet!");

        bindData();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String semester = spinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString();
                if (i > 0) {
                    openPdf(user.get(SessionManager.nis_key), semester);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return myView;
    }

    private void bindData(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiClient.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ParameterApiInterface api = retrofit.create(ParameterApiInterface.class);
        Call<ResultMessage> call = api.getSemester();

        call.enqueue(new Callback<ResultMessage>() {
            @Override
            public void onResponse(Call<ResultMessage> call, Response<ResultMessage> response) {
                String value = response.body().getMessage();

                if(value.equals("1")){
                    Gson gson = new Gson();
                    JsonArray jsonArray = gson.toJsonTree(response.body().getResult()).getAsJsonArray();

                    List<Semester> semesters = new ArrayList<>();
                    semesters = gson.fromJson(jsonArray.toString(), new TypeToken<ArrayList<Semester>>(){}.getType());

                    List<String> listSpinner = new ArrayList<String>();
                    listSpinner.add("Pilih semester ..");
                    for (int i = 0; i < semesters.size(); i++){
                        listSpinner.add(semesters.get(i).getSemester());
                    }
                    // Set hasil result json ke dalam adapter spinner
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(myView.getContext().getApplicationContext(),
                            android.R.layout.simple_spinner_item, listSpinner);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<ResultMessage> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getActivity().getApplicationContext(), "Jaringan error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openPdf(String nis, String semester){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiClient.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RaportApiInterface api = retrofit.create(RaportApiInterface.class);
        Call<ResultMessage> call = api.getRaport(nis, semester);

        call.enqueue(new Callback<ResultMessage>() {
            @Override
            public void onResponse(Call<ResultMessage> call, Response<ResultMessage> response) {
                String message = response.body().getMessage();

                if (message.equals("1")) {
                    String url = ApiClient.URL + response.body().getResult().toString();

                    Intent openBrowser = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(openBrowser);

                } else {
                    Toast.makeText(getActivity().getApplicationContext(), response.body().getResult().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResultMessage> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getActivity().getApplicationContext(), "Jaringan error", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
