package com.example.hack1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Mode extends AppCompatActivity {
    Button b1,b2,b3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode);
        b1=(Button)findViewById(R.id.button_paypal);
        b2=(Button)findViewById(R.id.button_paytm);
        b3=(Button)findViewById(R.id.button_credit);
    }
    public void paypal(View view)
    {
        Intent intentl = new Intent(Mode.this,Paypal.class);
        startActivity(intentl);
    }
    public void paytm(View view)
    {
        Intent intent2 = new Intent(Mode.this,Paytm.class);
        startActivity(intent2);
    }
    public void credit(View view)
    {
        Intent intent3 = new Intent(Mode.this,Credit.class);
        startActivity(intent3);
    }
}
