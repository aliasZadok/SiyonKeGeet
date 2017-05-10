package com.judah.siyonkegeet.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.judah.siyonkegeet.R;
import com.judah.siyonkegeet.utils.DurationConverter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Judah on 12/21/2016.
 */
public class SCTrackAdapter extends RecyclerView.Adapter<SCTrackAdapter.SongViewHolder> {
    private Context mContext;
    private ArrayList<Track> mTracks;
    private RecyclerItemClickListener listener;
    private int selectedPosition;

    public SCTrackAdapter(Context context, ArrayList<Track> songList, RecyclerItemClickListener listener) {
        super();

        this.mContext = context;
        this.mTracks = songList;
        this.listener = listener;
    }


    @Override
    public SCTrackAdapter.SongViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.track_list_row, parent, false);

        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SongViewHolder holder, int position) {
        Track song = mTracks.get(position);
        if (song != null) {

            holder.titleTextView.setText(song.getTitle());
            String duration = DurationConverter.convertDuration(song.getDuration());
            holder.durationTextView.setText(duration);
            holder.descriptionTextView.setText(song.getDescription());
            Picasso.with(mContext).load(song.getArtworkUrl()).placeholder(R.drawable.block_helper).into(holder.trackImageView);
            holder.bind(song, listener);

        }

    }

    @Override
    public int getItemCount() {
        return mTracks.size();
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    public void setFilter(ArrayList<Track> newTrack){
        mTracks = new ArrayList<>();
        mTracks.addAll(newTrack);
        notifyDataSetChanged();

    }

    public interface RecyclerItemClickListener {
        void onClickListener(Track song, int position);
    }

    public static class SongViewHolder extends RecyclerView.ViewHolder {

        ImageView trackImageView;
        TextView titleTextView;
        TextView durationTextView;
        TextView descriptionTextView;

        public SongViewHolder(View itemView) {
            super(itemView);
            titleTextView = (TextView) itemView.findViewById(R.id.track_title);
            trackImageView = (ImageView) itemView.findViewById(R.id.track_image);
            durationTextView = (TextView) itemView.findViewById(R.id.track_duration);
            descriptionTextView = (TextView) itemView.findViewById(R.id.track_description);

        }

        public void bind(final Track song, final RecyclerItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClickListener(song, getLayoutPosition());
                }
            });
        }

    }

}
