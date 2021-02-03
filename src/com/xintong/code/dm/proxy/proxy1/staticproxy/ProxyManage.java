package com.xintong.code.dm.proxy.proxy1.staticproxy;

/**
 * @ClassName ProxyManage
 * @Description TODO
 * @Author zsk
 * @Date 2019/12/31 15:53
 * @Version 1.0
 */
public class ProxyManage implements IStar {

    private IStar iStar;

    public ProxyManage(IStar iStar){
        this.iStar = iStar;
    }
    @Override
    public void sing() {
        System.out.println("proxy is ready to .....");
        iStar.sing();
        System.out.println("proxy end");
    }
}
