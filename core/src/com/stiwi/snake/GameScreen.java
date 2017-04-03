package com.stiwi.snake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Json;

import java.io.File;
import java.util.Random;


/**
 * Created by Carles on 18/10/2016.
 */

public class GameScreen implements Screen {

    public Snake game;  //La super clase que ens crida
    private OrthographicCamera camera;
    private Serp serp; //el objecte que conté la serp a dibuixar
    private Comestible comestible;  //si colisiona amb la serp, esta creix
    private ProcesamentInputs inputProcessor; //processament propi de entrades
    private Skin skin;
    private Stage stage;
    private Table menuTable;
    private TextButton menuButton;
    private TextButton resumeButton;
    private TextButton saveButton;
    private Label titolLabel;
    private Texture fonsMenu, fons, serpTexture, capSerpTexture, cuaSerpTexture, comestibleTexture;
    private ShapeRenderer shapeRenderer;
    private boolean loadGame;

    private Preferences preferences;

    private final float buttonSize = 50f; //tamany del boto de pausa

    public GameScreen (final Snake game, boolean loadGame)
    {
        shapeRenderer = new ShapeRenderer();

        this.game = game;
        this.loadGame = loadGame;

        preferences = Gdx.app.getPreferences("snakeprefs");

        serp = new Serp(game.widthScreen/2, game.heightScreen/2);

        //asignem textura de la serp
        serpTexture = new Texture(Gdx.files.internal("snake_texture.png"));
        capSerpTexture = new Texture(Gdx.files.internal("capserp.png"));
        cuaSerpTexture = new Texture(Gdx.files.internal("cuaserp.png"));
        comestibleTexture = new Texture(Gdx.files.internal("comestible.png"));

        setComestible();

        //si restaurem joc carreguem la serp des de el arxiu
        if (loadGame)
        {
            loadGame();
        }

        //despres de comprovar si carreguem partida, asignem velocitat de la serp
        serp.setVelocitat(preferences.getInteger("gameSpeed", 3));
    }

