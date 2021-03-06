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
import com.example.dietapp.YoutubeConfig;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class DetailsActivity extends YouTubeBaseActivity {

    private static final String TAG="DetailsActivity";
    FirebaseAuth auth= FirebaseAuth.getInstance();
    Button btn;
    TextView recipe_title;
    TextView recipe;
    TextView details_time;

    YouTubePlayerView youTubePlayerView;
    YouTubePlayer.OnInitializedListener onInitializedListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        recipe_title=findViewById(R.id.recipe_title);
        recipe=findViewById(R.id.recipe);
        details_time=findViewById(R.id.details_time);
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
                    recipe.setText(m1.recipe);
                    details_time.setText(time);
                    recipe_title.setText(m1.recipe_title);
                    System.out.println(recipe);

                    youTubePlayerView=findViewById(R.id.youtube_play);
                    onInitializedListener=new YouTubePlayer.OnInitializedListener() {
                        @Override
                        public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

                            youTubePlayer.loadVideo(m1.video);
                        }

                        @Override
                        public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

                        }
                    };

                    youTubePlayerView.initialize(YoutubeConfig.getApiKey(),onInitializedListener);

                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}