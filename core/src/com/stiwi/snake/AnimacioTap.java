package com.stiwi.snake;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by Carles on 20/10/2016.
 */

public class AnimacioTap {
    private float x, y;//posicio en pantalla
    private ShapeRenderer shapeRenderer;
    private float radi;

    public AnimacioTap(float x, float y)
    {
        this.x = x;
        this.y = y;
        shapeRenderer = new ShapeRenderer();
        radi = 1;
    }

    public float getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void render()
    {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 0.3f, 0.5f, 1);
        shapeRenderer.circle(x, y, radi);
        shapeRenderer.end();

        radi += 0.1;
    }

    public float getRadi()
    {
        return radi;
    }
}
