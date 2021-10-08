package com.neeraj.allfirebase.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.neeraj.allfirebase.R;
import com.neeraj.allfirebase.models.usernotesModel;

import java.util.ArrayList;
import java.util.List;

public class DashBoardActivity extends AppCompatActivity {

    TextView header_txt;
    Button logout_btn;
    ListView notes_listview;
    ProgressDialog pd;
    DatabaseReference databaseReference;
    FloatingActionButton fab_btn;
    ArrayList<String> a;
    private FirebaseAuth mAuth;
    List<usernotesModel> allnotesList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);


        header_txt=findViewById(R.id.header_txt);
        fab_btn=findViewById(R.id.fab_btn);
        mAuth= FirebaseAuth.getInstance();
        logout_btn=findViewById(R.id.logout_btn);
        notes_listview=findViewById(R.id.notes_listview);


        pd=new ProgressDialog(this);
        pd.setTitle("Please wait");


        databaseReference= FirebaseDatabase.getInstance().getReference("USERNOTES");
        fab_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(DashBoardActivity.this, AddNoteActivity.class);
                startActivity(i);

            }
        });

        FirebaseUser user = mAuth.getCurrentUser();
        updateUI(user);
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Toast.makeText(DashBoardActivity.this,"Log out sucessfully",Toast.LENGTH_LONG).show();
                Intent i=new Intent(DashBoardActivity.this,MainActivity.class);
                startActivity(i);
            }
        });
        allnotes();
    }
    private void updateUI(FirebaseUser firebaseUser) {
        // b.setVisibility(View.VISIBLE);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if (account != null) {
            String personName = account.getDisplayName();
            String personGivenName = account.getGivenName();
            String personFamilyName = account.getFamilyName();
            String personEmail = account.getEmail();
            String personId = account.getId();
            Uri personPhoto = account.getPhotoUrl();
            header_txt.setText(personEmail + "\n" + personName);

        }
    }
    public void allnotes()
    {
       pd.setTitle("Please wait");
       pd.show();
       a=new ArrayList<>();
       databaseReference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              for(DataSnapshot snapshot:dataSnapshot.getChildren())
              {
                  usernotesModel u=snapshot.getValue(usernotesModel.class);
                  String id=u.getNoteId();
                  String dates=u.getNoteDates();
                  String desc=u.getNoteTitle();
                  a.add(desc);
                  a.add(dates);
                  pd.dismiss();

              }
              ArrayAdapter arrayAdapter=new ArrayAdapter(DashBoardActivity.this,android.R.layout.simple_list_item_1,a);
               notes_listview.setAdapter(arrayAdapter);
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {
            pd.dismiss();
           }
       });


    }

}
