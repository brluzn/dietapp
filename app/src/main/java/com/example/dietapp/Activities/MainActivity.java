package com.example.dietapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.usb.UsbRequest;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.dietapp.Models.UserInfo;
import com.example.dietapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends MenuActivity {

    Button submit;
    RadioButton radioButton;
    RadioGroup radioGroup;
    EditText name_input;
    EditText surname_input;
    EditText height_input;
    EditText weight_input;

    FirebaseAuth auth= FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater=LayoutInflater.from(this);
        View view=inflater.inflate(R.layout.activity_main,null,false);
        drawerLayout.addView(view,0);

        FirebaseUser currentUser=auth.getCurrentUser();
        FirebaseUser cUser=currentUser;




        radioGroup=findViewById(R.id.radio_group);

        submit=findViewById(R.id.submit_btn);
        name_input=findViewById(R.id.name_input);
        surname_input=findViewById(R.id.surname_input);
        height_input=findViewById(R.id.height_input);
        weight_input=findViewById(R.id.weight_input);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int radioId=radioGroup.getCheckedRadioButtonId();
                radioButton=findViewById(radioId);
                String name=name_input.getText().toString();
                String surname=surname_input.getText().toString();
                String height=height_input.getText().toString();
                String weight=weight_input.getText().toString();
                String gender=radioButton.getText().toString();

                UserInfo user=new UserInfo(name,surname,gender,height,weight,"245");

                SendDatabase(user,cUser);



                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(intent);
                finish();
            }
        });




    }

    public void checkRadioButton(View view){
        int radioId=radioGroup.getCheckedRadioButtonId();
        radioButton=findViewById(radioId);

    }

    public void SendDatabase(UserInfo user,FirebaseUser cUser){
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        System.out.println(cUser);
        DatabaseReference myRef = database.getReference(cUser.getUid() + "Users");



        myRef.setValue(user);


    }


}