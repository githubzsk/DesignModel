package com.xintong.code.dm.proxy.proxy3;

public class Run {
    public static void main(String[] args) {
        IPlayGame player = new ProxyPlayGame(new PersonA());
        player.playGame();
    }
}
