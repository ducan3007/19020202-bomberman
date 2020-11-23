
package Bomberman.Animations;

import Bomberman.Renderer;
import Bomberman.GlobalVariables.GlobalVariables;
import Bomberman.Entity.Entity;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;


public class BombAnimations {
    Sprite blackBomb;
    Sprite grass;
    double playSpeed;

    public Sprite getBlackBomb() {
        return blackBomb;
    }
    public Sprite getGrass(){ return grass;}
    public void setBlackBomb(Sprite blackBomb) {
        this.blackBomb = blackBomb;
    }

    public BombAnimations(Entity e) {
        Image img = Renderer.getSpriteSheet();
        playSpeed=0.2;
        List<Rectangle> bomb=new ArrayList<>();
        bomb.add(new Rectangle(181, 94, 16, 15));
        bomb.add(new Rectangle(211, 93, 15, 16));
        bomb.add(new Rectangle(241, 93, 16, 16));
        grass = new Sprite(e, 16, 0, 0, 245, 1,  16 , 16 ,2.0);
        blackBomb = new Sprite(e,30,playSpeed,img, bomb, GlobalVariables.PLAYER_WIDTH+2, GlobalVariables.PLAYER_HEIGHT+2, e.getScale());
    }
}
