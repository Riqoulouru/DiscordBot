package com.company.bot.commands;

public class HotCombienWaitingResponse extends Thread{

    private HotCombienMain hotCombien;

    public HotCombienWaitingResponse(HotCombienMain hotCombien) {
        this.hotCombien = hotCombien;
    }

    @Override
    public void run(){

        try {
            Thread.sleep(60000);
            hotCombien.playerResponse();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
