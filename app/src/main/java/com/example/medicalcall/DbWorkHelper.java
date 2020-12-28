package com.example.medicalcall;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.Random;

public class DbWorkHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "Hospital";

    public static final String ID = "Id";
    public static final String NAME = "Name";
    public static final String SURNAME = "Surname";
    public static final String MIDDLENAME = "MiddleName";

    public static final String DOCTOR_TABLE_NAME ="Doctors";


    public static final String PACIENT_TABLE_NAME = "Patients";
    public static final String PACIENT_DIAGNOSIS = "Diagnosis";
    public static final String PACIENT_WARD = "Ward";

    public static final String HISTORYCALL_TABLE_NAME = "HistoryCalls";
    public static final String HISTORYCALL_TIMECALL = "TimeCall";
    public static final String HISTORYCALL_VIEW = "View";



    /**
     * Create a helper object to create, open, and/or manage a database.
     * This method always returns very quickly.  The database is not actually
     * created or opened until one of {@link #getWritableDatabase} or
     * {@link #getReadableDatabase} is called.
     *
     * @param context to use for locating paths to the the database,
     *                {@link #onUpgrade} will be used to upgrade the database; if the database is
     *                newer, {@link #onDowngrade} will be used to downgrade the database
     */
    public DbWorkHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(String.format("CREATE TABLE %s ( %s INTEGER  NOT NULL,  %s TEXT,  %s TEXT,  %s TEXT,  PRIMARY KEY(%s));", DOCTOR_TABLE_NAME, ID, SURNAME, NAME, MIDDLENAME,ID));
        db.execSQL(String.format("CREATE TABLE %s ( %s INTEGER  NOT NULL,  %s TEXT,  %s TEXT,  %s TEXT,  %s TEXT, %s TEXT,  Doctor_Id INTEGER NOT NULL, PRIMARY KEY(%s), FOREIGN KEY(Doctor_Id) REFERENCES %s(%s) ON DELETE CASCADE ON UPDATE CASCADE);", PACIENT_TABLE_NAME,ID,SURNAME,NAME,MIDDLENAME, PACIENT_DIAGNOSIS, PACIENT_WARD, ID, DOCTOR_TABLE_NAME, ID));
        db.execSQL(String.format("CREATE TABLE %s ( %s INTEGER  NOT NULL, %s TEXT, %s Integer NOT NULL, Patient_Id INTEGER  NOT NULL , PRIMARY KEY(%s)  , FOREIGN KEY(Patient_Id) REFERENCES %s(%s) ON DELETE CASCADE ON UPDATE CASCADE);", HISTORYCALL_TABLE_NAME, ID, HISTORYCALL_TIMECALL, HISTORYCALL_VIEW, ID, PACIENT_TABLE_NAME, ID));

        String doctor = String.format("insert into %s (%s,%s,%s) values", DOCTOR_TABLE_NAME, SURNAME, NAME, MIDDLENAME);
        String pacient = String.format("insert into %s (%s, %s, %s, %s, %s, Doctor_id) values",PACIENT_TABLE_NAME, SURNAME, NAME, MIDDLENAME, PACIENT_DIAGNOSIS, PACIENT_WARD);

        db.execSQL(String.format("%s ('Кривошейко', 'Анатолий','Витальевич');", doctor));
        db.execSQL(String.format("%s ('Петренко', 'Виктор','Григорьевич');", doctor));
        db.execSQL(String.format("%s ('Василенко', 'Василий','Васильевич');", doctor));

        Random ran = new Random();

        db.execSQL(String.format("%s ('Иванов','Иван','Иванович','Ангина','3', %s);", pacient, (ran.nextInt(3)+1)));
        db.execSQL(String.format("%s ('Петров','Петр','Петрович','Эпилепсия','5', %s);", pacient, (ran.nextInt(3)+1)));
        db.execSQL(String.format("%s ('Николаенко','Виктор','Александрович','Корь','12', %s);", pacient, (ran.nextInt(3)+1)));
        db.execSQL(String.format("%s ('Мельников','Николай','Валерьевич','Отравление','1', %s);", pacient, (ran.nextInt(3)+1)));
        db.execSQL(String.format("%s ('Каликов','Артем','Владиславович','Подагра','3', %s);", pacient, (ran.nextInt(3)+1)));

    }

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     *
     * <p>
     * The SQLite ALTER TABLE documentation can be found
     * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
     * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
     * you can use ALTER TABLE to rename the old table, then create the new table and then
     * populate the new table with the contents of the old table.
     * </p><p>
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     * </p>
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + DOCTOR_TABLE_NAME);
        onCreate(db);
    }
}
