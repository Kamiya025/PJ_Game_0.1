package com.game.model;

import com.game.util.Const;

import java.awt.*;

public class MapItem {
    private int x,y,type;
    private Image image;

    public MapItem()
    {
        super();
    }

    public MapItem(int x, int y,int type, Image image)
    {
        super();
        this.x = x;
        this.y = y;
        this.type = type;
        this.image = image;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getType() {
        return type;
    }

    public Rectangle getRect() {
        return new Rectangle(x, y, Const.ITEM_MAP_SIZE-12, Const.ITEM_MAP_SIZE-10);
    }
    public void drawMapItem(Graphics2D g2d)
    {
        g2d.drawImage(image,x, y, Const.ITEM_MAP_SIZE,Const.ITEM_MAP_SIZE, null);
    }
}
