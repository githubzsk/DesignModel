package com.xintong.code.dm.adapter.demo3;

import com.xintong.code.dm.adapter.demo2.Mobile;
import com.xintong.code.dm.adapter.demo2.Voltage220;

public class Demo3Test {
    public static void main(String[] args) {
        VoltageAdapter2 voltageAdapter2 = new VoltageAdapter2(new Voltage220());
        Mobile mobile2 = new Mobile();
        mobile2.charging(voltageAdapter2);
    }
}
