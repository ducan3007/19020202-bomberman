package Bomberman.Entity.Tiles;

import Bomberman.Animations.Sprite;
import Bomberman.Entity.StaticEntity;

public class SpeedPowerup extends Tile implements StaticEntity {
    public SpeedPowerup(int x, int y){
        super(x,y);
        sprite = new Sprite(this,16,0,178,225,1,width,height,getScale());
    }

}
