package Bomberman.Entity.Tiles;

import Bomberman.Animations.Sprite;
import Bomberman.Entity.StaticEntity;

public class Portal extends Tile implements StaticEntity {
    public Portal(int x, int y){
        super(x,y);
        sprite = new Sprite(this,16,0,0,207,1,width,height,getScale());
    }
    public boolean isCollidePlayer() {
        return true;
    }
}
