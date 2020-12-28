package com.example.medicalcall;


import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


public class Notification extends Fragment {

    private int _idPacient;
    private Fragment ds;

    public Notification(int idPacient)
    {
        _idPacient = idPacient;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_notification, container,false);

        TextView fioPacient = (TextView)rootView.findViewById(R.id.tvPacient);
        TextView ward = (TextView)rootView.findViewById(R.id.tvWard);
        TextView timeCall = (TextView)rootView.findViewById(R.id.tvTimeCall);

        final DbWorkHelper database = new DbWorkHelper(this.getContext());
        final SQLiteDatabase db = database.getWritableDatabase();

        fioPacient.setText(DbWork.getFIOPatient(database, _idPacient));
        ward.setText(DbWork.getWard(database, _idPacient));
        timeCall.setText(DbWork.getTimeCall(database, _idPacient));

        Button success = (Button) rootView.findViewById(R.id.successView);
        Button about = (Button) rootView.findViewById(R.id.adoutPacient);
        Button call = (Button) rootView.findViewById(R.id.callDoctor);

        ds = this;

        success.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ContentValues cv = new ContentValues();
                cv.put(DbWorkHelper.HISTORYCALL_VIEW, 0);

                db.update(DbWorkHelper.HISTORYCALL_TABLE_NAME, cv, "Patient_Id = ?", new String[]{String.valueOf(_idPacient)});
                getActivity().getSupportFragmentManager().beginTransaction().remove(ds).commit();
            }
        });



        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AboutInfoPacient.class);
                intent.putExtra("idPacient", _idPacient);
                startActivity(intent);

            }
        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues cv = new ContentValues();
                cv.put(DbWorkHelper.HISTORYCALL_VIEW, 0);

                db.update(DbWorkHelper.HISTORYCALL_TABLE_NAME, cv, "Patient_Id = ?", new String[]{String.valueOf(_idPacient)});

                String message = String.format("Был вызван врач %s", DbWork.getFIODoctor(database, _idPacient));
                SendMessage sm = new SendMessage();
                sm.execute(message);
                getActivity().getSupportFragmentManager().beginTransaction().remove(ds).commit();


            }
        });

        return rootView;

    }
}
