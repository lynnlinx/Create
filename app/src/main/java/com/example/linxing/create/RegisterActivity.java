package com.example.linxing.create;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by jiana on 2018/2/27.
 */

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth myAuth;
    private Button buttonRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private static final String TAG = "RegisterActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        myAuth = myAuth.getInstance();

        if(myAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext(), IngredientActivity.class));
        }
        // set parameter to xml component
        buttonRegister = (Button) findViewById(R.id.btn_register);
        editTextEmail = (EditText) findViewById(R.id.txt_email);
        editTextPassword = (EditText) findViewById(R.id.txt_password);

        buttonRegister.setOnClickListener(new myListener());
    }
    public class myListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            registerUser();
        }
    }

    private void registerUser(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)) {
            //email is empty
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            //stopping the function execution further
            return;
        }
        if(TextUtils.isEmpty(password)){
            //password is empty
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            //stopping the function execution further
            return;
        }

        myAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //save the user information in the firebase
                            saveUserInfo();
                            //user is successful registered and logged in
                            //we will start the search(main) activity
                            finish();
                            startActivity(new Intent(getApplicationContext(), IngredientActivity.class));
                        }else {
                            Toast.makeText(RegisterActivity.this, "Could not register, please try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void saveUserInfo(){
        String email = editTextEmail.getText().toString().trim();

        UserProfile userInformation = new UserProfile(email);
        FirebaseUser user = myAuth.getCurrentUser();

        // set other infomation
        //databaseReference.child(user.getUid()).setValue(userInformation);

        Toast.makeText(this, "Information Saved", Toast.LENGTH_SHORT).show();
    }

}
