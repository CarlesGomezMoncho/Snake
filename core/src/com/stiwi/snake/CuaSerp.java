package com.stiwi.snake;

/**
 * Created by Carles on 19/10/2016.
 */

public class CuaSerp extends PartSerp{
    private int rotation; //per a girar el sprite del cap
    public CuaSerp (){}
    public CuaSerp(int x, int y)
    {
        super(x, y);
    }

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

}
