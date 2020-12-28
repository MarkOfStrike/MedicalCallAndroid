package com.example.medicalcall;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AllPacient extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_pacient);

        final TableLayout table = (TableLayout) findViewById(R.id.tablePacient);
        final DbWorkHelper database = new DbWorkHelper(this);

        final SQLiteDatabase db = database.getWritableDatabase();

        int ind = table.getChildCount();

        final String[][] patients = DbWork.getAllPatient(database);

        for (int i = 0; i< patients.length; i++){

            final int id = Integer.parseInt(patients[i][0]);
            final String pFIO = patients[i][1];

            final TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

            TextView num = new TextView(row.getContext());
            num.setLayoutParams(new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT, 1f));
            num.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            num.setText(String.valueOf(ind));


            TextView fio = new TextView(row.getContext());
            fio.setLayoutParams(new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT, 3f));
            fio.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            fio.setText(pFIO);

            LinearLayout btnContainer = new LinearLayout(row.getContext());
            btnContainer.setOrientation(LinearLayout.HORIZONTAL);
            btnContainer.setLayoutParams(new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 5f));

            Button about = new Button(btnContainer.getContext());
            about.setLayoutParams(new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT, 1f));

            about.setTextSize(10);
            about.setMinimumHeight(0);
            about.setText("Подробнее");
            about.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getBaseContext(), AboutInfoPacient.class);
                    intent.putExtra("idPacient", id);
                    startActivity(intent);
                }
            });


            Button del = new Button(btnContainer.getContext());
            del.setLayoutParams(new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT, 1f));
            del.setTextSize(10);
            del.setMinimumHeight(0);

            del.setText("Удалить");
            del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    db.execSQL(String.format("delete from %s where %s = %s", DbWorkHelper.PACIENT_TABLE_NAME, DbWorkHelper.ID, id));
                    table.removeView(row);

                    for (int i = 1; i < table.getChildCount(); i++){
                        TableRow r = (TableRow)table.getChildAt(i);

                        TextView n = (TextView)r.getChildAt(0);
                        n.setText(String.valueOf(i));
                    }
                }
            });

            btnContainer.addView(about,0);
            btnContainer.addView(del,1);

            row.addView(num, 0);
            row.addView(fio, 1);
            row.addView(btnContainer, 2);


            table.addView(row,ind);
            ind++;

        }

    }
}