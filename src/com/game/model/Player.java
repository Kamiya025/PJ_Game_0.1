package com.game.model;

import com.game.gui.PlayGamePanel;
import com.game.util.Const;
import com.game.util.ImageManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Player extends Character {

    private int magicPoint = Const.LIMIT_MP;


    public Player(int x, int y, int orient, int hp, PlayGamePanel playGamePanel) {
        setX(x);
        setY(y);
        setHp(hp);
        setOrient(orient);
        setOldOrient(orient);
        setImages(ImageManager.arrImgPlayer);
        setPlayGamePanel(playGamePanel);
        setSpeed(Const.PLAYER_SPEED);
        setLive(true);
        maxHp = Const.LIMIT_HP;
        hp = maxHp;

        for (int i = 0; i < playGamePanel.mapManager.mapItems.size(); i++) {
            if (playGamePanel.mapManager.mapItems.get(i).getType() == 0) {
                x = playGamePanel.mapManager.mapItems.get(i).getX();
                y = playGamePanel.mapManager.mapItems.get(i).getY();
            }
            if (checkItemMap(playGamePanel.mapManager.mapItems.get(i))) {
                System.out.println(i);
                setX(x);
                setY(y);
            }
        }
    }

    public int getMagicPoint() {
        return magicPoint;
    }

    public void setMagicPoint(int magicPoint) {
        this.magicPoint = magicPoint;
    }

    public void drawMagicPoint(Graphics2D g2d) {
        Color c = g2d.getColor();
        g2d.setColor(Color.BLUE);
        g2d.setFont(new Font("Sanserif", Font.BOLD + Font.TYPE1_FONT, 25));
        g2d.drawString("MP ", 0, 25);
        g2d.drawRect(40, 8, 200, 20);
        int w = (200 / Const.LIMIT_MP) * magicPoint;
        g2d.fillRect(40, 8, w, 20);
        g2d.setColor(c);

    }


    public void drawScore(Graphics2D g2d) {
        Color c = g2d.getColor();
        g2d.setColor(Color.BLUE);
        g2d.setFont(new Font("Sanserif", Font.BOLD + Font.TYPE1_FONT, 25));
        g2d.drawString("Score", Const.MAP_SIZE_WIDTH - 200, 25);
        g2d.drawString(playGamePanel.score + "", Const.MAP_SIZE_WIDTH - 100, 25);
        g2d.setColor(c);
    }

    public FireBall fire() {
        if (!isLive())
            return null;

        int x = getX() + Const.CHARACTER_SIZE / 2 - Const.ITEM_MAP_SIZE / 2;
        int y = getY() + Const.CHARACTER_SIZE / 2 - Const.ITEM_MAP_SIZE / 2;
        switch (getOrient()) {
            case 0:
                y -= 25;
                break;
            case 1:
                y += 25;
                break;
            case 2:
                x -= 25;
                break;
            case 3:
                x += 25;
                break;
        }
        FireBall m = new FireBall(x, y + 2, getOrient(), this.playGamePanel);
        playGamePanel.fireBalls.add(m);

        return m;
    }

    @Override
    public void draw(Graphics2D g2d) {

        switch (orient) {
            case 1:
                g2d.drawImage(images.get(step), x, y, Const.ITEM_MAP_SIZE, Const.ITEM_MAP_SIZE, null);
                break;
            case 0:
                g2d.drawImage(images.get(9 + step), x, y, Const.ITEM_MAP_SIZE, Const.ITEM_MAP_SIZE, null);
                break;
            case 2:
                g2d.drawImage(images.get(3 + step), x, y, Const.ITEM_MAP_SIZE, Const.ITEM_MAP_SIZE, null);
                break;
            case 3:
                g2d.drawImage(images.get(6 + step), x, y, Const.ITEM_MAP_SIZE, Const.ITEM_MAP_SIZE, null);
                break;
        }
        int mp = (Const.LIMIT_MP / 3);
        if (playGamePanel.appleMP != null && playGamePanel.appleMP.isEat())
            if (magicPoint < mp) {
                playGamePanel.appleMP.setEat(false);
                playGamePanel.appleMP.ramdomShow();
            }
        if (playGamePanel.appleMP != null) {
            playGamePanel.appleMP.draw(g2d);
            playGamePanel.appleMP.eatApple(this);
        }
    }

    public void keyPressed(KeyEvent e) {
        if (!playGamePanel.playGame) return;
        int key = e.getKeyCode();
        setMoveBoolen(false);
        switch (key) {
            case KeyEvent.VK_R:
                break;
            case KeyEvent.VK_RIGHT:
                setMoveBoolen(true);
                setOrient(Const.RIGHT_ORIENT);
                break;

            case KeyEvent.VK_LEFT:
                setMoveBoolen(true);
                setOrient(Const.LEFT_ORIENT);
                break;

            case KeyEvent.VK_UP:
                setMoveBoolen(true);
                setOrient(Const.UP_ORIENT);
                break;

            case KeyEvent.VK_DOWN:
                setMoveBoolen(true);
                setOrient(Const.DOWN_ORIENT);
                break;
        }
        move();

    }

    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();
        if (key == KeyEvent.VK_SHIFT)
            if (playGamePanel.stopGame) playGamePanel.stopGame = false;
            else playGamePanel.stopGame = true;
        setMoveBoolen(false);
        if (key == KeyEvent.VK_SPACE) {
            if (magicPoint <= 0) return;
            this.magicPoint--;
            fire();
        }
    }

}


