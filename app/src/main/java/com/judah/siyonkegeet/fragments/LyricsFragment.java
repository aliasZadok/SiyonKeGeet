package com.judah.siyonkegeet.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.judah.siyonkegeet.model.HindiLyrics;
import com.judah.siyonkegeet.ui.ViewPagerHindi;
import com.judah.siyonkegeet.R;

/**
 * Created by Judah on 1/11/2017.
 */

public class LyricsFragment extends Fragment {
    private static final String KEY_CHECKED_BOXES = "KEY_CHECKED_BOXES";
    private CheckBox[] mCheckBoxes;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int index = getArguments().getInt(ViewPagerHindi.KEY_LYRICS_INDEX);
        View view = inflater.inflate(R.layout.fragment_lyrics, container, false);

        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.fragmentLyrics);
        String[] lyrics = HindiLyrics.songLyrics[index].split("`");
        mCheckBoxes = new CheckBox[lyrics.length];
        boolean[] checkedBoxes = new boolean[mCheckBoxes.length];
        if (savedInstanceState != null && savedInstanceState.getBooleanArray(KEY_CHECKED_BOXES) != null) {
            checkedBoxes = savedInstanceState.getBooleanArray(KEY_CHECKED_BOXES);
        }
        setUpCheckBoxes(lyrics, linearLayout, checkedBoxes);

        return view;
    }

    private void setUpCheckBoxes(String[] lyrics, ViewGroup container, boolean[] checkedBoxes) {
        int i = 0;
        for (String lyric : lyrics) {
            mCheckBoxes[i] = new CheckBox(getActivity());
            mCheckBoxes[i].setTextSize(20f);
            mCheckBoxes[i].setText(lyric);
            container.addView(mCheckBoxes[i]);
            if (checkedBoxes[i]) {
                mCheckBoxes[i].toggle();
            }
            i++;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        boolean[] stateOfCheckBoxes = new boolean[mCheckBoxes.length];
        int i = 0;
        for (CheckBox checkBox : mCheckBoxes) {
            stateOfCheckBoxes[i] = checkBox.isChecked();
            i++;
        }
        outState.putBooleanArray(KEY_CHECKED_BOXES, stateOfCheckBoxes);
        super.onSaveInstanceState(outState);
    }

}
