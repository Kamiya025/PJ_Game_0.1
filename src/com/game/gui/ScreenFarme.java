package com.game.gui;

import com.game.util.Const;
import com.game.util.ImageManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ScreenFarme  extends JFrame{

    public PlayGamePanel playGamePanel;
    public ScreenFarme()
    {
        super("Version 0.1");
        setSize(Const.MAP_SIZE_WIDTH+15,Const.MAP_SIZE_HEIGHT+43);
        setLocationRelativeTo(null);
        playGamePanel = new PlayGamePanel(this);
        add(playGamePanel);
        setLayout(new CardLayout());
        setIconImage(ImageManager.imgIcon);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        setVisible(true);
    }

}
