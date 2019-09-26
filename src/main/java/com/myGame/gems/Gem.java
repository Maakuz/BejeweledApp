package com.myGame.gems;

import com.markus.framework.Graphics;
import com.markus.framework.Image;
import com.myGame.Animation;
import com.myGame.Vector2;

/**
 * Created by Markus on 2016-03-02.
 */
public class Gem {
    public enum Type {APPLE, BLUE, LIGHTBLUE, GREEN, RED, YELLOW, PURPLE, UNDEFINED};

    private Type type;
    private Animation animation;
    private int x;
    private int y;

    private int destX;
    private int destY;

    private boolean moving;

    public Gem(Image image, Type type, int startX, int startY) {
        this.type = type;
        this.animation = new Animation(image, new Vector2(100, 100),new Vector2(4, 1), 10);
        this.x = (startX * 100) + 40;
        this.y = (startY * 100) + 40;
        this.destX = this.x;
        this.destY = this.y;
        this.moving = false;
    }

    public Gem(Image image, Type type, int startX, int startY, int animationInterval, Vector2 sheetSize) {
        this.type = type;
        this.animation = new Animation(image, new Vector2(100, 100), sheetSize, 10);
        this.x = (startX * 100) + 40;
        this.y = (startY * 100) + 40;
        this.destX = this.x;
        this.destY = this.y;
        this.moving = false;

        this.animation.setInterval(animationInterval);
    }

    public void update(float dt) {
        if (this.x != this.destX || this.y != this.destY)
            this.moving = true;

        else
            this.moving = false;


        if (this.x < this.destX)
            this.x += dt * 6;

        if (this.x > this.destX)
            this.x -= dt * 6;

        if (this.y < this.destY)
            this.y += dt * 6;

        if (this.y > this.destY)
            this.y -= dt * 6;

        if (Math.abs(this.x - this.destX) < 10 && Math.abs(this.y - this.destY) < 10)
        {
            this.x = destX;
            this.y = destY;
        }

        this.animation.animate(dt);
    }

    public void draw(Graphics graphics) {
        graphics.drawImage(this.animation.getImage(), this.x, this.y,
                this.animation.getCurrentFrame().x * this.animation.getFrameSize().x,
                this.animation.getCurrentFrame().y * this.animation.getFrameSize().y,
                this.animation.getFrameSize().x, this.animation.getFrameSize().y);
    }

    public final Image getImage()
    {
        return this.animation.getImage();
    }

    public final Animation getAnimation()
    {
        return this.animation;
    }

    public final Type getType() {
        return this.type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public final int getX() {
        return (this.x - 40) / 100;
    }

    public final int getXPos() {
        return this.x;
    }

    public void setX(int x) {
        this.x = (x * 100) + 40;
        this.destX = this.x;
    }

    public final int getY() {
        return (this.y - 40) / 100;
    }

    public final int getYPos() {
        return this.y;
    }

    public void setY(int y) {
        this.y = (y * 100) + 40;
        this.destY = this.y;
    }

    public void setDest(int x, int y)
    {
        this.destX = (x * 100) + 40;
        this.destY = (y * 100) + 40;
    }

    public final boolean getMoving()
    {
        return this.moving;
    }

    public boolean equals(final Gem other)
    {
        return this.type == other.type;
    }


}
