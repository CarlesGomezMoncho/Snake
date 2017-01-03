package com.stiwi.snake;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by Carles on 28/10/2016.
 */

public class Comestible extends Primitiva{

    private float radi, radi2, maxRadi;

    public Comestible()  {}

    public Comestible(int x, int y)
    {
        super(x, y, 10, 10);
        setColor(new Color(0.3f, 0.6f, 0.2f, 1));
        maxRadi = 6;
        radi = 0;
        radi2 = 800;
    }

    public void render()
    {/*
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(color);
        shapeRenderer.circle(x+width/2, y+height/2, radi);
        //shapeRenderer.setColor(new Color(0.9f, 0.9f, 0.9f, 1f));
        //shapeRenderer.rect(x, y, width, height);
        if (radi < maxRadi)
            radi += 1;

        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        //este sengon cercle es el efecte especial
        shapeRenderer.setColor(color);
        shapeRenderer.circle(x+width/2, y+height/2, radi2);

        //fem un efecte de cercle gran que va fent-se menut i quan aplega al tamany del altre
        //es torna a fer gran (sols el doble esta vegada) i es fa menut de forma ciclica
        if (radi2 < radi)
            radi2 = radi+2;

        radi2 = radi2-40;
        shapeRenderer.end();
        */
    }


}
