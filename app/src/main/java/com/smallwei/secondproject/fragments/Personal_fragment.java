package com.smallwei.secondproject.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.smallwei.secondproject.R;


public class Personal_fragment extends Fragment implements View.OnClickListener {

    Button login,manager,night,sound,set;
    public Personal_fragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_personal_fragment, container, false);

         login= (Button) view.findViewById(R.id.person_login);
        manager= (Button) view.findViewById(R.id.person_manager);
        night= (Button) view.findViewById(R.id.person_night);
        sound= (Button) view.findViewById(R.id.person_sound);
        set= (Button) view.findViewById(R.id.person_set);
        login.setOnClickListener(this);
        manager.setOnClickListener(this);
        night.setOnClickListener(this);
        sound.setOnClickListener(this);
        set.setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick(View v) {

        int id = v.getId();
        switch (id){
            case R.id.person_login:
                break;
            case R.id.person_manager:
                break;
            case R.id.person_night:
                break;
            case R.id.person_sound:
                break;
            case R.id.person_set:
                break;
        }

    }
}
