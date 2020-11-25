package Bomberman.Entity.Tiles;

import Bomberman.Renderer;
import Bomberman.Animations.Sprite;
import Bomberman.GlobalVariables.GlobalVariables;
import Bomberman.Entity.Entity;
import Bomberman.Entity.StaticEntity;
import Bomberman.Entity.Boundedbox.RectBoundedBox;

public class Tile implements StaticEntity {
    int positionX;
    int positionY;
    int width;
    int height;
    int layer;

    double scale;
    boolean remove = false;

    RectBoundedBox boundedBox;
    Sprite sprite;

    Tile() {

    }

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

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setOffset() {
        this.positionX -= GlobalVariables.offSet;
        this.boundedBox.removeRect();
        this.boundedBox.setOffset();
        this.boundedBox.setBoundary();
    }

    public RectBoundedBox getBoundingBox() {
        return boundedBox;
    }

    public int getLayer() {
        return layer;
    }

    public double getScale() {
        return scale;
    }

}
