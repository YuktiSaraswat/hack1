package com.example.hack1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import java.lang.String;
import android.app.AlertDialog;
import android.app.MediaRouteButton;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Accept extends AppCompatActivity {
    Button b4,btndisp;
    FirebaseAuth mfirebaseAuth;
    FirebaseFirestore fstore;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    String value;
    String userId;
    ListView listView;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    Ret retrieve;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept);
        //Toolbar toolbar=findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        listView=(ListView)findViewById(R.id.list_view);
        list=new ArrayList<>();
        adapter=new ArrayAdapter<String>(this,R.layout.layout1,R.id.textViewinfo,list);
        retrieve=new Ret();
        b4 = (Button) findViewById(R.id.b4);
        btndisp=(Button)findViewById(R.id.btn_display);
        fstore = FirebaseFirestore.getInstance();
        database=FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("donate");
        b4.setOnClickListener(new View.OnClickListener() {
                                  @Override
                                  public void onClick(View v) {
                                      Query query=databaseReference.orderByChild("city1").equalTo("Corporate Type");
                                      query.addListenerForSingleValueEvent(new ValueEventListener() {
                                          @Override
                                          public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                              for (DataSnapshot ds:dataSnapshot.getChildren())
                                              {
                                                  ds.getRef().removeValue();
                                              }
                                          }

                                          @Override
                                          public void onCancelled(@NonNull DatabaseError databaseError) {
                                              Log.e("abc","OnCancelled",databaseError.toException());
                                          }
                                      });

                                      FirebaseUser user1=mfirebaseAuth.getCurrentUser();
                                      if (user1.isEmailVerified())
                                      {
                                          Toast.makeText(Accept.this, "Email is already Verified !", Toast.LENGTH_SHORT).show();
                                      }
                                      else
                                      {
                                          Toast.makeText(Accept.this, "E-mail not Verified!First Verify the email...", Toast.LENGTH_SHORT).show();
                                          user1.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                              @Override
                                              public void onComplete(@NonNull Task<Void> task) {
                                                  Toast.makeText(Accept.this, "Verification E-mail Send !", Toast.LENGTH_SHORT).show();
                                                  startActivity(new Intent(Accept.this,MainActivity.class));
                                              }
                                          });

                                      }
                                  }
                              }
        );
        btndisp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds:dataSnapshot.getChildren())
                        {
                            //retrieve.setCity1("kanpur");
                            //retrieve=ds.getValue(Ret.class);
                            list.add(ds.getValue()+"\n");
                        }
                        listView.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showUpdateDialog();
                Intent intent=new Intent(getApplicationContext(),Ret.class);
                startActivity(intent);
                //Ret r=(Ret)parent.getItemAtPosition(position);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //Ret retrieve=ada
                return false;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.logout,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menulogout:
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(Accept.this,MainActivity.class));
                break;
        }
        return true;
    }
    public void showUpdateDialog()
    {
        AlertDialog.Builder dialogBuilder=new AlertDialog.Builder(this);
        LayoutInflater inflater=getLayoutInflater();
        final View dialogView=inflater.inflate(R.layout.update,null);
        dialogBuilder.setView(dialogView);
        final Button btn_update=(Button)dialogView.findViewById(R.id.button_update);
        dialogBuilder.setTitle("Update");
        AlertDialog alertDialog=dialogBuilder.create();
        alertDialog.show();
    }

}
