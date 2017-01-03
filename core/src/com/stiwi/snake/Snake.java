package com.stiwi.snake;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class Snake extends Game {
	SpriteBatch batch;

	public BitmapFont font;

	final int widthScreen = 800;
	final int heightScreen = 480;
	private boolean enPausa;

	@Override
	public void create () {
		batch = new SpriteBatch();
		this.setScreen(new MenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
	}

	public int getWidthScreen()
	{
		return widthScreen;
	}

	public int getHeightScreen()
	{
		return heightScreen;
	}

	public boolean isEnPausa() {return enPausa;}
	public void setEnPausa(boolean enPausa)
	{this.enPausa = enPausa;}
}
