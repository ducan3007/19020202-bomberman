
package Bomberman.Entity;

import Bomberman.GlobalVariables.Direction;

public abstract class MovingEntity extends Entity {
    abstract public void die();
    abstract public void move(int steps, Direction direction);
}
