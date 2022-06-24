package com.game.model;

import com.game.gui.PlayGamePanel;
import com.game.util.Const;
import com.game.util.ImageManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class AppleMP {
    private int x,y;
    private boolean eat = false;
    private PlayGamePanel playGamePanel;
    private ArrayList<Integer> mapItems0 = new ArrayList<>();
    private Image image = ImageManager.imgApple;
    public AppleMP(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public AppleMP(int x, int y, PlayGamePanel playGamePanel) {
        this.x = x;
        this.y = y;
        this.playGamePanel = playGamePanel;
        checkItemsMap();
    }
    public AppleMP(PlayGamePanel playGamePanel)
    {

        this.playGamePanel = playGamePanel;
        checkItemsMap();
        ramdomShow();
    }
    public Rectangle getRect() {
        return new Rectangle(x, y, Const.APPLE_SIZE, Const.APPLE_SIZE);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isEat() {
        return eat;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setEat(boolean eat) {
        this.eat = eat;
    }


    public void ramdomShow()
    {
        Random random = new Random();
        if(mapItems0!=null) {
            int i = random.nextInt(mapItems0.size());
            i = mapItems0.get(i);
            this.x = playGamePanel.mapManager.mapItems.get(i).getX();
            this.y = playGamePanel.mapManager.mapItems.get(i).getY();
        }
    }
    public void checkItemsMap()
    {

        for (int i = 0; i < playGamePanel.mapManager.mapItems.size(); i++ )
        {
            if(playGamePanel.mapManager.mapItems.get(i).getType() == 0)
                mapItems0.add(i);
        }
    }
    public void draw(Graphics2D g2d)
    {
        if(eat) return;
        g2d.drawImage(image, x, y, Const.APPLE_SIZE,Const.APPLE_SIZE, null);

    }
    public void eatApple(Player player) {

        if (!this.eat && this.getRect().intersects(player.getRect())) {
            player.setMagicPoint(Const.LIMIT_MP);
            player.setHp(player.getHp()+1);
            playGamePanel.appleMP.setEat(true);
        }

    }

}
