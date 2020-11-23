package Bomberman;

import Bomberman.GlobalVariables.GlobalVariables;
import Bomberman.Entity.Entity;
import Bomberman.Entity.Player.Player;
import Bomberman.Entity.StaticObjects.BlackBomb;
import Bomberman.Entity.StaticObjects.Flame;
import Bomberman.Gamecontroller.InputManager;
import Bomberman.Scene.Sandbox;

import java.util.Vector;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;

public class GameLoop {

    static double currentGameTime;
    static double oldGameTime;
    static double deltaTime;
    final static long startNanoTime = System.nanoTime();

    public static double getCurrentGameTime() {
        return currentGameTime;
    }

    public static void start(GraphicsContext gc) {
        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                oldGameTime = currentGameTime;
                currentGameTime = (currentNanoTime - startNanoTime) / (1000000000.0);
                deltaTime = currentGameTime - oldGameTime;
                gc.clearRect(0, 0, GlobalVariables.CANVAS_WIDTH, GlobalVariables.CANVAS_WIDTH);
                updateGame();
                renderGame();
            }
        }.start();
    }

    public static void updateGame() {
        InputManager.handlePlayerMovements();
        Vector<Entity> entities = Sandbox.getEntities();
        Player player = Sandbox.getPlayer();
        if (GlobalVariables.NewGame) {
            Sandbox.NewGame();
            GlobalVariables.NewGame = false;
            GlobalVariables.passLevel = false;
        }
        for (int i = 0; i < entities.size(); ++i) {
            if (GlobalVariables.CameraMoving) {
                entities.elementAt(i).setOffset();
            }
            try {
                if (!(entities.elementAt(i) instanceof BlackBomb) && entities.elementAt(i).remove()) {
                    entities.remove(i);
                }
                if (entities.elementAt(i) instanceof BlackBomb) {
                    if (entities.elementAt(i).remove()) {
                        entities.remove(i);
                        player.incrementBombCount();
                    }
                }
            } catch (ArrayIndexOutOfBoundsException e) {
            }
        }

    }

    public static void renderGame() {
        Vector<Entity> entities = Sandbox.getEntities();
        for (int i = 0; i < entities.size(); ++i) {
            Entity e = entities.elementAt(i);
            if (e instanceof Flame) {
                if (((Flame) e).getFlameState()){
                    e.draw();
                }
            } else {
                e.draw();
            }
        }
    }

}
