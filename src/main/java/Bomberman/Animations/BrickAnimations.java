package Bomberman.Animations;

import Bomberman.Renderer;
import Bomberman.Entity.Entity;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class BrickAnimations {
    Sprite Brickanimation;
    double playspeed;
    public Sprite getBrickAnimation(){
        return Brickanimation;
    }
    public void setBrickanimation(Sprite sprite){this.Brickanimation = sprite;}
    public BrickAnimations(Entity e){
        Image img = Renderer.getSpriteSheet();
        playspeed = 0.15;
        List<Rectangle> destroyed = new ArrayList<>();
        destroyed.add(new Rectangle(35,225,15,15));
        destroyed.add(new Rectangle(53,225,15,15));
        destroyed.add(new Rectangle(71,225,15,15));
        destroyed.add(new Rectangle(89,225,15,15));
        destroyed.add(new Rectangle(107,225,15,15));
        destroyed.add(new Rectangle(125,225,15,15));
        Brickanimation = new Sprite(e,16,playspeed,img,destroyed,16,16,e.getScale());
    }

}
