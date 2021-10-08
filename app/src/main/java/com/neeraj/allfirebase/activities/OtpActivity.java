package com.neeraj.allfirebase.activities;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.neeraj.allfirebase.R;

import static java.util.concurrent.TimeUnit.*;

public class OtpActivity extends AppCompatActivity {

    EditText mobile_edt_text,verify_otp_edt;
Button send_opt_btn,verify_btn;
String codesent;
ProgressDialog progressDialog;
FirebaseAuth firebaseAuth;
    String  mobileno;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks verificationStateChangedCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        //getting ids
        mobile_edt_text = findViewById(R.id.mobile_edt_text);
        verify_otp_edt = findViewById(R.id.verify_otp_edt);
        verify_btn = findViewById(R.id.verify_btn);
        firebaseAuth=FirebaseAuth.getInstance();
        send_opt_btn = findViewById(R.id.send_opt_btn);


        //Listners Callbacks
        verify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codeVerify();
            }
        });

        send_opt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getVarification();
            }
        });
    }

    //Phone Auth
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks()
    {

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {

        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            codesent=s;
        }
    };

    //Sign in with phone number
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(OtpActivity.this,"Code verifed",Toast.LENGTH_LONG).show();
                            Intent i=new Intent(OtpActivity.this, DashBoardActivity.class);
                            startActivity(i);
                        } else {
                            Toast.makeText(OtpActivity.this,"Task  not complete",Toast.LENGTH_LONG).show();
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(OtpActivity.this,"code error",Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }

    private void getVarification()
    {
        String phonenumber=mobile_edt_text.getText().toString();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phonenumber,60, SECONDS,this,mCallbacks);

    }

    private void codeVerify() {
        String code=verify_otp_edt.getText().toString();
        PhoneAuthCredential credential=PhoneAuthProvider.getCredential(codesent,code);
        signInWithPhoneAuthCredential(credential);
    }
}
