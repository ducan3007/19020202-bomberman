
package Bomberman.Entity;

import Bomberman.GlobalVariables.Direction;

public interface MovingEntity extends Entity {
    void move(int steps, Direction direction);

}
