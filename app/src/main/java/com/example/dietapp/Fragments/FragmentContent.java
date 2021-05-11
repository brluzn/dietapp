package com.example.dietapp.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dietapp.Activities.LoginActivity;
import com.example.dietapp.Activities.MainActivity;
import com.example.dietapp.Activities.MenuActivity;
import com.example.dietapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import javax.sql.StatementEvent;


public class FragmentContent extends Fragment {
    FirebaseAuth auth= FirebaseAuth.getInstance();

    private static final String KEY_TITLE = "Content";

    FirebaseUser currentUser=auth.getCurrentUser();



    TextView t_t;

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

        t_t=view.findViewById(R.id.tiTle);
        String tiTle=getArguments().getString(KEY_TITLE);
        System.out.println(tiTle);
        if(tiTle!=null){
            if (tiTle=="Edit Profile"){

                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
            if (tiTle=="Logout"){

                auth.signOut();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        }

        else
            System.out.println("fragment hatası");

    }
}