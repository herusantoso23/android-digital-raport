package com.herusantoso.latihan.mingguapps;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.herusantoso.latihan.mingguapps.api.ApiClient;
import com.herusantoso.latihan.mingguapps.api.StudentApiInterface;
import com.herusantoso.latihan.mingguapps.model.ResultMessage;
import com.herusantoso.latihan.mingguapps.model.Student;
import com.herusantoso.latihan.mingguapps.model.StudentUpdate;
import com.herusantoso.latihan.mingguapps.session.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.R.id.edit;

public class ProfileFragment extends Fragment {

    private View myView;
    private EditText editNis;
    private EditText editName;
    private EditText editClass;
    private EditText editPhone;
    private EditText editAddress;

    private Button btnSimpan;
    private Button btnChangePassword;

    HashMap<String, String> user;
    SessionManager session;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_profile, container, false);
        session = new SessionManager(getActivity().getApplicationContext());
        user = session.getUserDetails();

        editNis = (EditText) myView.findViewById(R.id.edit_nis);
        editName = (EditText) myView.findViewById(R.id.edit_name);
        editClass = (EditText) myView.findViewById(R.id.edit_class);
        editPhone = (EditText) myView.findViewById(R.id.edit_phone);
        editAddress = (EditText) myView.findViewById(R.id.edit_address);

        btnSimpan = (Button) myView.findViewById(R.id.btn_save);
        btnChangePassword = (Button) myView.findViewById(R.id.btn_change_pass);

        bindData();

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateData();
            }
        });

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new PasswordFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return myView;
    }

    private void bindData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiClient.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        StudentApiInterface api = retrofit.create(StudentApiInterface.class);
        Call<ResultMessage> call = api.getProfile(user.get(SessionManager.nis_key));
        call.enqueue(new Callback<ResultMessage>() {
            @Override
            public void onResponse(Call<ResultMessage> call, Response<ResultMessage> response) {
                String message = response.body().getMessage();

                if (message.equals("1")) {
                    Gson gson = new Gson();
                    JsonArray jsonArray = gson.toJsonTree(response.body().getResult()).getAsJsonArray();
                    //json to array
                    List<Student> studentList = new ArrayList<Student>();
                    studentList = gson.fromJson(jsonArray.toString(), new TypeToken<ArrayList<Student>>(){}.getType());

                    editNis.setText(studentList.get(0).getNis());
                    editName.setText(studentList.get(0).getName());
                    editClass.setText(studentList.get(0).getClassLevel());
                    editAddress.setText(studentList.get(0).getAddress());
                    editPhone.setText(studentList.get(0).getPhone());
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


    private void updateData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiClient.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        StudentUpdate studentUpdate = new StudentUpdate();
        studentUpdate.setNis(editNis.getText().toString());
        studentUpdate.setClassLevel(editClass.getText().toString());
        studentUpdate.setPhone(editPhone.getText().toString());
        studentUpdate.setAddress(editAddress.getText().toString());

        StudentApiInterface api = retrofit.create(StudentApiInterface.class);
        Call<ResultMessage> call = api.updateProfile(studentUpdate);
        call.enqueue(new Callback<ResultMessage>() {
            @Override
            public void onResponse(Call<ResultMessage> call, Response<ResultMessage> response) {
                String message = response.body().getMessage();

                if (message.equals("1")) {
                    Toast.makeText(getActivity().getApplicationContext(), response.body().getResult().toString(), Toast.LENGTH_SHORT).show();
                    bindData();
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
