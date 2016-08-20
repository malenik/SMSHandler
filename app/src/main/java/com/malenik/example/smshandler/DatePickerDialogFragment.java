package com.malenik.example.smshandler;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@SuppressLint("ValidFragment")
public class DatePickerDialogFragment extends DialogFragment implements
        DatePickerDialog.OnDateSetListener {

    public DatePickerDialogFragment(EditText date, boolean isStartDate) {
        this.date = date;
        this.isStartDate = isStartDate;
    }

    private EditText date;
    private boolean isStartDate;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);

    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, monthOfYear, dayOfMonth);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        date.setText(format.format(calendar.getTime()));

        if (isStartDate) {
            //date.setText(format.format(calendar.getTime()));
            SMSState.setStartDate(year, monthOfYear, dayOfMonth);
        } else {
            //date.setText(format.format(calendar.getTime()));
            SMSState.setEndDate(year, monthOfYear, dayOfMonth);
        }
    }
}

