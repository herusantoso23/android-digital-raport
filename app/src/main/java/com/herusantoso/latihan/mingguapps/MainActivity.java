package com.herusantoso.latihan.mingguapps;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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
import com.herusantoso.latihan.mingguapps.api.StudentApiInterface;
import com.herusantoso.latihan.mingguapps.model.ResultMessage;
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

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private ImageView imgFoto;
    private TextView txtName;
    private TextView txtPhone;

    HashMap<String, String> user;
    SessionManager session;

    Boolean isReload = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        session = new SessionManager(getApplicationContext());
        user = session.getUserDetails();

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View hView =  navigationView.getHeaderView(0);
        imgFoto = (ImageView) hView.findViewById(R.id.imageView);
        txtName = (TextView) hView.findViewById(R.id.txt_name);
        txtPhone = (TextView) hView.findViewById(R.id.txt_phone);
        imgFoto.setImageResource(R.drawable.avatar);
        bindData();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, new ProfileFragment())
                    .commit();
        } else if (id == R.id.nav_pengajr) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, new PengajarFragment())
                    .commit();
        } else if (id == R.id.nav_jadwal) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, new ScheduleFragment())
                    .commit();
        } else if (id == R.id.nav_raport) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, new RaportFragment())
                    .commit();
        } else if (id == R.id.nav_logout){
            session.logout();
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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

                    txtName.setText(studentList.get(0).getName().toString());
                    txtPhone.setText(studentList.get(0).getPhone().toString());
                    Glide.with(getApplicationContext())
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
                            .into(imgFoto);
                } else {
                    Toast.makeText(getApplicationContext(), response.body().getResult().toString(), Toast.LENGTH_SHORT).show();
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
