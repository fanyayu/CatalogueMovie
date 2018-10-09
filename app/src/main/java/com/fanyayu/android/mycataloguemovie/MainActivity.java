package com.fanyayu.android.mycataloguemovie;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    @BindView(R.id.bottom_nav) BottomNavigationView bottomNavigationView;
    private CharSequence mTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        if (savedInstanceState == null){
            android.support.v4.app.Fragment currentFragment = new HomeFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_layout, currentFragment)
                    .commit();

            getSupportActionBar().setTitle(getString(R.string.home));
            mTitle = getSupportActionBar().getTitle();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle(mTitle);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings){
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        android.support.v4.app.Fragment fragment = null;

        String title = "";

        switch (item.getItemId()) {
            case R.id.action_home:
                title = getString(R.string.home);
                fragment = new HomeFragment();
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                break;
            case R.id.action_search:
                title = getString(R.string.search_movie);
                fragment = new SearchFragment();
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                break;
            case R.id.action_fav:
                title = getString(R.string.fav_movie);
                fragment = new FavoriteFragment();
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                break;
        }

        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_layout, fragment)
                    .commit();
        }
        getSupportActionBar().setTitle(title);
        mTitle = getSupportActionBar().getTitle();
        return true;
    }
}
