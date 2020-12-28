package com.example.medicalcall;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DbWork {

    /**ФИО Пациента
     * @param database Экземпляр БД
     * @param id Ид пациента
     * @return ФИО
     */
    public static String getFIOPatient(DbWorkHelper database, int id){
        SQLiteDatabase db = database.getWritableDatabase();

        Cursor patientData = db.query(DbWorkHelper.PACIENT_TABLE_NAME, new String[]{DbWorkHelper.SURNAME, DbWorkHelper.NAME, DbWorkHelper.MIDDLENAME}, DbWorkHelper.ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);

        String result = "";

        if (patientData.moveToFirst()){
            int pSurname = patientData.getColumnIndex(DbWorkHelper.SURNAME);
            int pName = patientData.getColumnIndex(DbWorkHelper.NAME);
            int pMiddleName = patientData.getColumnIndex(DbWorkHelper.MIDDLENAME);

            result = String.format("%s %s %s", patientData.getString(pSurname), patientData.getString(pName), patientData.getString(pMiddleName));
        }

        patientData.close();
        //db.close();

        return result;
    }

    /**Получение номера палаты
     *
     * @param database Экземпляр БД
     * @param id Ид Пациента
     * @return Номер палаты
     */
    public static String getWard(DbWorkHelper database, int id){
        SQLiteDatabase db = database.getWritableDatabase();

        Cursor patientData = db.query(DbWorkHelper.PACIENT_TABLE_NAME, new String[]{DbWorkHelper.PACIENT_WARD}, DbWorkHelper.ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);

        String result = "";

        if (patientData.moveToFirst()){
            int ward = patientData.getColumnIndex(DbWorkHelper.PACIENT_WARD);
            result = patientData.getString(ward);
        }

        patientData.close();
        //db.close();

        return result;
    }

    /**
     * Метод получения ФИО врача
     *
     * @param id Идентификатор пациента
     * @return ФИО лечащего врача
     */
    public static String getFIODoctor(DbWorkHelper database, int id){
        SQLiteDatabase db = database.getWritableDatabase();

        Cursor patient = db.query(DbWorkHelper.PACIENT_TABLE_NAME, new String[]{"Doctor_Id"}, DbWorkHelper.ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);

        String result = "";

        if (patient.moveToFirst()){
            int doc = patient.getColumnIndex("Doctor_Id");

            Cursor doctor = db.query(DbWorkHelper.PACIENT_TABLE_NAME, new String[]{DbWorkHelper.SURNAME, DbWorkHelper.NAME, DbWorkHelper.MIDDLENAME}, DbWorkHelper.ID + " = ?", new String[]{patient.getString(doc)}, null, null, null);
            if (doctor.moveToFirst()){
                int surname = doctor.getColumnIndex(DbWorkHelper.SURNAME);
                int name = doctor.getColumnIndex(DbWorkHelper.NAME);
                int middleName = doctor.getColumnIndex(DbWorkHelper.MIDDLENAME);

                result = String.format("%s %s %s", doctor.getString(surname), doctor.getString(name), doctor.getString(middleName));
            }

            doctor.close();
        }

        patient.close();
        //db.close();

        return result;
    }

    /**Получение идентификаторов пациентов в уведовмлениях
     * @param database Экземпляр БД
     * @return ИД пациентов в уведомлениях
     */
    public static String [] getIdsPatientNotification(DbWorkHelper database){

        SQLiteDatabase db = database.getWritableDatabase();

        String query = String.format("select * from %s where %s = 1", DbWorkHelper.HISTORYCALL_TABLE_NAME, DbWorkHelper.HISTORYCALL_VIEW);
        Cursor res = db.rawQuery(query, null);

        String[] result = new String[res.getCount()];

        if (res.moveToFirst()){

            int id = res.getColumnIndex("Patient_Id");

            int i = 0;

            do {
                result[i] = res.getString(id);
                i++;
            }while (res.moveToNext());
        }

        res.close();
        //db.close();

        return result;
    }

    /**Количество уведомлений
     * @param database Экземпляр БД
     * @return Количество уведомлений
     */
    public static int getCountNotice(DbWorkHelper database){
        SQLiteDatabase db = database.getWritableDatabase();

        Cursor data = db.query(DbWorkHelper.HISTORYCALL_TABLE_NAME, null, DbWorkHelper.HISTORYCALL_VIEW + " = 1", null, null, null, null);
        int result = data.getCount();

        data.close();
        //db.close();

        return result;
    }

    /** Получение количества активных запросов пациента
     * @param database Экземпляр БД
     * @param id ИД пациентка
     * @return Кол-во запросов
     */
    public static int getCountActivePatientNotice(DbWorkHelper database, String id){
        SQLiteDatabase db = database.getWritableDatabase();

        Cursor data = db.query(DbWorkHelper.HISTORYCALL_TABLE_NAME, null, "Patient_Id = ? and " + DbWorkHelper.HISTORYCALL_VIEW + " = 1", new String[]{id}, null, null, null);
        int result = data.getCount();

        data.close();
        //db.close();

        return result;
    }

    /**Получение всех пациентов
     * @param database Экземпляр БД
     * @return Все пациенты
     */
    public static String[][] getAllPatient(DbWorkHelper database){
        String[][] result;

        SQLiteDatabase db = database.getWritableDatabase();

        Cursor res = db.query(DbWorkHelper.PACIENT_TABLE_NAME, null,null,null,null,null,DbWorkHelper.ID + " ASC");

        result = new String[res.getCount()][];

        if (res.moveToFirst()){

            int id = res.getColumnIndex(DbWorkHelper.ID);

            int name = res.getColumnIndex(DbWorkHelper.NAME);
            int surname = res.getColumnIndex(DbWorkHelper.SURNAME);
            int middlename = res.getColumnIndex(DbWorkHelper.MIDDLENAME);

            int i = 0;

            do{
                String[] mas = new String[2];
                mas[0] = res.getString(id);
                mas[1] = String.format("%s %s. %s.", res.getString(surname), res.getString(name).toUpperCase().charAt(0), res.getString(middlename).toUpperCase().charAt(0));

                result[i] = mas;
                i++;

            }while (res.moveToNext());

        }

        res.close();
        return result;
    }

    /** Получение времени первого вызова
     * @param database Экземпляр БД
     * @param id ИД Пациента
     * @return Время вызова
     */
    public static String getTimeCall(DbWorkHelper database, int id){
        SQLiteDatabase db = database.getWritableDatabase();

        Cursor time = db.query(
                DbWorkHelper.HISTORYCALL_TABLE_NAME,
                new String[]{DbWorkHelper.HISTORYCALL_TIMECALL},
                "Patient_Id = ? and " + DbWorkHelper.HISTORYCALL_VIEW + " = 1",
                new String[]{String.valueOf(id)},
                null,
                null,
                null);

        String result = "";

        if (time.moveToFirst()){

            int cTime = time.getColumnIndex(DbWorkHelper.HISTORYCALL_TIMECALL);

            result = time.getString(cTime);

        }

        time.close();
        //db.close();

        return result;
    }

    /** Получение истории вызовов пациентом
     * @param database Экземпляр БД
     * @param id ИД пациента
     * @return История вызовов
     */
    public static String[] getHistoryCall(DbWorkHelper database, int id){
        SQLiteDatabase db = database.getWritableDatabase();

        Cursor time = db.query(
                DbWorkHelper.HISTORYCALL_TABLE_NAME,
                new String[]{DbWorkHelper.HISTORYCALL_TIMECALL},
                "Patient_Id = ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                DbWorkHelper.HISTORYCALL_TIMECALL + " DESC");

        String[] result = new String[time.getCount()];

        if (time.moveToFirst()){

            int cTime = time.getColumnIndex(DbWorkHelper.HISTORYCALL_TIMECALL);

            int i = 0;

            do {
                result[i] = time.getString(cTime);
                i++;
            }while (time.moveToNext());
        }

        time.close();
        //db.close();


        return result;
    }







}
