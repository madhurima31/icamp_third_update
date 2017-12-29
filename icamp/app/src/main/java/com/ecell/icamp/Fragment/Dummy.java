package com.ecell.icamp.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ecell.icamp.R;

/**
 * Created by Niklaus on 28-Feb-17.
 */

public class Dummy extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dummy, container,false);
        return view;
    }
    /*public static Dashboard_Startups newInstance() {
        Dashboard_Startups fragment = new Dashboard_Startups();
        return fragment;
    }*/
}
