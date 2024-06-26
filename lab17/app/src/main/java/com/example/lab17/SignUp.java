package com.example.lab17;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
public class SignUp extends AppCompatActivity {
    EditText editTextUsername, editTextFullname, editTextPhone, editTextEmail, editTextPassword;
    Button signUpButton;
    TextView logInTextView;
    ProgressBar progressBar;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        editTextUsername = findViewById(R.id.username);
        editTextFullname = findViewById(R.id.fullname);
        editTextPhone = findViewById(R.id.phone);
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        signUpButton = findViewById(R.id.signUpButton);
        logInTextView = findViewById(R.id.loginText);
        progressBar = findViewById(R.id.progress);


        logInTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LogIn.class);
                startActivity(intent);
                finish();
            }
        });
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username, fullname, phone, email, password;
                username = String.valueOf(editTextUsername.getText());
                fullname = String.valueOf(editTextFullname.getText());
                phone = String.valueOf(editTextPhone.getText());
                email = String.valueOf(editTextEmail.getText());
                password = String.valueOf(editTextPassword.getText());

                if(!username.equals("")&&!fullname.equals("")&&!phone.equals("")&&!email.equals("")&&!password.equals(""))
                {
                    progressBar.setVisibility(View.VISIBLE);
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String[] field = new String[5];
                            field[0] = "username";
                            field[1] = "fullname";
                            field[2] = "phone";
                            field[3] = "email";
                            field[4] = "password";
                            String[] data = new String[5];
                            data[0] = username;
                            data[1] = fullname;
                            data[2] = phone;
                            data[3] = email;
                            data[4] = password;
                            PutData putData = new PutData(
                                    getString(R.string.server)+"/signup.php",
                                    "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    progressBar.setVisibility(View.GONE);
                                    String result = putData.getResult();
                                    if (result.equals("Sign Up Success")) {
                                        Intent intent = new
                                                Intent(getApplicationContext(), LogIn.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(getApplicationContext(),"All field required!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}