
package Bomberman.Entity;

import Bomberman.Entity.Boundedbox.RectBoundedBox;

public interface Entity {
    boolean isColliding(Entity b);

    boolean isPlayerCollisionFriendly();

    void draw();

    int getPositionX();

    int getPositionY();

    void setOffset();

    boolean remove();

    RectBoundedBox getBoundingBox();

    int getLayer();

    double getScale();
}
