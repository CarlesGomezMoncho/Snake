package com.stiwi.snake;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Json;

import java.io.Serializable;

/**
 * Created by Carles on 19/10/2016.
 */

public class PartSerp extends Primitiva {

    private int xant, yant; //posicio del element anterior de la serp en la que ha fet un canvi de direcció, el necesitem per a comprovar si hem aplegat a la zona de canvi de direcció
    private int direccio;   //direcció en la que es mou la part
    private int direccioAnt;//direcció en la que es mourà quan aplegue a xant i yant
    private boolean direccioDiferent; //per a notificar que estem anant en una direcció diferent al anterior

    public PartSerp()
    {
    }
    //Constructor
    public PartSerp(int x, int y)
    {
        super(x, y, 10, 10);
        this.xant = x;
        this.yant = y;
        direccio = 0;//inicialment no es mou
        direccioDiferent = false;
        direccioAnt = 0;
        setColor(new Color(1, 1, 0.5f, 1));
    }

    public void setDireccio (int direccio) { this.direccio = direccio; }
    public int getDireccio () { return direccio; }

    public boolean isDireccioDiferent() {
        return direccioDiferent;
    }
    public void setDireccioDiferent(boolean direccioDiferent) {
        this.direccioDiferent = direccioDiferent;
    }

    public void setDireccioAnt (int direccioAnt) { this.direccioAnt = direccioAnt; }
    public int getDireccioAnt () { return direccioAnt; }
    public int getXant () { return xant; }
    public int getYant () { return yant; }
    public void setXant (int xant) { this.xant = xant; }
    public void setYant (int yant) { this.yant = yant; }

}
