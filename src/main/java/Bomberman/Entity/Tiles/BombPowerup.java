package Bomberman.Entity.Tiles;

import Bomberman.Animations.Sprite;

public class BombPowerup extends Tile {
    public BombPowerup(int x, int y){
        super(x,y);
        sprite = new Sprite(this,16,0,161,225,1,width,height,getScale());
    }

}
