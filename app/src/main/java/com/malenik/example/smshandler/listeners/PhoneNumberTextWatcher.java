package com.malenik.example.smshandler.listeners;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.malenik.example.smshandler.SMSDatabaseHelper;
import com.malenik.example.smshandler.SMSState;

/**
 * Created by NIK on 14.07.2016.
 */
public class PhoneNumberTextWatcher implements TextWatcher {

    private SMSDatabaseHelper dbHelper;

    public PhoneNumberTextWatcher(SMSDatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        dbHelper.clear();
        SMSState.setPhoneNumber(s.toString());
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
