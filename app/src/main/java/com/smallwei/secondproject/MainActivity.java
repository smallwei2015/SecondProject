package com.smallwei.secondproject;

import android.app.ActionBar;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.smallwei.secondproject.adapters.MyAdapter;
import com.smallwei.secondproject.fragments.Circle_fragment;
import com.smallwei.secondproject.fragments.Personal_fragment;
import com.smallwei.secondproject.fragments.Search_fragment;
import com.smallwei.secondproject.fragments.ThingFragment;
import com.smallwei.secondproject.fragments.Tips_fragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SlidingPaneLayout.PanelSlideListener {


    SlidingPaneLayout slide;
    NavigationView navigation;
    private FragmentManager manager;
    android.support.v7.app.ActionBar actionBar=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        slide = (SlidingPaneLayout) findViewById(R.id.slide);
        navigation = (NavigationView) findViewById(R.id.navigator);
        navigation.setNavigationItemSelectedListener(this);
        slide.setPanelSlideListener(this);

        actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        //actionBar.setDisplayShowHomeEnabled(true);
        //actionBar.setHomeButtonEnabled(true);

        manager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.add(R.id.container,new ThingFragment());
        fragmentTransaction.commit();

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                slide.openPane();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        switch (itemId) {
            case R.id.item_circle:
                fragmentTransaction.replace(R.id.container,new Circle_fragment());
                break;
            case R.id.item_thing:
                fragmentTransaction.replace(R.id.container,new ThingFragment());
                break;
            case R.id.item_find:
                fragmentTransaction.replace(R.id.container,new Search_fragment());
                break;
            case R.id.item_me:
                fragmentTransaction.replace(R.id.container,new Personal_fragment());
                break;
            case R.id.item_tips:
                fragmentTransaction.replace(R.id.container,new Tips_fragment());
                break;
            case R.id.menu_setting:
                fragmentTransaction.replace(R.id.container,new Personal_fragment());
                break;
            case R.id.menu_exit:
                ActivityCompat.finishAffinity(this);
                break;

        }
        fragmentTransaction.commit();
        slide.closePane();
        return true;
    }

    public void onPanelClosed(View view) {

    }

    @Override
    public void onPanelOpened(View viw) {

    }

    @Override
    public void onPanelSlide(View arg0, float arg1) {

    }
}
