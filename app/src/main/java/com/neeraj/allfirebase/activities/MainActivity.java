package com.neeraj.allfirebase.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.neeraj.allfirebase.R;

public class MainActivity extends AppCompatActivity {
TextView otp_text;
EditText person_email_edittext,person_username_edt;
Button login_btn;
TextView forgot_pass_txt,signup_text,fb_text;
FirebaseAuth firebaseAuth;
ProgressDialog pd;
    private SignInButton google_sign_in_btn;
    private GoogleSignInClient mGoogleSignInClient;
    private  String TAG = "MainActivity";
    private FirebaseAuth mAuth;
    private int RC_SIGN_IN = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        signup_text=findViewById(R.id.signup_text);
        forgot_pass_txt=findViewById(R.id.forgot_pass_txt);
        person_email_edittext=findViewById(R.id.person_email_edittext);
        person_username_edt=findViewById(R.id.person_username_edt);
        login_btn=findViewById(R.id.login_btn);
        otp_text=findViewById(R.id.otp_text);
        fb_text=findViewById(R.id.fb_text);
        firebaseAuth=FirebaseAuth.getInstance();


        pd=new ProgressDialog(MainActivity.this);
        google_sign_in_btn=findViewById(R.id.google_sign_in_btn);
        mAuth=FirebaseAuth.getInstance();
        fb_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this, FacebookActivity.class);
                startActivity(i);
            }
        });
        GoogleSignInOptions geo=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient= GoogleSignIn.getClient(this,geo);
        google_sign_in_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
        otp_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this, OtpActivity.class);
                startActivity(i);
            }
        });
        forgot_pass_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=person_email_edittext.getText().toString();
                if(!email.equalsIgnoreCase(""))
                {
                    firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(MainActivity.this,"Email reset",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(MainActivity.this,"Email  not reset",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Please enter the valid email",Toast.LENGTH_SHORT).show();
                }

            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=person_email_edittext.getText().toString().trim();
                String pass=person_username_edt.getText().toString().trim();
                if(!email.equalsIgnoreCase(""))
                {
               if(!pass.equalsIgnoreCase(""))
               {
                loginUser(email,pass);
               }
               else
               {
                   Toast.makeText(MainActivity.this,"Please enter password",Toast.LENGTH_SHORT).show();
               }
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Please enter email id",Toast.LENGTH_SHORT).show();
                }
            }
        });

        signup_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(i);
            }
        });
    }
    public void loginUser(String email,String pass)
    {
        firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                pd.show();
                pd.setMessage("Please wait");
           if(task.isSuccessful())
           {
               Toast.makeText(MainActivity.this,"Log in sucessfully",Toast.LENGTH_SHORT).show();
               pd.dismiss();
               Intent i=new Intent(MainActivity.this, DashBoardActivity.class);
               startActivity(i);
               finish();
           }
           else
           {
               String errormsg=task.getException().getMessage();
               Toast.makeText(MainActivity.this,""+errormsg,Toast.LENGTH_SHORT).show();
               pd.dismiss();
           }
            }
        });
    }
    private void signIn()
    {
        Intent i=mGoogleSignInClient.getSignInIntent();
        startActivityForResult(i,RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RC_SIGN_IN)
        {
            Task<GoogleSignInAccount> task=GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask)
    {
        try {
            GoogleSignInAccount acc=completedTask.getResult(ApiException.class);
            Toast.makeText(MainActivity.this,"Signed In sucessfully",Toast.LENGTH_LONG).show();
            FirebaseGoogleAuth(acc);
        }
        catch (ApiException e)
        {
            Toast.makeText(MainActivity.this,"Sign In Failed",Toast.LENGTH_SHORT).show();
            FirebaseGoogleAuth(null);
        }
    }
    private void FirebaseGoogleAuth(GoogleSignInAccount acct) {
        AuthCredential authCredential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Sucessfully", Toast.LENGTH_SHORT).show();
                    FirebaseUser user = mAuth.getCurrentUser();
                    // updateUI(user);
                    Intent i = new Intent(MainActivity.this, DashBoardActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    // updateUI(null);
                }
            }
        });
    }
}
