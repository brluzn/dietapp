package com.example.dietapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.example.dietapp.R;

public class DetailsActivity extends AppCompatActivity {

    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        btn=findViewById(R.id.button);

        Bundle data=getIntent().getExtras();

        if (data!=null){
            btn.setText(data.getCharSequence("time"));
            System.out.println(data.getCharSequence("day"));
        }
    }
}