
package Bomberman.Entity;

import Bomberman.Animations.Sprite;
import Bomberman.Entity.Boundedbox.RectBoundedBox;
import Bomberman.GlobalVariables.GlobalVariables;

public abstract class Entity {

    public int positionX;
    public int positionY;
    public int layer;
    public int width;
    public int height;
    public double scale;
    public RectBoundedBox boundedBox;
    public Sprite sprite;

    abstract public boolean isCollideEntity(Entity b);

    abstract public boolean isCollidePlayer();

    abstract public void render();

    abstract public boolean remove();

    public void setOffset() {
        this.positionX -= GlobalVariables.offSet;
        this.boundedBox.removeRect();
        this.boundedBox.setOffset();
        this.boundedBox.setBoundary();
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
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
