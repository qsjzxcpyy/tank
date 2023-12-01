package com.qsj.tank2;


import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Scanner;

public class TankGame02 extends JFrame {
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args){
        TankGame02 qsjTank = new TankGame02();
    }
    public TankGame02(){
         System.out.println("请输入1(开启新游戏),2(继续上局游戏)");
         String key = scanner.next();
        MyPanel myPanel = new MyPanel(key);
        Thread thread = new Thread(myPanel);
        thread.start();
        this.add(myPanel);
        this.addKeyListener(myPanel);
        this.setSize(1300,950);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Record.storeEnemyDt();
                Record.storeHeroDt();
                System.exit(0);
            }
        });


    }

}