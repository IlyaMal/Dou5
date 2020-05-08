package com.example.dou5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class DetailActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    private ImageView imageView;
    private TextView titleTExtView, detailTextView, text;
    private String detailString;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    FloatingActionButton fabMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        fabMain = findViewById(R.id.fabMain);

        imageView = findViewById(R.id.imageView);
        titleTExtView = findViewById(R.id.textView);
        detailTextView = findViewById(R.id.detailTextView);
        text = findViewById(R.id.text);

        titleTExtView.setText(getIntent().getStringExtra("title"));
        Picasso.get().load("https://dou-ufa.ru"+getIntent().getStringExtra("image")).into(imageView);
        Content content = new Content();
        content.execute();

        drawerLayout = findViewById(R.id.drawer1);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigationView1);

        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawerOpen,R.string.drawerClose);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_page:
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("item", "news");
                startActivity(intent);
                break;
            case R.id.nav_sub:

                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://dou-ufa.ru/subscription.html"));
                startActivity(intent);
                Toast.makeText(this, "Открывается...", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_main:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://dou-ufa.ru"));
                startActivity(intent);
                Toast.makeText(this, "Открывается...", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_about:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://dou-ufa.ru/about.html"));
                startActivity(intent);
                Toast.makeText(this, "Открывается...", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_ads:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://dou-ufa.ru/ads.html"));
                startActivity(intent);
                Toast.makeText(this, "Открывается...", Toast.LENGTH_SHORT).show();
                navigationView.setCheckedItem(R.id.nav_ads);
                break;
            case R.id.nav_close:
                drawerLayout.closeDrawer(GravityCompat.END);
                fabMain.animate().rotation(0f).setDuration(300).start();
                break;
        }
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

    private class Content extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            detailTextView.setText(detailString);

        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                String baseUrl = "https://dou-ufa.ru/";
                String detailUrl = getIntent().getStringExtra("detailUrl");

                String url = baseUrl + detailUrl;

                Document doc = Jsoup.connect(url).get();

                Elements data = doc.select("div.row.intro");

                detailString = data.select("p")
                        .text();
                Log.d( "det", "data: " + data+"ds: " + detailString);


            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }
    }
    public  void perform_action(View v){
        Intent intent;
        String baseUrl = "https://dou-ufa.ru/";
        String detailUrl = getIntent().getStringExtra("detailUrl");
        String url = baseUrl + detailUrl;
        intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }
    public void open(View v){
        fabMain.animate().rotation(180f).setDuration(300).start();
        drawerLayout.openDrawer(GravityCompat.END);
    }
}

