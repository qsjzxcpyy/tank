package com.qsj.tank2;


public class Hero  extends Tank {
    Shot shot = null;
    public Hero(int x, int y) {
        super(x, y);

    }
    public void shotEnemy(){
        switch(getDirect()) {
            case 1:
                shot = new Shot(getX() + 20, getY(), 1, 2);
                break;
            case 2:
                shot = new Shot(getX() + 60, getY() + 20, 2, 2);
                break;
            case 3:
                shot = new Shot(getX() + 20 , getY() + 60, 3 , 2);
                break;
            case 4 :
                shot = new Shot(getX(),getY() + 20 , 4, 2);
                break;
            default:
                System.out.println("子弹方向有误");
        }
        new Thread(shot).start();


    }

}
