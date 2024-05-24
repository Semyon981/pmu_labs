package com.example.lab11;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {
    private TextView myText;
    private static final int CALL_PHONE_PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button bCallPhone = findViewById(R.id.bCallPhone);
        final Button bCallActivity = findViewById(R.id.bCallActivity);
        final Button bOpenCamera = findViewById(R.id.bOpenCamera);
        final Button bOpenContacts = findViewById(R.id.bOpenContacts);
        final Button bOpenMediaPlayer = findViewById(R.id.bOpenMediaPlayer);

        bCallPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                checkPermission(Manifest.permission.CALL_PHONE, CALL_PHONE_PERMISSION_CODE);
            }
        });

        bCallActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });

        bOpenCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivity(cameraIntent);
            }
        });

        bOpenContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent contactsIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivity(contactsIntent);
            }
        });

        bOpenMediaPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mediaIntent = new Intent(Intent.ACTION_VIEW);
                mediaIntent.setDataAndType(Uri.parse("https://d443ecef-ff72-4261-b3b0-9558e1241612.selstorage.ru/cat.jpg"), "image/*");
                startActivity(mediaIntent);
            }
        });

        myText = findViewById(R.id.text);
        myText.append("onCreate()\n");
    }

    public void callPhone() {
        Intent dialIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "123456789"));
        startActivity(dialIntent);
    }

    public void checkPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(MainActivity.this, permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, requestCode);
        } else {
            callPhone();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CALL_PHONE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, "Разрешение предоставлено", Toast.LENGTH_SHORT).show();
                callPhone();
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)) {
                    Toast.makeText(MainActivity.this, "Разрешение отклонено! Оно нужно для совершения звонка!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        myText.append("onRestart()\n");
    }

    @Override
    protected void onStart() {
        super.onStart();
        myText.append("onStart()\n");
    }

    @Override
    protected void onResume() {
        super.onResume();
        myText.append("onResume()\n");
    }

    @Override
    protected void onPause() {
        super.onPause();
        myText.append("onPause()\n");
    }

    @Override
    protected void onStop() {
        super.onStop();
        myText.append("onStop()\n");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myText.append("onDestroy()\n");
    }
}