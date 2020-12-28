package com.example.medicalcall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class AboutInfoPacient extends AppCompatActivity {

    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_info_pacient);

        Intent in = getIntent();

        int id = in.getIntExtra("idPacient", 1);

        adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item);

        DbWorkHelper dbase = new DbWorkHelper(this);

        SQLiteDatabase db = dbase.getWritableDatabase();

        TextView surname = (TextView)findViewById(R.id.tvSurname);
        TextView name = (TextView)findViewById(R.id.tvName);
        TextView middleName = (TextView)findViewById(R.id.tvMiddleName);
        TextView diagnosis = (TextView)findViewById(R.id.tvDiagnosis);
        TextView ward = (TextView)findViewById(R.id.tvWard);
        TextView doctor = (TextView)findViewById(R.id.tvDoc);

        ListView history = (ListView)findViewById(R.id.historyCall);
        history.setAdapter(adapter);

        Cursor res = db.query(DbWorkHelper.PACIENT_TABLE_NAME, null, DbWorkHelper.ID + " = ?",new String[]{String.valueOf(id)},null,null,null);


        if (res.moveToFirst()){

            int pSurname = res.getColumnIndex(DbWorkHelper.SURNAME);
            int pName = res.getColumnIndex(DbWorkHelper.NAME);
            int pMiddleName = res.getColumnIndex(DbWorkHelper.MIDDLENAME);
            int pDiagnosis = res.getColumnIndex(DbWorkHelper.PACIENT_DIAGNOSIS);
            int pWard = res.getColumnIndex(DbWorkHelper.PACIENT_WARD);

            surname.setText(res.getString(pSurname));
            name.setText(res.getString(pName));
            middleName.setText(res.getString(pMiddleName));
            diagnosis.setText(res.getString(pDiagnosis));
            ward.setText(res.getString(pWard));

            doctor.setText(DbWork.getFIODoctor(dbase, id));

            adapter.addAll(DbWork.getHistoryCall(dbase, id));

        }

        res.close();

    }
}