package com.markus.framework;

/**
 * Created by Markus on 2016-03-01.
 */
public interface Game
{
    public Audio getAudio();

    public Input getInput();

    public FileIO getFileIO();

    public Graphics getGraphics();

    public void setScreen(Screen screen);

    public Screen getCurrentScreen();

    public Screen getInitScreen();
}
