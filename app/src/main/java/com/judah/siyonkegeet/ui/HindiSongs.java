package com.judah.siyonkegeet.ui;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.judah.siyonkegeet.R;
import com.judah.siyonkegeet.fragments.FragmentListHindi;

public class HindiSongs extends AppCompatActivity implements FragmentListHindi.OnSongsSelectedInterface {
    public static final String LIST_FRAGMENT = "list_frag";
    public static final String VIEW_PAGER_FRAGMENT = "viewpager_frag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hindi_songs);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FragmentListHindi savedFragment = (FragmentListHindi) getSupportFragmentManager().findFragmentByTag(LIST_FRAGMENT);
        if (savedFragment == null) {
            FragmentListHindi listFragment = new FragmentListHindi();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.placeHolder, listFragment, LIST_FRAGMENT);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onListSongsSelected(int index) {
        ViewPagerHindi ViewPager = new ViewPagerHindi();
        Bundle bundle= new Bundle();
        bundle.putInt(ViewPagerHindi.KEY_LYRICS_INDEX, index);
        ViewPager.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.placeHolder, ViewPager, VIEW_PAGER_FRAGMENT);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
