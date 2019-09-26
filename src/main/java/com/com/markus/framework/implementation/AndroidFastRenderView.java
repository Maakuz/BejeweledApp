package com.com.markus.framework.implementation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Markus on 2016-03-02.
 */
public class AndroidFastRenderView extends SurfaceView implements Runnable
{
    AndroidGame game;
    Bitmap frameBuffer;
    Thread renderThread = null;
    SurfaceHolder holder;
    volatile boolean running = false;

    public AndroidFastRenderView(AndroidGame game, Bitmap frameBuffer)
    {
        super(game);
        this.game = game;
        this.frameBuffer = frameBuffer;
        this.holder = getHolder();
    }

    public void resume()
    {
        running = true;
        renderThread = new Thread(this);
        renderThread.start();
    }

    public void run()
    {
        Rect dstRect = new Rect();
        long startTime = System.nanoTime();
        while(running)
        {
            if (!holder.getSurface().isValid())
                continue;


            float dt = (System.nanoTime() - startTime) / 10000000.000f;
            startTime = System.nanoTime();

            if (dt > 3.15)
            {
                dt = (float) 3.15;
            }

            game.getCurrentScreen().update(dt);
            game.getCurrentScreen().draw(dt);

            Canvas canvas = holder.lockCanvas();
            canvas.getClipBounds(dstRect);
            canvas.drawBitmap(frameBuffer, null, dstRect, null);
            holder.unlockCanvasAndPost(canvas);
        }
    }

    public void pause()
    {
        running = false;
        boolean success = false;
        //while (true)
        while (!success)
        {
            try
            {
                renderThread.join();
                success = true;
                //break;
            }
            catch (InterruptedException e)
            {
                // retry
            }
        }
    }
}
