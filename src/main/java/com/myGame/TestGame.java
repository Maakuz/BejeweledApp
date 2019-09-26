package com.myGame;

import com.markus.framework.Screen;
import com.com.markus.framework.implementation.AndroidGame;

/**
 * Created by Markus on 2016-03-02.
 */
public class TestGame extends AndroidGame
{
    @Override
    public Screen getInitScreen()
    {
        return new LoadingScreen(this);
    }

    @Override
    public void onBackPressed()
    {
        getCurrentScreen().backButton();
    }
}
