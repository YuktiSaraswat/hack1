package com.example.hack1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUp extends AppCompatActivity {

    EditText Email,Password,CPassword;
    TextView Login;
    Button btn_register;
    FirebaseAuth mfirebaseAuth;
    FirebaseFirestore fstore;
    String userId;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Email=(EditText)findViewById(R.id.edit_email);
        Password=(EditText)findViewById(R.id.edit_password);
        CPassword=(EditText)findViewById(R.id.edit_password1);
        btn_register=(Button) findViewById(R.id.button_register);
        Login=(TextView) findViewById(R.id.register1);
        progressBar=(ProgressBar)findViewById(R.id.progress_circular1);
        mfirebaseAuth=FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();
        final FirebaseUser user1=mfirebaseAuth.getCurrentUser();
        progressBar.setVisibility(View.INVISIBLE);
        if (mfirebaseAuth.getCurrentUser()!=null)
        {
            startActivity(new Intent(getApplicationContext(),Login.class));
            finish();
        }
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(SignUp.this,Login.class);
                startActivity(intent);
            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                final String email = Email.getText().toString().trim();
                                                final String password = Password.getText().toString().trim();
                                                String cnfpswrd = CPassword.getText().toString().trim();
                                                if (password.length() < 6) {
                                                    Password.setError("Password must be >= 6 Characters");
                                                    Password.requestFocus();
                                                    return;
                                                }
                                                if (email.isEmpty()) {
                                                    Email.setError("Please Enter E-mail Id");
                                                    Email.requestFocus();
                                                    return;
                                                }
                                                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                                                    Email.setError("Please Enter valid E-mail Id");
                                                    Email.requestFocus();
                                                    return;
                                                }
                                                if (password.isEmpty()) {
                                                    Password.setError("Please Enter your Password");
                                                    Password.requestFocus();
                                                    return;
                                                }
                                                if (cnfpswrd.isEmpty()) {
                                                    CPassword.setError("Please Confirm your Password");
                                                    CPassword.requestFocus();
                                                    return;
                                                }
                                                //if(cnfpswrd!=password)
                                                //{
                                                   // CPassword.setError("Password Not Match");
                                                    //CPassword.requestFocus();
                                                    //return;
                                                //}
                                                progressBar.setVisibility(View.VISIBLE);
                                                mfirebaseAuth.createUserWithEmailAndPassword(email, password)
                                                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                                                   @Override
                                                                                   public void onComplete(@NonNull Task<AuthResult> task) {
                                                                                       if (!task.isSuccessful()) {
                                                                                           if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                                                                               Toast.makeText(getApplicationContext(), "You are already Registered!", Toast.LENGTH_SHORT).show();
                                                                                           } else {
                                                                                               Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                                                           }
                                                                                           Toast.makeText(SignUp.this, "Signup Unsuccessful,Please Try Again!", Toast.LENGTH_SHORT).show();
                                                                                       } else {
                                                                                           mfirebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                               @Override
                                                                                               public void onComplete(@NonNull Task<Void> task) {
                                                                                                  if (task.isSuccessful())
                                                                                                  {
                                                                                                      if (mfirebaseAuth.getCurrentUser().isEmailVerified()) {
                                                                                                          Toast.makeText(SignUp.this, "Signup Successful!", Toast.LENGTH_SHORT).show();
                                                                                                          startActivity(new Intent(SignUp.this, Login.class));
                                                                                                      }
                                                                                                      else
                                                                                                      {
                                                                                                          Toast.makeText(SignUp.this, "Please Verify Your E-mail!", Toast.LENGTH_SHORT).show();
                                                                                                      }
                                                                                                  }
                                                                                                  else
                                                                                                  {
                                                                                                      Toast.makeText(SignUp.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                                                                  }
                                                                                               }
                                                                                           });

                                                                                       }
                                                                                   }
                                                                               }

                                                        );

                                            }
                                        }
        );

    }
}

