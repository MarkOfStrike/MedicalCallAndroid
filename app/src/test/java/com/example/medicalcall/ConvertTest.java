package com.example.medicalcall;

import org.junit.Test;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ConvertTest {

    /**
     * Тестирование конвертировая даты в строку
     */
    @Test
    public void dateToString() {

        Calendar c = new GregorianCalendar(2020,11,12,1,0,0);

        String date = "12.12.2020 01:00:00";

        assertEquals(date, Convert.dateToString(c.getTime()));

        c.set(2021,13,1);

        assertNotEquals(date, Convert.dateToString(c.getTime()));

    }

    /**Тестирование конвертирования строки в дату
     *
     * @throws ParseException
     */
    @Test
    public void stringToDate() throws ParseException {

        Date d = new Date();

        String date = Convert.dateToString(d);

        assertEquals(d.toString(), Convert.stringToDate(date).toString());

    }
}