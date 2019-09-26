package com.myGame;

import java.util.List;

import com.markus.framework.Game;
import com.markus.framework.Graphics;
import com.markus.framework.Screen;
import com.markus.framework.Input.TouchEvent;


public class MainMenuScreen extends Screen
{
    UserInterface ui;
    public MainMenuScreen(Game game)
    {
        super(game);
        ui = new UserInterface();
        ui.setTextures(Assets.menu, Assets.playImage, Assets.quitImage, Assets.bg, Assets.pauseImage, Assets.gameOverImage, Assets.mainBackground);
    }


    @Override
    public void update(float deltaTime)
    {
        Graphics g = game.getGraphics();
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

        int len = touchEvents.size();
        for (int i = 0; i < len; i++)
        {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_UP)
            {


                if (inBounds(event, (1920/2) - (Assets.playImage.getWidth() / 2), 200, Assets.playImage.getWidth(), Assets.playImage.getHeight()))
                {
                    //START GAME
                    Assets.click.play(1);
                    game.setScreen(new GameScreen(game));
                }

                if (inBounds(event, (1920/2) - (Assets.quitImage.getWidth() / 2), 600, Assets.quitImage.getWidth(), Assets.quitImage.getHeight()))
                {
                    //QUIT GAME
                    android.os.Process.killProcess(android.os.Process.myPid());
                }


            }
        }
    }


    private boolean inBounds(TouchEvent event, int x, int y, int width, int height)
    {
        if (event.x > x && event.x < x + width - 1 && event.y > y
                && event.y < y + height - 1)
            return true;
        else
            return false;
    }


    @Override
    public void draw(float deltaTime)
    {
        Graphics g = game.getGraphics();
        ui.drawMenu(g);
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
        //TODO exit game mebbeh
    }
}