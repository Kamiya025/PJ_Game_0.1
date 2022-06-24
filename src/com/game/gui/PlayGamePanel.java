package com.game.gui;

import com.game.map.MapManager;
import com.game.model.*;
import com.game.util.Const;
import com.game.util.ImageManager;
import com.game.util.MediaManager;
import kotlin.jvm.internal.SpreadBuilder;

import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class PlayGamePanel extends JPanel {

    public MapManager mapManager;
    public Player player;
    public AppleMP appleMP;
    public ArrayList<FireBall> fireBalls = new ArrayList<>();
    public ArrayList<Wolf> wolfs = new ArrayList<>();
    public ArrayList<WolfAttack> wolfsAttacks = new ArrayList<>();
    private Graphics2D g2d;
    private ScreenFarme screenFarme;
    public boolean playGame = true, stopGame = true;
    public int score;
    private MediaManager mediaManager = new MediaManager();
    public Clip battleBGM = mediaManager.getAduio("BattleBGM.wav");
    public Clip themeBGM = mediaManager.getAduio("Theme.wav");
    public Clip victory = mediaManager.getAduio("Victory1.wav");
    public Clip gameover = mediaManager.getAduio("Gameover1.wav");
    public int show, ending = 0;
    //show 1-menu 2-tutorial 3-game 4-ending
    //ending 1-badend 2-goodend
    Image screenImage = null;
    private int minute, second;


    public PlayGamePanel(ScreenFarme screenFarme) {
        setLayout(null);
        setFocusable(true);
        addKeyListener(new PlayGameListener());
        this.screenFarme = screenFarme;
        openMenu();
        new Thread(new PaintThread()).start();
        new Thread(new AudioThread()).start();
        new Thread(new ClockThread()).start();
    }

    public void startGame() {
        this.show = 3;
        this.playGame = true;
        this.stopGame = false;
        score = 0;
        System.out.println(this.isFocusable());
        minute = 5;
        second = 0;
        mapManager = new MapManager("map2");
        player = new Player(Const.MAP_SIZE_WIDTH / 2, Const.MAP_SIZE_HEIGHT / 2, 1, Const.LIMIT_HP, this);
        appleMP = new AppleMP(this);
        wolfs = new ArrayList<>();
        fireBalls = new ArrayList<>();
        new Thread(new AddWolfThread()).start();
    }

    private void addWolf() {
        if (wolfs.size() > 10) return;
        Wolf wolf = new Wolf(this.screenFarme.playGamePanel);
        wolf.randomShow();
        wolfs.add(wolf);
    }

    public void openMenu() {
        show = 1;
    }

    public void openTutorial() {
        show = 2;
    }

    public void openEnding(int end) {
        ending = end;
        show = 4;
    }
    //paint

    @Override
    protected void paintComponent(Graphics g) {
        if (!playGame) return;
        super.paintComponent(g);
        g2d = (Graphics2D) g;
        switch (show) {
            case 1:
                paintMenu(g2d);
                break;
            case 2:
                paintTutorial(g2d);
                break;
            case 3:
                paintGame(g2d);
                break;
            case 4:
                paintEnding(g2d);
                break;
        }
    }

    public void paintMenu(Graphics2D g2d) {
        g2d.drawImage(ImageManager.imgStart, 0, 0, Const.MAP_SIZE_WIDTH, Const.MAP_SIZE_HEIGHT, null);
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Sanserif", Font.BOLD + Font.TYPE1_FONT, 30));
        g2d.drawString("[Nhấn Enter để bắt đầu]", Const.MAP_SIZE_WIDTH / 3, Const.MAP_SIZE_HEIGHT - 100);
        g2d.setFont(new Font("Sanserif", Font.BOLD + Font.TYPE1_FONT, 20));
        g2d.drawString("[H] Hướng dẫn", 10, Const.MAP_SIZE_HEIGHT - 30);
        g2d.drawString("[x] Thoát", Const.MAP_SIZE_WIDTH - 100, Const.MAP_SIZE_HEIGHT - 30);
    }

    public void paintTutorial(Graphics2D g2d) {
        setBackground(new Color(0x077907));
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Sanserif", Font.BOLD + Font.TYPE1_FONT, 20));
        g2d.drawString("Hãy điều khiển nhân vật sống đến khi đồng hồ đếm ngược về không", Const.MAP_SIZE_WIDTH / 7, 80);
        g2d.drawString("Điều khiển nhân vật: nút[Lên, Xuống, Trái, Phải]", Const.MAP_SIZE_WIDTH / 7, 120);
        g2d.drawString("Đừng quên tránh những con sói, và tấn công chúng bằng cầu lửa", Const.MAP_SIZE_WIDTH / 7, 170);
        g2d.drawString("Bắn cầu lửa: nút[SPACE]", Const.MAP_SIZE_WIDTH / 7, 220);
        g2d.setFont(new Font("Sanserif", Font.BOLD + Font.TYPE1_FONT, 30));
        g2d.drawString("[Nhấn Enter để bắt đầu]", Const.MAP_SIZE_WIDTH / 3, Const.MAP_SIZE_HEIGHT - 100);
        g2d.setFont(new Font("Sanserif", Font.BOLD + Font.TYPE1_FONT, 20));
        g2d.drawString("[z] Quay lại", Const.MAP_SIZE_WIDTH - 130, Const.MAP_SIZE_HEIGHT - 30);
    }

    public void paintGame(Graphics2D g2d) {
        if (playGame) setBackground(new Color(0x077907));

        mapManager.drawAllMap(g2d);
        for (int i = 0; i < fireBalls.size(); i++) {
            fireBalls.get(i).draw(g2d);
        }

        for (int i = 0; i < wolfs.size(); i++) {
            wolfs.get(i).drawWolf(g2d);
        }
        player.draw(g2d);
        for (int i = 0; i < wolfsAttacks.size(); i++) {
            wolfsAttacks.get(i).draw(g2d);
        }
        player.drawMagicPoint(g2d);
        player.drawHP(g2d);
        player.drawScore(g2d);
        g2d.setColor(Color.BLUE);
        g2d.setFont(new Font("Sanserif", Font.BOLD, 30));
        g2d.drawString(minute + " : " + second, 400, 30);

    }

    public void paintEnding(Graphics2D g2d) {
        if (ending == 0) return;
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Sanserif", Font.BOLD + Font.TYPE1_FONT, 30));
        if (ending == 1) {
            setBackground(new Color(0x000300));
            g2d.drawString("Tôi đã cố hết sức", Const.MAP_SIZE_WIDTH / 2 - 130, 100);
        }
        if (ending == 2) {
            setBackground(new Color(0x077907));
            g2d.drawString("Sống sót đến cùng", Const.MAP_SIZE_WIDTH / 2 - 130, 100);
        }
        g2d.drawString("[Nhấn Enter để chơi lại]", Const.MAP_SIZE_WIDTH / 3, Const.MAP_SIZE_HEIGHT - 100);
        g2d.setFont(new Font("Sanserif", Font.BOLD + Font.TYPE1_FONT, 15));
        g2d.drawString("Điểm số", Const.MAP_SIZE_WIDTH / 3, Const.MAP_SIZE_HEIGHT / 2);
        g2d.drawString(score + "", Const.MAP_SIZE_WIDTH - (Const.MAP_SIZE_WIDTH / 3), Const.MAP_SIZE_HEIGHT / 2);

    }


    //thead
    private class PaintThread implements Runnable {
        public void run() {
            while (playGame) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (!stopGame && show == 3) repaint();
                if (show != 3) repaint();
                switch (show) {
                    case 3:
                        if (player != null)
                            if (!player.isLive() && playGame) {
                                battleBGM.stop();
                                stopGame = true;

                                try {
                                    Thread.sleep(500);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                openEnding(1);
                            }
                        break;
                }
            }
        }
    }

    private class AudioThread implements Runnable {
        public void run() {
            while (playGame) {
                switch (show) {
                    case 1:
                        victory.stop();
                        gameover.stop();
                        themeBGM.start();
                        battleBGM.stop();
                        break;
                    case 3:
                        victory.stop();
                        gameover.stop();
                        themeBGM.stop();
                        if (stopGame) {
                            battleBGM.stop();
                        } else battleBGM.loop(2);
                        break;
                    case 4:
                        themeBGM.stop();
                        battleBGM.stop();
                        if (ending == 2) victory.start();
                        else gameover.start();
                        break;
                }
                if (stopGame) {
                    battleBGM.stop();
                } else battleBGM.start();
            }
        }
    }

    private class AddWolfThread implements Runnable {
        public void run() {
            Random rd = new Random();
            while (playGame) {
                int timeSkip = (minute * 60 + second);
                if (timeSkip < 50) timeSkip = 50;
                int i = rd.nextInt(timeSkip);
                if (i < 50) i = 50;
                try {
                    Thread.sleep(i * 5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(i * 10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (!stopGame) addWolf();
            }
        }
    }

    private class ClockThread implements Runnable {
        public void run() {
            while (playGame) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (!stopGame) {
                    second--;
                    if (second < 0 && minute >= 0) {
                        minute--;
                        second = 59;
                        if (minute < 0) {
                            second++;
                            stopGame = true;
                            openEnding(2);
                        }
                    }
                }
            }
        }
    }

    private class PlayGameListener extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            switch (show) {
                case 1:
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        startGame();
                    } else if (e.getKeyCode() == KeyEvent.VK_H) openTutorial();
                    else if (e.getKeyCode() == KeyEvent.VK_X) System.exit(0);
                    ;
                    break;
                case 2:
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        startGame();
                    } else if (e.getKeyCode() == KeyEvent.VK_Z) openMenu();
                    break;
                case 3:
                    player.keyReleased(e);
                    break;
                case 4:
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        openMenu();
                    }
                    break;
            }
        }

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            switch (show) {
                case 1:
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        startGame();
                    }
                    break;
                case 3:
                    player.keyPressed(e);
                    break;
            }
        }
    }
}
