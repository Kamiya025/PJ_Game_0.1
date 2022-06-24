package com.game.model;

import com.game.gui.PlayGamePanel;
import com.game.util.Const;
import com.game.util.ImageManager;
import com.game.util.MediaManager;

import javax.sound.sampled.Clip;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WolfAttack extends Skill {
    private int x, y, drawStep = 0;
    private MediaManager mediaManager = new MediaManager();
    private Clip hit = mediaManager.getAduio("slashSE.wav");


    private PlayGamePanel playGamePanel;
    private ArrayList<Image> images = ImageManager.arrImgAttack;
    private static Toolkit tk = Toolkit.getDefaultToolkit();

    private static Map<String, Image> imgs = new HashMap<String, Image>();

    public WolfAttack(int x, int y, PlayGamePanel playGamePanel) {
        this.x = x;
        this.y = y;
        this.playGamePanel = playGamePanel;
    }


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

    public void draw(Graphics2D g2d) {
        if (drawStep >= images.size())
            live = false;
        if (!live) {
            playGamePanel.wolfsAttacks.remove(this);
            return;
        }
        g2d.drawImage(images.get(drawStep), x, y, Const.SKILL_SIZE_W, Const.SKILL_SIZE_H, null);
        drawStep++;
    }


    public Rectangle getRect() {
        return new Rectangle(x, y, Const.ITEM_MAP_SIZE, Const.ITEM_MAP_SIZE);
    }


    public void hitPLayer() {
    }

    ;
}
