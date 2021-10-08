package com.neeraj.allfirebase.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.neeraj.allfirebase.R;

public class SignUpActivity extends AppCompatActivity {

FirebaseAuth firebaseAuth;
ProgressDialog pd;
Button signup_btn;
EditText person_edt_text,person_password_edt,person_email_edt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signup_btn=findViewById(R.id.signup_btn);
        person_edt_text=findViewById(R.id.person_edt_text);
        person_password_edt=findViewById(R.id.person_password_edt);
        person_email_edt=findViewById(R.id.person_email_edt);

        //get Instance of firebase Auth instance
        firebaseAuth=FirebaseAuth.getInstance();

        //Callback for the signup btn
        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Getting the text for values
                String name = person_edt_text.getText().toString();
                String pass = person_password_edt.getText().toString();
                String email = person_email_edt.getText().toString();


                if (!name.equalsIgnoreCase("")) {
                    if (!email.equalsIgnoreCase("")) {
                        if (!pass.equalsIgnoreCase("")) {
                            //passing the argument to the register method
                         register(pass,email);
                        } else {
                            Toast.makeText(SignUpActivity.this, "Please enter the correct pass", Toast.LENGTH_LONG).show();
                        }

                    } else {
                        Toast.makeText(SignUpActivity.this, "Please enter correct email id", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(SignUpActivity.this, "Please enter name", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    private void register(String pass, String email) {
        //FirebaseAuth Attaching callback
        firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                //Progressbar related code
                pd=new ProgressDialog(SignUpActivity.this);
                pd.setMessage("Please wait");
                pd.show();

               if(task.isSuccessful())
               {
                   Toast.makeText(SignUpActivity.this, "Sucessful", Toast.LENGTH_SHORT).show();
                   pd.dismiss();
               }
               else {
                   Toast.makeText(SignUpActivity.this, "Chutia hai tu", Toast.LENGTH_SHORT).show();
                   pd.dismiss();
               }
            }
        });
    }

}

