package com.stiwi.snake;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Carles on 28/10/2016.
 */

public class Primitiva {
    protected int x, y, width, height;
    protected Rectangle rectangle;
    //protected ShapeRenderer shapeRenderer;
    protected Color color;

    public Primitiva()
    {
    }

    public Primitiva (int x, int y, int width, int height)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        rectangle = new Rectangle(x, y, width, height);
        //shapeRenderer = new ShapeRenderer();
        color = new Color(1f, 1f, 1f, 1);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
        rectangle.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
        rectangle.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    /*public ShapeRenderer getShapeRenderer() {
        return shapeRenderer;
    }

    public void setShapeRenderer(ShapeRenderer shapeRenderer) {
        this.shapeRenderer = shapeRenderer;
    }*/

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void render()
    {
        /*shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(color);
        shapeRenderer.rect(x, y, width, height);
        shapeRenderer.end();
        */
    }

    public boolean colisiona(Primitiva p)
    {
        return rectangle.overlaps(p.getRectangle());
    }
}
