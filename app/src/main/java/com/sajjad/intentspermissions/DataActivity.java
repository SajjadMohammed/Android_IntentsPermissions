package com.sajjad.intentspermissions;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class DataActivity extends AppCompatActivity {

    EditText dataTobeReturned;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        dataTobeReturned = findViewById(R.id.dataTobeReturned);
    }


    public void goToMainActivity(View view) {
        Intent intent = getIntent();
        intent.putExtra("dataToBeReturned", dataTobeReturned.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }
}
