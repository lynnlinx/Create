package com.example.linxing.create;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
/**
 * Created by jiana on 2018/2/27.
 */

public class SettingActivity extends AppCompatActivity implements View.OnClickListener{
    private Button buttonSave;
    private Button buttonChangePhoto;
    private EditText editTextUsername;
    private Spinner spinnerAge;
    private Spinner spinnerWeight;
    private FirebaseAuth myAuth;
    UserProfile userInformation;
    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference myRef;
    private static final String TAG = "SettingActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);

        editTextUsername = (EditText) findViewById(R.id.txt_username);
        spinnerAge = (Spinner) findViewById(R.id.spinner_age);
        spinnerWeight = (Spinner) findViewById(R.id.spinner_weight);
        buttonChangePhoto = (Button) findViewById(R.id.change_photo);
        buttonSave = (Button) findViewById(R.id.btn_save_info);
        buttonChangePhoto.setOnClickListener(this);
        buttonSave.setOnClickListener(this);
        myAuth = myAuth.getInstance();
        user = myAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference(("profile/" + user.getUid()));
        //set default
        setDefault();
    }
    @Override
    public void onClick(View v) {
        if(v == buttonSave){
            saveUserInfo();
            startActivity(new Intent(getApplicationContext(),IngredientActivity.class));
            finish();
        }
        if(v == buttonChangePhoto){
        }
    }

    private void setDefault() {
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userInformation = dataSnapshot.getValue(UserProfile.class);
                editTextUsername.setText(userInformation.getUsername_profile());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        myRef.addListenerForSingleValueEvent(postListener);
        spinnerAge.setSelection(2, true);
        spinnerWeight.setSelection(2, true);
    }
    private void saveUserInfo(){
        String username = editTextUsername.getText().toString().trim();
        String age = spinnerAge.getSelectedItem().toString();
        String weight = spinnerWeight.getSelectedItem().toString();
        userInformation.setUsername_profile(username);
        userInformation.setAge_profile(age);
        userInformation.setWeight_profile(weight);

        //userInformation;

        // set other infomation
        myRef.setValue(userInformation);

        Toast.makeText(this, "Information Saved", Toast.LENGTH_SHORT).show();
    }
}
