package com.myGame.gems;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.markus.framework.Graphics;
import com.markus.framework.Input;
import com.myGame.Assets;
import com.myGame.Vector2;

public class GemHandler {
    public static final int CAP = 10;
    public static final int TOTALGEMCAP = 150;


    private Random rand;

    private boolean refundMove;
    private boolean swapped;
    private int score;
    private int combo;
    private int movesLeft;
    private Gem[][] gems;
    private int nrOfGems;
    private boolean somethingIsMoving;
    private int xFirst;
    private int yFirst;
    private Gem.Type typePopped;

    public GemHandler() {
        this.rand = new Random();
        this.gems = new Gem[10][10];
        this.nrOfGems = 0;
        this.xFirst = -1;
        this.yFirst = -1;
        this.somethingIsMoving = false;
        this.typePopped = Gem.Type.UNDEFINED;
        this.combo = 0;
        this.score = 0;
        this.movesLeft = 5;
        this.swapped = false;
        this.refundMove = false;


        this.initializeGems();
    }

    public void update(float dt, List<Input.TouchEvent> events)
    {
        this.somethingIsMoving = false;
        this.refundMove = false;

        for (int x = 0; x < CAP; x++)
        {
            for (int y = 0; y < CAP; y++)
            {
                if (this.gems[x][y] != null)
                {
                    this.gems[x][y].update(dt);

                    if (this.gems[x][y].getMoving())
                    {
                        this.somethingIsMoving = true;
                    }
                }
            }
        }

        if (!this.moveGems() && !this.somethingIsMoving)
        {
            if (!this.addNewGems())
            {
                this.popGems();
                this.checkInputs(events);
            }
        }

        if (this.combo % 10 == 0 && this.refundMove)
        {
            this.movesLeft++;
            this.refundMove = false;
        }
    }

    public void draw(Graphics graphics) {
        if (this.yFirst != -1)
            graphics.drawImage(Assets.highlight, gems[this.xFirst][this.yFirst].getXPos(), gems[this.xFirst][this.yFirst].getYPos());

        for (int i = 0; i < CAP; i++)
            for (int j = 0; j < CAP; j++) {
                if (gems[i][j] != null)
                    gems[i][j].draw(graphics);
            }

    }

    public void initializeGems() {
        for (int x = 0; x < CAP; x++)
            for (int y = 0; y < CAP; y++)
                gems[x][y] = (this.randomizeGem(x, y));

        this.nrOfGems = 100;
    }

    private void checkInputs(List<Input.TouchEvent> events)
    {
        //The alps!
        for (Input.TouchEvent event : events)
        {
            if (event.type == Input.TouchEvent.TOUCH_UP)
            {
                boolean onePressed = false;
                if (this.xFirst == -1)
                {
                    for (int x = 0; x < CAP && !onePressed; x++)
                    {
                        for (int y = 0; y < CAP && !onePressed; y++)
                        {
                            if (gems[x][y] != null && inBounds(event, gems[x][y].getXPos(), gems[x][y].getYPos(), 100, 100))
                            {
                                this.xFirst = x;
                                this.yFirst = y;
                                onePressed = true;
                            }
                        }
                    }
                }

                else
                {
                    for (int x = 0; x < CAP && !onePressed; x++)
                    {
                        for (int y = 0; y < CAP && !onePressed; y++)
                        {
                            if (gems[x][y] != null && inBounds(event, gems[x][y].getXPos(), gems[x][y].getYPos(), 100, 100))
                            {
                                if (((x + 1 == xFirst || x - 1 == xFirst) || (y + 1 == yFirst || y - 1 == yFirst)) &&
                                        (x == xFirst || y == yFirst))
                                {
                                    this.swapGems(xFirst, yFirst, x, y);
                                    this.xFirst = -1;
                                    this.yFirst = -1;
                                    this.swapped = true;

                                    onePressed = true;
                                }

                                else
                                {
                                    this.xFirst = -1;
                                    this.yFirst = -1;
                                    this.swapped = false;

                                    onePressed = true;

                                }
                            }
                        }
                    }
                }
            }
        }

    }

    private boolean inBounds(Input.TouchEvent event, int x, int y, int width, int height)
    {
        return event.x > x && event.y > y && event.x < x + width && event.y < y + height;
    }

