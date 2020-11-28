package Bomberman.Entity.Enemy;

import Bomberman.Renderer;
import Bomberman.Animations.BalloomAnimations;
import Bomberman.Animations.Sprite;
import Bomberman.GlobalVariables.Direction;
import Bomberman.GlobalVariables.GlobalVariables;
import Bomberman.Entity.Entity;
import Bomberman.Entity.KillableEntity;
import Bomberman.Entity.Boundedbox.RectBoundedBox;
import Bomberman.Entity.BombnFlame.BlackBomb;
import Bomberman.Entity.BombnFlame.Flame;
import Bomberman.Scene.Board;

import java.util.Date;
import java.util.Random;

public class Balloom extends MovingEntity {

    int dir = 0;
    boolean isAlive = true;
    boolean disappear = false;
    boolean checkCollision = false;
    public int step;

    Random random = new Random();
    Date dieTime;
    BalloomAnimations balloomAnimations;
    Direction currentDirection;

    public Balloom() {
    }

    public Balloom(int x, int y) {
        init(x, y);
        balloomAnimations = new BalloomAnimations(this, scale);
        sprite = balloomAnimations.getIdle();
        step = 2;
    }

    public void init(int x, int y) {
        layer = 0;
        scale = 2.8;
        positionX = x;
        positionY = y;
        boundedBox = new RectBoundedBox(positionX + 18, positionY + 18, 48, 48);
    }

    public void setCurrentSprite(Sprite s) {
        if (s != null) {
            sprite = s;
        }
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setOffset() {
        this.positionX -= GlobalVariables.offSet;
        this.boundedBox.setOffset();
    }

    public boolean isCollideEntity(Entity b) {
        RectBoundedBox temp = b.getBoundingBox();
        return boundedBox.checkCollision(temp);
    }

    public void render() {
        if (sprite != null && isAlive()) {
            Renderer.playAnimation(sprite);
        }
        if (!isAlive()) {
            Renderer.playAnimation(balloomAnimations.getDie());
            if (new Date().getTime() > (600 + dieTime.getTime())) {
                disappear = true;
            }
        }
    }

    public void RandomMoving() {
        int direction;
        if ((positionX % 48 == 0 && positionY % 48 == 0) || ((positionY + 2) % 48 == 0) || ((positionX + 2) % 48 == 0) ||
                ((positionX + 8) % 48 == 0) || ((positionX - 8) % 48 == 0)) {
            direction = random.nextInt(4);
            dir = direction;
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

    public void die() {
        isAlive = false;
        dieTime = new Date();
        Board.enemy--;
    }

    public boolean checkCollisions(int x, int y) {
        boundedBox.setEnmeyPosition(x, y);
        for (Entity e : Board.getEntities()) {
            if (e != this && isCollideEntity(e) && e instanceof BlackBomb) {
                boundedBox.setEnmeyPosition(positionX, positionY);
                return true;
            }
            if (!(e instanceof Balloom) && isCollideEntity(e) && !e.isCollidePlayer()) {
                checkCollision = true;
                boundedBox.setEnmeyPosition(positionX, positionY);
                return true;
            }

        }
        checkCollision = false;
        boundedBox.setEnmeyPosition(positionX, positionY);
        return false;
    }

    public boolean remove() {
        if (isAlive) {
            for (Entity e : Board.getEntities()) {
                if (e instanceof Flame && ((Flame) e).getFlameState()) {
                    if (Math.abs(this.positionX - ((Flame) e).getPositionX()) < 40 && Math.abs(this.positionY - ((Flame) e).getPositionY()) < 40) {
                        die();
                        break;
                    }
                }
            }
        }
        return disappear;
    }

    public void move(int steps, Direction direction) {
        if (isAlive) {
            if (steps == 0) {
                setCurrentSprite(balloomAnimations.getIdle());
                return;
            } else {
                switch (direction) {
                    case UP:
                        if (!checkCollisions(positionX, positionY - steps)) {
                            positionY -= steps;
                            setCurrentSprite(balloomAnimations.getBallom());
                            currentDirection = Direction.UP;
                        }
                        break;
                    case DOWN:
                        if (!checkCollisions(positionX, positionY + steps)) {
                            positionY += steps;
                            setCurrentSprite(balloomAnimations.getBallom());
                            currentDirection = Direction.DOWN;
                        }
                        break;
                    case LEFT:
                        if (!checkCollisions(positionX - steps, positionY)) {
                            positionX -= steps;
                            setCurrentSprite(balloomAnimations.getBallom());
                            currentDirection = Direction.LEFT;
                        }
                        break;
                    case RIGHT:
                        if (!checkCollisions(positionX + steps, positionY)) {
                            positionX += steps;
                            setCurrentSprite(balloomAnimations.getBallom());
                            currentDirection = Direction.RIGHT;
                        }
                        break;
                    default:
                        setCurrentSprite(balloomAnimations.getIdle());
                }
            }
        }
    }

    @Override
    public RectBoundedBox getBoundingBox() {
        boundedBox.setPosition(positionX, positionY);
        return boundedBox;
    }

    public boolean isCollidePlayer() {
        return false;
    }
}
