package com.smallwei.secondproject.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.widget.SlidingPaneLayout;

import com.smallwei.secondproject.fragments.itemFragment;

import java.util.List;

/**
 * Created by smallwei on 2015/12/28.
 */
public class MyAdapter extends FragmentPagerAdapter {
    List<String> titles;

    public MyAdapter(FragmentManager fm, List<String> titles) {
        super(fm);
        this.titles = titles;
    }

    public static itemFragment newInstance(String title){
        itemFragment fragment=new itemFragment();
        Bundle bundle=new Bundle();
        bundle.putString("title", title);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public Fragment getItem(int position) {
        return newInstance(titles.get(position));
    }

    @Override
    public int getCount() {
        if(titles!=null){
            return titles.size();
        }
        return 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