    @Override
    public void show() {
        this.camera = new OrthographicCamera();
        camera.setToOrtho(false, game.widthScreen, game.heightScreen);


        skin = new Skin(Gdx.files.internal("uiskin.json"));
        stage = new Stage();

        fons =  new Texture(Gdx.files.internal("dark_embroidery.png"));    //imatge de fons de pantalla
        fons.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);   //posem la imatge com a patró
        fonsMenu =  new Texture(Gdx.files.internal("congruent_outline.png"));    //imatge de fons del menú
        fonsMenu.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);   //posem la imatge com a patró

        //asignem el procesament de entrades propi i el del menu pausa
        inputProcessor = new ProcesamentInputs(serp, camera, game);
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);//el de la iu (este sempre primer)
        multiplexer.addProcessor(inputProcessor);//el del joc en general

        Gdx.input.setInputProcessor(multiplexer);

        //taula del menu de pausa
        menuTable = new Table();
        menuTable.setFillParent(true);
        menuTable.setVisible(false);
        menuTable.align(Align.center);

        stage.addActor(menuTable);

        titolLabel = new Label("Pause Menu", skin);
        titolLabel.setFontScale(2, 2);
        menuTable.add(titolLabel).padBottom(30).row();

        //botons del menu
        resumeButton = new TextButton("Resume Game", skin);
        resumeButton.getLabelCell().pad(5, 10, 5, 10);
        saveButton = new TextButton("Title Menu", skin);
        saveButton.getLabelCell().pad(5, 10, 5, 10);

        menuTable.padTop(30);
        menuTable.add(resumeButton);
        menuTable.row();
        menuTable.add(saveButton).padTop(10);

        resumeButton.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor){
                game.setEnPausa(!game.isEnPausa());
                menuTable.setVisible(false);
                menuButton.setVisible(true);
                //menu.setText("Starting game");
                //game.setScreen( new GameScreen(game));
                //dispose();
            }
        });

        saveButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                saveGame();
                game.setScreen( new MenuScreen(game));
                game.setEnPausa(false);
                dispose();
            }
        });

        //posem el boto menu
        menuButton = new TextButton("||", skin);
        menuButton.setWidth(buttonSize);
        menuButton.setHeight(buttonSize);
        menuButton.setPosition(10, 10);

        menuButton.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor){
                game.setEnPausa(!game.isEnPausa());
                menuTable.setVisible(true);
                menuButton.setVisible(false);
                //menu.setText("Starting game");
                //game.setScreen( new GameScreen(game));
                //dispose();
            }
        });




        stage.addActor(menuButton);

    }

    public void setComestible()
    {
        Random r = new Random();
        int randomX, randomY;

        //per a que no es pose el comestible damunt la serp, si este colisiona, el canviem de lloc
        //ni tampoc s'ha de posar damunt del botó
        do {
            randomX = r.nextInt(game.getWidthScreen()-10);
            randomY = r.nextInt(game.getHeightScreen()-10);
            comestible = new Comestible(randomX, randomY);
        } while (serp.colisiona(comestible) || (randomX < buttonSize+10 && randomY < buttonSize+10));

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.7f, 0.6f, 0.5f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        //si la serp colisiona amb el comestible, creem una nova part i moguem el comestible a una
        //altra zona
        if (serp.colisiona(comestible))
        {
            setComestible();
            serp.incrementaParts();
        }

        //si la serp colisiona
        if (serp.colisionaAmbEllaMateixa())
        {
            //game over
            game.setScreen(new GameOverScreen(game));
            dispose();
        }

        //moguem la serp si no estem en pausa
        if (!game.isEnPausa())
            serp.mou();

        game.batch.setProjectionMatrix(camera.combined);
        //dibuixem una textura repetida de fons
        game.batch.begin();
        game.batch.draw(fons, 0, 0, 0, 0, game.getWidthScreen(), game.getHeightScreen());

        //serp
        //cap de la serp
        game.batch.draw(capSerpTexture, serp.getCap().getX(), serp.getCap().getY(), serp.getCap().getWidth()/2, serp.getCap().getHeight()/2, serp.getCap().getWidth(), serp.getCap().getHeight(), 1, 1, serp.getCap().getRotation(), 0, 0, 10, 10, false, false);
        //resta de la serp
        for (PartSerp part: serp.getCos()) {
            game.batch.draw(serpTexture, part.getX(), part.getY());
        }

        //dibuixem la cua
        game.batch.draw(cuaSerpTexture, serp.getCua().getX(), serp.getCua().getY(), serp.getCua().getWidth()/2, serp.getCua().getHeight()/2, serp.getCua().getWidth(), serp.getCua().getHeight(), 1, 1, serp.getCua().getRotation(), 0, 0, 10, 10, false, false);

        //comestible
        game.batch.draw(comestibleTexture, comestible.getX(), comestible.getY());

        //game.font.draw(game.batch, "x: "+serp.getX(), 20, 20);
        //game.font.draw(game.batch, "y: "+serp.getY(), 100, 20);
        //game.font.draw(game.batch, "direccio: "+serp.getDireccio(), 20, 40);
        //game.font.draw(game.batch, "xt:"+Gdx.input.getX(), 20, 60);
        //game.font.draw(game.batch, "yt:"+Gdx.input.getY(), 100, 60);
        //game.font.draw(game.batch, "speed: "+serp.getVelocitat(), 20, 20);
        game.batch.end();


        //actualitzem gràfics
        //serp.render();

        /*shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        //cap de la serp
        shapeRenderer.setColor(serp.getCap().getColor());
        shapeRenderer.rect(serp.getCap().getX(), serp.getCap().getY(),serp.getCap().getWidth(), serp.getCap().getHeight());
        //resta de la serp
        for (PartSerp part: serp.getCos()) {
            shapeRenderer.setColor(part.getColor());
            shapeRenderer.rect(part.getX(), part.getY(), part.getWidth(), part.getHeight());
        }

        //comestible
        shapeRenderer.setColor(comestible.getColor());
        shapeRenderer.circle(comestible.getX()+comestible.getWidth()/2, comestible.getY()+comestible.getHeight()/2, 6);
        shapeRenderer.end();*/


        //el menu va despres i ho tapa tot
        game.batch.begin();
        //si està el joc en pausa, mostrem una imatge de fons
        if (game.isEnPausa())
        {
            game.batch.draw(fonsMenu, 0, 0, 0, 0, game.getWidthScreen(), game.getHeightScreen());
        }

        game.batch.end();



        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    public void loadGame()
    {
        Json json = new Json();
        FileHandle saveFile = Gdx.files.local("serp.save");
        serp = json.fromJson(Serp.class, saveFile.readString());
        saveFile = Gdx.files.local("comestible.save");
        comestible = json.fromJson(Comestible.class, saveFile.readString());
    }

    private void saveGame()
    {
        FileHandle serpSave = Gdx.files.local("serp.save");
        FileHandle comestibleSave = Gdx.files.local("comestible.save");

        Json json = new Json();

        serpSave.writeString(json.toJson(serp), false);
        comestibleSave.writeString(json.toJson(comestible), false);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume()
    {
        game.setScreen( new MenuScreen(game));
        dispose();
    }

    @Override
    public void hide()
    {
        saveGame();
    }

    @Override
    public void dispose() {
        skin.dispose();
        stage.dispose();
    }
}
