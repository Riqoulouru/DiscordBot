package com.company.bot.commands;

public class HotCombienWaiting extends Thread{

    private HotCombienMain hotCombien;

    public HotCombienWaiting(HotCombienMain hotCombien) {
        this.hotCombien = hotCombien;
    }

    @Override
    public void run(){

        try {
            Thread.sleep(60000);
            hotCombien.secondPlayerJoin();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


}
