package com.sajjad.intentspermissions;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {

    TextView receivedData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        receivedData = findViewById(R.id.receivedDataText);

        String receivedText = getIntent().getStringExtra(getResources().getString(R.string.userNameKey));

        receivedData.setText(receivedText);

    }
}
