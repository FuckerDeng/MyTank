package com.df.tank;

public enum Direction {
    TOP,RIGHT,BOTTOM,LEFT,STOP;
    public static Direction getDirection(int directionNumber){
        Direction[] values = Direction.values();
        for (int i = 0;  i< values.length; i++) {
            if(values[i].ordinal()==directionNumber){
                return values[i];
            }
        }
        return null;
    }
}
