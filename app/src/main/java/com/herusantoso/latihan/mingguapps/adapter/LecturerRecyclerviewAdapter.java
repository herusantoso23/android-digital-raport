package com.herusantoso.latihan.mingguapps.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.herusantoso.latihan.mingguapps.PengajarProfileFragment;
import com.herusantoso.latihan.mingguapps.R;
import com.herusantoso.latihan.mingguapps.api.ApiClient;
import com.herusantoso.latihan.mingguapps.model.Lecturer;

import java.util.List;

/**
 * Created by santoso on 4/12/18.
 */

public class LecturerRecyclerviewAdapter extends RecyclerView.Adapter<LecturerRecyclerviewAdapter.ViewHolder>{
    private Context context;
    private List<Lecturer> lecturers;

    public LecturerRecyclerviewAdapter(Context context, List<Lecturer> lecturers) {
        this.context = context;
        this.lecturers = lecturers;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_pengajar, parent, false);
        ViewHolder holder = new ViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Lecturer lecturer = lecturers.get(position);
        holder.txtNip.setText(lecturer.getNip());
        holder.txtNama.setText(lecturer.getName());
        holder.txtMapel.setText(lecturer.getMapel());
        Glide.with(context)
                .load(ApiClient.URL + lecturer.getPhoto())
                .placeholder(R.drawable.avatar)
                .into(holder.imgPhoto);
    }

    @Override
    public int getItemCount() {
        return lecturers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txtNip;
        private TextView txtNama;
        private TextView txtMapel;
        private ImageView imgPhoto;
        Activity activity;

        public ViewHolder(View itemView) {
            super(itemView);
            activity = (Activity) context;

            txtNip = (TextView) itemView.findViewById(R.id.txt_nip);
            txtNama = (TextView) itemView.findViewById(R.id.txt_nama);
            txtMapel = (TextView) itemView.findViewById(R.id.txt_mapel);
            imgPhoto = (ImageView) itemView.findViewById(R.id.img_photo);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            String nip = txtNip.getText().toString();

            Bundle data = new Bundle();
            data.putString(PengajarProfileFragment.KEY_NIP, nip);

            Fragment fragment = new PengajarProfileFragment();
            fragment.setArguments(data);
            FragmentManager fragmentManager = ((FragmentActivity)context).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content_frame, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }


}
