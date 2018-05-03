package com.example.linxing.create;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by jiana on 2018/2/27.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private FirebaseAuth myAuth;
    private Button buttonSignin;
    private Button buttonRegister;
    private TextView buttonSecurity;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private static final String TAG = "LoginActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        myAuth = myAuth.getInstance();
        if(myAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext(), IngredientActivity.class));
        }
        editTextEmail = (EditText) findViewById(R.id.txt_email);
        editTextPassword = (EditText) findViewById(R.id.txt_password);
        buttonSignin = (Button) findViewById(R.id.btn_login);
        buttonRegister = (Button) findViewById(R.id.btn_register);
        buttonSecurity = (TextView) findViewById(R.id.btn_security);
        buttonSecurity.setOnClickListener(this);

        buttonSignin.setOnClickListener(this);
        buttonRegister.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if(v == buttonSignin){
            userLogin();
        }
        if(v == buttonRegister){
            finish();
            startActivity(new Intent(this, RegisterActivity.class));
        }
        if (v== buttonSecurity) {
            Toast.makeText(this, "Be coming soon", Toast.LENGTH_SHORT).show();
        }
    }

    public void userLogin(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        if(TextUtils.isEmpty(email)){
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


        myAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //save the correct user information in the database
                            saveUserInfo();
                            finish();
                            startActivity(new Intent(getApplicationContext(), IngredientActivity.class));
                        }else {
                            Toast.makeText(LoginActivity.this, "Could not Sign in, please try again", Toast.LENGTH_SHORT).show();
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
