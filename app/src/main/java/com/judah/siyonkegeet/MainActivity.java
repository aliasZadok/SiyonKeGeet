package com.judah.siyonkegeet;


import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.RequestQueue;
import com.judah.siyonkegeet.Hindi.HindiSongs;
import com.judah.siyonkegeet.ui.SCTrackAdapter;
import com.judah.siyonkegeet.ui.Track;
import com.judah.siyonkegeet.ui.WelcomeScreen;
import com.judah.siyonkegeet.utils.Configuration;
import com.judah.siyonkegeet.utils.SoundCloudApiRequest;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.stephentuso.welcome.WelcomeHelper;

import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    WelcomeHelper screen;
    ProgressBar mLoader;
    ProgressBar mMainLoader;
    long currentSongLength;
    private Toolbar toolbar;
    private AccountHeader headerResult = null;
    private Drawer result = null;
    private ArrayList<Track> songList;
    private SCTrackAdapter mAdapter;
    private TextView mSelectedTrackTitle;
    private MediaPlayer mMediaPlayer;
    private RecyclerView recycler;
    private ImageView mPlayerController;
    private ImageView mButtonNext;
    private ImageView mButtonPrev;
    private SeekBar mSeekBar;
    private boolean firstLaunch = true;
    private int currentIndex;
    private MaterialSearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSelectedTrackTitle = (TextView) findViewById(R.id.selected_track_title);
        recycler = (RecyclerView) findViewById(R.id.track_list_view);
        mPlayerController = (ImageView) findViewById(R.id.player_control);
        mButtonNext = (ImageView) findViewById(R.id.btn_next);
        mButtonPrev = (ImageView) findViewById(R.id.btn_previous);
        mLoader = (ProgressBar) findViewById(R.id.loader);
        mMainLoader = (ProgressBar) findViewById(R.id.mainLoader);
        mSeekBar = (SeekBar) findViewById(R.id.seekProgressBar);
        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                newText = newText.toLowerCase();
                ArrayList<Track> newTrackList = new ArrayList<>();
                for (Track track : songList) {
                    String name = track.getTitle().toLowerCase();
                    if (name.contains(newText)) {
                        newTrackList.add(track);
                    }
                }
                mAdapter.setFilter(newTrackList);
                return true;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {

            }
        });

        getSongList("");

        songList = new ArrayList<>();

        recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mAdapter = new SCTrackAdapter(getApplicationContext(), songList, new SCTrackAdapter.RecyclerItemClickListener() {
            @Override
            public void onClickListener(Track song, int position) {
                firstLaunch = false;
                changeSelectedSong(position);
                prepareSong(song);
            }
        });
        recycler.setAdapter(mAdapter);

        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                togglePlay(mediaPlayer);
            }
        });
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                if (currentIndex + 1 < songList.size()) {
                    Track next = songList.get(currentIndex + 1);
                    changeSelectedSong(currentIndex + 1);
                    prepareSong(next);
                } else {
                    Track next = songList.get(0);
                    changeSelectedSong(0);
                    prepareSong(next);
                }
            }
        });

        handleSeekbar();

        pushPlay();
        pushPrevious();
        pushNext();

        screen = new WelcomeHelper(this, WelcomeScreen.class);
        screen.show(savedInstanceState);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final IProfile profile = new ProfileDrawerItem().withName("Siyon Ke Geet").withIcon(R.drawable.profile).withIdentifier(100);

        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withTranslucentStatusBar(true)
                .withHeaderBackground(R.drawable.header)
                .withSelectionListEnabledForSingleProfile(false)
                .addProfiles(profile)
                .build();


        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult)
                .withDisplayBelowStatusBar(true)
                .withActionBarDrawerToggleAnimated(true)
                .withDrawerGravity(Gravity.START)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("Songs").withIdentifier(1).withIcon(GoogleMaterial.Icon.gmd_music_note),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName("Lyrics").withIdentifier(2).withIcon(GoogleMaterial.Icon.gmd_library_books),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName("About").withIcon(GoogleMaterial.Icon.gmd_help).withIdentifier(3)

                ).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem != null) {
                            Intent intent = null;
                            if (drawerItem.getIdentifier() == 1) {
                                intent = new Intent(MainActivity.this, MainActivity.class);
                            } else if (drawerItem.getIdentifier() == 2) {
                                intent = new Intent(MainActivity.this, HindiSongs.class);
                            } else if (drawerItem.getIdentifier() == 3) {
                                intent = new Intent(MainActivity.this, About.class);
                            }
                            if (intent != null) {
                                MainActivity.this.startActivity(intent);
                            }
                        }

                        return false;
                    }
                })
                .build();

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_menu, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);

        return true;
    }

    public void getSongList(String query) {
        RequestQueue queue = VolleySingleton.getInstance(this).getRequestQueue();
        SoundCloudApiRequest request = new SoundCloudApiRequest(queue);
        mMainLoader.setVisibility(View.VISIBLE);
        request.getSongList(query, new SoundCloudApiRequest.SoundCloudInterface() {
            @Override
            public void onSuccess(ArrayList<Track> songs) {
                currentIndex = 0;
                mMainLoader.setVisibility(View.GONE);
                songList.clear();
                songList.addAll(songs);
                mAdapter.notifyDataSetChanged();
                mAdapter.setSelectedPosition(0);

            }

            @Override
            public void onError(String message) {
                mMainLoader.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        screen.onSaveInstanceState(outState);
    }

    private void prepareSong(Track song) {

        currentSongLength = song.getDuration();
        mLoader.setVisibility(View.VISIBLE);
        mSelectedTrackTitle.setVisibility(View.GONE);
        mPlayerController.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_play_white_36dp));
        mSelectedTrackTitle.setText(song.getTitle());
        mMediaPlayer.reset();

        try {
            mMediaPlayer.setDataSource(song.getStreamUrl() + "?client_id=" + Configuration.CLIENT_ID);
            mMediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void togglePlay(MediaPlayer mp) {

        if (mp.isPlaying()) {
            mp.stop();
            mp.reset();
        } else {
            mLoader.setVisibility(View.GONE);
            mSelectedTrackTitle.setVisibility(View.VISIBLE);
            mp.start();
            mPlayerController.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_pause_white_36dp));
            final Handler mHandler = new Handler();
            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mSeekBar.setMax((int) currentSongLength / 1000);
                    int mCurrentPosition = mMediaPlayer.getCurrentPosition() / 1000;
                    mSeekBar.setProgress(mCurrentPosition);
                    mHandler.postDelayed(this, 1000);
                }

            });
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMediaPlayer != null) {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
            }
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    private void changeSelectedSong(int index) {
        mAdapter.notifyItemChanged(mAdapter.getSelectedPosition());
        currentIndex = index;
        mAdapter.setSelectedPosition(currentIndex);
        mAdapter.notifyItemChanged(currentIndex);
    }

    private void pushPlay() {
        mPlayerController.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mMediaPlayer.isPlaying() && mMediaPlayer != null) {
                    mPlayerController.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_play_white_36dp));
                    mMediaPlayer.pause();
                } else {
                    if (firstLaunch) {
                        Track song = songList.get(0);
                        changeSelectedSong(0);
                        prepareSong(song);
                    } else {
                        mMediaPlayer.start();
                        firstLaunch = false;
                    }
                    mPlayerController.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_pause_white_36dp));
                }

            }
        });
    }

    private void pushPrevious() {

        mButtonPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstLaunch = false;
                if (mMediaPlayer != null) {

                    if (currentIndex - 1 >= 0) {
                        Track previous = songList.get(currentIndex - 1);
                        changeSelectedSong(currentIndex - 1);
                        prepareSong(previous);
                    } else {
                        changeSelectedSong(songList.size() - 1);
                        prepareSong(songList.get(songList.size() - 1));
                    }

                }
            }
        });

    }

    private void pushNext() {
        mButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstLaunch = false;
                if (mMediaPlayer != null) {
                    if (currentIndex + 1 < songList.size()) {
                        Track next = songList.get(currentIndex + 1);
                        changeSelectedSong(currentIndex + 1);
                        prepareSong(next);
                    } else {
                        changeSelectedSong(0);
                        prepareSong(songList.get(0));
                    }
                }
            }
        });
    }

    private void handleSeekbar() {
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mMediaPlayer != null && fromUser) {
                    mMediaPlayer.seekTo(progress * 1000);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


}