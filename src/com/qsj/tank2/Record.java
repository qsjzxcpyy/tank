package com.qsj.tank2;

import java.io.*;
import java.util.Vector;

public class Record {
    static int killEnemySum = 0;
    static Vector<EnemyTank> enemyTanks = null;
    static BufferedReader read = null;
    static String recordEyFile = "src\\myEnemyRecord.txt";
    static String recordHeroFile = "src\\myHeroRecord.txt";

    static BufferedWriter w = null;
    private static Vector<Shot> shots = null;
    static Vector<String[]> dt = new Vector<>();
    static Vector<String[]> he = new Vector<>();
    static Hero hero = null;
    static void addKillEnemySum(){
        killEnemySum ++;
    }
    static void storeEnemyDt() {
        try {
            w = new BufferedWriter(new FileWriter(recordEyFile));
            w.write(killEnemySum + "\r\n");
            for(int i = 0; i < enemyTanks.size(); i ++){
                EnemyTank et = enemyTanks.get(i);
                if(et.isLive){
                    w.write(et.getX() + " " + et.getY() + " " + et.getDirect() + " "
                    + et.getSpeed() +  "\r\n");
                    shots = et.enemyshots;
                    for(int j = 0; j < shots.size(); j ++){
                        Shot shot = shots.get(j);
                        if(shot.isLive()){
                            String s = shot.getX() + " " + shot.getY() + " " + shot.getDirect();
                            w.write(s + "\r\n");
                        }
                    }


                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                if(w != null)
                w.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static Vector<String[]> getEnemyDt()  {

        try {
            read = new BufferedReader(new FileReader(recordEyFile));
            String line = "";
            killEnemySum = Integer.parseInt(read.readLine());
            while((line = read.readLine()) != null){
              String[] etd = line.split(" ");
              dt.add(etd);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(read != null)
                read.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return dt;
    }
    static void storeHeroDt() {
        try {
            w = new BufferedWriter(new FileWriter(recordHeroFile));
            w.write(killEnemySum + "\r\n");
                  Hero et = hero;
                if(et.isLive){
                    w.write(et.getX() + " " + et.getY() + " " + et.getDirect() + " "
                    + et.getSpeed() + "\r\n");
                    shots = et.heroshots;
                    for(int j = 0; j < shots.size(); j ++){
                        Shot shot = shots.get(j);
                        if(shot.isLive()){
                            String s = shot.getX() + " " + shot.getY() + " " + shot.getDirect();
                            w.write(s + "\r\n");
                        }
                    }



            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                if(w != null)
                w.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static Vector<String[]> getHeroDt()  {

        try {
            read = new BufferedReader(new FileReader(recordHeroFile));
            String line = "";
            killEnemySum = Integer.parseInt(read.readLine());
            while((line = read.readLine()) != null){
              String[] etd = line.split(" ");
              he.add(etd);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(read != null)
                read.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return he;
    }
    public static void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        Record.enemyTanks = enemyTanks;
    }

    public static int getKillEnemySum() {
        return killEnemySum;
    }

    public static void setHero(Hero hero) {
        Record.hero = hero;
    }
}
