package com.neeraj.allfirebase;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import static java.util.concurrent.TimeUnit.*;

public class otp extends AppCompatActivity {
EditText e,e1;
Button b,v;
String codesent;
ProgressDialog progressDialog;
FirebaseAuth firebaseAuth;
    String  mobileno;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks verificationStateChangedCallbacks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        e = findViewById(R.id.mobile);
        e1 = findViewById(R.id.e1);
        v = findViewById(R.id.v);
        firebaseAuth=FirebaseAuth.getInstance();
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codeVerify();
            }
        });
        b = findViewById(R.id.veri);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getVarification();
            }
        });
    }
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
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(otp.this,"Code verifed",Toast.LENGTH_LONG).show();
                            Intent i=new Intent(otp.this,dash.class);
                            startActivity(i);
                        } else {
                            Toast.makeText(otp.this,"Task  not complete",Toast.LENGTH_LONG).show();
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(otp.this,"code error",Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }
    private void getVarification()
    {
        String phonenumber=e.getText().toString();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phonenumber,60, SECONDS,this,mCallbacks);

    }

    private void codeVerify() {
        String code=e1.getText().toString();
        PhoneAuthCredential credential=PhoneAuthProvider.getCredential(codesent,code);
        signInWithPhoneAuthCredential(credential);
    }
}
