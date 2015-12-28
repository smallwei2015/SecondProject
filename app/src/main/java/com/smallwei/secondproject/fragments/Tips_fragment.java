package com.smallwei.secondproject.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smallwei.secondproject.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Tips_fragment extends Fragment {


    public Tips_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tips_fragment, container, false);
    }


}
