package com.qsj.tank2;


import java.util.Vector;

public class Hero  extends Tank {
    Shot shot = null;
    boolean isLive = true;
    Vector<Shot> heroshots = new Vector<>();
    public Hero(int x, int y) {
        super(x, y);

    }

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }

    public void shotEnemy(){
        switch(getDirect()) {
            case 1:
                shot = new Shot(getX() + 20, getY(), 1, 1);
                break;
            case 2:
                shot = new Shot(getX() + 60, getY() + 20, 2, 1);
                break;
            case 3:
                shot = new Shot(getX() + 20 , getY() + 60, 3 , 1);
                break;
            case 4 :
                shot = new Shot(getX(),getY() + 20 , 4, 1);
                break;
            default:
                System.out.println("子弹方向有误");
        }
        shot.setSpeed(3);
        new Thread(shot).start();
        heroshots.add(shot);


    }

}
