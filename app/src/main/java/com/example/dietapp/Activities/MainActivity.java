package com.example.dietapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.usb.UsbRequest;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.dietapp.Models.UserInfo;
import com.example.dietapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    Button submit;
    RadioButton radioButton;
    RadioGroup radioGroup;
    EditText name_input;
    EditText surname_input;
    EditText height_input;
    EditText weight_input;
    FrameLayout frameLayout;

    FirebaseAuth auth= FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*LayoutInflater inflater=LayoutInflater.from(this);
        View view=inflater.inflate(R.layout.activity_main,null,false);
        drawerLayout.addView(view,0);
        frameLayout=findViewById(R.id.frame);
        frameLayout.setVisibility(View.INVISIBLE);*/

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

                String bmi_state=new String();

                float height_m=(Float.parseFloat(height)/100);
                float bmi=(Float.parseFloat(weight))/((height_m)*(height_m));
                float roundedBmi = (float) (Math.round(bmi * 100.0) / 100.0);
                System.out.println("bmi"+bmi);

                if (bmi!=0){

                    if (0.0<bmi && bmi<15.9){
                        bmi_state="OverUnderweight";
                    }
                    else if (16.0<bmi && bmi<18.4){
                        bmi_state="Underweight";
                    }
                    else if (18.5<bmi && bmi<24.9){
                        bmi_state="Normal";
                    }
                    else if (25.0<bmi && bmi<29.9){
                        bmi_state="Overweight";
                    }
                    else if (30.0<bmi && bmi<34.9){
                        bmi_state="Obesity";
                    }
                    else
                        bmi_state=null;

                }


                System.out.println("bmi____"+roundedBmi);
                UserInfo user=new UserInfo(name,surname,gender,height,weight,String.valueOf(roundedBmi),bmi_state);

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
        DatabaseReference myRef = database.getReference("Users").child(cUser.getUid());



        myRef.setValue(user);


    }


}