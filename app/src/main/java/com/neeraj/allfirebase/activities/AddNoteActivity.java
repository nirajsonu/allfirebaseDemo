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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.neeraj.allfirebase.R;
import com.neeraj.allfirebase.models.usernotesModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddNoteActivity extends AppCompatActivity {


//initialized the variables
Button post_create_btn;
EditText edt_name_text,edt_description_text;
ProgressDialog pd;
DatabaseReference databaseNotes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnote);

        //initialized the views
        post_create_btn=findViewById(R.id.post_create_btn);
        edt_name_text=findViewById(R.id.edt_name_text);
        edt_description_text=findViewById(R.id.edt_description_text);

        //initialized progress dialog
        pd=new ProgressDialog(this);

        //initialized database reference
        databaseNotes= FirebaseDatabase.getInstance().getReference("USERNOTES");

        //Set Listeners
        post_create_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //taking the inpur and convert to String
                String title=edt_name_text.getText().toString();
                String desc=edt_description_text.getText().toString();


                //Storing current date
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-mmmm-yyyy");

                //Take Calendar instance
                Calendar calendar=Calendar.getInstance();

                //todayDate
                String todayDate=simpleDateFormat.format(calendar.getTime());

                //Progressbar set title
                pd.setTitle("Please wait");

                //Progressbar show
                pd.show();

                //equalsIgnorecase for case sensitive string comparison.
                if(!title.equalsIgnoreCase(""))
                {
                    if(!desc.equalsIgnoreCase(""))
                    {
                        //Getting the key of the current node.
                    String key=databaseNotes.push().getKey();

                    //set value to userModel constuctor
                    usernotesModel u=new usernotesModel(title,desc,todayDate,key);


                    //setting the value to current node
                    databaseNotes.child(key).setValue(u).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                       if(task.isSuccessful())
                       {
                           //Success Callback
                           Toast.makeText(AddNoteActivity.this,"Notes sucessfully created",Toast.LENGTH_LONG).show();
                           finish();
                           pd.dismiss();
                       }
                       else
                       {
                           //Failure Callback
                           Toast.makeText(AddNoteActivity.this,"error",Toast.LENGTH_LONG).show();
                           finish();
                           pd.dismiss();

                       }
                        }
                    });
                    }
                    else {
                        Toast.makeText(AddNoteActivity.this,"Please Enter the description",Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(AddNoteActivity.this,"Please Enter the title",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
