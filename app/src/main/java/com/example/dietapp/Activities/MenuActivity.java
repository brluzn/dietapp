package com.example.dietapp.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.dietapp.Adapters.ExpendableListAdapter;
import com.example.dietapp.Helper.FragmentNavigationManager;
import com.example.dietapp.Helper.NavigationManager;
import com.example.dietapp.Models.UserInfo;
import com.example.dietapp.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

public class MenuActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;

    FirebaseAuth auth= FirebaseAuth.getInstance();

    private ArrayList<UserInfo> info=new ArrayList<>();

    private ActionBarDrawerToggle actionBarDrawerToggle;
    private String mActivityTitle;
    private String[] items;

    private ExpandableListView expandableListView;
    private ExpandableListAdapter adapter;
    private List<String> lstTitle;
    private Map<String,List<String>> lstChild;
    private NavigationManager navigationManager;

    TextView username;
    public TextView weight;
    public TextView height;
    public TextView bmi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        drawerLayout=findViewById(R.id.drawer_layout);
        mActivityTitle=getTitle().toString();
        expandableListView=(ExpandableListView) findViewById(R.id.navList);
        navigationManager= FragmentNavigationManager.getmInstance(this);




        initItems();

        View listHeaderView=getLayoutInflater().inflate(R.layout.drawer_header_layout,null,false);
        expandableListView.addHeaderView(listHeaderView);


        username=listHeaderView.findViewById(R.id.drawer_userName_textView);
        weight=listHeaderView.findViewById(R.id.drawer_weight_textView);
        height=listHeaderView.findViewById(R.id.drawer_height_textView);
        bmi=listHeaderView.findViewById(R.id.drawer_bmi_textView);

        loadData();

        genData();

        addDrawersItem();
        setupDrawer();

        if (savedInstanceState==null){
            selectFirstItemAsDefault();
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("DietApp");

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    private void selectFirstItemAsDefault() {
        if (navigationManager !=null){

            String firstItem=lstTitle.get(0);
            navigationManager.showFragment(firstItem);
            getSupportActionBar().setTitle(firstItem);
        }

    }

    private void setupDrawer() {
            actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close){

                @Override
                public void onDrawerOpened(View drawerView) {
                    super.onDrawerOpened(drawerView);
                  //  getSupportActionBar().setTitle("EDDev");
                    invalidateOptionsMenu();
                }

                @Override
                public void onDrawerClosed(View drawerView) {
                    super.onDrawerClosed(drawerView);
                    //getSupportActionBar().setTitle(mActivityTitle);
                    invalidateOptionsMenu();
                }
            };
            actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
            drawerLayout.setDrawerListener(actionBarDrawerToggle);

    }

    private void addDrawersItem() {
        adapter=new ExpendableListAdapter(this,lstTitle,lstChild);
        expandableListView.setAdapter(adapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                getSupportActionBar().setTitle(lstTitle.get(groupPosition).toString());
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                getSupportActionBar().setTitle("DietApp");
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                String selectedItem=((List)(lstChild.get(lstTitle.get(groupPosition)))).get(childPosition).toString();
                getSupportActionBar().setTitle(selectedItem);

                if(items[groupPosition].equals(lstTitle.get(groupPosition)))
                    navigationManager.showFragment(selectedItem);
                else
                    throw new IllegalArgumentException("Not Supported Fragment");

                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });
    }

    private void genData() {

        List<String> title= Arrays.asList("Profile","Week 1","Week 2","Week 3","Week 4");
        List<String> childitem1=Arrays.asList("02.04.2020");
        List<String> childitem2=Arrays.asList("12.04.2020","03.04.2020","04.04.2020","05.04.2020","04.04.2020","05.04.2020");
        List<String> childitemprofile=Arrays.asList("Edit Profile","Logout");

        lstChild=  new TreeMap<>();
        lstChild.put(title.get(0),childitemprofile);
        lstChild.put(title.get(1),childitem2);
        lstChild.put(title.get(2),childitem2);
        lstChild.put(title.get(3),childitem2);
        lstChild.put(title.get(4),childitem1);








        lstTitle=new ArrayList<>(lstChild.keySet());


    }

    private void initItems() {

        items=new String[] {"Profile","Week 1","Week 2","Week 3","Week 4"};
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nav_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();

        if (actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }

    public void loadData(){

        FirebaseUser currentUser=auth.getCurrentUser();

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
        Query query=reference.child("Users").child(currentUser.getUid());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                UserInfo a=snapshot.getValue(UserInfo.class);
                info.add(a);
                username.setText(info.get(0).user_firstname+" "+info.get(0).user_surname);
                weight.setText("Weight:"+info.get(0).user_weight);
                height.setText("Height:"+info.get(0).user_height);
                bmi.setText("BMI:"+info.get(0).user_BMI);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }



    /*@Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            Intent intent=new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }*/
}