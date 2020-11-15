package com.df.tank.listener;

import javafx.event.Event;
import javafx.event.EventHandler;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MyKeyListener implements KeyListener {

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println(1);
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
