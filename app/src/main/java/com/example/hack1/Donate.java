package com.example.hack1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.net.Uri;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Donate extends AppCompatActivity {
    Button b1, b2, b3;
    TextView t1, t2, t3,  t5, t6;
    EditText editText, editText2, editText3, editText4,email,name;
    FirebaseAuth mfirebaseAuth;
    FirebaseFirestore fstore;
    DatabaseReference databaseReference;
    String userId, value,emailx;
    Spinner state,city;
    String accepted="No";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);
        b1 = (Button) findViewById(R.id.b1);
        b2 = (Button) findViewById(R.id.b2);
        b3 = (Button) findViewById(R.id.b3);
        t1 = (TextView) findViewById(R.id.t1);
        t2 = (TextView) findViewById(R.id.t2);
        t3 = (TextView) findViewById(R.id.t3);
        t5 = (TextView) findViewById(R.id.t5);
        t6 = (TextView) findViewById(R.id.t6);
        state=(Spinner)findViewById(R.id.s_state);
        city=(Spinner)findViewById(R.id.s_city);
        final NGOUser ngoUser=new NGOUser();
        //rg = (RadioGroup) findViewById(R.id.rg);
        editText = (EditText) findViewById(R.id.editText);
        editText2 = (EditText) findViewById(R.id.editText2);
        editText3 = (EditText) findViewById(R.id.editText3);
        editText4 = (EditText) findViewById(R.id.editText4);
        name=(EditText)findViewById(R.id.editText_name);
        email=(EditText)findViewById(R.id.editText_email);
        mfirebaseAuth=FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("donate");
        ArrayAdapter<String> myadapter3=new ArrayAdapter<String>(Donate.this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.nature));
        myadapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        state.setAdapter(myadapter3);
        ArrayAdapter<String> myadapter4=new ArrayAdapter<String>(Donate.this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.type));
        myadapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        city.setAdapter(myadapter4);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentf = new Intent(Donate.this, Payment.class);
                startActivity(intentf);
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name1 = name.getText().toString().trim();
                String email1 = email.getText().toString().trim();
                String item = editText.getText().toString().trim();
                String quantity = editText2.getText().toString().trim();
                String phone = editText4.getText().toString().trim();
                String state1=state.getSelectedItem().toString().trim();
                String city1=city.getSelectedItem().toString().trim();
                ValueEventListener valueEventListener=new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                             emailx= String.valueOf(ds.getValue(NGOUser.class));
                           // emailx = ds.getValue(NGOUser.class).orderByChild("City").equalTo(city1).;
                            //list.add("Name: "+retrieve.getName1()+"\n"+"E-mail Id: "+retrieve.getEmail1()+"\n"+"Item: "+retrieve.getItem1()+" \n"+"Quantity: "
                            //+retrieve.getQuantity1()+"\n"+"Mobile No: "+retrieve.getPhone1()+"\n"+"Address: "+retrieve.getAddress1());
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                };
                //value = ((RadioButton) findViewById(rg.getCheckedRadioButtonId())).getText().toString();
                String address = editText3.getText().toString();
                Query query=FirebaseDatabase.getInstance().getReference("NGOUsers").orderByChild("city2").equalTo(city1);
                query.addListenerForSingleValueEvent(valueEventListener);
                if (name1.isEmpty()) {
                    name.setError("Please Enter Item");
                    name.requestFocus();
                    return;
                }
                if (email1.isEmpty()) {
                    email.setError("Please Enter Item");
                    email.requestFocus();
                    return;
                }
                if (address.isEmpty()) {
                    editText3.setError("Please Enter your Address");
                    editText3.requestFocus();
                    return;
                }
                if (item.isEmpty()) {
                    editText.setError("Please Enter Item");
                    editText.requestFocus();
                    return;
                }
                if (quantity.isEmpty()) {
                    editText2.setError("Please Enter Item Quantity");
                    editText2.requestFocus();
                    return;
                }
                if (phone.isEmpty()) {
                    editText4.setError("Please Enter Phone Number");
                    editText4.requestFocus();
                    return;
                }
                String id = databaseReference.push().getKey();
                //userId = mfirebaseAuth.getCurrentUser().getUid();
                //DocumentReference documentReference = fstore.collection("Donate");
                /*Map<String, Object> user = new HashMap<>();
                user.put("Name",name1);
                user.put("Email",email1);
                user.put("Item", item);
                user.put("Quantity", quantity);
                user.put("Phone no", phone);
                user.put("State",state1);
                user.put("City",city1);
                user.put("Address", address);
                user.put("Accepted","No");*/
                Ret retrieve=new Ret(id,accepted,address,city1,email1,item,name1,phone,quantity,state1);
                databaseReference.child(id).setValue(retrieve);
                Toast.makeText(Donate.this, "Thanks for Donation!", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("mail to:"+emailx));
                intent.putExtra(Intent.EXTRA_SUBJECT,"Collect the item from the Specified Location");
                intent.putExtra(Intent.EXTRA_TEXT,"Details already shown in the profile");
                startActivity(intent);
                Intent intents = new Intent(Donate.this, MainActivity.class);
                startActivity(intents);


            }
        });
    }
}
