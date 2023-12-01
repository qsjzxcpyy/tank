package com.qsj.tank2;

import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Vector;

public class MyPanel extends Panel implements KeyListener , Runnable{

    boolean res = true;
    Image image1 = null;
    Image image2 = null;
    Image image3 = null;
    Image image4 = null;
    Image image5 = null;
   Hero hero = null;
   Shot shot = null;
   EnemyTank enemyTank = null;
   GetEnemtTanks getEnemtTanks = null;
  Vector<EnemyTank>  enemyTanks  = new Vector<>();
  Vector<Shot> shots = null;
   Vector<String[]> enemyDt = null;
   Vector<String[]> heroDt = null;
    Vector<Bomb> bombs = new Vector<>();

    public MyPanel(String key) {

        File file = new File(Record.recordEyFile);
         if(!file.exists()){
             System.out.println("文件不存在只能开始新的游戏");
             key = "1";
         }
         switch(key){
             case "1":
                 hero = new Hero(700,500);
                 hero.setDirect(1);
                 hero.setSpeed(3);
                getEnemtTanks = new GetEnemtTanks(enemyTanks);
                new Thread(getEnemtTanks).start();//结束根据击败的数量结束。
                break;
             case "2":
                   enemyDt = Record.getEnemyDt();
                   enemyTanks = getEnemyTanks(enemyDt);
                 getEnemtTanks = new GetEnemtTanks(enemyTanks);
                 new Thread(getEnemtTanks).start();//结束根据击败的数量结束。
                   heroDt = Record.getHeroDt();
                   hero = getHeroTank(heroDt);

                 break;
             default:
                 System.out.println("输入有误！！");
         }


        image1 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_1.gif"));
        image2 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_2.gif"));
        image3 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_3.gif"));
        image4 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/gameover.jpg"));
        image5 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/win.png"));
        new AePlayWave("src\\111.wav").start();
        Record.setHero(hero);
        Record.setEnemyTanks(enemyTanks);

    }

    private Hero getHeroTank(Vector<String[]> heroDt) {
        String[] hd = heroDt.get(0);
        int x = Integer.parseInt(hd[0]);
        int y = Integer.parseInt(hd[1]);
        int direct = Integer.parseInt(hd[2]);
        shots = getShots(1, heroDt);
        hero = new Hero(x , y , direct , shots);
        hero.setSpeed(5);
        return hero;
    }

    private Vector<Shot> getShots(int i, Vector<String[]> Dt) {
        shots = new Vector<>();
        boolean res = false;
        for(int j = i; j < Dt.size(); j ++){
            String[] sd = Dt.get(j);
            if(sd.length == 4) {
                break;
            }
            int x = Integer.parseInt(sd[0]);
            int y = Integer.parseInt(sd[1]);
            int direct = Integer.parseInt(sd[2]);
            shot = new Shot(x , y , direct , 2);
            new Thread(shot).start();
            shots.add(shot);
        }
        return shots;
    }

    private Vector<EnemyTank> getEnemyTanks(Vector<String[]> enemyDt){
         String[] s = null;
         Integer j = 1;
        for(int i = 0; i < enemyDt.size(); i ++){
            s = enemyDt.get(i);
            int x = Integer.parseInt(s[0]);
            int y = Integer.parseInt(s[1]);
            int direct = Integer.parseInt(s[2]);
            shots = getShots(i + 1,enemyDt);
            j = getJ(i + 1,enemyDt);
            enemyTank = new EnemyTank(x , y , direct , shots);
            enemyTank.setEnemytanks(enemyTanks);
            new Thread(enemyTank).start();
            enemyTanks.add(enemyTank);
            i = j - 1;

        }
        return enemyTanks;
    }

    private Integer getJ(int i, Vector<String[]> Dt) {
        for(int j = i; j < Dt.size(); j ++) {
            String[] sd = Dt.get(j);
            if (sd.length == 4) {
               return j;
            }
        }
        return Dt.size();
    }

