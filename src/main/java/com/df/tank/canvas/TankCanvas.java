package com.df.tank.canvas;

import com.df.tank.ResourceManager;
import com.df.tank.tobj.TObject;
import com.df.tank.tobj.Tank;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TankCanvas extends Canvas {
    List<TObject> tObjects = new ArrayList<>();
    private Thread drawThread;
    private Tank player;
    public double mouseX = -1;
    public double mouseY = -1;
    public TankCanvas(){
        super();
        startDraw();
    }



    public TankCanvas(double width, double height){
        super(width,height);
        startDraw();

    }
    private void startDraw() {
        if(drawThread !=null) return;
        drawThread = new Thread(()->{
            draw();
        });
        drawThread.setDaemon(true);
        drawThread.start();
    }
    public void draw() {
        while (true){
            this.getGraphicsContext2D().clearRect(0,0,this.getWidth(),this.getHeight());
            this.getGraphicsContext2D().strokeText("坦克数量："+tObjects.size(),50,50);

            if(mouseX >=0){
                getGraphicsContext2D().strokeRect(mouseX-20,mouseY-20,40,40);
            }


            if(tObjects.size()>0){

                //画图,此处只能使用fori的形式，不能用增强for或iterator，因为会在遍历的时候增加元素，后两种方法都会报错
                for (int i = 0; i < tObjects.size(); i++) {
                    tObjects.get(i).drawObj();
                }


                //移除死亡的对象
                removeDieObj();

            }

            //每16ms画一次，相当于每秒60帧
            try { TimeUnit.MILLISECONDS.sleep(16); } catch (InterruptedException e) { e.printStackTrace(); }
        }
    }



    private void removeDieObj() {
        Iterator<TObject> iterator = tObjects.iterator();
        while (iterator.hasNext()){
            TObject next = iterator.next();
            if(!next.isAlive)  iterator.remove();

        }
    }




    public Tank getPlayer() {
        return player;
    }

    public void setPlayer(Tank player) {
        this.player = player;
        this.getObjs().add(player);
    }

    public List<TObject> getObjs(){
        return tObjects;
    }



}
