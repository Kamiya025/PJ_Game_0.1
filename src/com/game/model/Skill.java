package com.game.model;

import com.game.util.Const;
import com.game.util.MediaManager;

public class Skill {

    public int x, y, step = 0;
    public boolean live = true;


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

}