    public void paint(Graphics g){
        super.paint(g);
        g.fillRect(0,0,1000,750);
        if(hero.isLive) {
            drawTank(hero.getX(), hero.getY(), hero.getDirect(), 1, g);
        }
        else{

            g.drawImage(image4,250,187,500,370,this);
            res = false;
        }

        if(hero.heroshots.size()> 0){
            for(int i = 0; i < hero.heroshots.size();i ++){
                if (hero.heroshots.get(i).isLive()) {
                    g.draw3DRect(hero.heroshots.get(i).getX(), hero.heroshots.get(i).getY(), 1, 1, false);
                }
                else{
                    hero.heroshots.remove(i);
                }
            }
        }
        showInfo(g);
        if(enemyTanks.size() > 0) {
            for (int i = 0; i < enemyTanks.size(); i++) {
                EnemyTank et = enemyTanks.get(i);
                if(et.isLive) {
                    drawTank(et.getX(), et.getY(), et.getDirect(), 0, g);
                }
                else {
                    enemyTanks.remove(et);
                }

                for(int j = 0; j < et.enemyshots.size(); j++){
                        Shot shot = et.enemyshots.get(j);
                        if(shot.isLive()){
                            g.draw3DRect(shot.getX(),shot.getY(), 1, 1, false);
                        }
                        else{
                            et.enemyshots.remove(shot);
                        }
                    }

            }
        }
        else {
            g.drawImage(image5,250,187,500,370,this);
            res = false;
        }
        for(int i = 0; i < bombs.size(); i ++){
            Bomb bomb = bombs.get(i);
            if(bomb.isLive){
                if(bomb.value > 6){
                    g.drawImage(image1,bomb.x,bomb.y,100,100,this);
                }
                else if(bomb.value > 3){
                    g.drawImage(image2,bomb.x,bomb.y,100,100,this);

                }
                else{
                    g.drawImage(image3,bomb.x,bomb.y,100,100,this);
                }
            }
            else{
                bombs.remove(bomb);
            }

        }
    }

    private void showInfo(Graphics g) {
      g.setColor(Color.red);
      Font font = new Font("宋体",Font.BOLD,25);
      g.setFont(font);
      g.drawString("您累计摧毁敌方坦克",1020,30);
        Font font1 = new Font("宋体",Font.BOLD,25);
        g.setFont(font1);
        g.setColor(Color.black);
      g.drawString("累计击败 5 个敌人后",1010,200);
        Font font2 = new Font("宋体",Font.BOLD,25);
        g.setFont(font2);
        g.setColor(Color.black);
      g.drawString("新的敌人将不会产生",1010,230);
        drawTank(1020, 60,1, 2, g);//画出一个敌方坦克
        g.setColor(Color.red);
        g.drawString(Record.getKillEnemySum() + "", 1080, 100);
    }


    public  void hitTank (Shot shot , Tank tank){
        switch(tank.getDirect()){
            case 1:
            case 3:
                if(shot.getX()>= tank.getX() && shot.getX() <= tank.getX() + 40
                        && shot.getY() <= tank.getY() + 60 && shot.getY() >= tank.getY()){
                    if(tank instanceof EnemyTank){
                        Record.addKillEnemySum();
                        if(Record.killEnemySum >= 5){
                            getEnemtTanks.isLive = false;
                        }
                    }
                    shot.setLive(false);
                    tank.setLive(false);
                    Bomb bomb = new Bomb(tank.getX(), tank.getY());
                    new Thread(bomb).start();
                    bombs.add(bomb);
                }
                break;

            case 2:
            case 4:
                if(shot.getX()>= tank.getX() && shot.getX() <= tank.getX() + 60
                        && shot.getY() <= tank.getY() + 40 && shot.getY() >= tank.getY()){
                    if(tank instanceof EnemyTank){
                        Record.addKillEnemySum();
                        if(Record.killEnemySum == 5){
                            getEnemtTanks.isLive = false;
                        }
                    }
                    shot.setLive(false);
                    tank.setLive(false);
                    Bomb bomb = new Bomb(tank.getX(), tank.getY());
                    new Thread(bomb).start();
                    bombs.add(bomb);
                }
                break;
        }
    }
    public void hitEnemyTank(){
        for (int j = 0;j  < hero.heroshots.size(); j ++) {
             Shot shot = hero.heroshots.get(j);
            if (shot.isLive()) {
                for(int i = 0; i < enemyTanks.size(); i ++){
                    EnemyTank et = enemyTanks.get(i);
                    if(et.isLive) {
                         hitTank(shot, et);
                    }

                }
            }
        }

    }
    public void  hitHore(){
        for(int i = 0; i < enemyTanks.size(); i ++){
            EnemyTank et = enemyTanks.get(i);
            for(int j = 0; j < et.enemyshots.size(); j ++){
                Shot shot = et.enemyshots.get(j);
                    if(shot.isLive()){
                        hitTank(shot,hero);
                    }

            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

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

                    if (enemyTank.isLive) {
                        int x = enemyTank.getX();
                        int y = enemyTank.getY();
                        int dir = enemyTank.getDirect();
                        if (isIn(hero.getX(), hero.getY(), x, y, dir) || isIn(hero.getX() + 40, hero.getY(), x, y, dir))
                            return true;
                    }
                }
                break;
            case 2:
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    if (enemyTank.isLive) {
                        int x = enemyTank.getX();
                        int y = enemyTank.getY();
                        int dir = enemyTank.getDirect();
                        if (isIn(hero.getX() + 60, hero.getY(), x, y, dir) || isIn(hero.getX() + 60, hero.getY() + 40, x, y, dir))
                            return true;
                    }
                }
                break;
            case 3:
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);

