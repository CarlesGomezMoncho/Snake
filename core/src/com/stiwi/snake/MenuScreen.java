package com.stiwi.snake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

/**
 * Created by Carles on 18/10/2016.
 */

public class MenuScreen implements Screen {

    final Snake game;

    //private SpriteBatch batch;

    private Skin skin;
    private Stage stage;

    private Table table;
    private TextButton startButton, optionsButton, resumeButton;

    public MenuScreen (final Snake game)
    {
        this.game = game;

        //batch = game.batch;

        skin = new Skin(Gdx.files.internal("uiskin.json"));
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        table = new Table();
        table.setFillParent(true);
        //table.align(Align.center|Align.top);
        table.align(Align.center);
        stage.addActor(table);

        //table.setDebug(true); //activa linies de depuració

        //startButton = new TextButton("Start Game", skin, "default");
        startButton = new TextButton("Start Game", skin);
        optionsButton = new TextButton("Options", skin);
        resumeButton = new TextButton("Resume Game", skin);

        //si no existeix el arxiu de guardat, desactivem el botó
        if (!Gdx.files.local("serp.save").exists() && !Gdx.files.local("comestible.save").exists())
        {
            resumeButton.setTouchable(Touchable.disabled);
            resumeButton.setColor(1f, 1f, 1f, 0.5f);
        }


        /*startButton.setWidth(200f);
        optionsButton.setWidth(200f);
        startButton.setHeight(50f);
        optionsButton.setHeight(50f);*/

        startButton.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor){
                startButton.setText("Starting game");
                game.setScreen( new GameScreen(game, false));
                dispose();
            }
        });

        resumeButton.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor){
                game.setScreen( new GameScreen(game, true));
                dispose();
            }
        });

        optionsButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                optionsButton.setText("Boto apretat");
            }
        });


        //table.padTop(30);
        table.add(startButton).width(200f).height(50f).padBottom(20f);
        table.row();
        table.add(resumeButton).width(200f).height(50).padBottom(20);
        table.row();
        table.add(optionsButton).width(200f).height(50f);

    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor( 0, 0, 0, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();


    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
