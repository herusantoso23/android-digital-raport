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
import com.herusantoso.latihan.mingguapps.ScheduleDetailFragment;
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
        holder.txtJurusan.setText(schedule.getJurusan());
        holder.txtTeacher.setText(schedule.getTeacher());
    }

    @Override
    public int getItemCount() {
        return schedules.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txtTime;
        private TextView txtKelas;
        private TextView txtMapel;
        private TextView txtJurusan;
        private TextView txtTeacher;

        Activity activity;


        public ViewHolder(View itemView) {
            super(itemView);
            activity = (Activity) context;

            txtTime = (TextView) itemView.findViewById(R.id.txt_waktu);
            txtKelas = (TextView) itemView.findViewById(R.id.txt_nama_kelas);
            txtMapel = (TextView) itemView.findViewById(R.id.txt_mapel);
            txtJurusan = (TextView) itemView.findViewById(R.id.txt_jurusan);
            txtTeacher = (TextView) itemView.findViewById(R.id.txt_teacher);


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            String kelas = txtKelas.getText().toString();
            String mapel = txtMapel.getText().toString();
            String time = txtTime.getText().toString();;
            String teacher = txtTeacher.getText().toString();
            String jurusan = txtJurusan.getText().toString();

            Bundle data = new Bundle();
            data.putString(ScheduleDetailFragment.KEY_CLASS_NAME, kelas);
            data.putString(ScheduleDetailFragment.KEY_MAPEL, mapel);
            data.putString(ScheduleDetailFragment.KEY_TIME, time);
            data.putString(ScheduleDetailFragment.KEY_JURUSAN, jurusan);
            data.putString(ScheduleDetailFragment.KEY_TEACHER, teacher);

            Fragment fragment = new ScheduleDetailFragment();
            fragment.setArguments(data);
            FragmentManager fragmentManager = ((FragmentActivity)context).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content_frame, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }


}
