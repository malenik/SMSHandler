package com.malenik.example.smshandler;

import android.os.Bundle;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by NIK on 12.07.2016.
 */
public class SMSState {

    private static String phoneNumber;
    private static Double moneyBalance = 0.0;
    private static String currency = "UAH";
    private static Date startDate = new Date();
    private static Date endDate = new Date();

    public static void setStartDate(int year, int month, int date) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.DATE, date);
        SMSState.startDate = calendar.getTime();
    }

    public static void setEndDate(int year, int month, int date) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.DATE, date);
        SMSState.endDate = calendar.getTime();
    }

    public static Date getStartDate() {

        return startDate;
    }

    public static Date getEndDate() {
        return endDate;
    }

    public static String getCurrency() {
        return currency;
    }

    public static void setCurrency(String currency) {
        SMSState.currency = currency;
    }

    private static final Map<String, List<String>> currencyMap = new HashMap<String, List<String>>(){{
        put("UAH", Arrays.asList("UAH", " UAH", "GRN", " GRN", "ГРН", " ГРН", "грн", " грн"));
        put("USD", Arrays.asList("USD", " USD", "DOL", " DOL", "ДОЛ", " ДОЛ", "дол", " дол"));
        put("RUB", Arrays.asList("RUB", " RUB", "RUB", " RUB", "РУБ", " РУБ", "руб", " руб"));
    }};

    public static void setPhoneNumber(String phoneNumber) { SMSState.phoneNumber = phoneNumber; }

    public static void setMoneyBalance(Double moneyBalance) { SMSState.moneyBalance = moneyBalance; }

    public static String getPhoneNumber() {
        return SMSState.phoneNumber;
    }

    public static Double getMoneyBalance() {
        return SMSState.moneyBalance;
    }

    public static List<String> getCurrencyList() {
        return SMSState.currencyMap.get(SMSState.currency);
    }

    public static Map<String, List<String>> getCurrencyMap() {
        return SMSState.currencyMap;
    }

    public static Boolean isEmpty() {
        return SMSState.phoneNumber == null; // || SMSState.moneyBalance == null;
    }

}


