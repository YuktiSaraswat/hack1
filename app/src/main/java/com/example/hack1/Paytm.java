package com.example.hack1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Paytm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paytm);
    }
    public void confirm(View view)
    {
        Toast.makeText(this, "Payment Sucessfull", Toast.LENGTH_SHORT).show();
        Intent intentl = new Intent(Paytm.this,MainActivity.class);
        startActivity(intentl);
    }
}
