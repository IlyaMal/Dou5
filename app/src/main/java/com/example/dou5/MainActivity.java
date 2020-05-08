package com.example.dou5;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    FloatingActionButton fabMain;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fabMain = findViewById(R.id.fabMain);

        drawerLayout = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigationView);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawerOpen,R.string.drawerClose);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        if (savedInstanceState==null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new NewsFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_news);
            Log.d( "items", "3");
        }


    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_news:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new NewsFragment()).commit();
                navigationView.setCheckedItem(R.id.nav_news);
                break;
            case R.id.nav_contests:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ContestsFragment()).commit();
                navigationView.setCheckedItem(R.id.nav_contests);
                break;
            case R.id.nav_sub:
                Intent intent;
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://dou-ufa.ru/subscription.html"));
                startActivity(intent);
                Toast.makeText(this, "Открывается...", Toast.LENGTH_SHORT).show();
                navigationView.setCheckedItem(R.id.nav_sub);
                break;
            case R.id.nav_main:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://dou-ufa.ru"));
                startActivity(intent);
                Toast.makeText(this, "Открывается...", Toast.LENGTH_SHORT).show();
                navigationView.setCheckedItem(R.id.nav_main);
                break;
            case R.id.nav_about:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://dou-ufa.ru/about.html"));
                startActivity(intent);
                Toast.makeText(this, "Открывается...", Toast.LENGTH_SHORT).show();
                navigationView.setCheckedItem(R.id.nav_ads);
                break;
            case R.id.nav_ads:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://dou-ufa.ru/ads.html"));
                startActivity(intent);
                Toast.makeText(this, "Открывается...", Toast.LENGTH_SHORT).show();
                navigationView.setCheckedItem(R.id.nav_ads);
                break;
            case R.id.nav_close:
                drawerLayout.closeDrawer(GravityCompat.END);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.END);
        fabMain.animate().rotation(0f).setDuration(300).start();
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
            drawerLayout.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }
    public void open(View v){
        fabMain.animate().rotation(180f).setDuration(300).start();
        drawerLayout.openDrawer(GravityCompat.END);
    }



}