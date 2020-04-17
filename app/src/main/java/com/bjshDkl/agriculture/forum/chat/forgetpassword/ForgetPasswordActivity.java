package com.bjshDkl.agriculture.forum.chat.forgetpassword;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bjshDkl.agriculture.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


public class ForgetPasswordActivity extends AppCompatActivity implements View.OnClickListener{
    EditText emailAddressET;
    Button goBack, forgetPasswordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        bindActivity();
    }

    private void bindActivity() {
        emailAddressET = (EditText) findViewById(R.id.emailET);
        forgetPasswordButton = (Button) findViewById(R.id.resetPasswordButton);
        forgetPasswordButton.setOnClickListener(this);

        goBack = (Button) findViewById(R.id.goBackButton);
        goBack.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.goBackButton:
                onBackPressed();
                break;
            case R.id.resetPasswordButton:
                String email = emailAddressET.getText().toString();
                if(email.length()<0){
                    emailAddressET.setError("Enter Email Address.");
                }
                else if(!email.contains("@") && !email.contains(".")){
                    emailAddressET.setError("Enter Valid Address.");
                }
                else {
                    FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
//                                        Log.d(TAG, "Email sent.");
                                        Toast.makeText(ForgetPasswordActivity.this, "Instruction sent to your Email.", Toast.LENGTH_LONG).show();
                                    }
                                    else{
                                        Toast.makeText(ForgetPasswordActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        Log.d("Response",task.getException().getMessage());
                                    }
                                }
                            });

                    break;
                }
        }
    }
}