    private Gem randomizeGem(int x, int y) {
        Gem gem;
        int randVal = rand.nextInt(141);

        if (randVal < 20)
            gem = new Gem(Assets.appleGem, Gem.Type.APPLE, x, y, 5000, new Vector2(4, 1));

        else if (randVal >= 20 && randVal < 40)
            gem = new Gem(Assets.blueGem, Gem.Type.BLUE, x, y);

        else if (randVal >= 40 && randVal < 60)
            gem = new Gem(Assets.greenGem, Gem.Type.GREEN, x, y, 3000, new Vector2(4, 2));

        else if (randVal >= 60 && randVal < 80)
            gem = new Gem(Assets.redGem, Gem.Type.RED, x, y);

        else if (randVal >= 80 && randVal < 100)
            gem = new Gem(Assets.yellowGem, Gem.Type.YELLOW, x, y, 4000, new Vector2(4, 1));

        else if (randVal >= 100 && randVal < 120)
            gem = new Gem(Assets.lBlueGem, Gem.Type.LIGHTBLUE, x, y, 6000, new Vector2(4, 1));

        else
            gem = new Gem(Assets.purpleGem, Gem.Type.PURPLE, x, y, 4000, new Vector2(4, 1));


        return gem;
    }

    private Gem randomizeGem(int x, int y, int xStart, int yStart) {
        Gem gem = this.randomizeGem(x, y);
        gem.setY(yStart);
        gem.setX(xStart);
        gem.setDest(x, y);

        return gem;
    }

    private void swapGems(int xA, int yA, int xB, int yB) {
        Gem temp = new Gem(gems[xA][yA].getImage(), gems[xA][yA].getType(), gems[xA][yA].getX(), gems[xA][yA].getY(),
                gems[xA][yA].getAnimation().getInterval(), gems[xA][yA].getAnimation().getSheetSize());

        gems[xA][yA].setX(gems[xB][yB].getX());
        gems[xA][yA].setY(gems[xB][yB].getY());
        gems[xB][yB].setX(temp.getX());
        gems[xB][yB].setY(temp.getY());

        temp = new Gem(gems[xA][yA].getImage(), gems[xA][yA].getType(), gems[xA][yA].getX(), gems[xA][yA].getY(),
                gems[xA][yA].getAnimation().getInterval(), gems[xA][yA].getAnimation().getSheetSize());

        gems[xA][yA] = gems[xB][yB];
        gems[xB][yB] = temp;

    }

    private void moveGem(int xToMove, int yToMove, int destX, int destY) {

        gems[destX][destY] = new Gem(gems[xToMove][yToMove].getImage(), gems[xToMove][yToMove].getType(), gems[xToMove][yToMove].getX(), gems[xToMove][yToMove].getY()
        , gems[xToMove][yToMove].getAnimation().getInterval(), gems[xToMove][yToMove].getAnimation().getSheetSize());
        gems[destX][destY].setDest(destX, destY);

        gems[xToMove][yToMove] = null;
    }

    private void popGems() {
        typePopped = Gem.Type.UNDEFINED;
        List<Gem> removeList = new ArrayList<>();

        for (int x = 0; x < CAP; x++)
            for (int y = 0; y < CAP; y++) {
                if (this.gems[x][y] != null && this.typePopped == Gem.Type.UNDEFINED) {
                    removeList = this.checkRows(x, y, 1);

                    if (removeList.size() == 0)
                        removeList = this.checkColumns(x, y, 1);

                    for (Gem gem : removeList) {
                        typePopped = this.gems[gem.getX()][gem.getY()].getType();
                        this.gems[gem.getX()][gem.getY()] = null;
                    }
                }
            }

        if (removeList.size() > 0)
        {
            this.swapped = false;
            this.combo++;
            this.refundMove = true;
        }

        else if (removeList.size() == 0  && this.swapped)
        {
            this.swapped = false;
            this.combo = 0;
            this.movesLeft--;
        }

        this.score += removeList.size() * (10 * (this.combo + 1));
    }

    private ArrayList<Gem> checkRows(int x, int y, int nrsInRow) {
        ArrayList<Gem> removeList;
        removeList = new ArrayList<>();
        if (x < 9 && this.gems[x + 1][y] != null && this.gems[x][y].equals(this.gems[x + 1][y])) {
            removeList = this.checkRows(x + 1, y, nrsInRow + 1);

            //If greater than 3 then it will be added twice
            if (removeList.size() > 0 && nrsInRow < 3) {
                removeList.add(this.gems[x][y]);
            }
        }

        if (nrsInRow >= 3) {
            removeList.add(this.gems[x][y]);
        }

        return removeList;
    }

