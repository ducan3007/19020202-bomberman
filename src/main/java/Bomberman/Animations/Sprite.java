package Bomberman.Animations;

import Bomberman.Entity.Entity;

import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

public class Sprite {

    public double playSpeed;
    public int spriteLocationOnSheetX;
    public int spriteLocationOnSheetY;
    public int numberOfFrames;
    public double width;
    public double height;
    private double scale;
    public int actualSize;
    public Image[] spriteImages;
    public boolean hasSpriteArrayImages;
    public Entity entity;

    public Sprite(Entity e, int actualSize, double playSpeed, int spriteLocationOnSheetX, int spriteLocationOnSheetY, int numberOfFrames, double width, double height,
                  double scale) {
        this.actualSize = actualSize;
        this.playSpeed = playSpeed;
        this.spriteLocationOnSheetX = spriteLocationOnSheetX;
        this.spriteLocationOnSheetY = spriteLocationOnSheetY;
        this.numberOfFrames = numberOfFrames;
        this.width = width;
        this.height = height;
        this.scale = scale;
        this.entity = e;
    }

    public Sprite(Entity e, int actualSize, double playSpeed, Image spriteSheet, List<Rectangle> specifications, double width, double height, double scale) {
        this.actualSize = actualSize;
        this.playSpeed = playSpeed;
        this.numberOfFrames = specifications.size();
        this.width = width;
        this.height = height;
        this.scale = scale;
        this.entity = e;
        hasSpriteArrayImages = true;
        spriteImages = new Image[specifications.size()];
        for (int i = 0; i < specifications.size(); i++) {
            Rectangle rect = specifications.get(i);
            int x = (int) rect.getX();
            int y = (int) rect.getY();
            int w = (int) rect.getWidth();
            int h = (int) rect.getHeight();
            spriteImages[i] = ImageUtils.crop(spriteSheet, x, y, w, h);
        }
    }

    public int getXPosition() {
        return entity.getPositionX();
    }

    public int getYPosition() {
        return entity.getPositionY();
    }

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

}
