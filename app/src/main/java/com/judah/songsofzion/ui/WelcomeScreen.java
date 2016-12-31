package com.judah.songsofzion.ui;

import com.judah.songsofzion.R;
import com.stephentuso.welcome.BasicPage;
import com.stephentuso.welcome.ParallaxPage;
import com.stephentuso.welcome.TitlePage;
import com.stephentuso.welcome.WelcomeActivity;
import com.stephentuso.welcome.WelcomeConfiguration;

/**
 * Created by Judah on 11/6/2016.
 */
public class WelcomeScreen extends WelcomeActivity {
    @Override
    protected WelcomeConfiguration configuration() {
        return new WelcomeConfiguration.Builder(this)
                .defaultBackgroundColor(R.color.colorPrimary)
                .page(new TitlePage(R.drawable.human_greeting,
                                "Welcome")
                )

                .page(new BasicPage(R.drawable.music_note,
                                "Gospel Songs",
                                "download and listen to all gospel songs")
                                .background(R.color.colorPrimary)
                )

                .page(new BasicPage(R.drawable.translate,
                                "Diverse Languages",
                                "includes Hindi, Nepali, Tamil, and Telugu songs")
                                .background(R.color.colorPrimary)
                )

                .swipeToDismiss(true)
                .exitAnimation(android.R.anim.fade_out)
                .build();
    }
}
