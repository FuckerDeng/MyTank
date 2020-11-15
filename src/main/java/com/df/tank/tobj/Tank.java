package com.df.tank.tobj;

import com.df.tank.Direction;
import com.df.tank.ResourceManager;
import com.df.tank.TObjectType;
import javafx.scene.shape.Rectangle;

import java.util.Date;
import java.util.List;
import java.util.Random;


public class Tank extends TObject {
    public int speed = 5;


    public  boolean t = false;
    public  boolean r = false;
    public  boolean b = false;
    public  boolean l = false;
    private boolean moving = false;
    public  Direction direction = Direction.TOP;
    private Random random = new Random();

    private double oldX;
    private double oldY;




    public Tank(){}
    public Tank(int x, int y){
        this.x = x*1.0;
        this.y = y*1.0;
    }
    public Tank(int x, int y, TObjectType type){
        this(x,y);
        this.type = type;
    }

    {
        if(this.type == TObjectType.PLAYER){
            this.width = (int) ResourceManager.Rtank.getWidth();
            this.heigth = (int) ResourceManager.Rtank.getHeight();
        }else {
            this.width = (int) ResourceManager.REnemyTank.getWidth();
            this.heigth = (int) ResourceManager.REnemyTank.getHeight();
        }

    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public void drawObj() {
        //做碰撞检测
        checkCollide();
        if(isAlive){
            getDirection();//判断最终的移动方向以及是否要移动，如果是地方坦克，决定是否要改变方向和发射子弹
            move();//作出位移
            checkTObjOut();
            drawTankWithImg();//画坦克
            return;
        }

//        Paint fill = graphContext.getFill();
//        graphContext.setFill(Color.BLUE);
//        graphContext.fillRect(this.x,this.y,100,100);
//        graphContext.setFill(fill);


    }


    private void drawTankWithImg() {
        graphContext.strokeRect(this.x,this.y,this.width,this.heigth);
        switch (direction){
            case TOP:
                graphContext.drawImage(ResourceManager.Ttank,this.x,this.y);
                break;
            case RIGHT:
                graphContext.drawImage(ResourceManager.Rtank,this.x,this.y);
                break;
            case BOTTOM:
                graphContext.drawImage(ResourceManager.Btank,this.x,this.y);
                break;
            case LEFT:
                graphContext.drawImage(ResourceManager.Ltank,this.x,this.y);
                break;

        }
    }

    //如果有发射一颗，发射多颗的不同情况，可以考虑下策略模式
    public void fire(){
        //生成子弹
        Bullet bullet = new Bullet();
        Double b_x = this.x+this.width/2-ResourceManager.Bullet.getWidth()/2;
        Double b_y = this.y + this.heigth/2-ResourceManager.Bullet.getHeight()/2;
        switch (direction){
            case TOP:
                b_y -= this.heigth/2;
                bullet.setDirection(Direction.TOP);
                break;
            case RIGHT:
                b_x += this.width/2;
                bullet.setDirection(Direction.RIGHT);
                break;
            case BOTTOM:
                b_y += this.heigth/2;
                bullet.setDirection(Direction.BOTTOM);
                break;
            case LEFT:
                b_x -= this.width/2;
                bullet.setDirection(Direction.LEFT);
                break;

        }
        bullet.setX(b_x);
        bullet.setY(b_y);
        bullet.setTankCanvas(this.tankCanvas);
        if(type==TObjectType.ENEMY){
            bullet.type = TObjectType.ENEMY;

        }
        this.tankCanvas.getObjs().add(bullet);

        //生成声音
//        ResourceManager.mediaPlayer.stop();
//        ResourceManager.mediaPlayer.setVolume(30);
//        ResourceManager.mediaPlayer.play();
    }

    private void move() {
        //移动前先记录下坐标
        oldX = this.x;
        oldY = this.y;
        if(!moving) return;
        switch (direction){
            case TOP:
                y -= speed;
                break;
            case RIGHT:
                x +=speed;
                break;
            case BOTTOM:
                y +=speed;
                break;
            case LEFT:
                x -=speed;
                break;

        }
        //检查是否会与同类坦克发生碰撞
        checkCollideWithSameTypeObj();
    }

    private boolean checkCollideWithSameTypeObj() {
        Rectangle own = new Rectangle(this.x,this.y,this.width,this.heigth);
        List<TObject> objs = tankCanvas.getObjs();
        for (TObject other : objs) {
            if(this==other) continue;
            if(this.type==other.type){//同类型的进行碰撞检测，决定是否能移动
                boolean intersects = own.intersects(other.x, other.y, other.width, other.heigth);
                if(intersects){//有碰撞，则把坐标设置成移动前的，在原地不动
                    this.x = oldX;
                    this.y = oldY;
                }
            }
        }
        return true;
    }

    private void getDirection() {
        if(type==TObjectType.PLAYER){
            if(t) direction = Direction.TOP;
            if(r) direction = Direction.RIGHT;
            if(b) direction = Direction.BOTTOM;
            if(l) direction = Direction.LEFT;
            if(!t && !r && !b && !l){//没有任何键按下，则停止移动
                moving = false;
            }else {
                moving = true;
            }
        }else {
            int i = random.nextInt(150);
            int i2 = random.nextInt(200);
            if(i>145){
                fire();
            }
            if(i2>195){
                int var1 = i % 4;
                this.direction = Direction.getDirection(var1);
            }
        }

    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }
}
