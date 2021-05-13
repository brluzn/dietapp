package com.example.dietapp.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.dietapp.Activities.DetailsActivity;
import com.example.dietapp.Activities.LoginActivity;
import com.example.dietapp.Activities.MainActivity;
import com.example.dietapp.Activities.MenuActivity;
import com.example.dietapp.Models.MenuModel;
import com.example.dietapp.Models.UserInfo;
import com.example.dietapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import javax.sql.StatementEvent;


public class FragmentContent extends Fragment {
    FirebaseAuth auth= FirebaseAuth.getInstance();

    private static final String KEY_TITLE = "Content";

    FirebaseUser currentUser=auth.getCurrentUser();

    ArrayList<MenuModel> menus= new ArrayList<>();
    ArrayList<UserInfo> user_info= new ArrayList<>();
    TextView today;
    TextView breakfast;
    TextView launch;
    TextView dinner;
    Button breakfast_details;
    Button launch_details;
    Button dinner_details;


    public FragmentContent() {
        // Required empty public constructor
    }


    public static FragmentContent newInstance(String param1) {
        FragmentContent fragment = new FragmentContent();
        Bundle args = new Bundle();
        args.putString(KEY_TITLE, param1);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_content, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        breakfast=view.findViewById(R.id.breakfast_text);
        launch=view.findViewById(R.id.launch_text);
        dinner=view.findViewById(R.id.dinner_text);

        breakfast_details=view.findViewById(R.id.breakfast_details);
        launch_details=view.findViewById(R.id.launch_details);
        dinner_details=view.findViewById(R.id.dinner_details);




        today=view.findViewById(R.id.today);
        String tiTle=getArguments().getString(KEY_TITLE);
        System.out.println(tiTle);
        if(tiTle!=null){
            if (tiTle=="Edit Profile"){

                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);

            }
            else if (tiTle=="Logout"){

                auth.signOut();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
            else if (tiTle=="Profile"){

            }
            else {


                //Intent intent = new Intent(getContext(), MenuActivity.class);
                //startActivity(intent);
                today.setText(tiTle);
                //getMenu();
                loadUserInfo();





            }

        }

        else{
            System.out.println("fragment hatası");
        }


    }

    public void getMenu(String bmi_state){



        String title=getArguments().getString(KEY_TITLE);
        String day=("Day"+title.charAt(0)+title.charAt(1));
        FirebaseUser currentUser=auth.getCurrentUser();

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
        Query query=reference.child("Lists").child(bmi_state).child("Day"+title.charAt(0)+title.charAt(1));
        System.out.println("Day"+day);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                MenuModel m1=snapshot.child("Breakfast").getValue(MenuModel.class);
                MenuModel m2=snapshot.child("Launch").getValue(MenuModel.class);
                MenuModel m3=snapshot.child("Dinner").getValue(MenuModel.class);

                if (m1!=null||m2!=null||m3!=null){
                    breakfast.setText(m1.menu);
                    launch.setText(m2.menu);
                    dinner.setText(m3.menu);
                    goDetails(breakfast_details,day,"Breakfast",bmi_state);
                    goDetails(launch_details,day,"Launch",bmi_state);
                    goDetails(dinner_details,day,"Dinner",bmi_state);
                }



            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void loadUserInfo(){


        String bmi_state=new String();
        FirebaseUser currentUser=auth.getCurrentUser();

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
        Query query=reference.child("Users").child(currentUser.getUid());
        query.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                UserInfo a=snapshot.getValue(UserInfo.class);
                user_info.add(a);

                String bmi_state=user_info.get(0).user_bmi_state;
                getMenu(bmi_state);
                System.out.println("BBBBB"+ bmi_state);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    public void goDetails(Button btn,String day,String time,String bmi_state){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DetailsActivity.class);
                intent.putExtra("day",day);
                intent.putExtra("time",time);
                intent.putExtra("bmi_state",bmi_state);
                startActivity(intent);
            }
        });
    }
}