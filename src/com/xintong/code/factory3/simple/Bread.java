package com.xintong.code.factory3.simple;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @ClassName Rice
 * @Description TODO
 * @Author zsk
 * @Date 2019/12/31 9:14
 * @Version 1.0
 */
public class Bread implements Food{

    PreparedStatement preparedStatement;//
    Connection connection;


    @Override
    public void getFood() {
        System.out.println("This is bread impl food");
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class<?> name = Class.forName("com.mysql.cj.jdbc.Driver");
        DriverManager.getConnection("","","");
    }
}
