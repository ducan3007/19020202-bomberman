package Bomberman.Entity.Tiles;

import Bomberman.Animations.Sprite;
import Bomberman.Entity.StaticEntity;

public class FlamePowerup extends Tile implements StaticEntity {
    public FlamePowerup(int x, int y){
        super(x,y);
        super.setSprite(new Sprite(this,16,0,143,225,1,width,height,getScale()));
    }
}
