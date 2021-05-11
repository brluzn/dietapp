package com.example.dietapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.dietapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText login_email_edittext;
    TextInputEditText login_password_edittext;
    Button btn_login_signIn;
    Button btn_login_signUp;

    FirebaseAuth auth= FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //ui
        login_email_edittext=findViewById(R.id.login_email_edittext);
        login_password_edittext=findViewById(R.id.login_password_edittext);
        btn_login_signIn=findViewById(R.id.btn_login_signIn);
        btn_login_signUp=findViewById(R.id.btn_login_signUp);


        //
        btn_login_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });


        btn_login_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email=login_email_edittext.getText().toString();
                String password=login_password_edittext.getText().toString();

                signIn(email,password);
            }
        });

    }

    private void signIn(String email,String password){

        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent intent=new Intent(LoginActivity.this, MenuActivity.class);
                    startActivity(intent);
                    finish();

                }else {
                    Toast.makeText(LoginActivity.this, "Lütfen Bilgilerinizi Kontrol Edin", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user=auth.getCurrentUser();
        if (user !=null){
            Intent intent=new Intent(LoginActivity.this,MenuActivity.class);
            startActivity(intent);
            finish();
        }

    }

}