    private ArrayList<Gem> checkColumns(int x, int y, int nrsInRow) {
        ArrayList<Gem> removeList;
        removeList = new ArrayList<>();
        if (y < 9 && this.gems[x][y + 1] != null && this.gems[x][y].equals(this.gems[x][y + 1])) {
            removeList = this.checkColumns(x, y + 1, nrsInRow + 1);

            //If greater than 3 then it will be added twice
            if (removeList.size() > 0 && nrsInRow < 3) {
                removeList.add(this.gems[x][y]);
            }
        }

        if (nrsInRow >= 3) {
            removeList.add(this.gems[x][y]);
        }

        return removeList;
    }

    private boolean moveGems() {
        boolean somethingMoved = false;
        for (int x = 0; x < CAP; x++) {
            for (int y = 0; y < CAP; y++) {
                if (this.typePopped == Gem.Type.GREEN || this.typePopped == Gem.Type.PURPLE) {
                    if (y > 0 && this.gems[x][y - 1] != null && this.gems[x][y] == null) {
                        moveGem(x, y - 1, x, y);
                        somethingMoved = true;
                    }
                } else if (this.typePopped == Gem.Type.BLUE) {
                    if (y > 0 && this.gems[x][y] != null && this.gems[x][y - 1] == null) {
                        moveGem(x, y, x, y - 1);
                        somethingMoved = true;
                    }
                } else if (this.typePopped == Gem.Type.RED) {
                    if (x > 0 && this.gems[x - 1][y] != null && this.gems[x][y] == null) {
                        moveGem(x - 1, y, x, y);
                        somethingMoved = true;
                    }
                } else if (this.typePopped == Gem.Type.LIGHTBLUE) {
                    if (x > 0 && this.gems[x][y] != null && this.gems[x - 1][y] == null) {
                        moveGem(x, y, x - 1, y);
                        somethingMoved = true;
                    }
                } else if (this.typePopped == Gem.Type.YELLOW) {


                    if (this.gems[x][y] == null) {
                        int xCurrent = x;
                        int yCurrent = y;
                        int xRand;
                        int yRand;

                        for (int i = 0; i < this.combo && i < 100; i++) {
                            xRand = this.rand.nextInt(CAP);
                            yRand = this.rand.nextInt(CAP);

                            if (this.gems[xRand][yRand] != null) {
                                moveGem(xRand, yRand, xCurrent, yCurrent);
                                xCurrent = xRand;
                                yCurrent = yRand;
                            }

                            this.typePopped = Gem.Type.APPLE;
                            somethingMoved = true;
                        }
                    }
                }


            }
        }

        return somethingMoved;
    }

    private boolean addNewGems() {
        boolean addedGem = false;

        if (this.nrOfGems < TOTALGEMCAP)
            for (int i = 0; i < CAP; i++) {
                if (this.typePopped == Gem.Type.GREEN || this.typePopped == Gem.Type.PURPLE) {
                    if (this.gems[i][0] == null) {
                        this.nrOfGems++;
                        addedGem = true;
                        this.gems[i][0] = this.randomizeGem(i, 0);
                    }
                } else if (this.typePopped == Gem.Type.BLUE) {
                    if (this.gems[i][CAP - 1] == null) {
                        this.nrOfGems++;
                        addedGem = true;
                        this.gems[i][CAP - 1] = this.randomizeGem(i, CAP - 1);
                    }
                } else if (this.typePopped == Gem.Type.RED) {
                    if (this.gems[0][i] == null) {
                        this.nrOfGems++;
                        addedGem = true;
                        this.gems[0][i] = this.randomizeGem(0, i);
                    }
                } else if (this.typePopped == Gem.Type.LIGHTBLUE) {
                    if (this.gems[CAP - 1][i] == null) {
                        this.nrOfGems++;
                        addedGem = true;
                        this.gems[CAP - 1][i] = this.randomizeGem(CAP - 1, i);
                    }
                } else if (this.typePopped == Gem.Type.APPLE) {
                    for (int j = 0; j < CAP; j++) {
                        if (this.gems[j][i] == null) {
                            this.nrOfGems++;
                            addedGem = true;
                            this.gems[j][i] = this.randomizeGem(j, i, this.rand.nextInt(CAP - 1), this.rand.nextInt(CAP - 1));
                        }
                    }
                }

        }

        if (addedGem)
            Assets.click.play(1);

        return addedGem;
    }

    public final int getScore()
    {
        return this.score;
    }

    public final int getCombo()
    {
        return this.combo;
    }

    public final int getMovesLeft()
    {
        return this.movesLeft;
    }
}

/*TODO STUFF
* Give up pryl
* explosion typ
* bättre ljud
* all grafik typ
* sova lite.
* Zzzz...
* */