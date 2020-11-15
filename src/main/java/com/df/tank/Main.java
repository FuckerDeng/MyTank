package com.df.tank;

import com.df.tank.canvas.TankCanvas;
import com.df.tank.client.TankClient;
import com.df.tank.config.TankWarConfig;
import com.df.tank.tobj.Bullet;
import com.df.tank.tobj.Tank;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.concurrent.TimeUnit;

public class Main extends Application{
    public static void main(String[] args) {
        Application.launch(args);
    }

    private TankCanvas tankCanvas = null;
    private boolean end = false;
    private TankClient tankClient = null;


    @Override
    public void start(Stage primaryStage) throws Exception {
        TankCanvas tankCanvas = new TankCanvas(900,700);
        this.tankCanvas = tankCanvas;

        initTankCanvas();



//        Group group = new Group();
        VBox box = new VBox(2);
        box.getChildren().add(tankCanvas);
//        box.t
        Scene scene = new Scene(box);
        addKeyEvent(scene);
        TextArea textArea = new TextArea();
        textArea.setOnKeyReleased(event -> {
            if(event.getCode()== KeyCode.ENTER){
                String text = event.getText();
                tankClient.sendMsg(text);
            }
        });
//        textArea.setMaxHeight(100);
        box.getChildren().add(textArea);

        initStage(primaryStage);
        primaryStage.setScene(scene);
        if(end) return;

        primaryStage.show();

        this.tankClient = new TankClient();
        this.tankClient.run();

    }

    private void initStage(Stage primaryStage) {
        primaryStage.setWidth(900);
        primaryStage.setHeight(1000);
        primaryStage.setResizable(false);
        primaryStage.setTitle("坦克大战");
        primaryStage.setOnCloseRequest(windowEvent->{
            System.out.println("客户端关闭");
            tankClient.closeClient();
        });
    }

    public void addKeyEvent(Scene node){
        node.setOnKeyPressed((event -> {
//            System.out.println("按下:"+Thread.currentThread());
            switch (event.getCode()){
                case W:
                    tankCanvas.getPlayer().t = true;
                    break;
                case D:
                    tankCanvas.getPlayer().r = true;
                    break;
                case S:
                    tankCanvas.getPlayer().b = true;
                    break;
                case A:
                    tankCanvas.getPlayer().l = true;
                    break;
            }
        }));
        node.setOnKeyReleased((event -> {
//            System.out.println("抬起:"+Thread.currentThread());
            switch (event.getCode()){
                case W:
                    tankCanvas.getPlayer().t = false;
                    break;
                case D:
                    tankCanvas.getPlayer().r = false;
                    break;
                case S:
                    tankCanvas.getPlayer().b = false;
                    break;
                case A:
                    tankCanvas.getPlayer().l = false;
                    break;
            }
        }));
        node.setOnMouseClicked(event -> {
            if(event.getButton()==MouseButton.PRIMARY){
                tankCanvas.getPlayer().fire();
            }
        });

    }



    private void initTankCanvas() {
        //添加玩家坦克
        GraphicsContext g2d = tankCanvas.getGraphicsContext2D();

        Tank tank = new Tank();
        tank.x = tankCanvas.getWidth()/2-tank.width/2;
        tank.y = tankCanvas.getHeight()-tank.heigth*2;
        tank.setTankCanvas(tankCanvas);
        tankCanvas.setPlayer(tank);

        //添加地方坦克
        String enemyTankCount = TankWarConfig.getProperty("enemyTankCount");
        if(null==enemyTankCount){
            System.out.println("敌方坦克数量初始化失败");
            end = true;
        }
        for(int i= 0;i<Integer.parseInt(enemyTankCount);i++){
            Tank enemy = new Tank();
            enemy.setX(30+i*(ResourceManager.REnemyTank.getWidth()+10));
            enemy.setY(50.0);
            enemy.setDirection(Direction.BOTTOM);
            enemy.setTankCanvas(tankCanvas);
            enemy.type = TObjectType.ENEMY;
            enemy.speed = Integer.parseInt(TankWarConfig.getProperty("enemyTankSpeed"));
            enemy.setMoving(true);
            tankCanvas.getObjs().add(enemy);
        }

        //测试用
//        tankCanvas.setOnMouseClicked(event -> {
//            tankCanvas.mouseX=0;
//        });

        tankCanvas.setOnMouseMoved((event -> {
            if(tankCanvas.mouseX>=0){
                tankCanvas.mouseX = event.getX();
                tankCanvas.mouseY = event.getY();

            }
        }));


    }

}
