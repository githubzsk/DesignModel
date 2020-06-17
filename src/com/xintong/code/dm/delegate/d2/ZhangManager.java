package com.xintong.code.dm.delegate.d2;

public class ZhangManager implements Employee{

    private Employee employee;

    public ZhangManager(Employee employee) {
        this.employee = employee;
    }
    @Override
    public void work() {
        employee.work();
    }
}
