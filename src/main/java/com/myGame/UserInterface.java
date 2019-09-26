package com.myGame;

import android.graphics.Color;
import android.graphics.Paint;

import com.markus.framework.Graphics;
import com.markus.framework.Image;

/**
 * Created by Markus on 2016-03-04.
 */
public class UserInterface extends Menu implements MainGameUI
{

    private String score;
    private String combo;
    private String moves;
    private String highScore;

    private Paint paint;

    private Image backgroundImage;
    private Image pauseImage;
    private Image gameOverImage;
    private Image mainBackground;

    public UserInterface()
    {
        this.score = "Score: ";
        this.combo = "Combo: ";
        this.moves = "Moves left: ";
        this.highScore = "High score: ";

        paint = new Paint();
        paint.setTextSize(72);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
    }

    public void setTextures(Image menu, Image start, Image quit, Image backgroundImage, Image pauseImage, Image gameOverImage, Image mainBackground)
    {
        super.setTextures(menu, start, quit);
        this.backgroundImage = backgroundImage;
        this.gameOverImage = gameOverImage;
        this.pauseImage = pauseImage;
        this.mainBackground = mainBackground;
    }

    public void drawMainGame(Graphics graphics, int score, int combo, int movesLeft, int highScore)
    {
        graphics.drawString(this.score + score, 1300, 100, paint);
        graphics.drawString(this.combo + combo, 1300, 300, paint);
        graphics.drawString(this.moves + movesLeft, 1300, 500, paint);
        graphics.drawString(this.highScore + highScore, 1300, 700, paint);
    }

    public void drawBackground(Graphics graphics)
    {
        graphics.drawImage(this.mainBackground, 0, 0);
        graphics.drawImage(this.backgroundImage, 40, 40);
    }

    public void drawPause(Graphics graphics)
    {
        graphics.drawImage(this.pauseImage, 0, 0);
    }

    public void drawGameOver(Graphics graphics)
    {
        graphics.drawImage(this.gameOverImage, 0, 0);
    }

}
