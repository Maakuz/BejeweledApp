package com.myGame;

import com.markus.framework.Graphics;
import com.markus.framework.Image;

/**
 * Created by Markus on 2016-03-08.
 */
public class Menu
{
    private Image menuBackground;
    private Image startButton;
    private Image quitButton;

    public Menu()
    {

    }

    public void setTextures(Image menu, Image start, Image quit)
    {
        this.menuBackground = menu;
        this.startButton = start;
        this.quitButton = quit;
    }

    public void drawMenu(Graphics graphics)
    {
        graphics.drawImage(menuBackground, 0, 0);
        graphics.drawImage(startButton, (1920/2) - (startButton.getWidth() / 2), 200);
        graphics.drawImage(quitButton, (1920/2) - (quitButton.getWidth() / 2), 600);
    }

}
