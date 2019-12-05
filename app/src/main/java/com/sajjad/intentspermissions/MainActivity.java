package com.sajjad.intentspermissions;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView resultText;
    ImageView cameraImage;

    String cameraPermission = Manifest.permission.CAMERA;
    String callPermission = Manifest.permission.CALL_PHONE;

    int cameraRequestCode = 103;
    int cameraPermissionCode = 104;
    int callPermissionCode = 105;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultText = findViewById(R.id.resultText);
        cameraImage = findViewById(R.id.cameraImage);
    }

    public void startAboutActivity(View view) {
        startActivity(new Intent(this, AboutActivity.class));
    }

    public void sendDataToSettings(View view) {
        Intent settingsIntent = new Intent(this, SettingsActivity.class);
        settingsIntent.putExtra(getResources().getString(R.string.userNameKey), "Ahmed");
        startActivity(settingsIntent);
    }

    public void startForResult(View view) {
        startActivityForResult(new Intent(this, DataActivity.class), 100);
    }

    public void captureImage(View view) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            // check for camera permission
            if (ContextCompat.checkSelfPermission(this, cameraPermission) != PackageManager.PERMISSION_GRANTED) {
                // ask for camera permission
                ActivityCompat.requestPermissions(this, new String[]{cameraPermission}, cameraPermissionCode);
            } else {
                capture();
            }

        } else {
            capture();
        }
    }

    private void capture() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, cameraRequestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK) {
            if (data != null) {
                resultText.setText(data.getStringExtra("dataToBeReturned"));
            }
        } else if (requestCode == cameraRequestCode && resultCode == RESULT_OK) {
            if (data != null) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                cameraImage.setImageBitmap(bitmap);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == cameraPermissionCode && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            capture();
        } else if (requestCode == callPermissionCode && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            callTo("12345678");
        }
    }

    public void openWebPage(View view) {
        Uri uri = Uri.parse("https://www.google.com");
        Intent webIntent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(webIntent);
    }

    public void sendEmail(View view) {
        Uri uri = Uri.parse("mailto:" + "ahmed@yahoo.com");
        Intent mailIntent = new Intent(Intent.ACTION_SENDTO, uri);
        mailIntent.putExtra(Intent.EXTRA_SUBJECT, "Title");
        startActivity(Intent.createChooser(mailIntent, "Chooser Title"));
    }

    public void sendSms(View view) {
//        Uri uri = Uri.parse("smsto:" + "1234567");
//        Intent smsIntent = new Intent(Intent.ACTION_SENDTO, uri);
//        smsIntent.putExtra("sms_body", "Text");
//        startActivity(smsIntent);

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage("1111", null, "Text", null, null);
    }

    public void makeAPhoneCall(View view) {
        callTo("123456789");
    }

    private void callTo(String phoneNumber) {
        Uri uri = Uri.parse("tel:" + phoneNumber);
        Intent callIntent = new Intent(Intent.ACTION_CALL, uri);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            // check for camera permission
            if (ContextCompat.checkSelfPermission(this, callPermission) != PackageManager.PERMISSION_GRANTED) {
                // ask for camera permission
                ActivityCompat.requestPermissions(this, new String[]{callPermission}, callPermissionCode);
            } else {
                startActivity(callIntent);
            }

        } else {
            startActivity(callIntent);
        }
    }
}