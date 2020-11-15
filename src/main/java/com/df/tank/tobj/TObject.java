package com.df.tank.tobj;

import com.df.tank.TObjectType;
import com.df.tank.canvas.TankCanvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;

import java.util.List;

public abstract class TObject {

    //坐标
    public  Double x = 0.0;
    public  Double y = 0.0;
    //画笔
    protected GraphicsContext  graphContext= null;
    protected TankCanvas tankCanvas = null;
    public boolean isAlive = true;//是否存活，存活的时候才进行绘画
    // 0 玩家，1 敌人
    public TObjectType type = TObjectType.PLAYER;
    //长宽属性
    public double width = 100;
    public double heigth = 100;

    //绘画方法
    //如果绘画里有多个步骤是相同的，则考虑下模板模式
    public abstract void drawObj();

    //碰撞检测
    protected void checkCollide() {
        //只在玩家阵营的对象上进行检测，减少不必要的遍历操作
        if(type == TObjectType.ENEMY) return;

        Rectangle own = new Rectangle(this.x,this.y,this.width,this.heigth);
        List<TObject> objs = tankCanvas.getObjs();
        for (TObject other : objs) {
            if(this==other) continue;
            if(this.type!=other.type){//同类之间不需要进行碰撞检测
                boolean intersects = own.intersects(other.x, other.y, other.width, other.heigth);
                if(intersects){//有碰撞
                    //把自己和敌方都设置为死亡状态
                    this.isAlive = false;
                    other.isAlive = false;
                    //添加爆炸效果类
                    Blast blast = new Blast(other.x, other.y);
                    blast.setTankCanvas(this.tankCanvas);
                    tankCanvas.getObjs().add(blast);
                    return;
                }
            }
        }
    }

    //检查图形是否超出边界，超出就移除对象
    protected void checkTObjOut() {
        if(this.x>tankCanvas.getWidth()-this.width || this.x<=0 || this.y<=2 || this.y>tankCanvas.getHeight()-this.heigth){
            if(this instanceof Tank){
                if(this.x>tankCanvas.getWidth()-this.width) this.x = tankCanvas.getWidth()-this.width;
                if(this.x<0) this.x = 0.0;
                if(this.y>tankCanvas.getHeight()-this.heigth) this.y = tankCanvas.getHeight()-this.heigth;
                if(this.y<0) this.y = 0.0;
                return;
            }
            this.isAlive = false;
        }
    }


    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public GraphicsContext getGraphContext() {
        return graphContext;
    }

    public void setTankCanvas(TankCanvas tankCanvas) {
        this.tankCanvas = tankCanvas;
        this.graphContext = tankCanvas.getGraphicsContext2D();
    }
}
