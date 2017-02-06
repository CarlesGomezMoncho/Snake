package com.stiwi.snake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;

/**
 * Created by incar on 24/01/2017.
 */

public class OptionsScreen implements Screen {

    final Snake game;

    Preferences preferences;

    private Skin skin;
    private Stage stage;

    private Table table;
    private TextButton btnBack;
    private Label lblSpeed, lblSound, lblMusic;
    private TextField txtSpeed;
    private CheckBox chkWallEnabled, chkSelfCollision;
    private Slider sldSpeed, sldSound, sldMusic;
    private Texture fonsMenu;

    private float gameSpeed, sound, music;
    private boolean wallCollision, selfCollision;

    public OptionsScreen (final Snake game)
    {
        this.game = game;

        //agafem preferencies
        preferences = Gdx.app.getPreferences("snakeprefs");

        gameSpeed = preferences.getFloat("gameSpeed", 3);
        sound = preferences.getFloat("sound", 8);
        music = preferences.getFloat("music", 8);
        wallCollision = preferences.getBoolean("wallCollision", true);
        selfCollision = preferences.getBoolean("selfCollision", true);


        fonsMenu =  new Texture(Gdx.files.internal("congruent_outline.png"));    //imatge de fons del menú
        fonsMenu.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);   //posem la imatge com a patró

        skin = new Skin(Gdx.files.internal("uiskin.json"));
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        table = new Table();
        table.setFillParent(true);
        table.align(Align.center);
        stage.addActor(table);

        //Control de velocitat de moviment de la serp
        lblSpeed = new Label("Speed Game", skin);
        sldSpeed = new Slider(1, 5, 1, false, skin);
        sldSpeed.setValue(gameSpeed);

        //colisions en parets
        chkWallEnabled = new CheckBox("Enable Wall Collision", skin);
        chkWallEnabled.setChecked(wallCollision);
        //colisions en ella mateixa
        chkSelfCollision = new CheckBox("Enable Self Collision", skin);
        chkSelfCollision.setChecked(selfCollision);

        //Control del so
        lblSound = new Label("Sound Volume", skin);
        sldSound = new Slider(1, 10, 1, false, skin);
        sldSound.setValue(sound);

        //Control de la musica
        lblMusic = new Label("Music Volume", skin);
        sldMusic = new Slider(1, 10, 1, false, skin);
        sldMusic.setValue(music);

        //Boto per a tornar
        btnBack = new TextButton("Back", skin);


        sldSpeed.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameSpeed = sldSpeed.getValue();
            }
        });

        sldMusic.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                music = sldMusic.getValue();
            }
        });

        sldSound.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                sound = sldSound.getValue();
            }
        });

        chkSelfCollision.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                selfCollision = chkSelfCollision.isChecked();
            }
        });

        chkWallEnabled.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                wallCollision = chkWallEnabled.isChecked();
            }
        });

        btnBack.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor){

                preferences.putFloat("gameSpeed", gameSpeed);
                preferences.putFloat("music", music);
                preferences.putFloat("sound", sound);
                preferences.putBoolean("selfCollision", selfCollision);
                preferences.putBoolean("wallCollision", wallCollision);

                preferences.flush();

                game.setScreen(new MenuScreen(game));
                dispose();
            }
        });

        table.add(lblSpeed);
        table.row();
        table.add(sldSpeed).padRight(5f);
        table.add(txtSpeed).width(20f);
        table.row();
        table.add(chkWallEnabled).padTop(40f).padBottom(20f);
        table.row();
        table.add(chkSelfCollision).padBottom(20f);
        table.row();
        table.add(lblSound);
        table.row();
        table.add(sldSound).padBottom(20f);
        table.row();
        table.add(lblMusic);
        table.row();
        table.add(sldMusic).padBottom(40f);
        table.row();
        table.add(btnBack).width(200f).height(50f);
        table.row();


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
