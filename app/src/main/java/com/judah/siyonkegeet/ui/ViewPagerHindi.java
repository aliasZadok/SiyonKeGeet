package com.judah.siyonkegeet.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.judah.siyonkegeet.R;
import com.judah.siyonkegeet.fragments.LyricsFragment;
import com.judah.siyonkegeet.model.HindiLyrics;

/**
 * Created by Judah on 1/11/2017.
 */

public class ViewPagerHindi extends Fragment {
    public static final String KEY_LYRICS_INDEX = "lyrics_index";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        int index = getArguments().getInt(KEY_LYRICS_INDEX);
        getActivity().setTitle(HindiLyrics.songNames[index]);
        Toast.makeText(getActivity(), HindiLyrics.songNames[index], Toast.LENGTH_SHORT).show();
        View view = inflater.inflate(R.layout.viewpager_hindi_fragment, container, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);

        final LyricsFragment lyrics = new LyricsFragment();
        Bundle bundleLyrics = new Bundle();
        bundleLyrics.putInt(KEY_LYRICS_INDEX, index);
        lyrics.setArguments(bundleLyrics);

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        viewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return lyrics;
            }

            @Override
            public int getCount() {
                return 1;
            }
        });
        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        getActivity().setTitle(getResources().getString(R.string.lyrics));
    }

}
