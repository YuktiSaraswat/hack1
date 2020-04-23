package com.example.hack1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Contact extends AppCompatActivity {
    TextView marquee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        marquee=(TextView)findViewById(R.id.textcontact);
        marquee.setSelected(true);
    }
}

