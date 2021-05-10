package com.example.dietapp.Helper;




import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.dietapp.Activities.MenuActivity;


import com.example.dietapp.BuildConfig;
import com.example.dietapp.Fragments.FragmentContent;
import com.example.dietapp.R;


public class FragmentNavigationManager implements NavigationManager {

    private static FragmentNavigationManager mInstance;
    private FragmentManager fragmentManager;
    private MenuActivity menuActivity;

    public static FragmentNavigationManager getmInstance(MenuActivity menuActivity){
        if(mInstance==null)
            mInstance=new FragmentNavigationManager();
        mInstance.configure(menuActivity);
        return mInstance;
    }

    private void configure(MenuActivity menuActivity) {
        menuActivity=menuActivity;
        fragmentManager=menuActivity.getSupportFragmentManager();

    }

    @Override
    public void showFragment(String title) {

        showFragment(FragmentContent.newInstance(title),false);


    }

    private void showFragment(Fragment fragmentContent, boolean b) {

        FragmentManager fm=fragmentManager;
        FragmentTransaction ft=fm.beginTransaction().replace(R.id.frame,fragmentContent);
        ft.addToBackStack(null);

        if (b|| !BuildConfig.DEBUG)
            ft.commitAllowingStateLoss();
        else
            ft.commit();
        fm.executePendingTransactions();
    }


}
