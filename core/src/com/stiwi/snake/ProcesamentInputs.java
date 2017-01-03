package com.stiwi.snake;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Carles on 20/10/2016.
 */

public class ProcesamentInputs implements InputProcessor {
    private Serp serp;
    private OrthographicCamera camera;
    private Snake game;

    public ProcesamentInputs (Serp serp, OrthographicCamera camera, Snake game)
    {
        this.serp = serp;
        this.camera = camera;
        this.game = game;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        //si el joc està en pausa, pasa del tema, els touchs del menu se gestionen en la stage
        if (game.isEnPausa())
            return false;

       //agafem posició del touch
        Vector3 touchPos = new Vector3();
        touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(touchPos);
        float xt = touchPos.x;
        float yt = touchPos.y;

        //agafem el ultim canvi de direccio de la serp
        int direccio = serp.getDireccio();

        //agafem coordenades de la serp
        int xs = serp.getX();
        int ys = serp.getY();

        //canviem la direcció de la serp depenent de la posició relativa al touch
        if (direccio == 1 || direccio == 3) //si anem a la dreta o esquerra
        {
            if (ys > yt) //apretem baix
                direccio = 2;
            else direccio = 4;
        }else if (direccio == 2 || direccio == 4)//si anem amunt o aball
        {
            if (xs > xt) //apretem a la dreta
                direccio = 3;
            else direccio = 1;
        }

        //finalment afegim a la cua de futures direccions la nova direcció
        serp.addDireccio(direccio);
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
