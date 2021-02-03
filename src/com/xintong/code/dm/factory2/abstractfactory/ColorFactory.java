package com.xintong.code.dm.factory2.abstractfactory;

/**
 * @ClassName ColorFactory
 * @Description TODO
 * @Author zsk
 * @Date 2019/12/30 17:34
 * @Version 1.0
 */
public class ColorFactory extends AbstractFactory {
    @Override
    public Shape getShape(String shapeType){
        return null;
    }

    @Override
    public Color getColor(String color) {
        if(color == null){
            return null;
        }
        if(color.equalsIgnoreCase("RED")){
            return new Red();
        } else if(color.equalsIgnoreCase("GREEN")){
            return new Green();
        } else if(color.equalsIgnoreCase("BLUE")){
            return new Blue();
        }
        return null;
    }
}
