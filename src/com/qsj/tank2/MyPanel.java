package com.qsj.tank2;

import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

public class MyPanel extends Panel implements KeyListener , Runnable{


   Hero hero = null;
    Vector<EnemyTank> enemyTanks  = new Vector<>();
    private int enemyTankSize = 3;
    public MyPanel(){
        hero = new Hero(100,100);
        hero.setDirect(1);
        hero.setSpeed(5);
        for(int i = 0; i < enemyTankSize; i ++){
            EnemyTank enemyTank = new EnemyTank((i + 1) * 100, 0);
             enemyTank.setDirect(3);
             enemyTanks.add(enemyTank);
        }
    }
    public void paint(Graphics g){
        super.paint(g);
        g.fillRect(0,0,1000,750);
        drawTank(hero.getX(),hero.getY(),hero.getDirect(),1,g);
        if (hero.shot != null && hero.shot.isLive()) {
            g.draw3DRect(hero.shot.getX(), hero.shot.getY(), 1, 1, false);
        }
        for(int i = 0; i < enemyTankSize; i ++) {
            drawTank(enemyTanks.get(i).getX(),enemyTanks.get(i).getY(),enemyTanks.get(i).getDirect(),0,g);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_DOWN){
            hero.setDirect(3);
            hero.moveDown();
        }
        if(e.getKeyCode() == KeyEvent.VK_UP){
            hero.setDirect(1);
            hero.moveUp();
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            hero.setDirect(4);
            hero.moveLeft();
        }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
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
        while(true){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.repaint();
        }
    }
}
