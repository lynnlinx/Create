package com.example.linxing.create;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        finish();
        startActivity(new Intent(this, RegisterActivity.class));
    }
}
