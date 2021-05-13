package com.example.dietapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.dietapp.Models.MenuModel;
import com.example.dietapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class DetailsActivity extends AppCompatActivity {

    FirebaseAuth auth= FirebaseAuth.getInstance();
    Button btn;
    TextView recipe_title;
    TextView recipe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        recipe_title=findViewById(R.id.recipe_title);
        recipe=findViewById(R.id.recipe);
        btn=findViewById(R.id.btn_home);

        Bundle data=getIntent().getExtras();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(intent);
            }
        });
        if (data!=null){

            System.out.println(data.getCharSequence("day"));
            getData(data.getCharSequence("day").toString(),data.getCharSequence("time").toString(),data.getCharSequence("bmi_state").toString());
        }
    }

    public void getData(String day,String time,String bmi_state){


        FirebaseUser currentUser=auth.getCurrentUser();

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
        Query query=reference.child("Lists").child(bmi_state).child(day);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                MenuModel m1=snapshot.child(time).getValue(MenuModel.class);


                if (m1!=null){
                    recipe.setText(m1.menu);
                }



            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}