package Bomberman.Entity.Enemy;

import Bomberman.Renderer;
import Bomberman.Animations.BalloomAnimations;
import Bomberman.Animations.Sprite;
import Bomberman.GlobalVariables.Direction;
import Bomberman.GlobalVariables.GlobalVariables;
import Bomberman.Entity.Entity;
import Bomberman.Entity.KillableEntity;
import Bomberman.Entity.MovingEntity;
import Bomberman.Entity.Boundedbox.RectBoundedBox;
import Bomberman.Entity.StaticObjects.BlackBomb;
import Bomberman.Entity.StaticObjects.Flame;
import Bomberman.Scene.Sandbox;

import java.util.Date;
import java.util.Random;

public class Balloom implements MovingEntity, KillableEntity {

    int positionX = 0;
    int positionY = 0;
    int layer;
    int dir = 0;
    boolean isAlive = true;
    boolean disappear = false;
    boolean checkCollision = false;
    public int step;
    double scale;
    Random random = new Random();
    Date dieTime;
    RectBoundedBox boundedBox;
    Sprite sprite;
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

    @Override
    public void setOffset() {
        this.positionX -= GlobalVariables.offSet;
        this.boundedBox.setOffset();
    }

    @Override
    public boolean isColliding(Entity b) {
        RectBoundedBox temp = b.getBoundingBox();
        return boundedBox.checkCollision(temp);
    }

    public void draw() {
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
                ((positionX+8) % 48 == 0) || ((positionX-8) % 48 == 0) ) {
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
        Sandbox.enemy--;
    }

    public boolean checkCollisions(int x, int y) {
        boundedBox.setEnmeyPosition(x, y);
        for (Entity e : Sandbox.getEntities()) {
            if (e != this && isColliding(e) && e instanceof BlackBomb) {
                boundedBox.setEnmeyPosition(positionX, positionY);
                return true;
            }
            if (!(e instanceof Balloom) && isColliding(e) && !e.isPlayerCollisionFriendly()) {
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
            for (Entity e : Sandbox.getEntities()) {
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

    @Override
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


    public RectBoundedBox getBoundingBox() {
        boundedBox.setPosition(positionX, positionY);
        return boundedBox;
    }

    @Override
    public boolean isPlayerCollisionFriendly() {
        return false;
    }

    public int getLayer() {
        return layer;
    }

    public double getScale() {
        return scale;
    }

    @Override
    public int getPositionX() {
        return positionX;
    }

    @Override
    public int getPositionY() {
        return positionY;
    }
}
