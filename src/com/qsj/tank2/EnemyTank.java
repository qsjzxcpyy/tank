package com.qsj.tank2;

import java.util.Vector;

public class EnemyTank extends Tank implements Runnable {
    Vector<Shot> enemyshots = new Vector<>();
    Vector<EnemyTank> enemyTanks = null;
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
                        shot = new Shot(getX() + 20, getY(), 1, 5);
                        break;
                    case 2:
                        shot = new Shot(getX() + 60, getY() + 20, 2, 5);
                        break;
                    case 3:
                        shot = new Shot(getX() + 20, getY() + 60, 3, 5);
                        break;
                    case 4:
                        shot = new Shot(getX(), getY() + 20, 4, 5);
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
                            if (!isOverlap(1))
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
                            if (!isOverlap(2))
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
                                    if (!isOverlap(3))
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
                                    if (!isOverlap(4))
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
            setDirect((int) (Math.random() * 4 + 1));
            if (!isLive) break;
        }
    }


        private boolean isIn ( int posx, int posy, int x, int y, int direct){
            switch (direct) {
                case 1:
                case 3:
                    if (posx >= x && posx <= x + 40 && posy >= y && posy <= y + 60)
                        return true;
                break;
                case 2:
                case 4:
                    if (posx >= x && posx <= x + 60 && posy >= y && posy <= y + 40)
                        return true;
                break;
            }
            return false;
        }
        private boolean isOverlap ( int direct){
            switch (direct) {
                case 1:
                    for (int i = 0; i < enemyTanks.size(); i++) {
                        EnemyTank enemyTank = enemyTanks.get(i);
                        if (enemyTank == this) continue;
                        if (enemyTank.isLive) {
                            int x = enemyTank.getX();
                            int y = enemyTank.getY();
                            int dir = enemyTank.getDirect();
                            if (isIn(getX(), getY(), x, y, dir) || isIn(getX() + 40, getY(), x, y, dir))
                                return true;
                        }
                    }
                    break;
                case 2:
                    for (int i = 0; i < enemyTanks.size(); i++) {
                        EnemyTank enemyTank = enemyTanks.get(i);
                        if (enemyTank == this) continue;
                        if (enemyTank.isLive) {
                            int x = enemyTank.getX();
                            int y = enemyTank.getY();
                            int dir = enemyTank.getDirect();
                            if (isIn(getX() + 60, getY(), x, y, dir) || isIn(getX() + 60, getY() + 40, x, y, dir))
                                return true;
                        }
                    }
                    break;
                case 3:
                    for (int i = 0; i < enemyTanks.size(); i++) {
                        EnemyTank enemyTank = enemyTanks.get(i);
                        if (enemyTank == this) continue;
                        if (enemyTank.isLive) {
                            int x = enemyTank.getX();
                            int y = enemyTank.getY();
                            int dir = enemyTank.getDirect();
                            if (isIn(getX(), getY() + 60, x, y, dir) || isIn(getX() + 40, getY() + 60, x, y, dir))
                                return true;
                        }
                    }
                    break;
                case 4:
                    for (int i = 0; i < enemyTanks.size(); i++) {
                        EnemyTank enemyTank = enemyTanks.get(i);
                        if (enemyTank == this) continue;
                        if (enemyTank.isLive) {
                            int x = enemyTank.getX();
                            int y = enemyTank.getY();
                            int dir = enemyTank.getDirect();
                            if (isIn(getX(), getY(), x, y, dir) || isIn(getX(), getY() + 40, x, y, dir))
                                return true;

                        }
                    }
                    break;
            }
            return false;
        }

        public boolean isLive () {
            return isLive;
        }

        public void setLive ( boolean live){
            isLive = live;
        }

    public EnemyTank( int x, int y, int direct, Vector<Shot > enemyshots){
            super(x, y, direct);
            this.enemyshots = enemyshots;
        }

        public void setEnemytanks (Vector < EnemyTank > enemytanks) {
            this.enemyTanks = enemytanks;
        }


}