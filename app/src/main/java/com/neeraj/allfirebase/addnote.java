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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class addnote extends AppCompatActivity {
Button b;
EditText e,e1;
ProgressDialog pd;
DatabaseReference databaseNotes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnote);
        b=findViewById(R.id.b);
        e=findViewById(R.id.e);
        e1=findViewById(R.id.e1);
        pd=new ProgressDialog(this);
        databaseNotes= FirebaseDatabase.getInstance().getReference("USERNOTES");
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title=e.getText().toString();
                String desc=e1.getText().toString();
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-mmmm-yyyy");
                Calendar calendar=Calendar.getInstance();
                String todayDate=simpleDateFormat.format(calendar.getTime());
                pd.setTitle("Please wait");
                pd.show();
                if(!title.equalsIgnoreCase(""))
                {
                    if(!desc.equalsIgnoreCase(""))
                    {
                    String key=databaseNotes.push().getKey();
                    usernotes u=new usernotes(title,desc,todayDate,key);
                    databaseNotes.child(key).setValue(u).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                       if(task.isSuccessful())
                       {

                           Toast.makeText(addnote.this,"Notes sucessfully created",Toast.LENGTH_LONG).show();
                           finish();
                           pd.dismiss();
                       }
                       else
                       {  Toast.makeText(addnote.this,"error",Toast.LENGTH_LONG).show();
                           finish();
                           pd.dismiss();

                       }
                        }
                    });
                    }
                    else {
                        Toast.makeText(addnote.this,"Please Enter the description",Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(addnote.this,"Please Enter the title",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
