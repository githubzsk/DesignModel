package com.xintong.code.dm.proxy.proxy3;

public class ProxyPlayGame implements IPlayGame {

    private IPlayGame player;

    ProxyPlayGame(IPlayGame player){
        this.player = player;
    }
    @Override
    public void playGame() {
        System.out.println("开机.....");
        player.playGame();
        System.out.println("关机.....");
    }
}
