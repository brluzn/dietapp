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

public class SignUpActivity extends AppCompatActivity {

    TextInputEditText signUp_email_edittext;
    TextInputEditText signUp_password_edittext;
    TextInputEditText signUp_passwordagain_edittext;
    Button btn_signUp;

    FirebaseAuth auth=FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //ui

        signUp_email_edittext=findViewById(R.id.signUp_email_edittext);
        signUp_password_edittext=findViewById(R.id.signUp_password_edittext);
        signUp_passwordagain_edittext=findViewById(R.id.signUp_passwordagain_edittext);
        btn_signUp=findViewById(R.id.btn_signUp);

        //
        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=signUp_email_edittext.getText().toString();
                String password=signUp_password_edittext.getText().toString();
                String password_again=signUp_passwordagain_edittext.getText().toString();

                if (password.equals(password_again)==true){
                    signUp(email,password);
                }else {
                    Toast.makeText(getApplicationContext(), "Control passwors", Toast.LENGTH_SHORT).show();
                    System.out.println("Wrong Pass");
                    System.out.println(password);
                    System.out.println(password_again);
                }



            }
        });

    }


    private void signUp(String email,String password){

        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(SignUpActivity.this, "SUCCESSFUL", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(SignUpActivity.this, MenuActivity.class);
                    startActivity(intent);
                    finish();


                }else{

                    Toast.makeText(SignUpActivity.this, "FAILED", Toast.LENGTH_SHORT).show();



                }
            }
        });

    }


}