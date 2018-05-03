package com.example.linxing.create;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
    private Spinner spinnerGender;
    private FirebaseAuth myAuth;
    private UserProfile userInformation;
    private FirebaseUser user;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private static final String TAG = "SettingActivity";
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);

        editTextUsername = (EditText) findViewById(R.id.txt_username);
        spinnerAge = (Spinner) findViewById(R.id.spinner_age);
        spinnerWeight = (Spinner) findViewById(R.id.spinner_weight);
        spinnerGender = (Spinner) findViewById(R.id.spinner_gender);
        buttonChangePhoto = (Button) findViewById(R.id.change_photo);
        buttonSave = (Button) findViewById(R.id.btn_save_info);
        buttonChangePhoto.setOnClickListener(this);
        buttonSave.setOnClickListener(this);
        myAuth = myAuth.getInstance();
        user = myAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference(("profile/" + user.getUid()));
        //set default
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(),IngredientActivity.class));
                }
            });
        }
        setDefault();
    }
    @Override
    public void onClick(View v) {
        if(v == buttonSave){
            saveUserInfo();
            startActivity(new Intent(getApplicationContext(),SettingActivity.class));
            finish();
        }
        if(v == buttonChangePhoto){
        }
    }

    private void setDefault() {
        spinnerAge.setSelection(2, true);
        spinnerWeight.setSelection(2, true);
        spinnerGender.setSelection(2, true);
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userInformation = dataSnapshot.getValue(UserProfile.class);
                editTextUsername.setText(userInformation.getUsername_profile());
                switch (userInformation.getGender_profile()) {
                    case "Male":
                        spinnerGender.setSelection(0, true);
                        break;
                    case "Female":
                        spinnerGender.setSelection(1,true);
                        break;
                    default:
                        spinnerGender.setSelection(2,true);

                }
                switch (userInformation.getAge_profile()) {
                    case "Age: below 20":
                        spinnerAge.setSelection(0, true);
                        break;
                    case "Age: 20 to 50":
                        spinnerAge.setSelection(1,true);
                        break;
                    case "Age: over 50":
                        spinnerAge.setSelection(2,true);
                        break;
                    default:
                        spinnerAge.setSelection(3,true);

                }
                switch (userInformation.getAge_profile()) {
                    case "Weight: below 50 kg/110lbs":
                        spinnerWeight.setSelection(0, true);
                        break;
                    case "Weight: 50 to 70 kg/110lbs to 154lbs":
                        spinnerWeight.setSelection(1,true);
                        break;
                    case "Weight: Over 70kg/154lbs":
                        spinnerWeight.setSelection(2,true);
                        break;
                    default:
                        spinnerWeight.setSelection(3,true);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        myRef.addListenerForSingleValueEvent(postListener);
    }
    private void saveUserInfo(){
        String username = editTextUsername.getText().toString().trim();
        String age = spinnerAge.getSelectedItem().toString();
        String weight = spinnerWeight.getSelectedItem().toString();
        String gender = spinnerGender.getSelectedItem().toString();
        userInformation.setUsername_profile(username);
        userInformation.setAge_profile(age);
        userInformation.setWeight_profile(weight);
        userInformation.setGender_profile(gender);

        //userInformation;

        // set other infomation
        myRef.setValue(userInformation);

        Toast.makeText(this, "Information Saved", Toast.LENGTH_SHORT).show();
    }
}
