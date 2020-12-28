package com.example.medicalcall;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private DbWorkHelper _db;
    SQLiteDatabase db;

    TextView countNotice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countNotice = (TextView)findViewById(R.id.countNotice);

        _db = new DbWorkHelper(this);
        db = _db.getWritableDatabase();


        int cn = DbWork.getCountNotice(_db);
        countNotice.setText(String.valueOf(cn));

        new Thread(received).start();
    }

    public void allPacients(View view) {

        Intent intent = new Intent(MainActivity.this, AllPacient.class);
        startActivity(intent);

    }

    public void allNotice(View view) {
        Intent intent = new Intent(MainActivity.this, AllNotification.class);
        startActivity(intent);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        _db.close();
        db.close();
    }

    /**
     * Dispatch onResume() to fragments.  Note that for better inter-operation
     * with older versions of the platform, at the point of this call the
     * fragments attached to the activity are <em>not</em> resumed.
     */
    @Override
    protected void onResume() {
        super.onResume();

        db =_db.getWritableDatabase();


        int cn = DbWork.getCountNotice(_db);
        countNotice.setText(String.valueOf(cn));
    }

    Runnable received = new Runnable() {
        @Override
        public void run() {
            int i = 0;
            try {

                ServerSocket ss = new ServerSocket(7900);
                do {

                    Socket s = ss.accept();
                    InputStreamReader isr = new InputStreamReader(s.getInputStream());
                    BufferedReader br = new BufferedReader(isr);

                    String message = br.readLine();

                    int count = DbWork.getCountActivePatientNotice(_db, message);

                    int view = count > 0 ? 0 : 1;
                    Date currentData = new Date();

                    ContentValues cv = new ContentValues();
                    cv.put(DbWorkHelper.HISTORYCALL_VIEW, view);
                    cv.put(DbWorkHelper.HISTORYCALL_TIMECALL, Convert.dateToString(currentData));
                    cv.put("Patient_Id", Integer.parseInt(message));

                    db.insert(DbWorkHelper.HISTORYCALL_TABLE_NAME, null, cv);

                    countNotice.post(new Runnable() {
                        @Override
                        public void run() {
                            int cn = DbWork.getCountNotice(_db);

                            countNotice.setText(String.valueOf(cn));
                        }
                    });

                    s.close();
                    isr.close();
                    br.close();
                } while (true);

            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    };

}