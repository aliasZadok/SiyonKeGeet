package com.judah.siyonkegeet.ui;

import com.judah.siyonkegeet.R;
import com.stephentuso.welcome.BasicPage;
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
                                "Welcome to Siyon Ke Geet")
                )

                .page(new BasicPage(R.drawable.music_note,
                                "Hindi Gospel Songs",
                                "Listen to all the latest and old Hindi gospel songs")
                                .background(R.color.colorPrimary)
                )

                .page(new BasicPage(R.drawable.translate,
                                "All Lyrics",
                                "This app includes all the lyrics in Siyon Ke Geet song book")
                                .background(R.color.colorPrimary)
                )

                .swipeToDismiss(true)
                .exitAnimation(android.R.anim.fade_out)
                .build();
    }
}
