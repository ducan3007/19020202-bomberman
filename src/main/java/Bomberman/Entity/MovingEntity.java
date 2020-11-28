
package Bomberman.Entity;

import Bomberman.GlobalVariables.Direction;

public abstract class MovingEntity extends KillableEntity {
    abstract public void move(int steps, Direction direction);
}
