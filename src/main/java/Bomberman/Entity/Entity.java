
package Bomberman.Entity;

import Bomberman.Entity.Boundedbox.RectBoundedBox;

public interface Entity {

    boolean isCollideEntity(Entity b);

    boolean isCollidePlayer();

    void render();

    int getPositionX();

    int getPositionY();

    void setOffset();

    boolean remove();

    RectBoundedBox getBoundingBox();

    int getLayer();

    double getScale();
}
