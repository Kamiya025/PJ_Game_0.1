package com.game.map;

import com.game.model.MapItem;
import com.game.util.Const;
import com.game.util.ImageManager;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class MapManager {
    public ArrayList<MapItem> mapItems;

    public MapManager(String nameMap)
    {
        mapItems = new ArrayList<>();
        readMap(nameMap);
    }
    public void readMap(String nameMap) {
        File mapFile = new File("src/map/"+nameMap);
        if(!mapFile.exists()){System.out.println("map don't access");}
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(mapFile,"r");
            int row = 0 ;
            String content;
            Image image;
            while((content = randomAccessFile.readLine())!= null)
            {
            for(int colum =0; colum < content.length(); colum++)
            {
                int type = Integer.parseInt(content.charAt(colum)+"");
                mapItems.add(new MapItem(colum* Const.ITEM_MAP_SIZE,row*Const.ITEM_MAP_SIZE,type, ImageManager.arrMapItem.get(type)));
            }
            row++;
        }
        } catch (FileNotFoundException e) {

            System.out.println("Read map error");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void drawAllMap(Graphics2D g2d)
    {
        for (int i = 0; i < mapItems.size(); i++ )
        {
            mapItems.get(i).drawMapItem(g2d);
        }
    }

}
