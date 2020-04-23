package com.example.hack1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Payment extends AppCompatActivity {
    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        b1=(Button)findViewById(R.id.button_pay);
    }
    public void pay(View view)
    {
        Intent intentl = new Intent(Payment.this, Mode.class);
        startActivity(intentl);
    }
}
