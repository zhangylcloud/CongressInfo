package com.example.zhang.homework9;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Fragment;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;

@TargetApi(11)
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, leg_main.OnFragmentInteractionListener, bill_main.OnFragmentInteractionListener {

    Set<Legislator> legSet;
    Set<Bill> billSet;
    Set<Committee> comSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {






        Log.d("zyl", "program started");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Handle Drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_legislators) {
            leg_main legMain = new leg_main();
            FragmentManager manager = getFragmentManager();
            manager.beginTransaction().replace(R.id.content_main, legMain).commit();
            //Toast.makeText(MainActivity.this, "Legislators", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_bills) {
            bill_main billMain = new bill_main();
            FragmentManager manager = getFragmentManager();
            manager.beginTransaction().replace(R.id.content_main, billMain).commit();
            //Toast.makeText(MainActivity.this, "Bills", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_committees) {
            //Toast.makeText(MainActivity.this, "Committees", Toast.LENGTH_SHORT).show();
            com_main comMain = new com_main();
            FragmentManager manager = getFragmentManager();
            manager.beginTransaction().replace(R.id.content_main, comMain).commit();
        } else if (id == R.id.nav_favorites) {

            fav_main favMain = new fav_main();
            FragmentManager manager = getFragmentManager();
            manager.beginTransaction().replace(R.id.content_main, favMain).commit();
            //Toast.makeText(MainActivity.this, "Favorites", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_about) {
            Intent intent = new Intent("com.example.zhang.homework9.AboutMe");

            startActivity(intent);
            //Toast.makeText(MainActivity.this, "About", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onFragmentInteraction(String tag){
        switch (tag){
            case "Legislators": break;
            case "Bills" : break;
        }

    }

}
