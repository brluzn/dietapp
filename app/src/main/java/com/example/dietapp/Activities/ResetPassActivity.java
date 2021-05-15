package com.example.dietapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dietapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassActivity extends AppCompatActivity {

    Button btn_reset_pass;
    EditText email;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_reset_pass);

        email=findViewById(R.id.reset_email_edittext);
        btn_reset_pass=findViewById(R.id.btn_reset_password);


        btn_reset_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String emailAddress = email.getText().toString();

                mAuth.sendPasswordResetEmail(emailAddress).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull  Task<Void> task) {
                        if(email!=null){
                            Toast.makeText(ResetPassActivity.this, "Succesfull", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


    }
}