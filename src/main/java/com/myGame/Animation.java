package com.myGame;

import com.markus.framework.Image;

/**
 * Created by Markus on 2016-03-04.
 */
public class Animation
{
    private Image image;
    private Vector2 frameSize;
    private Vector2 sheetSize;
    private Vector2 currentFrame;
    private int speed;
    private int counter;
    private int intervalCounter;
    private int interval;

    public Animation(Image image, Vector2 frameSize, Vector2 sheetSize, int speed)
    {
        this.image = image;
        this.frameSize = frameSize;
        this.sheetSize = sheetSize;
        this.currentFrame = new Vector2();
        this.speed = speed;
        this.counter = 0;
        this.intervalCounter = 0;
        this.interval = 0;
    }

    public void animate(float dt)
    {
        this.intervalCounter += 10 * dt;


        if (this.intervalCounter >= interval)
        {
            this.counter += dt * speed;

            if (this.counter >= 100)
            {
                this.counter = 0;
                this.currentFrame.x++;
                if (this.currentFrame.x >= this.sheetSize.x)
                {
                    this.currentFrame.x = 0;
                    this.currentFrame.y++;

                    if (this.currentFrame.y >= this.sheetSize.y)
                    {
                        this.currentFrame.y = 0;
                        this.intervalCounter = 0;
                    }
                }
            }
        }


    }

    public final Vector2 getFrameSize()
    {
        return this.frameSize;
    }

    public final Vector2 getSheetSize()
    {
        return this.sheetSize;
    }

    public final Vector2 getCurrentFrame()
    {
        return this.currentFrame;
    }

    public final Image getImage()
    {
        return this.image;
    }

    public void setInterval(int interval)
    {
        this.interval = interval;
    }

    public final int getInterval()
    {
        return this.interval;
    }
}
