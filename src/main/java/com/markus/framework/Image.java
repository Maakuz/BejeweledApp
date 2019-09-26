package com.markus.framework;

import com.markus.framework.Graphics.ImageFormat;

/**
 * Created by Markus on 2016-03-01.
 */
public interface Image
{
    public int getWidth();
    public int getHeight();
    public ImageFormat getFormat();
    public void dispose();
}
