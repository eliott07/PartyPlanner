package com.example.eliott.partyplanner;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.LoginPack.LoginActivity;
import com.Objects.NewUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.welcome.Welcome;

public class MainActivity extends AppCompatActivity {

    //IMPORT VIEWS AND BUTTON

    Button createUser, moveToLogin;
    EditText userEmailEdit, userPassEdit, firstName, lastName;

    //FB instances

    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Link to layout

        createUser = (Button) findViewById(R.id.createUserButton);
        moveToLogin = (Button) findViewById(R.id.moveToLogin);

        userEmailEdit = (EditText) findViewById(R.id.emailEditTextCreate);
        userPassEdit = (EditText) findViewById(R.id.passEditTextCreate);
        firstName = (EditText) findViewById(R.id.firstName);
        lastName = (EditText) findViewById(R.id.lastName);


        //Assign instances

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = firebaseAuth.getCurrentUser();

                if(user != null){
                    startActivity(new Intent(MainActivity.this, Welcome.class));

                }


            }
        };

        mDatabase = FirebaseDatabase.getInstance().getReference();



        //Listen to click

        createUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String userEmailString, userPassString, userNameString;

                userEmailString = userEmailEdit.getText().toString().trim();
                userPassString = userPassEdit.getText().toString().trim();
                userNameString = firstName.getText().toString() + " " + lastName.getText().toString();


                //if none of the fields are empty, add account and display name for new user

                if(!TextUtils.isEmpty(userEmailString) && !TextUtils.isEmpty(userPassString) && !TextUtils.isEmpty(userNameString)){
                    mAuth.createUserWithEmailAndPassword(userEmailString,userPassString).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(MainActivity.this, "User account Successfully created", Toast.LENGTH_LONG).show();
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(userNameString).build();
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                user.updateProfile(profileUpdates);

                                NewUser newUser = new NewUser(firstName.getText().toString(), lastName.getText().toString(), userEmailString, userNameString);


                                mDatabase.child("Users").child(user.getUid()).setValue(newUser);




                                startActivity(new Intent(MainActivity.this, Welcome.class));

                            }
                            else {
                                Toast.makeText(MainActivity.this, "Failed account creation", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }else if(!TextUtils.isEmpty(userEmailString) && !TextUtils.isEmpty(userPassString)){
                    Toast.makeText(MainActivity.this, "Please enter your name so your friends can find you!", Toast.LENGTH_LONG).show();

                }

            }
        });

        //Move to Login
        moveToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });


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
}
