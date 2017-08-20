package com.welcome;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.eliott.partyplanner.R;
import com.google.firebase.database.DatabaseReference;

public class PartyManager extends AppCompatActivity {


    Button addSomeoneBtn;
    EditText addSomeoneTxt;
    private DatabaseReference mDatabase;
    private String user;
    private boolean userExist;
    private String partyID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_manager);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addSomeoneBtn = (Button) findViewById(R.id.addSomeoneBtn);
        addSomeoneTxt = (EditText) findViewById(R.id.addSomeoneText);

        Welcome welcome = new Welcome();
        partyID = welcome.getCurrentPartyId();

        addSomeoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(addSomeoneTxt.toString())){
                    //Search database for user with name == addSomeoneTxt.toString
                    userExist=true;

                    if(userExist){
                        mDatabase.child("Users").child(user).child("PartiesInvited").setValue(partyID);
                    }

                }
            }
        });


    }
}
