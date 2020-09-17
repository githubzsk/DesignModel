package com.xintong.code.string;

/**
 * @ClassName PlayGame
 * @Description TODO
 * @Author zsk
 * @Date 2020/1/7 11:00
 * @Version 1.0
 */
public final class PlayGame {
    public static void playGame(Game game){

        System.out.println("打开机器".equals(""));
        System.out.println("打开外挂");
        System.out.println("加速器...");
        //开始游戏
        game.play();//

        System.out.println("存档...");
    }
    public static void main(String[] args){
        playGame(new WOW());
    }
}


class LOL implements Game{
    public void play(){
        System.out.println("开始 LOL ing");
    }
}
class WOW implements Game{
    public void play(){
        System.out.println("开始 WOW ing");
    }
}


/***
 * 创建一个Game接口
 */
interface Game {
    void play();
}
