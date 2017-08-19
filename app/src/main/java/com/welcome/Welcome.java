package com.welcome;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.LoginPack.LoginActivity;
import com.Objects.Party;
import com.example.eliott.partyplanner.MainActivity;
import com.example.eliott.partyplanner.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import java.util.zip.Inflater;

public class Welcome extends AppCompatActivity {


    Calendar cal = Calendar.getInstance();
    int yeartoday = cal.get(Calendar.YEAR);
    int monthtoday = cal.get(Calendar.MONTH);
    int daytoday = cal.get(Calendar.DAY_OF_MONTH);

    int partyYear;
    int partyMonth;
    int partyDay;
    int partyHour;
    int partyMinute;

    Button logoutbtn;
    Button newParty;

    TextView test;

    private String partyName;
    private String location;

    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TimePickerDialog.OnTimeSetListener mTimeSetListener;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);





        logoutbtn = (Button) findViewById(R.id.logoutbtn);
        newParty = (Button) findViewById(R.id.newPartybtn);






        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = firebaseAuth.getCurrentUser();

                if(user != null){





                    setTitle(user.getDisplayName());






                }else{

                    startActivity(new Intent(Welcome.this, MainActivity.class));

                }

            }
        };
        mDatabase = FirebaseDatabase.getInstance().getReference();



        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




            }
        });

        newParty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                newParty();





            }
        });


        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                if (year<yeartoday || (year==yeartoday &&month<monthtoday) ||(year==yeartoday && month == monthtoday && dayOfMonth<daytoday)){
                    Toast.makeText(Welcome.this, "Please enter a day in the future, we don't allow time-travellers", Toast.LENGTH_LONG).show();
                    chooseDate();

                }else{

                    partyYear =year;
                    partyMonth = month;
                    partyDay = dayOfMonth;

                    chooseTime();

                }

            }
        };

        mTimeSetListener = new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                partyHour = hourOfDay;
                partyMinute = minute;


                String uniqueID = UUID.randomUUID().toString();

                Intent goToNewParty = new Intent(Welcome.this, PartyManager.class);


                if(!TextUtils.isEmpty(user.getDisplayName())){
                    addNewParty(partyYear, partyMonth, partyDay, partyHour, partyMinute, user.getDisplayName(), uniqueID, location);
                    startActivity(goToNewParty);

                }else{
                    addNewParty(partyYear, partyMonth, partyDay, partyHour, partyMinute, user.getEmail(), uniqueID, location);
                    startActivity(goToNewParty);
                }


            }
        };

    }


    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();

        mAuth.removeAuthStateListener(mAuthListener);
    }

    private void newParty(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Create a new Party");

        final EditText input_field = new EditText(this);
        input_field.setHint("Party Address");

        builder.setView(input_field);
        builder.setPositiveButton("Next", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                location = input_field.getText().toString();

               chooseDate();






            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.cancel();

            }
        });

        builder.show();
    }

    private void chooseDate(){
        DatePickerDialog partyDate = new DatePickerDialog(Welcome.this, mDateSetListener, yeartoday, monthtoday, daytoday);

        partyDate.show();
    }

    private void chooseTime(){

        TimePickerDialog partyTime = new TimePickerDialog(Welcome.this, mTimeSetListener, 20, 0, true);

        partyTime.show();

    }








    private void addNewParty(int year, int month, int day, int hour, int minute, String user, String partyID, String location){

        Party party = new Party(year, month, day, hour, minute, user, location, partyID);
        mDatabase.child("Parties").child(partyID).setValue(party);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        return true;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.newParty:
                newParty();
                return true;
            case R.id.logout:
                mAuth.signOut();
                finish();
                startActivity(new Intent(Welcome.this, MainActivity.class));
                return true;
            case R.id.myProfile:

                Intent intent = new Intent(this, MyProfile.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);



        }


    }
}
