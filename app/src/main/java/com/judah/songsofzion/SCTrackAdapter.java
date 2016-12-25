package com.judah.songsofzion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Judah on 12/21/2016.
 */
public class SCTrackAdapter extends BaseAdapter {
    private Context mContext;
    private List<Track> mTracks;

    public SCTrackAdapter(Context context, List<Track> tracks){
        mContext = context;
        mTracks = tracks;
    }
    @Override
    public int getCount() {
        return mTracks.size();
    }

    @Override
    public Object getItem(int i) {
        return mTracks.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Track track = (Track) getItem(i);
        ViewHolder viewHolder;
        if (view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.track_list_row, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.trackImageView = (ImageView) view.findViewById(R.id.track_image);
            viewHolder.titleTextView = (TextView) view.findViewById(R.id.track_title);
            viewHolder.durationTextView = (TextView) view.findViewById(R.id.track_duration);
            viewHolder.descriptionTextView = (TextView) view.findViewById(R.id.track_description);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.titleTextView.setText(track.getTitle());
        viewHolder.durationTextView.setText(track.getDuration());
        viewHolder.descriptionTextView.setText(track.getDescription());
        Picasso.with(mContext).load(track.getArtworkURL()).into(viewHolder.trackImageView);
        return view;
    }
    static class ViewHolder {
        ImageView trackImageView;
        TextView titleTextView;
        TextView durationTextView;
        TextView descriptionTextView;
    }
}
