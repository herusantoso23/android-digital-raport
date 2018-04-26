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
import android.widget.TextView;

import com.herusantoso.latihan.mingguapps.PengajarProfileFragment;
import com.herusantoso.latihan.mingguapps.R;
import com.herusantoso.latihan.mingguapps.model.Schedule;

import java.util.List;

/**
 * Created by santoso on 4/12/18.
 */

public class ScheduleRecyclerviewAdapter extends RecyclerView.Adapter<ScheduleRecyclerviewAdapter.ViewHolder>{
    private Context context;
    private List<Schedule> schedules;

    public ScheduleRecyclerviewAdapter(Context context, List<Schedule> schedules) {
        this.context = context;
        this.schedules = schedules;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_schedule, parent, false);
        ViewHolder holder = new ViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Schedule schedule = schedules.get(position);
        holder.txtTime.setText(schedule.getTime());
        holder.txtKelas.setText(schedule.getClassName());
        holder.txtMapel.setText(schedule.getMapelName());
    }

    @Override
    public int getItemCount() {
        return schedules.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txtTime;
        private TextView txtKelas;
        private TextView txtMapel;
        Activity activity;

        public ViewHolder(View itemView) {
            super(itemView);
            activity = (Activity) context;

            txtTime = (TextView) itemView.findViewById(R.id.txt_waktu);
            txtKelas = (TextView) itemView.findViewById(R.id.txt_nama_kelas);
            txtMapel = (TextView) itemView.findViewById(R.id.txt_mapel);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            
        }
    }


}
