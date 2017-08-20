package com.welcome;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.example.eliott.partyplanner.R;
import com.google.firebase.database.DatabaseReference;

public class MyProfile extends AppCompatActivity {


    private DatabaseReference mDatabase;
    EditText firstNameChange, lastNameChange, emailChange, phoneChange,addressChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        firstNameChange = (EditText) findViewById(R.id.firstnameChange);
        lastNameChange = (EditText) findViewById(R.id.lastNameChange);
        emailChange = (EditText) findViewById(R.id.emailChange);
        phoneChange = (EditText) findViewById(R.id.phoneChange);
        addressChange = (EditText) findViewById(R.id.addressCange);





    }
}
