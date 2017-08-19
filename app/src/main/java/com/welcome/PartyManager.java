package com.welcome;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.eliott.partyplanner.R;

public class PartyManager extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_manager);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
