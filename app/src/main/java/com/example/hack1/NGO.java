package com.example.hack1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


public class NGO extends AppCompatActivity{
    Spinner s1, s2, s3, s4;
    EditText name, number, phone, email, address;
    Button b1;
    TextView login;
    FirebaseAuth mfirebaseAuth;
    FirebaseFirestore fstore;
    DatabaseReference databaseReference;
    String userId1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngo);
        //NGOUser ngoUser1=new NGOUser();
        mfirebaseAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        databaseReference=FirebaseDatabase.getInstance().getReference("NGOUsers");
        s1 = (Spinner) findViewById(R.id.edit_nature);
        s2 = (Spinner) findViewById(R.id.edit_type);
        s3 = (Spinner) findViewById(R.id.edit_state1);
        s4 = (Spinner) findViewById(R.id.edit_city1);
        name = (EditText) findViewById(R.id.edit_name1);
        number = (EditText) findViewById(R.id.edit_number);
        phone = (EditText) findViewById(R.id.edit_mobile);
        email = (EditText) findViewById(R.id.edit_email1);
        address = (EditText) findViewById(R.id.edit_address1);
        //password = (EditText) findViewById(R.id.edit_password1);
        //cnfpassword = (EditText) findViewById(R.id.edit_cnfpassword1);
        login = (TextView) findViewById(R.id.textview_login);
        b1 = (Button) findViewById(R.id.button_signup);
        ArrayAdapter<String> myadapter1 = new ArrayAdapter<String>(NGO.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.nature));
        myadapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s1.setAdapter(myadapter1);
        ArrayAdapter<String> myadapter2 = new ArrayAdapter<String>(NGO.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.type));
        myadapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s2.setAdapter(myadapter2);
        ArrayAdapter<String> myadapter3 = new ArrayAdapter<String>(NGO.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.nature));
        myadapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s3.setAdapter(myadapter3);
        ArrayAdapter<String> myadapter4 = new ArrayAdapter<String>(NGO.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.type));
        myadapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s4.setAdapter(myadapter4);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(NGO.this, Login.class);
                startActivity(intent);
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name1 = name.getText().toString().trim();
                final String number1 = number.getText().toString().trim();
                final String phone1 = phone.getText().toString().trim();
                final String address1 = address.getText().toString().trim();
                final String nature1 = s1.getSelectedItem().toString().trim();
                final String type1 = s2.getSelectedItem().toString().trim();
                final String state1 = s3.getSelectedItem().toString().trim();
                final String city1 = s4.getSelectedItem().toString().trim();
                final String email1 = email.getText().toString().trim();
                //String pass1=password.getText().toString().trim();
                if (name1.isEmpty()) {
                    name.setError("Email required");
                    name.requestFocus();
                    return;
                }
                if (email1.isEmpty()) {
                    email.setError("Email required");
                    email.requestFocus();
                    return;
                }
                if (number1.isEmpty()) {
                    number.setError("Email required");
                    number.requestFocus();
                    return;
                }

                if (phone1.isEmpty()) {
                    phone.setError("Email required");
                    phone.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email1).matches()) {
                    email.setError("Please enter valid email id");
                    email.requestFocus();
                    return;
                }
                if (address1.isEmpty()) {
                    address.setError("Password is required");
                    address.requestFocus();
                    return;
                }
                String id = databaseReference.push().getKey();
                NGOUser ngoUser = new NGOUser(name1, number1, email1, phone1, nature1, type1, state1, city1, address1);
                databaseReference.child(id).setValue(ngoUser);
                Toast.makeText(NGO.this, "Submit Successfully!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(NGO.this, SignUp.class));
            }
        });
    }

}

