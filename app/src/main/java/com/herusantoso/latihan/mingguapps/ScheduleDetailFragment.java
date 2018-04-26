package com.herusantoso.latihan.mingguapps;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static com.herusantoso.latihan.mingguapps.PengajarProfileFragment.KEY_NIP;


public class ScheduleDetailFragment extends Fragment {
    private View myView;

    private TextView txtTime;
    private TextView txtKelas;
    private TextView txtMapel;
    private TextView txtJurusan;
    private TextView txtTeacher;

    public static String KEY_CLASS_NAME = "class_name";
    public static String KEY_MAPEL = "mapel";
    public static String KEY_TIME = "time";
    public static String KEY_TEACHER = "teacher";
    public static String KEY_JURUSAN = "jurusan";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_schedule_detail, container, false);

        txtTime = (TextView) myView.findViewById(R.id.txt_waktu);
        txtKelas = (TextView) myView.findViewById(R.id.txt_kelas);
        txtMapel = (TextView) myView.findViewById(R.id.txt_mapel);
        txtJurusan = (TextView) myView.findViewById(R.id.txt_jurusan);
        txtTeacher = (TextView) myView.findViewById(R.id.txt_teacher);

        txtTime.setText(getArguments().getString(KEY_TIME));
        txtKelas.setText(getArguments().getString(KEY_CLASS_NAME));
        txtMapel.setText(getArguments().getString(KEY_MAPEL));
        txtJurusan.setText(getArguments().getString(KEY_JURUSAN));
        txtTeacher.setText(getArguments().getString(KEY_TEACHER));

        return myView;
    }

}
