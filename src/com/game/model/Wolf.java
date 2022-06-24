package com.game.model;

import com.game.gui.PlayGamePanel;
import com.game.util.Const;
import com.game.util.ImageManager;
import com.game.util.MediaManager;

import javax.sound.sampled.Clip;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Wolf extends Character {
    public ArrayList<Wolf> wolfs;
    public Player player;
    public ArrayList<FireBall> fireBalls;
    public int skipStep = 3;
    public int cdSkill = 0;
    private MediaManager mediaManager = new MediaManager();
    ;
    private Clip skill = mediaManager.getAduio("slashSE.wav");
    private WolfAttack wolfAttack;

    public Wolf() {
        setImages(ImageManager.arrImgEnemy);
        speed = Const.WOLF_SPEED;
        maxHp = 3;
        hp = maxHp;
    }

    public Wolf(PlayGamePanel playGamePanel) {
        this();
        this.playGamePanel = playGamePanel;

    }

    private ArrayList<Image> images = ImageManager.arrImgEnemy;

    @Override
    public Rectangle getRect() {
        return new Rectangle(x, y, Const.WOLF_SIZE - 5, Const.WOLF_SIZE);
    }

    @Override
    public void move() {
        if (isLive()) {
            int playerX = playGamePanel.player.x;
            int playerY = playGamePanel.player.y;
            int size = Const.CHARACTER_SIZE;
            boolean move = true;

            if (skipStep > 0) {
                orient = oldOrient;
                skipStep--;
            }
            if (this.oldOrient == this.orient) {
                step = (step < 2) ? step + 1 : 0;
                if (step < 2) orient = oldOrient;
            } else this.oldOrient = this.orient;
            switch (orient) {
                case Const.UP_ORIENT:
                    this.y -= speed;
                    if (x > playerX)
                        this.x -= speed/2;
                    else this.x += speed/2;
                    break;
                case Const.DOWN_ORIENT:
                    this.y += speed;
                    if (x > playerX)
                        this.x -= speed/2;
                    else this.x += speed/2;
                    break;
                case Const.LEFT_ORIENT:
                    this.x -= speed;
                    if (y > playerY)
                        this.y -= speed/2;
                    else this.y += speed/2;
                    break;
                case Const.RIGHT_ORIENT:
                    this.x += speed;
                    if (y > playerY)
                        this.y -= speed/2;
                    else this.y += speed/2;
                    break;
            }
            if(cdSkill>0) cdSkill--;
            for (int i = 0; i < playGamePanel.mapManager.mapItems.size(); i++) {
                if (checkItemMap(playGamePanel.mapManager.mapItems.get(i))) {
                    x = oldX;
                    y = oldY;
                }
            }

            if (x - playerX < y - playerY) {
                if (x > playerX)
                    if (x < playerX + size)
                        orient = (y < playerY) ? Const.DOWN_ORIENT : Const.UP_ORIENT;
                    else orient = Const.LEFT_ORIENT;
                else if (x < playerX)
                    if (x > playerX + size)
                        orient = (y < playerY) ? Const.DOWN_ORIENT : Const.UP_ORIENT;
                    else
                        orient = Const.RIGHT_ORIENT;
                else if (y > playerY)
                    if (y < playerY + size)
                        orient = (x > playerX) ? Const.LEFT_ORIENT : Const.RIGHT_ORIENT;
                    else
                        orient = Const.UP_ORIENT;
                else orient = Const.DOWN_ORIENT;
            } else {
                if (y > playerY)
                    if (y < playerY + size)
                        orient = (x > playerX) ? Const.LEFT_ORIENT : Const.RIGHT_ORIENT;
                    else
                        orient = Const.UP_ORIENT;
                else if (y < playerY)
                    if (y > playerY + size)
                        orient = (x > playerX) ? Const.LEFT_ORIENT : Const.RIGHT_ORIENT;
                    else
                        orient = Const.DOWN_ORIENT;
                else if (x > playerX)
                    if (x < playerX + size)
                        orient = (y < playerY) ? Const.DOWN_ORIENT : Const.UP_ORIENT;
                    else orient = Const.LEFT_ORIENT;

                else
                    orient = Const.RIGHT_ORIENT;
            }
            for (int i = 0; i < playGamePanel.wolfs.size(); i++) {
                if (!playGamePanel.wolfs.get(i).equals(this))
                    if (this.isLive() && this.getRect().intersects(playGamePanel.wolfs.get(i).getRect())) {
                        if (this.orient == playGamePanel.wolfs.get(i).orient) {
                            switch (orient) {
                                case Const.UP_ORIENT:
                                    if (this.y >= playGamePanel.wolfs.get(i).y) this.y += speed / 2;
                                    orient =(this.x < playGamePanel.player.getX())?Const.RIGHT_ORIENT:Const.LEFT_ORIENT;
                                    break;
                                case Const.DOWN_ORIENT:
                                    if (this.y <= playGamePanel.wolfs.get(i).y) this.y -= speed / 2;
                                    orient =(this.x < playGamePanel.player.getX())?Const.RIGHT_ORIENT:Const.LEFT_ORIENT;
                                    break;
                                case Const.LEFT_ORIENT:
                                    if (this.x >= playGamePanel.wolfs.get(i).x) this.x += speed / 2;
                                    orient =(this.y > playGamePanel.player.getY())?Const.UP_ORIENT:Const.DOWN_ORIENT;
                                    break;
                                case Const.RIGHT_ORIENT:
                                    if (this.x <= playGamePanel.wolfs.get(i).x) this.x -= speed / 2;
                                    orient =(this.y > playGamePanel.player.getY())?Const.UP_ORIENT:Const.DOWN_ORIENT;
                                    break;
                            }
                            skipStep = 22;
                        }

                    }
            }
            this.oldX = x;
            this.oldY = y;


            this.oldOrient = this.orient;
            if (x < 0)
                x = 0;
            if (y < 0)
                y = 0;
            if (x + Const.CHARACTER_SIZE > Const.MAP_SIZE_WIDTH)
                x = Const.MAP_SIZE_WIDTH - Const.CHARACTER_SIZE;
            if (y + Const.CHARACTER_SIZE > Const.MAP_SIZE_HEIGHT)
                y = Const.MAP_SIZE_HEIGHT - Const.CHARACTER_SIZE;
        }
        hitPlayer();


    }


    public void randomShow() {
        Random random = new Random();
        int i = random.nextInt(5);
        switch (i) {
            case 1:
                this.setY(Const.MAP_SIZE_HEIGHT - Const.CHARACTER_SIZE);
                this.setX(random.nextInt(Const.MAP_SIZE_WIDTH - Const.CHARACTER_SIZE));
                this.orient = Const.UP_ORIENT;

                break;
            case 2:
                this.setX(random.nextInt(Const.MAP_SIZE_WIDTH - Const.CHARACTER_SIZE));
                this.setY(Const.ITEM_MAP_SIZE);
                this.orient = Const.DOWN_ORIENT;

                break;
            case 3:
                this.setX(Const.ITEM_MAP_SIZE);
                this.setY(random.nextInt(Const.MAP_SIZE_HEIGHT - Const.CHARACTER_SIZE));
                this.orient = Const.RIGHT_ORIENT;
                break;
            case 4:
                this.setX(Const.MAP_SIZE_WIDTH - Const.CHARACTER_SIZE);
                this.setY(random.nextInt(Const.MAP_SIZE_HEIGHT - Const.CHARACTER_SIZE));
                this.orient = Const.LEFT_ORIENT;
                break;

        }


        for (int j = 0; j < playGamePanel.wolfs.size(); j++) {
            if (this.getRect().intersects(playGamePanel.wolfs.get(j).getRect())) {
                randomShow();
            }
        }
    }

    public void drawWolf(Graphics2D g2d) {
        if (!isLive()) playGamePanel.wolfs.remove(this);
        draw(g2d);
        drawHP(g2d);
    }

    public boolean hitPlayer() {
        if (this.isLive() && this.getRect().intersects(playGamePanel.player.getRect()) && playGamePanel.player.isLive()) {
            System.out.println("cd" + cdSkill);
            if (playGamePanel.player.hp <= 0)
                playGamePanel.player.setLive(false);
            else if (cdSkill <= 0) {
                cdSkill = 50;
                playGamePanel.player.hp--;
                wolfAttack = new WolfAttack(playGamePanel.player.getX(), playGamePanel.player.getY(), playGamePanel);
                playGamePanel.wolfsAttacks.add(wolfAttack);
                skill.start();

                System.out.println("hit player");
            }


            return true;
        }
        return false;
    }
}

