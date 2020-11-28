package Bomberman.Entity.Tiles;

import Bomberman.Animations.Sprite;

public class Portal extends Tile {
    public Portal(int x, int y){
        super(x,y);
        sprite = new Sprite(this,16,0,0,207,1,width,height,getScale());
    }
    public boolean isCollidePlayer() {
        return true;
    }
}
