package com.herusantoso.latihan.mingguapps;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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

public class PengajarProfileFragment extends Fragment {
    private View myView;
    private TextView txtName;
    private TextView txtPhone;
    private TextView txtAddress;
    private TextView txtMapel;
    private ImageView imgPhoto;
    private Button btnCall;
    private Button btnMessage;

    public static String KEY_NIP = "nip";

    String nip;

    //private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_pengajar_profile, container, false);
        //progressBar = (ProgressBar) myView.findViewById(R.id.progress_bar);

        txtName = (TextView) myView.findViewById(R.id.txt_name);
        txtPhone = (TextView) myView.findViewById(R.id.txt_phone);
        txtAddress = (TextView) myView.findViewById(R.id.txt_address);
        txtMapel = (TextView) myView.findViewById(R.id.txt_mapel);
        imgPhoto = (ImageView) myView.findViewById(R.id.img_photo);
        btnCall = (Button) myView.findViewById(R.id.btn_call);
        btnMessage = (Button) myView.findViewById(R.id.btn_message);

        nip = getArguments().getString(KEY_NIP);

        bindData(nip);

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+txtPhone.getText().toString()));
                startActivity(callIntent);
            }
        });

        btnMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent msgIntent = new Intent(Intent.ACTION_VIEW);
                msgIntent.setData(Uri.parse("sms:"+txtPhone.getText().toString()));
                startActivity(msgIntent);
            }
        });

        return myView;
    }

    @Override
    public void onResume() {
        super.onResume();
        bindData(nip);

    }

    private void bindData(String nip){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiClient.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LecturerApiInterface api = retrofit.create(LecturerApiInterface.class);
        Call<ResultMessage> call = api.getDetail(nip);

        call.enqueue(new Callback<ResultMessage>() {
            @Override
            public void onResponse(Call<ResultMessage> call, Response<ResultMessage> response) {
                String value = response.body().getMessage();

                if(value.equals("1")){
                    //progressBar.setVisibility(myView.GONE);
                    Gson gson = new Gson();
                    JsonArray jsonArray = gson.toJsonTree(response.body().getResult()).getAsJsonArray();
                    //json to array
                    List<Lecturer> lecturerList = new ArrayList<Lecturer>();
                    lecturerList = gson.fromJson(jsonArray.toString(), new TypeToken<ArrayList<Lecturer>>(){}.getType());

                    txtName.setText(lecturerList.get(0).getName());
                    txtMapel.setText(lecturerList.get(0).getMapel());
                    txtPhone.setText(lecturerList.get(0).getPhone());
                    txtAddress.setText(lecturerList.get(0).getAddress());
                    Glide.with(getContext())
                            .load(lecturerList.get(0).getPhoto())
                            .placeholder(R.drawable.avatar)
                            .into(imgPhoto);
                } else {
                    //progressBar.setVisibility(myView.GONE);
                    Toast.makeText(getActivity().getApplicationContext(), response.body().getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResultMessage> call, Throwable t) {
                t.printStackTrace();
                //progressBar.setVisibility(myView.GONE);
                Toast.makeText(getActivity().getApplicationContext(), "Jaringan error", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
