package com.stiwi.snake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Json;

/**
 * Created by Carles on 03/11/2016.
 */

public class GameOverScreen implements Screen {

    public Snake game;  //La super clase que ens crida
    private Skin skin;
    private Stage stage;
    private Table table;
    private TextButton acceptButton;

    public GameOverScreen (final Snake game)
    {
        this.game = game;
    }

    @Override
    public void show() {
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        table = new Table();
        table.setFillParent(true);
        //table.align(Align.center|Align.top);
        table.align(Align.center);
        stage.addActor(table);

        acceptButton = new TextButton("Return to Menu", skin);

        acceptButton.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor){
                game.setScreen( new MenuScreen(game));
                dispose();
            }
        });

        table.add(acceptButton);

        //Borrem el arxiu de guardat
        Gdx.files.local("serp.save").delete();
        Gdx.files.local("comestible.save").delete();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1f, 0.667f, 0.333f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        skin.dispose();
        stage.dispose();
    }
}
