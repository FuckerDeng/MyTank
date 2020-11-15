package com.df.tank.tobj;

import com.df.tank.ResourceManager;
import com.df.tank.TObjectType;

public class Blast extends TObject{

    public Blast(double x,double y){
        this.x = x;
        this.y = y;
        this.type = TObjectType.ENEMY;

    }
    private int step = 0;
    {
        width = ResourceManager.blasts[0].getWidth();
        heigth = ResourceManager.blasts[0].getHeight();
    }

    @Override
    public void drawObj() {
        graphContext.drawImage(ResourceManager.blasts[step++],x,y);
        if(step>=ResourceManager.blasts.length-1){
            isAlive = false;
        }
    }
}
