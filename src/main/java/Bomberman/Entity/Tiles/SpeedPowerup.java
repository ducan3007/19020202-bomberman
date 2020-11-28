package Bomberman.Entity.Tiles;

import Bomberman.Animations.Sprite;

public class SpeedPowerup extends Tile {
    public SpeedPowerup(int x, int y){
        super(x,y);
        sprite = new Sprite(this,16,0,178,225,1,width,height,getScale());
    }

}
