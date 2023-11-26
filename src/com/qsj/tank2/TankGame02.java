package com.qsj.tank2;



import javax.swing.*;

public class TankGame02 extends JFrame {
    public static void main(String[] args) {
        TankGame02 qsjTank = new TankGame02();
    }
    public TankGame02(){
        MyPanel myPanel = new MyPanel();
        Thread thread = new Thread(myPanel);
        thread.start();
        this.add(myPanel);
        this.addKeyListener(myPanel);
        this.setSize(1000,750);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);


    }

}