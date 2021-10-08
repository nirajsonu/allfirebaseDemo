package com.neeraj.allfirebase;

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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signup extends AppCompatActivity {
FirebaseAuth firebaseAuth;
ProgressDialog pd;
DatabaseReference databaseUsers;
Button b;
EditText e,e1,e2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        b=findViewById(R.id.b);
        e=findViewById(R.id.e);
        e1=findViewById(R.id.e1);
        e2=findViewById(R.id.e2);
        firebaseAuth=FirebaseAuth.getInstance();
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = e.getText().toString();
                String pass = e1.getText().toString();
                String email = e2.getText().toString();
                if (!name.equalsIgnoreCase("")) {
                    if (!email.equalsIgnoreCase("")) {
                        if (!pass.equalsIgnoreCase("")) {
                         register(name,pass,email);
                        } else {
                            Toast.makeText(signup.this, "Please enter the correct pass", Toast.LENGTH_LONG).show();
                        }

                    } else {
                        Toast.makeText(signup.this, "Please enter correct email id", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(signup.this, "Please enter name", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    private void register(String name, String pass, String email) {
        firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                pd=new ProgressDialog(signup.this);
                pd.setMessage("Please wait");
                pd.show();
               if(task.isSuccessful())
               {
                   Toast.makeText(signup.this, "Sucessful", Toast.LENGTH_SHORT).show();
                   pd.dismiss();
               }
               else {
                   Toast.makeText(signup.this, "Chutia hai tu", Toast.LENGTH_SHORT).show();
                   pd.dismiss();
               }
            }
        });
    }

}

