package com.malenik.example.smshandler.listeners;

import android.widget.SeekBar;
import android.widget.TextView;

import com.malenik.example.smshandler.SMSState;

/**
 * Created by NIK on 14.07.2016.
 */
public class CustomSeekBarListener implements SeekBar.OnSeekBarChangeListener {

    private TextView textView;

    public CustomSeekBarListener(TextView textView) {
        this.textView = textView;
    }
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        textView.setText(String.valueOf(progress));

        if (progress == 0){
            SMSState.setCurrency("UAH");
            textView.setText("UAH");
        }
        else if(progress == 1) {
            textView.setText("USD");
            SMSState.setCurrency("USD");
        }
        else if (progress == 2){
            textView.setText("RUB");
            SMSState.setCurrency("RUB");
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        seekBar.setSecondaryProgress(seekBar.getProgress());
    }
}
