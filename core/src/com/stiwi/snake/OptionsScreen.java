package com.stiwi.snake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
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

import static com.badlogic.gdx.scenes.scene2d.ui.Table.Debug.actor;

/**
 * Created by incar on 24/01/2017.
 */

public class OptionsScreen implements Screen {

    final Snake game;

    Preferences preferences;

    private Skin skin;
    private Stage stage;

    private Table table;
    private TextButton btnBack, btnVolUp, btnVolDw, btnSpdUp, btnSpdDw, btnSndUp, btnSndDw;
    private Label lblSpeed, lblSound, lblMusic;
    private CheckBox chkWallEnabled, chkSelfCollision;
    private Slider sldSpeed, sldSound, sldMusic;
    private Texture fonsMenu;

    //private int gameSpeed;
    private float sound, music, gameSpeed;
    private boolean wallCollision, selfCollision;

    public OptionsScreen (final Snake game)
    {
        this.game = game;

        //agafem preferencies
        preferences = Gdx.app.getPreferences("snakeprefs");

        gameSpeed = preferences.getInteger("gameSpeed", 2);
        if (gameSpeed == 5) gameSpeed = 3; //en velocitats de 3 i 4, no funciona be :(
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

        //colisions en parets
        chkWallEnabled = new CheckBox("Enable Wall Collision", skin);
        chkWallEnabled.setChecked(wallCollision);
        //colisions en ella mateixa
        chkSelfCollision = new CheckBox("Enable Self Collision", skin);
        chkSelfCollision.setChecked(selfCollision);

        //Control de velocitat de moviment de la serp
        lblSpeed = new Label("Speed Game", skin);
        sldSpeed = new Slider(1, 3, 1, false, skin);
        sldSpeed.setValue(gameSpeed);

        //Control del so
        lblSound = new Label("Sound Volume", skin);
        sldSound = new Slider(0, 10, 1, false, skin);
        sldSound.setValue(sound);

        //Control de la musica
        lblMusic = new Label("Music Volume", skin);
        sldMusic = new Slider(0, 10, 1, false, skin);
        sldMusic.setValue(music);

        //Boto per a tornar
        btnBack = new TextButton("Back", skin);
        //botons de pujar i baixar
        btnVolUp = new TextButton("+", skin);
        btnVolDw = new TextButton("-", skin);
        btnSndUp = new TextButton("+", skin);
        btnSndDw = new TextButton("-", skin);
        btnSpdUp = new TextButton("+", skin);
        btnSpdDw = new TextButton("-", skin);


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
                game.music.setVolume(music / 10);
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

        btnSpdUp.addListener(new ChangeListener(){

            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (gameSpeed < 3) {
                    gameSpeed += 1;
                    sldSpeed.setValue(gameSpeed);
                }
            }
        });

        btnSpdDw.addListener(new ChangeListener(){

            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (gameSpeed > 1) {
                    gameSpeed -= 1;
                    sldSpeed.setValue(gameSpeed);
                }
            }
        });

        btnSndUp.addListener(new ChangeListener(){

            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (sound < 10)
                {
                    sound += 1;
                    sldSound.setValue(sound);
                }
            }
        });

        btnSndDw.addListener(new ChangeListener(){

            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (sound > 0)
                {
                    sound -= 1;
                    sldSound.setValue(sound);
                }
            }
        });

        btnVolUp.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor){
                if (music < 10) {
                    music += 1;
                    sldMusic.setValue(music);
                }
            }
        });

        btnVolDw.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor){
                if (music > 0)
                {
                    music -= 1;
                    sldMusic.setValue(music);
                }

            }
        });

        btnBack.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor){

                //el cas de la velocitat es diferent (sols funciona per a 1, 2 i 5)
                if (gameSpeed == 3)
                    gameSpeed = 5;

                preferences.putInteger("gameSpeed", (int)gameSpeed);
                preferences.putFloat("music", music);
                preferences.putFloat("sound", sound);
                preferences.putBoolean("selfCollision", selfCollision);
                preferences.putBoolean("wallCollision", wallCollision);

                preferences.flush();

                game.setScreen(new MenuScreen(game));
                dispose();
            }
        });


        table.add();
            table.add(lblSpeed);
            table.add();
        table.row();
            table.add(btnSpdDw).width(50f);
            table.add(sldSpeed).padRight(5f);
            table.add(btnSpdUp).width(50f);
        table.row();
            table.add();
            table.add(chkWallEnabled).padTop(40f).padBottom(20f);
            table.add();
        table.row();
            table.add();
            table.add(chkSelfCollision).padBottom(20f);
            table.add();
        table.row();
            table.add();
            table.add(lblSound);
            table.add();
        table.row();
            table.add(btnSndDw).width(50f);
            table.add(sldSound).padBottom(20f);
            table.add(btnSndUp).width(50f);
        table.row();
            table.add();
            table.add(lblMusic);
            table.add();
        table.row();
            table.add(btnVolDw).width(50f);
            table.add(sldMusic).padBottom(20f);
            table.add(btnVolUp).width(50f);
        table.row();
            table.add();
            table.add(btnBack).width(200f).height(50f).padTop(40f);
            table.add();


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
