package Bomberman.Entity.BombnFlame;

import Bomberman.Renderer;
import Bomberman.Animations.Sprite;
import Bomberman.GlobalVariables.GlobalVariables;
import Bomberman.Entity.Entity;
import Bomberman.Entity.StaticEntity;
import Bomberman.Entity.Boundedbox.RectBoundedBox;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Flame implements StaticEntity {

    static Image img = Renderer.getmainSheet();
    static List<Rectangle> vertical = new ArrayList<>();
    static List<Rectangle> horizonal = new ArrayList<>();
    static List<Rectangle> up = new ArrayList<>();
    static List<Rectangle> down = new ArrayList<>();
    static List<Rectangle> left = new ArrayList<>();
    static List<Rectangle> right = new ArrayList<>();
    static List<Rectangle> center = new ArrayList<>();

    static {
        vertical.add(new Rectangle(34, 170, 16, 17));
        vertical.add(new Rectangle(52, 206, 16, 17));
        vertical.add(new Rectangle(106, 206, 17, 17));
        vertical.add(new Rectangle(160, 206, 17, 17));

        horizonal.add(new Rectangle(34, 206, 16, 17));
        horizonal.add(new Rectangle(88, 206, 16, 17));
        horizonal.add(new Rectangle(142, 206, 16, 17));
        horizonal.add(new Rectangle(197, 206, 16, 17));

        up.add(new Rectangle(16, 173, 16, 16));
        up.add(new Rectangle(70, 171, 16, 16));
        up.add(new Rectangle(124, 170, 17, 17));
        up.add(new Rectangle(178, 170, 17, 17));

        down.add(new Rectangle(16, 206, 16, 16));
        down.add(new Rectangle(70, 205, 16, 16));
        down.add(new Rectangle(125, 206, 16, 16));
        down.add(new Rectangle(177, 207, 18, 16));

        left.add(new Rectangle(1, 188, 16, 16));
        left.add(new Rectangle(53, 188, 17, 16));
        left.add(new Rectangle(106, 188, 17, 17));
        left.add(new Rectangle(160, 188, 17, 17));

        right.add(new Rectangle(33, 188, 17, 17));
        right.add(new Rectangle(87, 188, 17, 17));
        right.add(new Rectangle(142, 188, 17, 17));
        right.add(new Rectangle(195, 188, 18, 17));

        center.add(new Rectangle(16, 188, 17, 17));
        center.add(new Rectangle(70, 188, 17, 17));
        center.add(new Rectangle(124, 188, 17, 17));
        center.add(new Rectangle(178, 188, 17, 17));
    }

    int positionX;
    int positionY;
    int direction;
    int layer;
    int height;
    int width;
    int timing;

    boolean last;

    double scale;
    double splayspeed;

    Date explodeTime;
    Sprite sprite;
    RectBoundedBox boundedBox;


    public Flame(int x, int y, int direction, boolean last) {
        this.positionX = x;
        this.positionY = y;
        this.direction = direction;
        this.last = last;
        timing = 0;
        layer = -1;
        scale = 2.2;
        splayspeed = 0.1;
        width = 16;
        height = 16;
        boundedBox = new RectBoundedBox(positionX, positionY, (int) (17 * (getScale())), (int) (17 * (getScale())));
        explodeTime = new Date();
        switch (direction) {
            case 0:
                if (last == false) {
                    sprite = new Sprite(this, 16, splayspeed, img, vertical, 16, 17, this.getScale());
                } else {
                    sprite = new Sprite(this, 16, splayspeed, img, up, 16, 17, this.getScale());
                }
                break;
            case 1:
                if (last == false) {
                    sprite = new Sprite(this, 16, splayspeed, img, horizonal, 17, 16, this.getScale());
                } else {
                    sprite = new Sprite(this, 16, splayspeed, img, right, 17, 16, this.getScale());
                }
                break;
            case 2:
                if (last == false) {
                    sprite = new Sprite(this, 16, splayspeed, img, vertical, 16, 17, this.getScale());
                } else {
                    sprite = new Sprite(this, 16, splayspeed, img, down, 16, 17, this.getScale());
                }
                break;
            case 3:
                if (last == false) {
                    sprite = new Sprite(this, 16, splayspeed, img, horizonal, 17, 16, this.getScale());
                } else {
                    sprite = new Sprite(this, 16, splayspeed, img, left, 17, 16, this.getScale());
                }
                break;
            case 4:
                sprite = new Sprite(this, 16, splayspeed, img, center, 16, 16, this.getScale());
                break;
        }
    }

    public boolean State = false;

    public boolean getFlameState() {
        return State;
    }

    public void setFlameState(boolean state) {
        State = state;
    }

    public void setTimeDuratione(int time) {
        timing = time;
    }

    public boolean checkFlameState() {
        long time = explodeTime.getTime();
        long newDate = new Date().getTime();
        if (newDate > (2000 - timing) + time) {
            State = true;
            if (newDate > ((2000 - timing) + 350) + time) {
                return true;
            }
        }
        return false;
    }

    public boolean remove() {
        return checkFlameState();
    }

    @Override
    public void setOffset() {
        this.positionX -= GlobalVariables.offSet;
        this.boundedBox.removeRect();
        this.boundedBox.setOffset();
        this.boundedBox.setBoundary();
    }

    @Override
    public void render() {
        if (!checkFlameState()) {
            Renderer.playAnimation(sprite);
        }
    }

    @Override
    public boolean isCollideEntity(Entity e) {
        RectBoundedBox rect = e.getBoundingBox();
        return boundedBox.checkCollision(rect);
    }

    @Override
    public boolean isCollidePlayer() {
        return true;
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
        return boundedBox;
    }

    @Override
    public int getLayer() {
        return layer;
    }

    @Override
    public double getScale() {
        return scale;
    }
}
