package com.qsj.tank2;

public class Bomb implements Runnable {
    int value = 10;
    int x;
    int y;
    boolean isLive = true;
    @Override
    public void run() {
        while(isLive){
            if(value == 0){
                isLive = false;
                break;
            }
           value --;
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

    public Bomb(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }
}
