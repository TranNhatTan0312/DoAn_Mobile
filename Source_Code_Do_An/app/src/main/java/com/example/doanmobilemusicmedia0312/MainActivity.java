package com.example.doanmobilemusicmedia0312;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.doanmobilemusicmedia0312.Fragment.FavoriteFragment;
import com.example.doanmobilemusicmedia0312.Fragment.HomeFragment;
import com.example.doanmobilemusicmedia0312.Fragment.ProfileFragment;
import com.example.doanmobilemusicmedia0312.Fragment.SearchFragment;
import com.example.doanmobilemusicmedia0312.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addBottomNavigation();


    }

    private void addBottomNavigation() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,HomeFragment.class,null);
        fragmentTransaction.commit();

        bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setBackground(null);
        bottomNav.setItemIconTintList(null);
        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                handleChangeFragment(item.getItemId());
                return true;
            }
        });
    }

    private void handleChangeFragment(int itemId) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if(itemId == R.id.nav_home){
            fragmentTransaction.replace(R.id.fragment_container, HomeFragment.class,null);
        }
        if(itemId == R.id.nav_search){
            fragmentTransaction.replace(R.id.fragment_container, SearchFragment.class,null);
        }
        if(itemId == R.id.nav_favorite){
            fragmentTransaction.replace(R.id.fragment_container, FavoriteFragment.class,null);
        }
        if(itemId == R.id.nav_profile){
            fragmentTransaction.replace(R.id.fragment_container, ProfileFragment.class,null);
        }
        fragmentTransaction.commit();

    }
}