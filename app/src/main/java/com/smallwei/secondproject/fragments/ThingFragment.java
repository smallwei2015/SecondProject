package com.smallwei.secondproject.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.smallwei.secondproject.R;
import com.smallwei.secondproject.adapters.MyAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThingFragment extends Fragment implements View.OnTouchListener {

    ViewPager pager;
    TabLayout tabLayout;
    private boolean isCanScroll;

    public ThingFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_thing, container, false);

        pager= (ViewPager) view.findViewById(R.id.viewPager);
        tabLayout= (TabLayout) view.findViewById(R.id.tab);
        List<String> titles = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            titles.add("title");
        }

        MyAdapter adapter = new MyAdapter(getChildFragmentManager(), titles);
        pager.setAdapter(adapter);

        pager.setOnTouchListener(this);
        tabLayout.setupWithViewPager(pager);
        return view;
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //Display display = ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        DisplayMetrics out = new DisplayMetrics();
        display.getMetrics(out);

        int totalWidth = out.widthPixels;
        float eX = event.getX();
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (eX >= totalWidth / 3) {
                    isCanScroll = true;
                } else {
                    isCanScroll = false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (isCanScroll) {
                    pager.requestDisallowInterceptTouchEvent(true);
                } else {
                    pager.requestDisallowInterceptTouchEvent(false);
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return false;
    }
}
