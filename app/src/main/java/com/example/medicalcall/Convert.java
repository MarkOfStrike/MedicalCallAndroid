package com.example.medicalcall;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Convert {

    private static DateFormat getDateFormat(){
        return new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    }

    public static String dateToString(Date date){
        return  getDateFormat().format(date);
    }

    public static Date stringToDate(String date) throws ParseException {
        return getDateFormat().parse(date);
    }


}
