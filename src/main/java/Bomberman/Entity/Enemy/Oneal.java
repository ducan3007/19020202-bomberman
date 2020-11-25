package Bomberman.Entity.Enemy;

import Bomberman.Renderer;
import Bomberman.Animations.OnealAnimations;
import Bomberman.GlobalVariables.Direction;
import Bomberman.Entity.KillableEntity;
import Bomberman.Entity.MovingEntity;
import Bomberman.Scene.Board;

import java.util.Date;

public class Oneal extends Balloom implements MovingEntity, KillableEntity {
    OnealAnimations onealAnimations;

    public Oneal(int x, int y) {
        super.init(x, y);
        onealAnimations = new OnealAnimations(this, scale);
        sprite = onealAnimations.getIdle();
        step = 4;
    }

    public void render() {
        if (sprite != null && isAlive()) {
            Renderer.playAnimation(sprite);
        }
        if (!isAlive()) {
            Renderer.playAnimation(onealAnimations.getDie());
            if (new Date().getTime() > (600 + dieTime.getTime())) {
                disappear = true;
            }
        }
    }

    @Override
    public void RandomMoving() {
        int direction;
        int x = Board.getPlayer().getPositionX();
        int y = Board.getPlayer().getPositionY();
        if ((positionX % 48 == 0 && positionY % 48 == 0) || ((positionY + 2) % 48 == 0) ||
                ((positionX + 8) % 48 == 0) || ((positionX - 8) % 48 == 0) || ((positionY - 8) % 48 == 0)) {
            direction = random.nextInt(4);
            dir = direction;
            if (Math.abs(this.positionY - y) <= 8) {
                if (this.positionX > x)
                    dir = 2;
                else
                    dir = 3;
                if (checkCollision) {
                    dir = direction;
                }
            }
            if (Math.abs(this.positionX - x) <= 8) {
                if (this.positionY > y)
                    dir = 0;
                else
                    dir = 1;
                if (checkCollision) {
                    dir = direction;
                }
            }
        }

        switch (dir) {
            case 0:
                move(step, Direction.UP);
                break;
            case 1:
                move(step, Direction.DOWN);
                break;
            case 2:
                move(step, Direction.LEFT);
                break;
            case 3:
                move(step, Direction.RIGHT);
                break;
        }
    }

    public void move(int steps, Direction direction) {
        if (isAlive) {
            if (steps == 0) {
                setCurrentSprite(onealAnimations.getIdle());
            } else {
                switch (direction) {
                    case UP:
                        if (!checkCollisions(positionX, positionY - steps)) {
                            positionY -= steps;
                            setCurrentSprite(onealAnimations.getOneal());
                            currentDirection = Direction.UP;
                        }
                        break;
                    case DOWN:
                        if (!checkCollisions(positionX, positionY + steps)) {
                            positionY += steps;
                            setCurrentSprite(onealAnimations.getOneal());
                            currentDirection = Direction.DOWN;
                        }
                        break;
                    case LEFT:
                        if (!checkCollisions(positionX - steps, positionY)) {
                            positionX -= steps;
                            setCurrentSprite(onealAnimations.getOneal());
                            currentDirection = Direction.LEFT;
                        }
                        break;
                    case RIGHT:
                        if (!checkCollisions(positionX + steps, positionY)) {
                            positionX += steps;
                            setCurrentSprite(onealAnimations.getOneal());
                            currentDirection = Direction.RIGHT;
                        }
                        break;
                    default:
                        setCurrentSprite(onealAnimations.getIdle());
                }
            }
        }
    }
}
