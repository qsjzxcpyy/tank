package com.qsj.tank2;

public class Tank {
    private int x = 0;
    private int y = 0;
    private int speed = 1;
    private int direct = 3;
    boolean isLive = true;
    public void moveUp(){
        y -= speed;
    }
    public void moveDown(){
        y += speed;
    }
    public void moveLeft(){
        x -= speed;
    }
    public void moveRight(){
        x += speed;
    }





    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }

    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }

    public void setY(int y) {
        this.y = y;
    }
}