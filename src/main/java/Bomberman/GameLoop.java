package Bomberman;

import Bomberman.GlobalVariables.GlobalVariables;
import Bomberman.Entity.Entity;
import Bomberman.Entity.Player.Player;
import Bomberman.Entity.BombnFlame.BlackBomb;
import Bomberman.Entity.BombnFlame.Flame;
import Bomberman.Gamecontroller.InputManager;
import Bomberman.Scene.Board;

import java.util.Vector;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;

public class GameLoop {

    static double currentTime;
    static double prevTime;
    final static long startTime = System.nanoTime();

    public static double getcurrentTime() {
        return currentTime;
    }

    public static void start(GraphicsContext gc) {
        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                prevTime = currentTime;
                currentTime = (currentNanoTime - startTime) / (1000000000.0);
                gc.clearRect(0, 0, GlobalVariables.CANVAS_WIDTH, GlobalVariables.CANVAS_WIDTH);
                updateGame();
                renderGame();
            }
        }.start();
    }

    public static void updateGame() {
        InputManager.handlePlayerMovements();
        Vector<Entity> entities = Board.getEntities();
        Player player = Board.getPlayer();
        if (GlobalVariables.NewGame) {
            Board.NewGame();
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
        Vector<Entity> entities = Board.getEntities();
        for (int i = 0; i < entities.size(); ++i) {
            Entity e = entities.elementAt(i);
            if (e instanceof Flame) {
                if (((Flame) e).getFlameState()){
                    e.render();
                }
            } else {
                e.render();
            }
        }
    }

}
