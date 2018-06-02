package com.herusantoso.latihan.mingguapps;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.herusantoso.latihan.mingguapps.api.ApiClient;
import com.herusantoso.latihan.mingguapps.api.StudentApiInterface;
import com.herusantoso.latihan.mingguapps.model.ResultMessage;
import com.herusantoso.latihan.mingguapps.model.Student;
import com.herusantoso.latihan.mingguapps.model.StudentUpdate;
import com.herusantoso.latihan.mingguapps.session.SessionManager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.R.attr.fragment;
import static android.R.attr.name;
import static android.app.Activity.RESULT_OK;
import static com.herusantoso.latihan.mingguapps.R.drawable.avatar;

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

    private Button btnUpload;
    private ImageView imgPhoto;
    private String imagePath;
    private static int LOAD_IMAGE_RESULTS = 1;

    private boolean isReload = true;

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
        btnUpload = (Button) myView.findViewById(R.id.btn_upload);
        imgPhoto = (ImageView) myView.findViewById(R.id.img_photo);

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

        imgPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] galleryPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

                if (EasyPermissions.hasPermissions(myView.getContext(), galleryPermissions)) {
                    Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i,LOAD_IMAGE_RESULTS);
                } else {
                    EasyPermissions.requestPermissions(myView.getContext(), "Access for storage",
                            101, galleryPermissions);
                }
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

                    Drawable placeholder = ContextCompat.getDrawable(myView.getContext(), R.drawable.avatar);

                    Glide.with(getContext())
                            .load(ApiClient.URL + studentList.get(0).getPhoto())
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
                            .into(imgPhoto);
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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == LOAD_IMAGE_RESULTS && resultCode == getActivity().RESULT_OK && data != null) {
            try {

                InputStream is = getActivity().getContentResolver().openInputStream(data.getData());

                uploadImage(getBytes(is));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public byte[] getBytes(InputStream is) throws IOException {
        ByteArrayOutputStream byteBuff = new ByteArrayOutputStream();

        int buffSize = 1024;
        byte[] buff = new byte[buffSize];

        int len = 0;
        while ((len = is.read(buff)) != -1) {
            byteBuff.write(buff, 0, len);
        }

        return byteBuff.toByteArray();
    }

    public void uploadImage(final byte[] imageBytes){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiClient.URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        StudentApiInterface api = retrofit.create(StudentApiInterface.class);
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), imageBytes);
        RequestBody requestNis = RequestBody.create(MediaType.parse("text/plain"), user.get(SessionManager.nis_key));
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", "image.jpg", requestFile);

        Call<ResultMessage> call = api.uploadImage(body, requestNis);
        call.enqueue(new Callback<ResultMessage>() {
            @Override
            public void onResponse(Call<ResultMessage> call, Response<ResultMessage> response) {
                String message = response.body().getMessage().toString();

                if (message.equals("1")) {
                    String url = ApiClient.URL + response.body().getResult().toString();
                    Glide.with(getContext())
                            .load(imageBytes)
                            .asBitmap()
                            .placeholder(R.drawable.avatar)
                            .into(imgPhoto);
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
