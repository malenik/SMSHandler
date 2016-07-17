package com.malenik.example.smshandler;

import android.os.Bundle;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by NIK on 12.07.2016.
 */
public class SMSState {

    private static String phoneNumber;
    private static Double moneyBalance;
    private static String currency = "UAH";

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
        return SMSState.phoneNumber == null || SMSState.moneyBalance == null;
    }

}


