package com.example.hack1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Volunteers extends AppCompatActivity {
    EditText Email, Name, Phone;
    EditText Address;
    Button signupbtn;
    TextView Login11;
    RadioButton r1, r2, rb1;
    RadioGroup rg1;
    Spinner state1, city1;
    FirebaseAuth mfirebaseAuth;
    FirebaseFirestore fstore;
    DatabaseReference databaseReference1;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteers);
        Email = (EditText) findViewById(R.id.edit_email);
        Name = (EditText) findViewById(R.id.edit_name);
        Phone = (EditText) findViewById(R.id.edit_mobile);
        state1 = (Spinner) findViewById(R.id.edit_state);
        city1 = (Spinner) findViewById(R.id.edit_city);
        Address = (EditText) findViewById(R.id.edit_address);
        signupbtn = (Button) findViewById(R.id.button_signup);
        Login11 = (TextView) findViewById(R.id.textview_login);
        databaseReference1 = FirebaseDatabase.getInstance().getReference("Volunteers");
        mfirebaseAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        rg1 = (RadioGroup) findViewById(R.id.rg1);
        ArrayAdapter<String> myadapter3 = new ArrayAdapter<String>(Volunteers.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.nature));
        myadapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        state1.setAdapter(myadapter3);
        ArrayAdapter<String> myadapter4 = new ArrayAdapter<String>(Volunteers.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.type));
        myadapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        city1.setAdapter(myadapter4);
        if (mfirebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
        Login11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Volunteers.this, Login.class);
                startActivity(intent);
            }
        });
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = Email.getText().toString().trim();
                //final String password=Password.getText().toString().trim();
                final String fullname = Name.getText().toString().trim();
                //String cnfpswrd=CPassword.getText().toString().trim();
                final String phone = Phone.getText().toString().trim();
                final String state = state1.getSelectedItem().toString().trim();
                final String city = city1.getSelectedItem().toString().trim();
                final String gender = rb1.getText().toString().trim();
                final String address = Address.getText().toString().trim();
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
                if (address.isEmpty()) {
                    Address.setError("Please Enter your Address");
                    Address.requestFocus();
                    return;
                }
                if (fullname.isEmpty()) {
                    Name.setError("Please Enter your Name");
                    Name.requestFocus();
                    return;
                }
                if (phone.isEmpty()) {
                    Phone.setError("Please Enter your Mobile Number");
                    Phone.requestFocus();
                    return;
                }
                String id = databaseReference1.push().getKey();
                User user = new User(gender,fullname,phone,email,state,city,address);
                databaseReference1.child(id).setValue(user);
                Toast.makeText(Volunteers.this, "Submit Successfully!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Volunteers.this, SignUp.class));
            }
        });
    }
    public void rbclick2(View view) {
        // t5=(TextView)findViewById(R.id.t5);
        //editText3=(EditText)findViewById(R.id.editText3);
        int rid = rg1.getCheckedRadioButtonId();
        rb1 = (RadioButton) findViewById(rid);
        /*if(rid==R.id.r2)
        {
            t5.setVisibility(View.VISIBLE);
            editText3.setVisibility(View.VISIBLE);
        }*/
        switch (rid) {
            case R.id.r2:
                break;
            case R.id.r1:
                break;
        }
    }
}
