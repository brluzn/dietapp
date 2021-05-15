package com.example.dietapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dietapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PasswordActivity extends AppCompatActivity {

    Button btn_apply;
    EditText password;
    EditText new_password;
    EditText new_password_again;

    FirebaseAuth auth= FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        password=findViewById(R.id.PA_password_edittext);
        new_password=findViewById(R.id.PA_newpassword_edittext);
        new_password_again=findViewById(R.id.PA_newpassword_again_edittext);
        btn_apply=findViewById(R.id.btn_changePass);

        btn_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseUser currentUser=auth.getCurrentUser();

                String pass=password.getText().toString();
                String new_pass=new_password.getText().toString();
                String new_pass_again=new_password_again.getText().toString();

                if (new_pass.equals(new_pass_again)){
                    currentUser.updatePassword(new_pass)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.e("TAG", "User password updated.");
                                    }
                                }
                            });
                }else
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });



    }
}