package com.bmhs.gametitle.gfx.assets.screens;


import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


import com.bmhs.gametitle.game.assets.characters.PlayerCharacter;
import com.bmhs.gametitle.game.utils.GameHandler;
import com.bmhs.gametitle.game.assets.worlds.World;
import com.bmhs.gametitle.gfx.assets.tiles.Tile;
import com.bmhs.gametitle.gfx.utils.TileHandler;

public class WorldGenTestScreen implements Screen {

    private GameHandler game;
    private Screen parent;

    private Stage stage;

    private OrthographicCamera camera;
    private Viewport viewport;
    private float effectiveViewportWidth, effectiveViewportHeight;

    private World world;

    private PlayerCharacter player;

    public WorldGenTestScreen (final GameHandler game, final Screen parent) {
        this.game = game;
        this.parent = parent;

        stage = new Stage();
        createScreenLabel();

        camera = new OrthographicCamera();
        camera.position.set(0, 0, 0);
        camera.zoom = 64;

        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        viewport.setScreenBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        world = new World(100, 300);

        camera.update();

        player = new PlayerCharacter(TileHandler.getTileHandler().getWorldTileArray().get(2), 100, 200);
    }

    @Override
    public void show() {
        Gdx.app.error("WorldGenTestScreen", "loaded");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        viewport.apply();
        setCameraLimits();
        camera.update();

        checkInput();

        // use the variables below to control what is being rendered
        // without the variables below, there are tiles off-screen being drawn
        // use the variables to control the for loop below
        int rowStart = (int)((camera.position.y/32-effectiveViewportHeight/64)/2);
        int rowEnd = (int)(camera.position.y/64+(effectiveViewportHeight/64/2));
        int colStart = (int)((camera.position.x/32-effectiveViewportWidth/64)/2);
        int colEnd = (int)(camera.position.x/64+(effectiveViewportWidth/64/2));

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();

        for (int r = 0; r < world.getWorldMapRows(); r++) {
            for (int c = 0; c < world.getWorldMapColumns(); c++) {
                game.batch.draw(world.getWorldTileTextureRegion(r, c), c * Tile.ON_SCREEN_DEFAULT_WIDTH, r * Tile.ON_SCREEN_DEFAULT_HEIGHT);
            }
        }

        game.batch.draw(player.getTile().getTexture(), player.getX(), player.getY());


        game.batch.end();

        stage.act();
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
        stage.dispose();
    }

    private void checkInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            effectiveViewportWidth = camera.viewportWidth * camera.zoom;
            effectiveViewportHeight = camera.viewportHeight * camera.zoom;

            camera.position.x = MathUtils.clamp(camera.position.x, effectiveViewportWidth / 2.0f, world.getWorldMapColumns()*Tile.ON_SCREEN_DEFAULT_WIDTH - effectiveViewportWidth/2.0f);
            camera.position.y = MathUtils.clamp(camera.position.y, effectiveViewportHeight / 2.0f, world.getWorldMapRows()*Tile.ON_SCREEN_DEFAULT_HEIGHT - effectiveViewportHeight/2.0f);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            camera.translate(0, 20*camera.zoom, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            camera.translate(-20*camera.zoom, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            camera.translate(0, -20*camera.zoom, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            camera.translate(20*camera.zoom, 0, 0);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            camera.zoom += 0.2;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            camera.zoom -= 0.2;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            camera.position.set(player.getX(), player.getY(), 0);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            player.adjustY(50);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            player.adjustY(-50);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            player.adjustX(-50);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            player.adjustX(50);
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            game.setScreen(parent);
        }
    }

    private void setCameraLimits() {
        //camera.position.set(player.getX(), player.getY(), 0);

        camera.zoom = MathUtils.clamp(camera.zoom, 0.1f, ((float)world.getWorldMapRows()*(float)Tile.ON_SCREEN_DEFAULT_HEIGHT/(float)Gdx.graphics.getHeight()));

        // world.getWorldMapRows()*Tile.ON_SCREEN_DEFAULT_HEIGHT)/Gdx.graphics.getHeight()
        effectiveViewportWidth = camera.viewportWidth * camera.zoom;
        effectiveViewportHeight = camera.viewportHeight * camera.zoom;

        camera.position.x = MathUtils.clamp(camera.position.x, effectiveViewportWidth / 2.0f, world.getWorldMapColumns()*Tile.ON_SCREEN_DEFAULT_WIDTH - effectiveViewportWidth/2.0f);
        camera.position.y = MathUtils.clamp(camera.position.y, effectiveViewportHeight / 2.0f, world.getWorldMapRows()*Tile.ON_SCREEN_DEFAULT_HEIGHT - effectiveViewportHeight/2.0f);
    }

    private void createScreenLabel() {
        Label title = new Label("WorldGen Test Screen", game.skin);
        title.setAlignment(Align.left);
        title.setWidth(100);
        title.setHeight(25);
        title.setPosition(190, 20);
        stage.addActor(title);

    }
}
