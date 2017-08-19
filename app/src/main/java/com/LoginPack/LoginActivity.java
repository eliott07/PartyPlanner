package com.LoginPack;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eliott.partyplanner.MainActivity;
import com.example.eliott.partyplanner.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.welcome.Welcome;

public class LoginActivity extends AppCompatActivity {

    Button loginbtn;
    EditText emailLogEdit, passLogEdit;

    String userEmail, userPass;



    //FB instances

    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //Assign id's

        loginbtn = (Button) findViewById(R.id.loginbtn);
        emailLogEdit = (EditText) findViewById(R.id.loginEmailEditText);
        passLogEdit = (EditText) findViewById(R.id.loginPassEditText);



        //Assign instances

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = firebaseAuth.getCurrentUser();

                if(user != null){
                    startActivity(new Intent(LoginActivity.this, Welcome.class));
                }else{
                }

            }
        };

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userEmail = emailLogEdit.getText().toString().trim();
                userPass = passLogEdit.getText().toString().trim();

                if(!TextUtils.isEmpty(userEmail) && !TextUtils.isEmpty(userPass)){

                    mAuth.signInWithEmailAndPassword(userEmail, userPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                startActivity(new Intent(LoginActivity.this, Welcome.class));
                            }else{
                                Toast.makeText(LoginActivity.this, "User Login Failed", Toast.LENGTH_LONG).show();
                            }

                        }
                    });

                }
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
