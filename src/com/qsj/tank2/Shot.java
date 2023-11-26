package com.qsj.tank2;

public class Shot implements Runnable {
    private int x;
    private int y;
    private int direct = 1;
    private int speed = 1;
    private boolean live = true;


    public boolean isLive() {
        return live;
    }
    public Shot(int x, int y, int direct, int speed) {
        this.x = x;
        this.y = y;
        this.direct = direct;
        this.speed = speed;
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

    public void setLive(boolean live) {
        this.live = live;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    public void run() {

        while(true){
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            switch(direct){
                case 1:
                    y -= speed;
                    break;
                case 2:
                    x += speed;
                    break;
                case 3:
                    y += speed;
                    break;
                case 4:
                    x -= speed;
                    break;
                default:
                    System.out.println("子弹方向设置错误");
            }
            if(!(x >= 0 && x <= 1000 && y >= 0 && y <= 750)){
                System.out.println("子弹到达边界");
                live = false;
                break;

            }
        }
    }
}
