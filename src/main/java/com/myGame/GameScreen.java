package com.myGame;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import android.graphics.Color;
import android.graphics.Paint;

import com.markus.framework.FileIO;
import com.markus.framework.Game;
import com.markus.framework.Graphics;
import com.markus.framework.Screen;
import com.markus.framework.Input.TouchEvent;
import com.myGame.gems.GemHandler;

public class GameScreen extends Screen
{
    enum GameState
    {
        Running, Paused, GameOver
    }

    private GameState state = GameState.Running;
    private GemHandler gemHandler;
    private UserInterface ui;
    private int highScore;
    private boolean highScoreBeaten;

    Paint paint;

    public GameScreen(Game game)
    {
        super(game);

        gemHandler = new GemHandler();
        ui = new UserInterface();
        ui.setTextures(Assets.menu, Assets.playImage, Assets.quitImage, Assets.bg, Assets.pauseImage, Assets.gameOverImage, Assets.mainBackground);
        this.highScoreBeaten = false;

        //Reading the high score, creepy business.
        FileIO io = game.getFileIO();
        try
        {
            InputStream readFile = io.readFile("highScore.txt");
            int data = readFile.read();
            String inData = "";
            while (data != -1)
            {
                inData += (char)data;
                data = readFile.read();
            }

            this.highScore = Integer.parseInt(inData);
            readFile.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();

            try
            {
                OutputStream writeFile = io.writeFile("highScore.txt");
                writeFile.write(48);
                writeFile.close();

                this.highScore = 0;
            }
            catch (IOException r)
            {
                r.printStackTrace();
            }
        }

        paint = new Paint();
        paint.setTextSize(30);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);

    }

    @Override
    public void update(float dt)
    {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

        // We have four separate update methods in this example.
        // Depending on the state of the game, we call different update methods.
        // Refer to Unit 3's code. We did a similar thing without separating the
        // update methods.

        if (state == GameState.Running)
            updateRunning(touchEvents, dt);
        if (state == GameState.Paused)
            updatePaused(touchEvents);
        if (state == GameState.GameOver)
            updateGameOver(touchEvents);
    }

    private void updateRunning(List<TouchEvent> touchEvents, float dt)
    {
        // 1. All touch input is handled here:

        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);

            if (event.type == TouchEvent.TOUCH_DOWN)
            {

            }

            if (event.type == TouchEvent.TOUCH_UP)
            {
                Assets.click.play(1);
            }
        }

        if (this.highScore < gemHandler.getScore())
        {
            this.highScore = gemHandler.getScore();
            this.highScoreBeaten = true;
        }
        gemHandler.update(dt, touchEvents);
        if (gemHandler.getMovesLeft() <= 0)
            this.state = GameState.GameOver;


    }

    private void updatePaused(List<TouchEvent> touchEvents)
    {
        int len = touchEvents.size();
        for (int i = 0; i < len; i++)
        {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_UP)
            {
                state = GameState.Running;
            }
        }
    }

    private void updateGameOver(List<TouchEvent> touchEvents)
    {
        int len = touchEvents.size();
        for (int i = 0; i < len; i++)
        {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_UP)
            {
                try
                {
                    OutputStream writeFile = game.getFileIO().writeFile("highScore.txt");

                    String highScore = "" + this.highScore;
                    for (int j = 0; j < highScore.length(); j++)
                    {
                        int data = (highScore.charAt(j));
                        writeFile.write(data);
                    }

                    writeFile.close();
                }

                catch (IOException e)
                {
                    e.printStackTrace();
                }


                nullify();
                game.setScreen(new MainMenuScreen(game));
                return;
            }
        }

    }

    @Override
    public void draw(float deltaTime)
    {
        Graphics g = game.getGraphics();

        g.clearScreen(Color.BLUE);


        if (state == GameState.Running)
            drawRunning();
        if (state == GameState.Paused)
            drawPaused();
        if (state == GameState.GameOver)
            drawGameOver();

    }

    private void nullify()
    {

        // Set all variables to null. You will be recreating them in the
        // constructor.
        paint = null;

        // Call garbage collector to clean up memory.
        System.gc();
    }


    private void drawRunning()
    {
        Graphics g = game.getGraphics();

        ui.drawBackground(g);
        gemHandler.draw(g);
        ui.drawMainGame(g, gemHandler.getScore(), gemHandler.getCombo(), gemHandler.getMovesLeft(), this.highScore);

    }

    private void drawPaused()
    {
        Graphics g = game.getGraphics();

        ui.drawPause(g);

    }

    private void drawGameOver()
    {
        Graphics g = game.getGraphics();
        ui.drawGameOver(g);

        if (highScoreBeaten)
        {
            g.drawString("!!!NEW HIGH SCORE!!!", 900, 600, paint);
            g.drawString("" + this.highScore, 900, 630, paint);
        }
    }

    @Override
    public void pause()
    {
        if (state == GameState.Running)
            state = GameState.Paused;

    }

    @Override
    public void resume()
    {

    }

    @Override
    public void dispose()
    {

    }

    @Override
    public void backButton()
    {
        pause();
    }
}