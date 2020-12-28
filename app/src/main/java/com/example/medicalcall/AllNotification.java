package com.example.medicalcall;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AllNotification extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_notification);

        DbWorkHelper database = new DbWorkHelper(this);

        String [] ids = DbWork.getIdsPatientNotification(database);

        if (ids.length > 0){
            for (String id : ids) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.frCon, new Notification(Integer.parseInt(id)))
                        .commit();
            }
        }
        else{
            TextView txt = new TextView(this);
            txt.setText("Уведомления отсутствуют");
            txt.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            txt.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

            LinearLayout layout = (LinearLayout)findViewById(R.id.frCon);
            layout.addView(txt);

        }


    }
}