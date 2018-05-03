package com.example.linxing.create;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by jiana on 2018/2/27.
 */

public class ShoppingListActivity extends AppCompatActivity implements View.OnClickListener{
    Toolbar toolbar;
    Button buttonShare;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shoppinglist);
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(),IngredientActivity.class));
                }
            });
        }
        buttonShare = (Button) findViewById(R.id.btn_share);
        buttonShare.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if (v== buttonShare) {
            Toast.makeText(this, "Be coming soon", Toast.LENGTH_SHORT).show();
        }
    }
}
