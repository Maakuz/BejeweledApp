package com.com.markus.framework.implementation;

import android.media.SoundPool;

import com.markus.framework.Sound;
/**
 * Created by Markus on 2016-03-02.
 */
public class AndroidSound implements Sound
{
    int soundID;
    SoundPool soundPool;

    public AndroidSound(SoundPool soundPool, int soundID)
    {
        this.soundID = soundID;
        this.soundPool = soundPool;
    }

    @Override
    public void play (float volume)
    {
        soundPool.play(soundID, volume, volume, 0, 0, 1);
    }

    @Override
    public void dispose()
    {
        soundPool.unload(soundID);
    }
}
