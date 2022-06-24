package com.game.model;

import com.game.gui.PlayGamePanel;
import com.game.util.Const;
import com.game.util.ImageManager;
import com.game.util.MediaManager;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FireBall extends Skill{
    private int orient;
    public int speed = Const.FIRE_SPEED, limit = 20;
    private MediaManager mediaManager = new MediaManager();
    private Clip fire = mediaManager.getAduio("FireBallSE.wav");
    private Clip fireHit = mediaManager.getAduio("FireHit.wav");
    private boolean live = true;

    private PlayGamePanel playGamePanel;
    private ArrayList<Image> images = ImageManager.arrImgFireball;

    public FireBall(int x, int y, int orient) {
        setX(x);
        setY(y);
        this.orient = orient;
    }

    public FireBall(int x, int y, int orient, PlayGamePanel playGamePanel) {
        this(x, y, orient);
        this.playGamePanel = playGamePanel;
        fire.start();
    }

    private void move() {
        for (int i = 0; i < playGamePanel.mapManager.mapItems.size(); i++) {
            if (checkItemMap(playGamePanel.mapManager.mapItems.get(i))) {
                setLive(false);
            }
        }
        switch (orient) {
            case 0:
                y -= speed;
                break;
            case 1:
                y += speed;
                break;
            case 2:
                x -= speed;
                break;
            case 3:
                x += speed;
                break;
        }
        step = (step < 1) ? step + 1 : 0;
        if (x < 0 || y < 0 || x > Const.MAP_SIZE_WIDTH
                || y > Const.MAP_SIZE_HEIGHT) {
            live = false;
        }

    }

    public boolean checkItemMap(MapItem mapItem) {
        if (mapItem.getType() == 0) return false;
        return this.getRect().intersects(mapItem.getRect());
    }

    public void draw(Graphics2D g2d) {
        if (!live) {
            playGamePanel.fireBalls.remove(this);
            return;
        }
        switch (orient) {
            case 1:
                g2d.drawImage(images.get(2 + step), x, y, Const.ITEM_MAP_SIZE, Const.ITEM_MAP_SIZE, null);
                break;
            case 0:
                g2d.drawImage(images.get(6 + step), x, y, Const.ITEM_MAP_SIZE, Const.ITEM_MAP_SIZE, null);
                break;
            case 2:
                g2d.drawImage(images.get(4 + step), x, y, Const.ITEM_MAP_SIZE, Const.ITEM_MAP_SIZE, null);
                break;
            case 3:
                g2d.drawImage(images.get(step), x, y, Const.ITEM_MAP_SIZE, Const.ITEM_MAP_SIZE, null);
                break;
        }
        if (hitWolfs(playGamePanel.wolfs)) setLive(false);
        limit--;
        if (limit < 0) setLive(false);
        move();
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public Rectangle getRect() {
        return new Rectangle(x, y+10, Const.ITEM_MAP_SIZE, Const.ITEM_MAP_SIZE-20);
    }

    public boolean hitWolfs(ArrayList<Wolf> wolfs) {
        for (int i = 0; i < wolfs.size(); i++) {
            if (hitWolf(wolfs.get(i))) {
                return true;
            }
        }
        return false;
    }

    public boolean hitWolf(Wolf wolf) {

        if (this.live && this.getRect().intersects(wolf.getRect()) && wolf.isLive()) {
            wolf.hp--;
            if(wolf.hp<=0)
                wolf.setLive(false);
            playGamePanel.score += 2;
            fireHit.start();
            return true;
        }
        return false;
    }

}
