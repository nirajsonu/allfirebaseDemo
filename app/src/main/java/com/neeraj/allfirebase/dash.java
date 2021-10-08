package com.neeraj.allfirebase;

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

import java.util.ArrayList;
import java.util.List;

public class dash extends AppCompatActivity {
    TextView t,t1;
    Button b;
    ListView l;
    ProgressDialog pd;
    DatabaseReference databaseReference;
    FloatingActionButton f;
    ArrayList<String> a;
    private FirebaseAuth mAuth;
    List<usernotes> allnotesList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);
        t=findViewById(R.id.t);
        f=findViewById(R.id.fa);
        mAuth= FirebaseAuth.getInstance();
        b=findViewById(R.id.but);
        pd=new ProgressDialog(this);
        pd.setTitle("Please wait");
        l=findViewById(R.id.l);
        databaseReference= FirebaseDatabase.getInstance().getReference("USERNOTES");
        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(dash.this,addnote.class);
                startActivity(i);

            }
        });
        FirebaseUser user = mAuth.getCurrentUser();
        updateUI(user);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Toast.makeText(dash.this,"Log out sucessfully",Toast.LENGTH_LONG).show();
                Intent i=new Intent(dash.this,MainActivity.class);
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
            t.setText(personEmail + "\n" + personName);

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
                  usernotes u=snapshot.getValue(usernotes.class);
                  String id=u.getNoteId();
                  String dates=u.getNoteDates();
                  String desc=u.getNoteTitle();
                  a.add(desc);
                  a.add(dates);
                  pd.dismiss();

              }
              ArrayAdapter arrayAdapter=new ArrayAdapter(dash.this,android.R.layout.simple_list_item_1,a);
              l.setAdapter(arrayAdapter);
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {
            pd.dismiss();
           }
       });


    }

}
