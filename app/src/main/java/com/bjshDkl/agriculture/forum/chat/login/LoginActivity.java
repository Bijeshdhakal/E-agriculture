package com.bjshDkl.agriculture.forum.chat.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bjshDkl.agriculture.R;
import com.bjshDkl.agriculture.forum.chat.chat.ChatActivity;
import com.bjshDkl.agriculture.forum.chat.forgetpassword.ForgetPasswordActivity;
import com.bjshDkl.agriculture.forum.chat.register.RegisterActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText emailET, passwordET;
    Button loginButton, signUpButton, forgetPasswordButton;

    FirebaseAuth auth;
    DatabaseReference reference;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            Intent i = new Intent(LoginActivity.this, ChatActivity.class);
            this.finish();
            startActivity(i);
        } else {
            setContentView(R.layout.activity_login);

            bindActivity();
        }


    }

    private void bindActivity() {
        emailET = (EditText) findViewById(R.id.emailAddressET);
        passwordET = (EditText) findViewById(R.id.passwordET);

        loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(this);
        signUpButton = (Button) findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(this);
        forgetPasswordButton = (Button) findViewById(R.id.forgetPasswordButton);
        forgetPasswordButton.setOnClickListener(this);

        reference = FirebaseDatabase.getInstance().getReference().child("chat/users");

        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Signing In..");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginButton:
                loginUser();
                break;
            case R.id.signUpButton:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));

                break;
            case R.id.forgetPasswordButton:
                startActivity(new Intent(LoginActivity.this, ForgetPasswordActivity.class));
                break;
        }


    }

    private void loginUser() {
        progressDialog.show();
        String email = emailET.getText().toString();
        String password = passwordET.getText().toString();

        if (email.equals("")) {
            emailET.setError("Enter Email Address.");
        } else if (password.equals("")) {
            passwordET.setError("Enter Password.");
        } else {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        Intent i = new Intent(LoginActivity.this, ChatActivity.class);
                        LoginActivity.this.finish();
                        startActivity(i);

                    }
                    else {
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, "Error Email Address or Password", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        progressDialog.dismiss();
    }
}
