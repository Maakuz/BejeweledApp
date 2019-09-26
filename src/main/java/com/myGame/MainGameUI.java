package com.myGame;

import com.markus.framework.Graphics;

/**
 * Created by Markus on 2016-03-08.
 */
public interface MainGameUI
{
    public void drawMainGame(Graphics graphics, int score, int combo, int movesLeft, int highScore);

    public void drawBackground(Graphics graphics);

    public void drawPause(Graphics graphics);

    public void drawGameOver(Graphics graphics);
}
