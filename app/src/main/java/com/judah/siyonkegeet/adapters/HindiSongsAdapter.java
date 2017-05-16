package com.judah.siyonkegeet.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.futuremind.recyclerviewfastscroll.SectionTitleProvider;
import com.judah.siyonkegeet.model.HindiLyrics;
import com.judah.siyonkegeet.R;
import com.judah.siyonkegeet.fragments.FragmentListHindi;


/**
 * Created by Judah on 1/11/2017.
 */

public class HindiSongsAdapter extends RecyclerView.Adapter implements SectionTitleProvider {

    private final FragmentListHindi.OnSongsSelectedInterface mListener;

    public HindiSongsAdapter(FragmentListHindi.OnSongsSelectedInterface listener) {
        mListener = listener;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hindi_songs_list, parent, false);
        return new HindiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((HindiViewHolder) holder).bindView(position);
    }

    @Override
    public int getItemCount() {
        return HindiLyrics.songNames.length;
    }


    @Override
    public String getSectionTitle(int position) {
        return HindiLyrics.songNames[position].substring(0, 2);
    }


    private class HindiViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTextView;
        private int mIndex;

        public HindiViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.itemText);
            itemView.setOnClickListener(this);
        }

        public void bindView(int position) {
            mIndex = position;
            mTextView.setText(HindiLyrics.songNames[position]);
        }

        @Override
        public void onClick(View view) {
            mListener.onListSongsSelected(mIndex);
        }
    }


}
