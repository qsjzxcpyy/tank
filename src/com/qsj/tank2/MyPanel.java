package com.qsj.tank2;

import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

public class MyPanel extends Panel implements KeyListener , Runnable{

    boolean res = true;
    Image image1 = null;
    Image image2 = null;
    Image image3 = null;
    Image image4 = null;
    Image image5 = null;
   Hero hero = null;
    Vector<EnemyTank> enemyTanks  = new Vector<>();
    Vector<Bomb> bombs = new Vector<>();
    private int enemyTankSize = 3;
    public MyPanel(){
        hero = new Hero(700,500);
        hero.setDirect(1);
        hero.setSpeed(5);
        for(int i = 0; i < enemyTankSize; i ++){
            EnemyTank enemyTank = new EnemyTank((i + 3) * 100, 0);
             enemyTank.setDirect(3);
             new Thread(enemyTank).start();
             enemyTanks.add(enemyTank);
        }
        image1 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_1.gif"));
        image2 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_2.gif"));
        image3 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_3.gif"));
        image4 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/gameover.jpg"));
        image5 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/win.png"));

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
    public  void hitTank (Shot shot , Tank tank){
        switch(tank.getDirect()){
            case 1:
            case 3:
                if(shot.getX()>= tank.getX() && shot.getX() <= tank.getX() + 40
                        && shot.getY() <= tank.getY() + 60 && shot.getY() >= tank.getY()){
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

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_S){
            hero.setDirect(3);
            hero.moveDown();
        }
        if(e.getKeyCode() == KeyEvent.VK_W){
            hero.setDirect(1);
            hero.moveUp();
        }
        if(e.getKeyCode() == KeyEvent.VK_A){
            hero.setDirect(4);
            hero.moveLeft();
        }
        if(e.getKeyCode() == KeyEvent.VK_D){
            hero.setDirect(2);
            hero.moveRight();
        }
        if(e.getKeyCode() ==  KeyEvent.VK_J){
            hero.shotEnemy();
        }
        this.repaint();

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
