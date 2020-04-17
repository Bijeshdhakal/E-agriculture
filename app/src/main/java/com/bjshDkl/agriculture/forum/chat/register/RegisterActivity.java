package com.bjshDkl.agriculture.forum.chat.register;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bjshDkl.agriculture.R;
import com.bjshDkl.agriculture.forum.chat.chat.ChatActivity;
import com.bjshDkl.agriculture.forum.chat.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    EditText nameET, emailET, passwordET, confirmPasswordET;
    Button signUpButton, goBackButton;

    ProgressDialog progressDialog;
    DatabaseReference reference;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();

        setContentView(R.layout.activity_register);

        bindActivity();
    }

    private void bindActivity() {
        nameET = (EditText) findViewById(R.id.nameET);
        emailET = (EditText) findViewById(R.id.emailAddressET);
        passwordET = (EditText) findViewById(R.id.passwordET);
        confirmPasswordET = (EditText) findViewById(R.id.confirmPasswordET);

        signUpButton = (Button) findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(this);

        goBackButton = (Button) findViewById(R.id.goBack);
        goBackButton.setOnClickListener(this);

        reference = FirebaseDatabase.getInstance().getReference().child("chat/users");

        progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setMessage("Signing Up User..");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        progressDialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signUpButton:
                signUpUser();
                break;
            case R.id.goBack:
                onBackPressed();
                break;
        }
    }

    private void signUpUser() {
        final String email = emailET.getText().toString();
        final String name = nameET.getText().toString();
        String password = passwordET.getText().toString();
        String passwordAgain = confirmPasswordET.getText().toString();

        if (name.equals("")) {
            nameET.setError("Enter Name.");
        } else if (email.equals("")) {
            emailET.setError("Enter Email Address.");
        } else if (password.equals("")) {
            passwordET.setError("Enter Password.");

        } else if (passwordAgain.equals("")) {
            confirmPasswordET.setError("Enter Password.");

        } else if (!password.equals(passwordAgain)) {
            confirmPasswordET.setError("Password Didnt matched.");
        } else if (password.length() < 6) {
            passwordET.setError("Atleast 6 character is needed. ");
        } else {
//            progressDialog.show();

            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressDialog.show();

                    if (task.isSuccessful()) {

                        FirebaseUser firebaseUser = auth.getCurrentUser();
                        User user = new User();
                        user.setEmail(email);
                        user.setName(name);

                        reference.child(firebaseUser.getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()) {
                                    progressDialog.dismiss();
                                    Toast.makeText(RegisterActivity.this, "User successfully created...", Toast.LENGTH_SHORT).show();
                                    RegisterActivity.this.finish();

                                    Intent intent = new Intent(RegisterActivity.this, ChatActivity.class);
                                    startActivity(intent);
                                } else {
                                    progressDialog.dismiss();
                                    Toast.makeText(RegisterActivity.this, "User Registration Failed.. Please Try Again...", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
                    } else {
                        Log.i("Response", "Failed to create user:" + task.getException().getMessage());

                        progressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                    }

                }
            });
        }

        progressDialog.dismiss();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
