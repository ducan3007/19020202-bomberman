package Bomberman.Entity.Tiles;

import Bomberman.Renderer;
import Bomberman.Animations.Sprite;
import Bomberman.Entity.Entity;
import Bomberman.Entity.Boundedbox.RectBoundedBox;

public class Tile extends Entity {
    boolean remove = false;

    Tile(int x, int y) {
        positionX = x;
        positionY = y;
        width = 16;
        height = 16;
        scale = 3.1;
        layer = 1;
        boundedBox = new RectBoundedBox(positionX, positionY, (int) (width * (getScale() + 0.9)), (int) (height * (getScale() + 0.9)));
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void checkCollision(boolean remove) {
        this.remove = remove;
    }

    public boolean remove() {
        return remove;
    }

    public void render() {
        Renderer.playAnimation(sprite);
    }

    public boolean isCollideEntity(Entity e) {
        RectBoundedBox rect = e.getBoundingBox();
        return boundedBox.checkCollision(rect);
    }

    public boolean isCollidePlayer() {
        return false;
    }

}
