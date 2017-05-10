package com.judah.siyonkegeet.Hindi;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.RatingCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.futuremind.recyclerviewfastscroll.FastScroller;
import com.judah.siyonkegeet.R;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;

/**
 * Created by Judah on 1/11/2017.
 */

public class FragmentListHindi extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        OnSongsSelectedInterface listener = (OnSongsSelectedInterface) getActivity();
        View view = inflater.inflate(R.layout.fragment_list_hindi, container, false);
        setHasOptionsMenu(true);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.hindiSongsList);
        FastScroller fastScroller = (FastScroller) view.findViewById(R.id.fastscroll);
        HindiSongsAdapter hindiSongsAdapter = new HindiSongsAdapter(listener);
        recyclerView.setAdapter(hindiSongsAdapter);
        fastScroller.setRecyclerView(recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        return view;
    }

    public interface OnSongsSelectedInterface {
        void onListSongsSelected(int index);
    }


}
