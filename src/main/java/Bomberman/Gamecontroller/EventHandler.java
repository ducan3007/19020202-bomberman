package Bomberman.Gamecontroller;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class EventHandler {
    public static ArrayList<KeyCode> controllerKeyList = new ArrayList<KeyCode>();

    public static void attachEventHandlers(Scene scene){
        keyReleaseHanlder releasedKey = new keyReleaseHanlder();
        keyPressedHandler pressedKey = new keyPressedHandler();
        scene.setOnKeyReleased(releasedKey);
        scene.setOnKeyPressed(pressedKey);
    }
    public static List getInputList(){
        return controllerKeyList;
    }
}

class keyReleaseHanlder implements javafx.event.EventHandler<KeyEvent>{
    public keyReleaseHanlder() {
    }
    @Override
    public void handle(KeyEvent event) {
        KeyCode code = event.getCode();
        if ( EventHandler.controllerKeyList.contains(code) )
        	EventHandler.controllerKeyList.remove(code);
    }
}
class keyPressedHandler implements javafx.event.EventHandler<KeyEvent>{
    @Override
    public void handle(KeyEvent event) {
        KeyCode code = event.getCode();
        if (!EventHandler.controllerKeyList.contains(code))
        	EventHandler.controllerKeyList.add(code);
    }
}
