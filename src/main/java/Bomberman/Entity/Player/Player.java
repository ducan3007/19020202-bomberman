package Bomberman.Entity.Player;

import Bomberman.Renderer;
import Bomberman.Animations.PlayerAnimations;
import Bomberman.Animations.Sprite;
import Bomberman.GlobalVariables.Direction;
import Bomberman.GlobalVariables.GlobalVariables;
import Bomberman.Entity.Enemy.Balloom;
import Bomberman.Entity.Entity;
import Bomberman.Entity.Tiles.BombPowerup;
import Bomberman.Entity.Tiles.FlamePowerup;
import Bomberman.Entity.Tiles.Portal;
import Bomberman.Entity.Tiles.SpeedPowerup;
import Bomberman.Entity.KillableEntity;
import Bomberman.Entity.MovingEntity;
import Bomberman.Entity.Boundedbox.RectBoundedBox;
import Bomberman.Entity.BombnFlame.BlackBomb;
import Bomberman.Entity.BombnFlame.Flame;
import Bomberman.Scene.Board;

import java.util.Date;

import static Bomberman.GlobalVariables.GlobalVariables.Level;
import static Bomberman.GlobalVariables.GlobalVariables.passLevel;

public class Player implements MovingEntity, KillableEntity {

    public static int step = 4;
    public static int bombCount = 1;

    boolean isAlive = true;
    boolean disappear = false;

    int positionX;
    int positionY;
    int layer;

    double scale;

    Date dieTime;
    Sprite currentSprite;
    Direction currentDirection;
    RectBoundedBox playerBoundary;
    PlayerAnimations playerAnimations;

    public Player() {
        init(64, 64);
    }

    public Player(int posX, int posY) {
        init(posX, posY);
        layer = 0;
    }

    private void init(int x, int y) {
        playerAnimations = new PlayerAnimations(this, 2.2);
        positionX = x;
        positionY = y;
        setScale(2);
        playerBoundary = new RectBoundedBox(positionX + (int) (GlobalVariables.PLAYER_WIDTH),
                positionY + (int) (GlobalVariables.PLAYER_WIDTH),
                (int) (GlobalVariables.PLAYER_WIDTH * (getScale() - 0.6)),
                (int) (GlobalVariables.PLAYER_HEIGHT * (getScale() - 0.8))
        );
        currentSprite = playerAnimations.getPlayerIdleSprite();
    }


