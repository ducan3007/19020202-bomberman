package Bomberman.Entity.Tiles;

import Bomberman.Renderer;
import Bomberman.Animations.BrickAnimations;
import Bomberman.Animations.Sprite;
import Bomberman.GlobalVariables.GlobalVariables;
import Bomberman.Entity.Entity;
import Bomberman.Entity.KillableEntity;
import Bomberman.Entity.Boundedbox.RectBoundedBox;
import Bomberman.Scene.Sandbox;

import java.util.Date;

public class Brick extends Tile implements KillableEntity {
    int items;
    boolean isAlive = true;
    boolean disappear = false;
    boolean check = true;
    boolean Animated = false;
    Sprite sprite;
    Sprite grass;
    Sprite CurrentSprite;
    BrickAnimations brickAnimations;
    Date destroyedTime;
    Date animationTime;

    public Brick(int x, int y, int items) {
        super(x, y);
        this.items = items;
        scale = 3.1;
        brickAnimations = new BrickAnimations(this);
        sprite = new Sprite(this, 16, 0, 17, 225, 1, super.width, super.height, getScale());
        grass = new Sprite(this, 16, 0, 0, 245, 1, 16, 16, 2.0);
        CurrentSprite = sprite;
        super.boundedBox = new RectBoundedBox(positionX, positionY, (int) (super.width * (getScale() + 0.9)), (int) (super.height * (getScale() + 0.9)));
    }

    public void setBrickState(boolean temp) {
        if (check && temp) {
            GlobalVariables.Bricktiming = 0;
            die();
        }
    }

    public boolean getBrickState() {
        return check;
    }

    public boolean BrickState() {
        if (!Animated && !check && new Date().getTime() > (2000 - GlobalVariables.Bricktiming) + destroyedTime.getTime()) {
            CurrentSprite = grass;
            animationTime = new Date();
            Animated = true;
            isAlive = false;
        }
        if (Animated && new Date().getTime() > 280 + animationTime.getTime()) {
            disappear = true;
        }
        return isAlive;
    }

    @Override
    public void draw() {
        if (BrickState()) {
            Renderer.playAnimation(CurrentSprite);
        }
        if (!BrickState()) {
            Renderer.playAnimation(brickAnimations.getBrickAnimation());
        }
    }

    @Override
    public void die() {
        check = false;
        destroyedTime = new Date();
    }
    @Override
    public boolean remove(){
        if(disappear){
            switch (items){
                case 0:
                    Sandbox.addEntityToGame(new Portal(positionX,positionY));
                    break;
                case 1:
                    Sandbox.addEntityToGame(new FlamePowerup(positionX,positionY));
                    break;
                case 2:
                    Sandbox.addEntityToGame(new BombPowerup(positionX,positionY));
                    break;
                case 3:
                    Sandbox.addEntityToGame(new SpeedPowerup(positionX,positionY));
                    break;
            }
        }
        return disappear;
    }

    @Override
    public boolean isColliding(Entity e) {
        RectBoundedBox rect = e.getBoundingBox();
        return super.boundedBox.checkCollision(rect);
    }

}
