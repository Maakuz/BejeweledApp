package com.myGame;

import com.markus.framework.Game;
import com.markus.framework.Graphics;
import com.markus.framework.Screen;
import com.markus.framework.Graphics.ImageFormat;


public class LoadingScreen extends Screen
{
    public LoadingScreen(Game game)
    {
        super(game);
    }


    @Override
    public void update(float deltaTime)
    {
        Graphics g = game.getGraphics();

        Assets.menu = g.newImage("menu.png", ImageFormat.RGB565);
        Assets.bg = g.newImage("bg.png", ImageFormat.RGB565);
        Assets.highlight = g.newImage("highlight.png", ImageFormat.ARGB4444);
        Assets.appleGem = g.newImage("appleGem.png", ImageFormat.ARGB4444);
        Assets.redGem = g.newImage("redGem.png", ImageFormat.ARGB4444);
        Assets.yellowGem = g.newImage("yellowGem.png", ImageFormat.ARGB4444);
        Assets.blueGem = g.newImage("blueGem.png", ImageFormat.ARGB4444);
        Assets.lBlueGem = g.newImage("lblue.png", ImageFormat.ARGB4444);
        Assets.greenGem = g.newImage("greenGem.png", ImageFormat.ARGB4444);
        Assets.purpleGem = g.newImage("purpleGem.png", ImageFormat.ARGB4444);
        Assets.gameOverImage = g.newImage("gameOverImage.png", ImageFormat.RGB565);
        Assets.pauseImage = g.newImage("pauseImage.png", ImageFormat.RGB565);
        Assets.playImage = g.newImage("play.png", ImageFormat.RGB565);
        Assets.quitImage = g.newImage("quit.png", ImageFormat.RGB565);
        Assets.mainBackground = g.newImage("mainBackground.png", ImageFormat.RGB565);

        Assets.click = game.getAudio().createSound("cursor.wav");




        game.setScreen(new MainMenuScreen(game));


    }


    @Override
    public void draw(float deltaTime)
    {

    }


    @Override
    public void pause()
    {

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

    }
}