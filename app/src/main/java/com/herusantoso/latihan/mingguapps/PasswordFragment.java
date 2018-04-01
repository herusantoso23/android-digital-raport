package com.herusantoso.latihan.mingguapps;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.herusantoso.latihan.mingguapps.api.ApiClient;
import com.herusantoso.latihan.mingguapps.api.StudentApiInterface;
import com.herusantoso.latihan.mingguapps.model.ResultMessage;
import com.herusantoso.latihan.mingguapps.model.StudentChangePassword;
import com.herusantoso.latihan.mingguapps.model.StudentUpdate;
import com.herusantoso.latihan.mingguapps.session.SessionManager;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PasswordFragment extends Fragment {
    private View myView;

    private EditText editOldPass;
    private EditText editNewPass;
    private EditText editConfirmPass;

    private Button btnChangePass;


    HashMap<String, String> user;
    SessionManager session;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myView =  inflater.inflate(R.layout.fragment_password, container, false);

        session = new SessionManager(getActivity().getApplicationContext());
        user = session.getUserDetails();

        editOldPass = (EditText) myView.findViewById(R.id.old_pass);
        editNewPass = (EditText) myView.findViewById(R.id.new_pass);
        editConfirmPass = (EditText) myView.findViewById(R.id.confirm_pass);

        btnChangePass = (Button) myView.findViewById(R.id.btn_change_pass);
        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePass();
            }
        });

        return myView;
    }

    private void changePass(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiClient.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        StudentChangePassword changePass = new StudentChangePassword();
        changePass.setNis(user.get(SessionManager.nis_key));
        changePass.setOldPass(editOldPass.getText().toString());
        changePass.setNewPass(editNewPass.getText().toString());
        changePass.setConfirmPass(editConfirmPass.getText().toString());

        StudentApiInterface api = retrofit.create(StudentApiInterface.class);
        Call<ResultMessage> call = api.changePass(changePass);
        call.enqueue(new Callback<ResultMessage>() {
            @Override
            public void onResponse(Call<ResultMessage> call, Response<ResultMessage> response) {
                String message = response.body().getMessage();

                if (message.equals("1")) {
                    Toast.makeText(getActivity().getApplicationContext(), response.body().getResult().toString(), Toast.LENGTH_SHORT).show();
                    getFragmentManager().popBackStack();
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
