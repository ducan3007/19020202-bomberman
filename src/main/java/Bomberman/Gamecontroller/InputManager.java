package Bomberman.Gamecontroller;

import java.util.List;

import Bomberman.GlobalVariables.Direction;
import Bomberman.GlobalVariables.GlobalVariables;
import Bomberman.Entity.Enemy.Balloom;
import Bomberman.Entity.Player.Player;
import Bomberman.Entity.BombnFlame.BlackBomb;
import Bomberman.Scene.Board;

import java.util.Vector;

import javafx.scene.input.KeyCode;

public class InputManager {

    public static void handlePlayerMovements() {

        List keyInputs = EventHandler.getInputList();
        Player player = Board.getPlayer();
        Vector<Balloom> balloom = Board.getBallooms();

        for (int i = 0; i < balloom.size(); i++) {
            balloom.elementAt(i).RandomMoving();
        }

        if (keyInputs.contains(KeyCode.UP)) {
            player.move(player.step, Direction.UP);

        }
        if (keyInputs.contains(KeyCode.DOWN)) {
            player.move(player.step, Direction.DOWN);

        }
        if (keyInputs.contains(KeyCode.LEFT)) {
            if (player.updatePosition()) {
                GlobalVariables.CameraMoving = true;
            }
            player.move(player.step, Direction.LEFT);

        }
        if (keyInputs.contains(KeyCode.RIGHT)) {
            if (player.updatePosition()) {
                GlobalVariables.CameraMoving = true;
            }
            player.move(player.step, Direction.RIGHT);

        }

        if (!keyInputs.contains(KeyCode.RIGHT) &&
                !keyInputs.contains(KeyCode.DOWN) &&
                !keyInputs.contains(KeyCode.LEFT) &&
                !keyInputs.contains(KeyCode.UP)
        ) {
            GlobalVariables.CameraMoving = false;
            player.move(0, Direction.UP);
        }

        if (keyInputs.contains(KeyCode.SPACE)) {
            if (player.hasMoreBombs()) {
                double temp = 48.0;
                int a = player.getPositionX();
                int b = player.getPositionY();
                double c = Math.round((double) a / temp);
                double e = Math.round((double) b / temp);
                Board.addEntityToGame(new BlackBomb((int) c * 48 + 8, (int) e * 48 + 8));
                player.decrementBombCount();
                keyInputs.remove(KeyCode.SPACE);
            }
        }
    }

}
