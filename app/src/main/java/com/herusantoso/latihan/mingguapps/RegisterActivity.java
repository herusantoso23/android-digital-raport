package com.herusantoso.latihan.mingguapps;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.herusantoso.latihan.mingguapps.api.ApiClient;
import com.herusantoso.latihan.mingguapps.api.StudentApiInterface;
import com.herusantoso.latihan.mingguapps.model.ResultMessage;
import com.herusantoso.latihan.mingguapps.model.Student;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {
    private Button btnRegister;
    private EditText editNis;
    private EditText editName;
    private EditText editClass;
    private EditText editPhone;
    private EditText editAddress;
    private EditText editPassword;
    private EditText editConfirmPassword;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editNis = (EditText) findViewById(R.id.edit_nis);
        editName = (EditText) findViewById(R.id.edit_name);
        editClass = (EditText) findViewById(R.id.edit_class);
        editPhone = (EditText) findViewById(R.id.edit_phone);
        editAddress = (EditText) findViewById(R.id.edit_address);
        editPassword = (EditText) findViewById(R.id.edit_password);
        editConfirmPassword = (EditText) findViewById(R.id.edit_confirm_password);
        btnRegister = (Button) findViewById(R.id.btn__register);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pass = editPassword.getText().toString();
                String confirmPass = editConfirmPassword.getText().toString();

                if(!pass.equals(confirmPass)){
                    Toast.makeText(getApplicationContext(), "Password tidak sama",
                            Toast.LENGTH_SHORT).show();
                } else {
                    bindData();
                }


            }
        });
    }

    private void bindData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiClient.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        StudentApiInterface api = retrofit.create(StudentApiInterface.class);

        Student student = new Student();
        student.setNis(editNis.getText().toString());
        student.setName(editName.getText().toString());
        student.setAddress(editAddress.getText().toString());
        student.setPhone(editPhone.getText().toString());
        student.setClassLevel(editClass.getText().toString());
        student.setPassword(editPassword.getText().toString());

        Call<ResultMessage> call = api.register(student);

        call.enqueue(new Callback<ResultMessage>() {
            @Override
            public void onResponse(Call<ResultMessage> call, Response<ResultMessage> response) {
                String message = response.body().getMessage();
                String result = response.body().getResult().toString();

                if (message.equals("1")) {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    Toast.makeText(getApplicationContext(), result,
                            Toast.LENGTH_SHORT).show();
                    startActivity(intent);

                } else {
                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResultMessage> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getApplicationContext(), "Jaringan Error !", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
