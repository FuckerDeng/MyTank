package com.df.tank.tobj;

import com.df.tank.Direction;
import com.df.tank.ResourceManager;
import com.df.tank.TObjectType;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.util.List;

public class Bullet extends TObject {
    private Direction direction = Direction.BOTTOM;
    private int speed = 10;

    @Override
    public void drawObj() {
        checkCollide();
        if(!isAlive) return;
        getDirection();
        move();
        checkTObjOut();
        graphContext.drawImage(ResourceManager.Bullet,this.x,this.y);
    }
    {
        this.width = ResourceManager.Bullet.getWidth();
        this.heigth = ResourceManager.Bullet.getWidth();
    }



    private void move() {

        switch (direction){
            case TOP:
                this.y -= speed;
                break;
            case RIGHT:
                x +=speed;
                break;
            case BOTTOM:
                this.y +=speed;
                break;
            case LEFT:
                x -=speed;
                break;

        }
    }

    private void getDirection() {

    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
