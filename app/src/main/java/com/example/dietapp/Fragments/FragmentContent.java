package com.example.dietapp.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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

    LinearLayout LLayout_day;
    LinearLayout LLayout_week;

    TextView day1_week_breakfast;
    TextView day1_week_launch;
    TextView day1_week_dinner;
    TextView day2_week_breakfast;
    TextView day2_week_launch;
    TextView day2_week_dinner;
    TextView day3_week_breakfast;
    TextView day3_week_launch;
    TextView day3_week_dinner;
    TextView day4_week_breakfast;
    TextView day4_week_launch;
    TextView day4_week_dinner;
    TextView day5_week_breakfast;
    TextView day5_week_launch;
    TextView day5_week_dinner;
    TextView day6_week_breakfast;
    TextView day6_week_launch;
    TextView day6_week_dinner;
    TextView day7_week_breakfast;
    TextView day7_week_launch;
    TextView day7_week_dinner;
    TextView day1_week;
    TextView day2_week;
    TextView day3_week;
    TextView day4_week;
    TextView day5_week;
    TextView day6_week;
    TextView day7_week;
    int counter=0;

    LinearLayout linear_day1;
    LinearLayout linear_day2;
    LinearLayout linear_day3;
    LinearLayout linear_day4;
    LinearLayout linear_day5;
    LinearLayout linear_day6;
    LinearLayout linear_day7;

    ImageView day1_imageview;
    ImageView day2_imageview;
    ImageView day3_imageview;
    ImageView day4_imageview;
    ImageView day5_imageview;
    ImageView day6_imageview;
    ImageView day7_imageview;




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

        LLayout_day=view.findViewById(R.id.LLayout_day);
        LLayout_week=view.findViewById(R.id.LLayout_week);

        getDiet();



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
                loadUserInfo();
                LLayout_week.setVisibility(View.VISIBLE);
                LLayout_day.setVisibility(View.GONE);

            }
            else {


                //Intent intent = new Intent(getContext(), MenuActivity.class);
                //startActivity(intent);
                today.setText(tiTle);
                //getMenu();
                loadUserInfo();
                LLayout_week.setVisibility(View.GONE);
                LLayout_day.setVisibility(View.VISIBLE);





            }

        }

        else{
            System.out.println("fragment hatası");
        }


    }

    private void getDiet() {
        day1_imageview=getView().findViewById(R.id.day1_imageview);
        linear_day1=getView().findViewById(R.id.linear_day1);

        day2_imageview=getView().findViewById(R.id.day2_imageview);
        linear_day2=getView().findViewById(R.id.linear_day2);

        day3_imageview=getView().findViewById(R.id.day3_imageview);
        linear_day3=getView().findViewById(R.id.linear_day3);

        day4_imageview=getView().findViewById(R.id.day4_imageview);
        linear_day4=getView().findViewById(R.id.linear_day4);

        day5_imageview=getView().findViewById(R.id.day5_imageview);
        linear_day5=getView().findViewById(R.id.linear_day5);

        day6_imageview=getView().findViewById(R.id.day6_imageview);
        linear_day6=getView().findViewById(R.id.linear_day6);

        day7_imageview=getView().findViewById(R.id.day7_imageview);
        linear_day7=getView().findViewById(R.id.linear_day7);


        setIBtn(day1_imageview,linear_day1);
        setIBtn(day2_imageview,linear_day2);
        setIBtn(day3_imageview,linear_day3);
        setIBtn(day4_imageview,linear_day4);
        setIBtn(day5_imageview,linear_day5);
        setIBtn(day6_imageview,linear_day6);
        setIBtn(day7_imageview,linear_day7);

    }

    private void setIBtn(ImageView imageView,LinearLayout layout) {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (layout.getVisibility()==View.GONE){
                    layout.setVisibility(View.VISIBLE);

                }else
                    layout.setVisibility(View.GONE);


            }
        });
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
                SettextWeek(bmi_state);
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



    public void getMenuWeek(String bmi_state,ArrayList<String> dates){

        for (int i=0;i<7;i++){
            String title=getArguments().getString(KEY_TITLE);

            FirebaseUser currentUser=auth.getCurrentUser();

            DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
            Query query=reference.child("Lists").child(bmi_state);

            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    String a1=("Day"+dates.get(0).charAt(0)+dates.get(0).charAt(1));
                    String a2=("Day"+dates.get(1).charAt(0)+dates.get(1).charAt(1));
                    String a3=("Day"+dates.get(2).charAt(0)+dates.get(2).charAt(1));
                    String a4=("Day"+dates.get(3).charAt(0)+dates.get(3).charAt(1));
                    String a5=("Day"+dates.get(4).charAt(0)+dates.get(4).charAt(1));
                    String a6=("Day"+dates.get(5).charAt(0)+dates.get(5).charAt(1));
                    String a7=("Day"+dates.get(6).charAt(0)+dates.get(6).charAt(1));


                    MenuModel m1=snapshot.child(a1).child("Breakfast").getValue(MenuModel.class);
                    MenuModel m2=snapshot.child(a1).child("Launch").getValue(MenuModel.class);
                    MenuModel m3=snapshot.child(a1).child("Dinner").getValue(MenuModel.class);

                    MenuModel m4=snapshot.child(a2).child("Breakfast").getValue(MenuModel.class);
                    MenuModel m5=snapshot.child(a2).child("Launch").getValue(MenuModel.class);
                    MenuModel m6=snapshot.child(a2).child("Dinner").getValue(MenuModel.class);

                    MenuModel m7=snapshot.child(a3).child("Breakfast").getValue(MenuModel.class);
                    MenuModel m8=snapshot.child(a3).child("Launch").getValue(MenuModel.class);
                    MenuModel m9=snapshot.child(a3).child("Dinner").getValue(MenuModel.class);

                    MenuModel m10=snapshot.child(a4).child("Breakfast").getValue(MenuModel.class);
                    MenuModel m11=snapshot.child(a4).child("Launch").getValue(MenuModel.class);
                    MenuModel m12=snapshot.child(a4).child("Dinner").getValue(MenuModel.class);

                    MenuModel m13=snapshot.child(a5).child("Breakfast").getValue(MenuModel.class);
                    MenuModel m14=snapshot.child(a5).child("Launch").getValue(MenuModel.class);
                    MenuModel m15=snapshot.child(a5).child("Dinner").getValue(MenuModel.class);

                    MenuModel m16=snapshot.child(a6).child("Breakfast").getValue(MenuModel.class);
                    MenuModel m17=snapshot.child(a6).child("Launch").getValue(MenuModel.class);
                    MenuModel m18=snapshot.child(a6).child("Dinner").getValue(MenuModel.class);

                    MenuModel m19=snapshot.child(a7).child("Breakfast").getValue(MenuModel.class);
                    MenuModel m20=snapshot.child(a7).child("Launch").getValue(MenuModel.class);
                    MenuModel m21=snapshot.child(a7).child("Dinner").getValue(MenuModel.class);

                    if (m1!=null||m2!=null||m3!=null){


                        day1_week_breakfast.setText(m1.menu);
                        day1_week_launch.setText(m2.menu);
                        day1_week_dinner.setText(m3.menu);
                        day2_week_breakfast.setText(m4.menu);
                        day2_week_launch.setText(m5.menu);
                        day2_week_dinner.setText(m6.menu);
                        day3_week_breakfast.setText(m7.menu);
                        day3_week_launch.setText(m8.menu);
                        day3_week_dinner.setText(m9.menu);
                        day4_week_breakfast.setText(m10.menu);
                        day4_week_launch.setText(m11.menu);
                        day4_week_dinner.setText(m12.menu);
                        day5_week_breakfast.setText(m13.menu);
                        day5_week_launch.setText(m14.menu);
                        day5_week_dinner.setText(m15.menu);
                        day6_week_breakfast.setText(m16.menu);
                        day6_week_launch.setText(m17.menu);
                        day6_week_dinner.setText(m18.menu);
                        day7_week_breakfast.setText(m19.menu);
                        day7_week_launch.setText(m20.menu);
                        day7_week_dinner.setText(m21.menu);
                        /*day1_week.setText(dates.get(0));
                        day2_week.setText(dates.get(1));
                        day3_week.setText(dates.get(2));
                        day4_week.setText(dates.get(3));
                        day5_week.setText(dates.get(4));
                        day6_week.setText(dates.get(5));
                        day7_week.setText(dates.get(6));*/


                    }



                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }


    }


    public void SettextWeek(String bmi_state){

            day1_week_breakfast=getView().findViewById(R.id.day1_week_breakfast);
            day1_week_launch=getView().findViewById(R.id.day1_week_launch);
            day1_week_dinner=getView().findViewById(R.id.day1_week_dinner);
            day2_week_breakfast=getView().findViewById(R.id.day2_week_breakfast);
            day2_week_launch=getView().findViewById(R.id.day2_week_launch);
            day2_week_dinner=getView().findViewById(R.id.day2_week_dinner);
            day3_week_breakfast=getView().findViewById(R.id.day3_week_breakfast);
             day3_week_launch=getView().findViewById(R.id.day3_week_launch);
             day3_week_dinner=getView().findViewById(R.id.day3_week_dinner);
             day4_week_breakfast=getView().findViewById(R.id.day4_week_breakfast);
             day4_week_launch=getView().findViewById(R.id.day4_week_launch);
             day4_week_dinner=getView().findViewById(R.id.day4_week_dinner);
             day5_week_breakfast=getView().findViewById(R.id.day5_week_breakfast);
             day5_week_launch=getView().findViewById(R.id.day5_week_launch);
             day5_week_dinner=getView().findViewById(R.id.day5_week_dinner);
             day6_week_breakfast=getView().findViewById(R.id.day6_week_breakfast);
             day6_week_launch=getView().findViewById(R.id.day6_week_launch);
             day6_week_dinner=getView().findViewById(R.id.day6_week_dinner);
             day7_week_breakfast=getView().findViewById(R.id.day7_week_breakfast);
             day7_week_launch=getView().findViewById(R.id.day7_week_launch);
             day7_week_dinner=getView().findViewById(R.id.day7_week_dinner);
             /*day1_week=getView().findViewById(R.id.day1_week);
             day2_week=getView().findViewById(R.id.day2_week);
             day3_week=getView().findViewById(R.id.day3_week);
             day4_week=getView().findViewById(R.id.day4_week);
             day5_week=getView().findViewById(R.id.day5_week);
             day6_week=getView().findViewById(R.id.day6_week);
             day7_week=getView().findViewById(R.id.day7_week);*/
             ArrayList<String> dates=getDateF();


             LinearLayout linear_day1;
             linear_day1=getView().findViewById(R.id.linear_day1);

            getMenuWeek(bmi_state,dates);







    }


    public ArrayList<String> getDateF(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        ArrayList<String> date_lists=new ArrayList<>();


        Calendar calendar = Calendar.getInstance();
        Date date=calendar.getTime();
        String date_S=sdf.format(date);
        date_lists.add(date_S);

        /*c.setTime(d);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        System.out.println(dayOfWeek);*/


        for (int i=0;i<30;i++){

            calendar.add(Calendar.DATE,1);
            date=calendar.getTime();
            date_S=sdf.format(date);
            date_lists.add(date_S);

        }


        return date_lists;

    }
}