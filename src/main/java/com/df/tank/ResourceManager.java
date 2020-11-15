package com.df.tank;

import com.df.tank.tobj.TObject;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ResourceManager {
    public static Image Ttank,Rtank,Btank,Ltank;
    public static Image Bullet;
    public static Media fireMedia;
    public static MediaPlayer mediaPlayer;
    public static Image[] blasts = new Image[8];


    public static Image TEnemyTank,REnemyTank,BEnemyTank,LEnemyTank;
    static {
        //玩家坦克
        Ttank = new Image(ClassLoader.getSystemResourceAsStream("img\\p1tankU.gif"));
        Rtank = new Image(ClassLoader.getSystemResourceAsStream("img\\p1tankR.gif"));
        Btank = new Image(ClassLoader.getSystemResourceAsStream("img\\p1tankD.gif"));
        Ltank = new Image(ClassLoader.getSystemResourceAsStream("img\\p1tankL.gif"));
        //子弹
        Bullet = new Image(ClassLoader.getSystemResourceAsStream("img\\enemymissile.gif"));
        //地方坦克
        TEnemyTank = new Image(ClassLoader.getSystemResourceAsStream("img\\enemy1U.gif"));
        REnemyTank = new Image(ClassLoader.getSystemResourceAsStream("img\\enemy1R.gif"));
        BEnemyTank = new Image(ClassLoader.getSystemResourceAsStream("img\\enemy1D.gif"));
        LEnemyTank = new Image(ClassLoader.getSystemResourceAsStream("img\\enemy1L.gif"));

        //开火音效
        fireMedia = new Media(ClassLoader.getSystemResource("img\\fire.wav").toString());
        mediaPlayer = new MediaPlayer(fireMedia);

        //爆炸资源
        for (int i = 0; i < blasts.length; i++) {
            blasts[i] = new Image(ClassLoader.getSystemResourceAsStream("img\\blast"+(i+1)+".gif"));
        }

    }

}
