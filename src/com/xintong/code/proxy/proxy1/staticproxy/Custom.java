package com.xintong.code.proxy.proxy1.staticproxy;

/**
 * @ClassName Custom
 * @Description TODO
 * @Author zsk
 * @Date 2019/12/31 16:34
 * @Version 1.0
 */
public class Custom {
    public static void main(String[] args) {
        IStar iStar = new Jay();
        ProxyManage proxyManage = new ProxyManage(iStar);
        proxyManage.sing();
    }
}