    private void setCurrentSprite(Sprite s) {
        if (s != null) {
            currentSprite = s;
        }
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setOffset() {
        this.positionX -= GlobalVariables.offSet;
        this.playerBoundary.setOffset();
    }

    @Override
    public boolean isCollideEntity(Entity b) {
        RectBoundedBox otherEntityBoundary = b.getBoundingBox();
        return playerBoundary.checkCollision(otherEntityBoundary);
    }

    @Override
    public void render() {
        if (currentSprite != null && isAlive()) {
            Renderer.playAnimation(currentSprite);
        }
        if (!isAlive()) {
            Renderer.playAnimation(playerAnimations.getPlayerDying());
            if (new Date().getTime() > (570 + dieTime.getTime())) {
                disappear = true;
                GlobalVariables.NewGame = true;
            }
        }
    }

    @Override
    public void die() {
        isAlive = false;
        dieTime = new Date();
    }

    private boolean checkCollisions(int x, int y) {
        playerBoundary.setPosition(x, y);
        for (Entity e : Board.getEntities()) {
            if (e instanceof Portal && isCollideEntity(e) && Board.enemy == 0) {
                Level = (Level % 4) + 1;
                GlobalVariables.NewGame = true;
                passLevel = true;
            } else {
                if (e instanceof FlamePowerup && isCollideEntity(e)) {
                    BlackBomb.radius++;
                    ((FlamePowerup) e).checkCollision(true);
                }
                if (e instanceof BombPowerup && isCollideEntity(e)) {
                    bombCount++;
                    ((BombPowerup) e).checkCollision(true);
                }
                if (e instanceof SpeedPowerup && isCollideEntity(e)) {
                    step++;
                    ((SpeedPowerup) e).checkCollision(true);
                }
                if (e instanceof BlackBomb) {
                    boolean bol1 = Math.abs(this.getPositionY() - e.getPositionY()) < 42;
                    boolean bol2 = Math.abs(this.getPositionX() - e.getPositionX()) < 42;
                    if (bol1 && bol2 && ((BlackBomb) e).CollidedPlayer == false && e.isCollidePlayer() == true) {
                        ((BlackBomb) e).CollidedPlayer = true;
                    }
                    if (!bol1 || !bol2 && ((BlackBomb) e).CollidedPlayer == true) {
                        ((BlackBomb) e).PlayerCollisionFriendly = false;
                    }
                }
                if (!(e instanceof Balloom) && e != this && isCollideEntity(e) && !e.isCollidePlayer()) {
                    playerBoundary.setPosition(positionX, positionY);
                    return true;
                }
            }
        }
        playerBoundary.setPosition(positionX, positionY);
        return false;
    }

    public boolean remove() {
        if (isAlive) {
            for (Entity e : Board.getEntities()) {
                if ((e instanceof Flame && ((Flame) e).getFlameState()) || e instanceof Balloom) {
                    if (isCollideEntity(e)) {
                        die();
                        break;
                    }
                }
            }
        }
        return disappear;
    }

    public boolean updatePosition() {
        if (getPositionX() - 47 < 0 && currentDirection == Direction.LEFT) {
            GlobalVariables.offSet = -96;
            return true;
        }
        if (getPositionX() - 864 > 0 && currentDirection == Direction.RIGHT) {
            GlobalVariables.offSet = 96;
            return true;
        } else {
            GlobalVariables.CameraMoving = false;
        }
        return false;
    }

    @Override
    public void move(int steps, Direction direction) {
        if (isAlive) {
            if (steps == 0) {
                setCurrentSprite(playerAnimations.getPlayerIdleSprite());
                GlobalVariables.CameraMoving = false;
                return;
            } else {
                switch (direction) {
                    case UP:
                        if (!checkCollisions(positionX, positionY - steps)) {
                            positionY -= steps;
                            setCurrentSprite(playerAnimations.getMoveUpSprite());
                            currentDirection = Direction.UP;
                        }
                        break;
                    case DOWN:
                        if (!checkCollisions(positionX, positionY + steps)) {
                            positionY += steps;
                            setCurrentSprite(playerAnimations.getMoveDownSprite());
                            currentDirection = Direction.DOWN;
                        }
                        break;
                    case LEFT:
                        if (!checkCollisions(positionX - steps, positionY)) {
                            positionX -= steps;
                            setCurrentSprite(playerAnimations.getMoveLeftSprite());
                            currentDirection = Direction.LEFT;
                        }
                        break;
                    case RIGHT:
                        if (!checkCollisions(positionX + steps, positionY)) {
                            positionX += steps;
                            setCurrentSprite(playerAnimations.getMoveRightSprite());
                            currentDirection = Direction.RIGHT;
                        }
                        break;
                    default:
                        setCurrentSprite(playerAnimations.getPlayerIdleSprite());
                }
            }
        }
    }

    @Override
    public int getPositionX() {
        return positionX;
    }

    @Override
    public int getPositionY() {
        return positionY;
    }

    @Override
    public RectBoundedBox getBoundingBox() {
        playerBoundary.setPosition(positionX, positionY);
        return playerBoundary;
    }

    @Override
    public boolean isCollidePlayer() {
        return true;
    }

    @Override
    public int getLayer() {
        return layer;
    }

    @Override
    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public boolean hasMoreBombs() {
        return bombCount > 0;
    }

    public void incrementBombCount() {
        bombCount++;
    }

    public void decrementBombCount() {
        bombCount--;
    }
}
