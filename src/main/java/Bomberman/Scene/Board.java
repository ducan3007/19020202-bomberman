
package Bomberman.Scene;

import Bomberman.Entity.Tiles.Tile;
import Bomberman.GameLoop;
import Bomberman.Entity.Enemy.Balloom;
import Bomberman.Entity.Enemy.Oneal;
import Bomberman.Entity.Entity;
import Bomberman.Entity.Player.Player;
import Bomberman.Entity.BombnFlame.BlackBomb;
import Bomberman.Entity.Tiles.Brick;
import Bomberman.Entity.Tiles.Grass;
import Bomberman.Entity.Tiles.Wall;
import Bomberman.Gamecontroller.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import static Bomberman.GlobalVariables.GlobalVariables.*;

public class Board {

    static Scene scene;
    static Group root;
    static Canvas canvas;
    static GraphicsContext gc;
    static boolean sceneStarted;
    static Player player;

    public static int enemy;

    static {
        sceneStarted = false;
    }

    static Vector<Balloom> ballooms = new Vector<>();
    static Vector<Entity> entities = new Vector<>();
    static Vector<Tile> tiles = new Vector<>();

    public static Vector<Entity> getEntities() {
        return entities;
    }

    static Comparator<Entity> layerComparator = (o1, o2) -> {
        return Integer.compare(o1.getLayer(), o2.getLayer());
    };

    public static boolean addEntityToGame(Entity e) {
        if (!entities.contains(e)) {
            entities.add(e);
            Collections.sort(entities, layerComparator);
            return true;
        } else {
            return false;
        }
    }

    private static void init() {
        root = new Group();
        scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
        canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
        root.getChildren().add(canvas);
        gc = canvas.getGraphicsContext2D();
        gc.fillRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
        GameLoop.start(gc);

        //load map
        try {
            loadMap();
        } catch (IOException e) {
            System.err.println("Unable to load map");
        }

        EventHandler.attachEventHandlers(scene);

    }

    public static void loadMap() throws IOException {
        String path = "Rescourses/maps/Level" + Integer.toString(Level) + ".txt";
        try (BufferedReader inputStream = new BufferedReader(new FileReader(path))) {
            String line;
            int y = 0;
            while ((line = inputStream.readLine()) != null) {
                line += "c";
                for (int x = 0; x < line.length(); x++) {
                    tiles.add(new Grass(x * CELL_SIZE, y * CELL_SIZE));
                    switch (line.charAt(x)) {
                        case '#':
                            tiles.add(new Wall(x * CELL_SIZE, y * CELL_SIZE));
                            break;
                        case 'p':
                            setPlayer(new Player(x * CELL_SIZE, y * CELL_SIZE));
                            break;
                        case '*':
                            tiles.add(new Brick(x * CELL_SIZE, y * CELL_SIZE, -1));
                            break;
                        case '1':
                            ballooms.add(new Balloom(x * CELL_SIZE, y * CELL_SIZE));
                            break;
                        case '2':
                            ballooms.add(new Oneal(x * CELL_SIZE, y * CELL_SIZE));
                            break;
                        case 'x':
                            tiles.add(new Brick(x * CELL_SIZE, y * CELL_SIZE, 0));
                            break;
                        case 'f':
                            tiles.add(new Brick(x * CELL_SIZE, y * CELL_SIZE, 1));
                            break;
                        case 'b':
                            tiles.add(new Brick(x * CELL_SIZE, y * CELL_SIZE, 2));
                            break;
                        case 's':
                            tiles.add(new Brick(x * CELL_SIZE, y * CELL_SIZE, 3));
                            break;
                    }
                }
                y++;
            }
        }
        enemy = ballooms.size();
        for (Balloom balloom : ballooms) {
            addEntityToGame(balloom);
        }

        for (Tile tile : tiles) {
            addEntityToGame(tile);
        }

        System.gc();
    }

    public static void setupScene() {
        if (!sceneStarted) {
            init();
            sceneStarted = true;
        }
    }

    public static void NewGame() {
        entities.clear();
        ballooms.clear();
        tiles.clear();
        if (!passLevel) {
            Player.step = 4;
            Player.bombCount = 1;
            BlackBomb.radius = 1;
        }
        try {
            loadMap();
        } catch (IOException e) {
            System.err.println("Unable to load map file.");
            System.exit(1);
        }
    }

    public static Scene getScene() {
        return scene;
    }

    public static GraphicsContext getGraphicsContext() {
        return gc;
    }

    public static Vector<Balloom> getBallooms() {
        return ballooms;
    }

    public static void setPlayer(Player p) {
        player = p;
        addEntityToGame(player);
    }

    public static Player getPlayer() {
        return player;
    }
}
