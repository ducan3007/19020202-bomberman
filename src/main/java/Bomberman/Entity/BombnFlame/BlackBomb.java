package Bomberman.Entity.BombnFlame;

import Bomberman.Renderer;
import Bomberman.Animations.BombAnimations;
import Bomberman.Animations.FlameAnimations;
import Bomberman.Animations.Sprite;
import Bomberman.GlobalVariables.GlobalVariables;
import Bomberman.Entity.Entity;
import Bomberman.Entity.StaticEntity;
import Bomberman.Entity.Boundedbox.RectBoundedBox;
import Bomberman.Scene.Board;

import java.util.Date;


public class BlackBomb implements StaticEntity {
    public static int radius = 1;
    public boolean CollidedPlayer = false;
    public boolean PlayerCollisionFriendly = true;

    int positionX;
    int positionY;
    int height;
    int width;
    int timerDurationInMillis = 2000;
    int layer;
    double scale;

    boolean exploded = false;
    boolean explodedbyFlame = false;

    Date plantedTime;
    Date explosionTime;
    STATE bombState;
    Sprite bombsprite;
    FlameAnimations[] Explosion;
    RectBoundedBox entityBoundary;
    BombAnimations bomb_animations;

    enum STATE {
        INACTIVE,
        ACTIVE,
        EXPLODED;
    }

    public BlackBomb(int x, int y) {
        positionX = x;
        positionY = y;
        width = 16;
        height = 16;
        layer = 1;
        setScale(1.8);
        bomb_animations = new BombAnimations(this);
        bombsprite = bomb_animations.getBlackBomb();
        entityBoundary = new RectBoundedBox(positionX, positionY, (int) (width * (getScale() + 1.35)), (int) (height * (getScale() + 1.65)));
        plantedTime = new Date();
        bombState = STATE.ACTIVE;
        Explosion = new FlameAnimations[5];
        for (int i = 0; i < 5; i++) {
            Explosion[i] = new FlameAnimations(x, y, i, radius);
            Explosion[i].addFlameToGame();
        }
    }

    public boolean remove() {
        STATE s = checkBombState();
        if (s == STATE.INACTIVE) {
            return true;
        }
        return false;
    }

    public STATE checkBombState() {
        long plantedtime = plantedTime.getTime();
        if (!exploded) {
            for (Entity e : Board.getEntities()) {
                if (e instanceof Flame && ((Flame) e).getFlameState()) {
                    if (e.isCollideEntity(this)) {
                        explodedbyFlame = true;
                        explosionTime = new Date();
                        GlobalVariables.Bricktiming = 2000 - (int) (explosionTime.getTime() - plantedtime);
                        for (int i = 0; i < Explosion.length; ++i) {
                            Explosion[i].setTimeDuration(2000 - (int) (explosionTime.getTime() - plantedtime));
                        }
                        break;
                    }
                }
            }
        }
        if (explodedbyFlame) {
            if (!exploded) {
                exploded = true;
                bombsprite = bomb_animations.getGrass();
            }
            if (exploded) {
                return STATE.INACTIVE;
            }
        }
        if (!explodedbyFlame) {
            if (!exploded && (new Date().getTime() > timerDurationInMillis + plantedtime)) {
                explosionTime = new Date();
                exploded = true;
                bombsprite = bomb_animations.getGrass();
                return STATE.EXPLODED;
            }
            if (exploded && new Date().getTime() > 350 + explosionTime.getTime()) {
                return STATE.INACTIVE;
            }
        }
        return STATE.ACTIVE;
    }


    @Override
    public void render() {
        if (checkBombState() == STATE.ACTIVE) {
            Renderer.playAnimation(bombsprite);
        }
        if (exploded) {
            Render();
        }

    }

    public void Render() {
        for (int i = 0; i < Explosion.length; i++) {
            Explosion[i].Render();
        }
    }

    @Override
    public void setOffset() {
        this.positionX -= GlobalVariables.offSet;
        this.entityBoundary.removeRect();
        this.entityBoundary.setOffset();
        this.entityBoundary.setBoundary();
    }

    @Override
    public boolean isCollideEntity(Entity e) {
        RectBoundedBox rect = e.getBoundingBox();
        return entityBoundary.checkCollision(rect);
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
        return entityBoundary;
    }

    @Override
    public boolean isCollidePlayer() {
        return this.PlayerCollisionFriendly;
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
}
