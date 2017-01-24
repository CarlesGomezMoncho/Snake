package com.stiwi.snake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;

/**
 * Created by incar on 24/01/2017.
 */

public class OptionsScreen implements Screen {

    final Snake game;

    private Skin skin;
    private Stage stage;

    private Table table;
    private TextButton backButton;
    private Texture fonsMenu;

    public OptionsScreen (final Snake game)
    {
        this.game = game;

        fonsMenu =  new Texture(Gdx.files.internal("congruent_outline.png"));    //imatge de fons del menú
        fonsMenu.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);   //posem la imatge com a patró

        skin = new Skin(Gdx.files.internal("uiskin.json"));
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        table = new Table();
        table.setFillParent(true);
        table.align(Align.center);
        stage.addActor(table);

        backButton = new TextButton("Back", skin);

        backButton.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor){
                game.setScreen(new MenuScreen(game));
                dispose();
            }
        });

        table.add(backButton).width(200f).height(50f);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor( 0, 0, 0, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.batch.draw(fonsMenu, 0, 0, 0, 0, (int)stage.getWidth(), (int)stage.getHeight());
        game.batch.end();

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
