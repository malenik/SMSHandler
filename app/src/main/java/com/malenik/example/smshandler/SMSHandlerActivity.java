package com.malenik.example.smshandler;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.malenik.example.smshandler.listeners.MoneyBalanceTextWatcher;
import com.malenik.example.smshandler.listeners.PhoneNumberTextWatcher;
import com.malenik.example.smshandler.listeners.CustomSeekBarListener;

import java.util.ArrayList;

public class SMSHandlerActivity extends Activity implements View.OnClickListener {

    public static final String ADDRESS = "address";
    public static final String BODY = "body";

    SMSDatabaseHelper myDb;
    boolean b;
    Button btnViewSMSList;
    EditText phoneNumberTextField;
    EditText moneyBalanceTextField;
    SeekBar sb;
    ProgressDialog dialog;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme(android.R.style.Theme_Material_Light_DarkActionBar);
        setContentView(R.layout.smsreceiver);
        btnViewSMSList = (Button) findViewById(R.id.ViewSMSList);
        myDb = new SMSDatabaseHelper(this);
        //new SMSCurrencyList();

        phoneNumberTextField = (EditText) findViewById(R.id.phoneNumber);
        moneyBalanceTextField = (EditText) findViewById(R.id.moneyBalance);
        sb = (SeekBar) findViewById(R.id.seekBar);
        final TextView sbValue = (TextView) findViewById(R.id.sbValue);

        phoneNumberTextField.addTextChangedListener(new PhoneNumberTextWatcher(myDb));
        moneyBalanceTextField.addTextChangedListener(new MoneyBalanceTextWatcher());
        sb.setOnSeekBarChangeListener(new CustomSeekBarListener(sbValue));
    }

    public void onClick(View v) {
        myDb.clear();
        String s = moneyBalanceTextField.getText().toString();
        SMSState.setMoneyBalance(
                s == null || s.isEmpty() ?
                        0.0 :
                        Double.valueOf(s)
        );
        showProgressDialog();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hideProgressDialog();
            }
        }, 2000);
    }

    private void showProgressDialog() {
        if (dialog == null) {
            dialog = new ProgressDialog(this);
            dialog.setCancelable(false);
            dialog.setTitle("Please wait");
            dialog.setMessage("Adding SMS to database...");
            dialog.setIndeterminate(true);
        }
        if(SMSState.getPhoneNumber() != null && SMSState.getMoneyBalance() != null) {
            dialog.show();
        }else{
            dialog.dismiss();
        }
    }

    private void hideProgressDialog() {
        if (dialog != null && dialog.isShowing()|| SMSState.isEmpty()) {
            dialog.hide();
        }

        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, null, null, null);
        int indexBody = cursor.getColumnIndex(BODY);
        int indexAddr = cursor.getColumnIndex(ADDRESS);

        if (indexBody < 0 || !cursor.moveToFirst()) return;

        do {
            String number = cursor.getString(indexAddr);
            String body = cursor.getString(indexBody);
            //String number = "1234567890";
            //String body = "ololo atata 100.00grn atata ololo Popolnenie";

            Boolean substract = true;
            Double total = 0.0;

            boolean isContainCurrency = false;

            for (String value : SMSState.getCurrencyList()) {
                if (body.contains(value)) {
                    isContainCurrency = true;
                    break;
                }
            }

            if (SMSState.getPhoneNumber() != null &&
                    !SMSState.getPhoneNumber().isEmpty() &&
                    number.equals(SMSState.getPhoneNumber()) &&
                    isContainCurrency
                    ) {
                if (body.contains("Popolnenie") || body.contains("Popovnennya")) { //add here key words
                    substract = false;
                }
                String[] splittedByCurrency = body.split(SMSState.getCurrency());
                if (splittedByCurrency.length > 0) {
                    try {
                        String[] splittedBySpace = splittedByCurrency[0].split(" ");
                        String balance = splittedBySpace[splittedBySpace.length - 1].replaceAll("[^0-9.]", "");
                        total += Double.valueOf(balance);
                    } catch (Exception e) {
                        // error during sms parsing
                    }
                }
                b = myDb.insertData(number, body);
                SMSState.setMoneyBalance(
                        substract ?
                                SMSState.getMoneyBalance().doubleValue() - total :
                                SMSState.getMoneyBalance().doubleValue() + total

                );
            }
        } while (cursor.moveToNext());

        cursor.close();

        Cursor res = myDb.getAllData();
        if (res.getCount() == 0) {
            showMessage("Attention!", "Nothing found for this phone number");
            Toast.makeText(SMSHandlerActivity.this, "SMS is not inserted to database", Toast.LENGTH_SHORT).show();
            return;
        }

        if (SMSState.isEmpty() || b != true) {
            Toast.makeText(SMSHandlerActivity.this, "SMS is not inserted to database", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(SMSHandlerActivity.this, "SMS inserted to database", Toast.LENGTH_SHORT).show();
        }

        StringBuffer buffer = new StringBuffer();
        buffer.append("BALANCE: " + SMSState.getMoneyBalance() + " " + SMSState.getCurrency() + "\n\n");
        int i = 1;
        while (res.moveToNext()) {
            buffer.append("№:" + i + "\n");
            buffer.append("Number: " + res.getString(1) + "\n");
            buffer.append("Body: " + res.getString(2) + "\n");
            i++;
        }
        showMessage("Selected Messages", buffer.toString());
    }


    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

}