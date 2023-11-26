package com.qsj.tank2;

import java.util.Vector;

public class EnemyTank extends Tank implements Runnable {
    Vector<Shot> enemyshots = new Vector<>();
    boolean isLive = true;
    public EnemyTank(int x, int y) {
        super(x, y);
    }

    @Override
    public void run() {
        while (true) {
            if (isLive && enemyshots.size() < 5) {
                Shot shot = null;
                switch (getDirect()) {
                    case 1:
                        shot = new Shot(getX() + 20, getY(), 1, 2);
                        break;
                    case 2:
                        shot = new Shot(getX() + 60, getY() + 20, 2, 2);
                        break;
                    case 3:
                        shot = new Shot(getX() + 20, getY() + 60, 3, 2);
                        break;
                    case 4:
                        shot = new Shot(getX(), getY() + 20, 4, 2);
                        break;
                    default:
                        System.out.println("enemy子弹方向有误");

                }
                new Thread(shot).start();
                enemyshots.add(shot);
            }
            switch (getDirect()) {
                case 1:
                    for (int i = 1; i <= 70; i++) {
                        if (getY() > 0) {
                            moveUp();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                    break;
                case 2:
                    for (int i = 1; i <= 70; i++) {
                        if (getX() + 60 < 1000) {
                            moveRight();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                    break;
                case 3:
                    for (int i = 1; i <= 70; i++) {
                        if (getY() + 60 < 750) {
                            moveDown();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                    break;
                case 4:
                    for (int i = 1; i <= 70; i++) {
                        if (getX() > 0) {
                            moveLeft();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                    break;
            }
            setDirect((int)(Math.random() * 4 + 1));
            if(!isLive) break;
        }

    }

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }
}
