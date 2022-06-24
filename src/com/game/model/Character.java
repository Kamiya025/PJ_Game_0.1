package com.game.model;

import com.game.gui.PlayGamePanel;
import com.game.util.Const;
import com.game.util.ImageManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class Character {
    public int x, y, oldX, oldY, step = 0, orient, oldOrient, speed, hp, maxHp;
    private boolean moveBoolen, live = true;
    ArrayList<Image> images;
    PlayGamePanel playGamePanel;

    public Character() {
    }

    public Character(int x, int y, int orient, int hp, PlayGamePanel playGamePanel) {
        this.x = x;
        this.y = y;
        this.hp = hp;
        this.orient = orient;
        this.oldOrient = orient;
        this.playGamePanel = playGamePanel;
        this.moveBoolen = false;
    }

    //get-set
    public Rectangle getRect() {
        return new Rectangle(x, y, Const.CHARACTER_SIZE - 5, Const.CHARACTER_SIZE);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getOldX() {
        return oldX;
    }

    public int getOldY() {
        return oldY;
    }

    public int getOrient() {
        return orient;
    }

    public int getOldOrient() {
        return oldOrient;
    }

    public int getSpeed() {
        return speed;
    }

    public int getStep() {
        return step;
    }

    public boolean isLive() {
        return live;
    }

    public boolean isMoveBoolen() {
        return live;
    }

    public ArrayList<Image> getImages() {
        return images;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setImages(ArrayList<Image> images) {
        this.images = images;
    }

    public void setOldOrient(int oldOrient) {
        this.oldOrient = oldOrient;
    }

    public void setOldX(int oldX) {
        this.oldX = oldX;
    }

    public void setOldY(int oldY) {
        this.oldY = oldY;
    }

    public void setOrient(int orient) {
        this.orient = orient;
    }

    public void setPlayGamePanel(PlayGamePanel playGamePanel) {
        this.playGamePanel = playGamePanel;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setMoveBoolen(boolean moveBoolen) {
        this.moveBoolen = moveBoolen;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void move() {
        if (!isLive()) return;
        if (!moveBoolen) return;

        this.oldX = x;
        this.oldY = y;

        switch (orient) {
            case Const.UP_ORIENT:
                y -= speed;
                break;
            case Const.DOWN_ORIENT:
                y += speed;
                break;
            case Const.LEFT_ORIENT:
                x -= speed;
                break;
            case Const.RIGHT_ORIENT:
                x += speed;
                break;
        }

        if (this.oldOrient == this.orient) {
            step = (step < 2) ? step + 1 : 0;
        } else this.oldOrient = this.orient;
        for (int i = 0; i < playGamePanel.mapManager.mapItems.size(); i++) {
            if (checkItemMap(playGamePanel.mapManager.mapItems.get(i))) {
                x = oldX;
                y = oldY;
            }
        }
        if (x < 0)
            x = 0;
        if (y < 0)
            y = 0;
        if (x + Const.CHARACTER_SIZE > Const.MAP_SIZE_WIDTH)
            x = Const.MAP_SIZE_WIDTH - Const.CHARACTER_SIZE;
        if (y + Const.CHARACTER_SIZE > Const.MAP_SIZE_HEIGHT)
            y = Const.MAP_SIZE_HEIGHT - Const.CHARACTER_SIZE;

    }

    public boolean checkItemMap(MapItem mapItem) {
        if (mapItem.getType() == 0) return false;
        return this.getRect().intersects(mapItem.getRect());
    }

    public void draw(Graphics2D g2d) {
        if (isLive())
            switch (orient) {
                case Const.DOWN_ORIENT:
                    g2d.drawImage(images.get(step), x, y, Const.ITEM_MAP_SIZE, Const.ITEM_MAP_SIZE, null);
                    break;
                case Const.UP_ORIENT:
                    g2d.drawImage(images.get(9 + step), x, y, Const.ITEM_MAP_SIZE, Const.ITEM_MAP_SIZE, null);
                    break;
                case Const.LEFT_ORIENT:
                    g2d.drawImage(images.get(3 + step), x, y, Const.ITEM_MAP_SIZE, Const.ITEM_MAP_SIZE, null);
                    break;
                case Const.RIGHT_ORIENT:
                    g2d.drawImage(images.get(6 + step), x, y, Const.ITEM_MAP_SIZE, Const.ITEM_MAP_SIZE, null);
                    break;
            }
        move();
    }

    public void drawHP(Graphics2D g2d) {
        Color c = g2d.getColor();
        g2d.setColor(Color.RED);
        g2d.setFont(new Font("Sanserif", Font.BOLD + Font.TYPE1_FONT, 10));
        g2d.drawRect(getX(), getY(), 50, 5);
        int w = (50 / maxHp) * hp;
        g2d.fillRect(getX(), getY(), w, 5);
        g2d.setColor(c);

    }


}



