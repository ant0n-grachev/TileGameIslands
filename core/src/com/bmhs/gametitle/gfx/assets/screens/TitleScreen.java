package com.bmhs.gametitle.gfx.assets.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;

import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bmhs.gametitle.game.utils.GameHandler;
import com.bmhs.gametitle.gfx.utils.TileHandler;

public class TitleScreen implements Screen {

    GameHandler game;
    Screen parent;

    Stage titleScreenStage;

    private OrthographicCamera camera;
    private Viewport viewport;

    public TitleScreen(final GameHandler game) {
        this.game = game;
        this.parent = this;

        camera = new OrthographicCamera();
        camera.position.set(0, 0, 0);
        camera.zoom = 64;

        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        viewport.setScreenBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        titleScreenStage = new Stage();

        Label title = new Label("GameTitle Test Screens", game.skin);
        title.setAlignment(Align.left);
        title.setWidth(100);
        title.setHeight(25);
        title.setPosition(190, 20);
        titleScreenStage.addActor(title);

        TextButton showWorldTiles = new TextButton("Show WorldTiles", game.skin, "default");
        showWorldTiles.setWidth(150);
        showWorldTiles.setHeight(25);
        showWorldTiles.setPosition(20, 20);
        showWorldTiles.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new TileArrayTestScreen(game, parent, TileHandler.getTileHandler().getWorldTileArray()));
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        titleScreenStage.addActor(showWorldTiles);

        TextButton worldGenTest = new TextButton("Show WorldGen", game.skin, "default");
        worldGenTest.setWidth(150);
        worldGenTest.setHeight(25);
        worldGenTest.setPosition(20, 55);
        worldGenTest.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new WorldGenTestScreen(game, parent));
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        titleScreenStage.addActor(worldGenTest);

        TextButton npcTestScreen = new TextButton("Show NPCTest", game.skin, "default");
        npcTestScreen.setWidth(150);
        npcTestScreen.setHeight(25);
        npcTestScreen.setPosition(20, 90);
        npcTestScreen.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new NPCTestScreen(game, parent));
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        titleScreenStage.addActor(npcTestScreen);
    }

    @Override
    public void show() {
        Gdx.app.log("TitleScreen", "loaded");
        Gdx.input.setInputProcessor(titleScreenStage);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 1, 1, 1);

        camera.position.set(0, 0, 0);
        camera.zoom = 0;

        titleScreenStage.act();
        titleScreenStage.draw();
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

    }


}
