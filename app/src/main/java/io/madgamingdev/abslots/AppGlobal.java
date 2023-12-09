package io.madgamingdev.abslots;

import android.app.Application;

public class AppGlobal extends Application
{
    private String AF_ID;
    private String GAME_URL = "https://www.ab.bet/abgames?referralCode=Google02";
    private String MAIN_URL = "https://www.skillpod.com/game/dino-bubbles/54/play";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public String getAF_ID()
    {
        AF_ID = getString(R.string.AppsFlyerID);
        return AF_ID;
    }

    public String getGAME_URL()
    {
        return GAME_URL;
    }

    public String getMAIN_URL()
    {
        return MAIN_URL;
    }
}
