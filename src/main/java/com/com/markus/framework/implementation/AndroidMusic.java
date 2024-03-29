package com.com.markus.framework.implementation;

import java.io.IOException;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.provider.MediaStore;

import com.markus.framework.Music;

/**
 * Created by Markus on 2016-03-02.
 */
public class AndroidMusic implements Music, OnCompletionListener, OnSeekCompleteListener, OnPreparedListener, OnVideoSizeChangedListener
{
    MediaPlayer mediaPlayer;
    boolean isPrepared = false;

    public AndroidMusic(AssetFileDescriptor descriptor)
    {
        this.mediaPlayer = new MediaPlayer();

        try
        {
            this.mediaPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
            this.mediaPlayer.prepare();
            this.isPrepared = true;
            this.mediaPlayer.setOnCompletionListener(this);
            this.mediaPlayer.setOnSeekCompleteListener(this);
            this.mediaPlayer.setOnPreparedListener(this);
            this.mediaPlayer.setOnVideoSizeChangedListener(this);
        }
        catch (Exception e)
        {
            throw new RuntimeException("Could not load music");
        }
    }

    @Override
    public void dispose()
    {
        if (this.mediaPlayer.isPlaying())
            this.mediaPlayer.stop();

        this.mediaPlayer.release();
    }

    @Override
    public boolean isLooping()
    {
        return this.mediaPlayer.isLooping();
    }

    @Override
    public boolean isPlaying()
    {
        return this.mediaPlayer.isPlaying();
    }

    @Override
    public boolean isStopped()
    {
        return !isPrepared;
    }

    @Override
    public void pause()
    {
        if (this.mediaPlayer.isPlaying())
            mediaPlayer.pause();
    }

    @Override
    public void play()
    {
        if (this.mediaPlayer.isPlaying())
            return;

        try
        {
            synchronized (this)
            {
                if (!this.isPrepared)
                    this.mediaPlayer.prepare();

                this.mediaPlayer.start();
            }
        }
        catch (IllegalStateException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void setLooping(boolean isLooping)
    {
        mediaPlayer.setLooping(isLooping);
    }

    @Override
    public void setVolume(float volume)
    {
        mediaPlayer.setVolume(volume, volume);
    }

    @Override
    public void stop() {
        if (this.mediaPlayer.isPlaying() == true)
        {
            this.mediaPlayer.stop();

            synchronized (this)
            {
                isPrepared = false;
            }}
    }

    @Override
    public void onCompletion(MediaPlayer player)
    {
        synchronized (this)
        {
            isPrepared = false;
        }
    }

    @Override
    public void seekBegin()
    {
        mediaPlayer.seekTo(0);
    }


    @Override
    public void onPrepared(MediaPlayer player)
    {
        synchronized (this) {
            isPrepared = true;
        }
    }

    @Override
    public void onSeekComplete(MediaPlayer player)
    {

    }

    @Override
    public void onVideoSizeChanged(MediaPlayer player, int width, int height)
    {

    }
}
