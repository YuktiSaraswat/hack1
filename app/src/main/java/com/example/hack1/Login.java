package com.example.hack1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    EditText Email;
    EditText Password;
    Button loginbtn;
    TextView Register1;
    FirebaseAuth mfirebaseAuth;
    ProgressBar progressBar;
    private FirebaseAuth.AuthStateListener mAuthStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Email = (EditText) findViewById(R.id.edit_email);
        Password = (EditText) findViewById(R.id.edit_password);
        loginbtn = (Button) findViewById(R.id.button_login);
        Register1 = (TextView) findViewById(R.id.register1);
        progressBar=(ProgressBar)findViewById(R.id.progress_circular);
        mfirebaseAuth = FirebaseAuth.getInstance();
        progressBar.setVisibility(View.INVISIBLE);
        //progressBar.setVisibility(View.VISIBLE);
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mfirebaseAuth.getCurrentUser();
                if (mFirebaseUser != null) {
                    Toast.makeText(Login.this, "You are logged in!", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Login.this, MainActivity.class);
                    startActivity(i);

                } else {
                    Toast.makeText(Login.this, "Error Occured!", Toast.LENGTH_SHORT).show();
                }


            }
        };
        Register1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Login.this, SignUp.class);
                startActivity(intent);
            }
        });
        loginbtn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            String email = Email.getText().toString();
                                            String password = Password.getText().toString();
                                            if (email.isEmpty()) {
                                                Email.setError("Please Enter E-mail Id");
                                                Email.requestFocus();
                                                return;
                                            } else if (password.isEmpty()) {
                                                Password.setError("Please Enter your Password");
                                                Password.requestFocus();
                                                return;
                                            }
                                            if (email.isEmpty() && password.isEmpty()) {
                                                Toast.makeText(Login.this, "Fields are Empty!", Toast.LENGTH_SHORT).show();
                                                return;
                                            }
                                            progressBar.setVisibility(View.VISIBLE);
                                            mfirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                    if (!task.isSuccessful()) {
                                                        Toast.makeText(Login.this, "Login Error,Please Try Again!", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        Toast.makeText(Login.this, "Login Successful !", Toast.LENGTH_SHORT).show();
                                                        startActivity(new Intent(Login.this, MainActivity.class));
                                                    }
                                                }
                                            });

                                            //Intent intent = new Intent(Login.this, MainActivity.class);
                                            //startActivity(intent);
                                        }
                                    }
        );
    }

    @Override
    protected void onStart() {
        super.onStart();
        mfirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
}
