package com.markus.framework;

/**
 * Created by Markus on 2016-03-01.
 */
public abstract class Screen
{
    protected final Game game;

    public Screen(Game game)
    {
        this.game = game;
    }

    public abstract void update(float dt);

    public abstract void draw(float dt);

    public abstract void pause();

    public abstract void resume();

    public abstract void dispose();

    public abstract void backButton();

}
