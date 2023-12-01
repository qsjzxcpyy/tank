package com.qsj.tank2;

import java.util.Vector;

public class GetEnemtTanks implements Runnable{
    boolean  isLive = true;
    private int enemyTankSize = 3;

    Vector<EnemyTank> enemyTanks = null;
    @Override
    public void run() {
        if (enemyTanks.size() == 0) {
            for (int i = 0; i < enemyTankSize; i++) {
                EnemyTank enemyTank = new EnemyTank((i + 3) * 100, 0);
                enemyTank.setDirect(3);
                enemyTank.setEnemytanks(enemyTanks);
                new Thread(enemyTank).start();
                enemyTanks.add(enemyTank);
            }
        }
        while (isLive) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (enemyTanks.size() < 3 && Record.killEnemySum < 5) {
                EnemyTank enemyTank = new EnemyTank(500, 100);
                enemyTank.setDirect(3);
                enemyTank.setEnemytanks(enemyTanks);
                new Thread(enemyTank).start();
                enemyTanks.add(enemyTank);
            }
        }

    }

    public GetEnemtTanks(Vector<EnemyTank> enemyTanks) {
        this.enemyTanks = enemyTanks;
    }

}
