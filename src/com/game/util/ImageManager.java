package com.game.util;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class ImageManager {

    public static Image imgStart;
    public static Image imgIcon;
    public static ArrayList<Image> arrImgPlayer;
    public static ArrayList<Image> arrImgFireball;
    public static ArrayList<Image> arrImgEnemy;
    public static ArrayList<Image> arrMapItem;
    public static ArrayList<Image> arrImgAttack;
    public static Image imgApple;


    public ImageManager() {
        imgStart = new ImageIcon(getClass().getResource("/img/system/backgroundStart.png")).getImage();
        imgIcon = new ImageIcon(getClass().getResource("/img/Actor1_1.png")).getImage();
        imgApple = getSubImage("/img/item/appleMP.png", Const.ITEM_SIZE, Const.ITEM_SIZE, 1, 1).get(0);
        arrImgPlayer = getSubImage("/img/player/player.png", Const.CHARACTER_SIZE, Const.CHARACTER_SIZE, 3, 4);
        arrMapItem = getSubImage("/img/system/systemItem2.png", Const.ITEM_MAP_SIZE, Const.ITEM_MAP_SIZE, 2, 1);
        arrImgEnemy = getSubImage("/img/enemies/wolf.png", Const.WOLF_SIZE, Const.WOLF_SIZE, 3, 4);
        arrImgFireball = getSubImage("/img/player/fireBall.png", Const.ITEM_SIZE, Const.ITEM_SIZE, 2, 4);
        arrImgAttack = getSubImage("/img/enemies/Attack.png", Const.SKILL_SIZE_W, Const.SKILL_SIZE_H, 5, 2);
    }

    public ArrayList<Image> getSubImage(String imageUrl, int width, int height, int numberImgRight, int numberImgDown) {
        ArrayList<Image> imageArrayList = new ArrayList<>();
        try {
            BufferedImage bufferedImage = ImageIO.read(getClass().getResource(imageUrl));
            BufferedImage subImage;
            for (int i = 0; i < numberImgDown; i++) {
                for (int j = 0; j < numberImgRight; j++) {
                    subImage = bufferedImage.getSubimage(j * width, i * height, width, height);
                    imageArrayList.add(subImage);
                }
            }
        } catch (IOException e) {
            System.out.println("Image don't access");
            e.printStackTrace();
        }

        return imageArrayList;
    }
}