                    if (enemyTank.isLive) {
                        int x = enemyTank.getX();
                        int y = enemyTank.getY();
                        int dir = enemyTank.getDirect();
                        if (isIn(hero.getX(), hero.getY() + 60, x, y, dir) || isIn(hero.getX() + 40, hero.getY() + 60, x, y, dir))
                            return true;
                    }
                }
                break;
            case 4:
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);

                    if (enemyTank.isLive) {
                        int x = enemyTank.getX();
                        int y = enemyTank.getY();
                        int dir = enemyTank.getDirect();
                        if (isIn(hero.getX(), hero.getY(), x, y, dir) || isIn(hero.getX(), hero.getY() + 40, x, y, dir))
                            return true;

                    }
                }
                break;
        }
        return false;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_S){
            hero.setDirect(3);
            if(hero.getY() + 60 <= 747)
                if(!isOverlap(3))
               hero.moveDown();
        }
        if(e.getKeyCode() == KeyEvent.VK_W){
            hero.setDirect(1);
            if(hero.getY() >= 3)
                if(!isOverlap(1))
              hero.moveUp();
        }
        if(e.getKeyCode() == KeyEvent.VK_A){
            hero.setDirect(4);
            if(hero.getX() >= 3)
                if(!isOverlap(4))
            hero.moveLeft();
        }
        if(e.getKeyCode() == KeyEvent.VK_D){
            hero.setDirect(2);
            if(hero.getX() + 60 <= 997)
                if(!isOverlap(2))
            hero.moveRight();
        }
        if(e.getKeyCode() ==  KeyEvent.VK_J){
            hero.shotEnemy();
        }
       // this.repaint();

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void drawTank(int x, int y , int dircet, int type, Graphics g){
        switch(type){
            case 0:
                g.setColor(Color.cyan);
                break;
            case 1:
                g.setColor(ColorUIResource.red);
                break;
            case 2:
                g.setColor(ColorUIResource.MAGENTA);
                break;
        }
        switch(dircet){
            case 1:
                g.fill3DRect(x,y,10,60,false);
                g.fill3DRect(x + 30,y,10,60,false);
                g.fill3DRect(x + 10,y + 10,20,40,false);
                g.fillOval(x + 10 , y + 20 , 20 , 20);
                g.drawLine(x + 20, y + 30, x + 20, y );
                break;
            case 2:
                g.fill3DRect(x,y,60,10,false);
                g.fill3DRect(x,y + 30,60,10,false);
                g.fill3DRect(x + 10,y + 10,40,20,false);
                g.fillOval(x + 20 , y + 10 , 20 , 20);
                g.drawLine(x + 30, y + 20, x + 60, y + 20 );
                break;
            case 3:
                g.fill3DRect(x,y,10,60,false);
                g.fill3DRect(x + 30,y,10,60,false);
                g.fill3DRect(x + 10,y + 10,20,40,false);
                g.fillOval(x + 10 , y + 20 , 20 , 20);
                g.drawLine(x + 20, y + 30, x + 20, y + 60 );
                break;
            case 4:
                g.fill3DRect(x,y,60,10,false);
                g.fill3DRect(x,y + 30,60,10,false);
                g.fill3DRect(x + 10,y + 10,40,20,false);
                g.fillOval(x + 20 , y + 10 , 20 , 20);
                g.drawLine(x + 30, y + 20, x, y + 20 );
                break;
            default:
                System.out.println("暂时没有处理");

        }
    }

    @Override
    public void run() {
        while(res){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            hitEnemyTank();
            hitHore();
            this.repaint();
        }
    }
}
