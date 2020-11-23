package Bomberman;

import Bomberman.Animations.Sprite;
import Bomberman.Scene.Sandbox;
import Bomberman.Animations.ImageUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Renderer {

    static Image img;
    static {
        img = ImageUtils.loadImage("Rescourses\\img\\texture.png");
    }
    public static Image getSpriteSheet(){
        return img;
    }
    public static void playAnimation(Sprite sprite) {
        double time = GameLoop.getCurrentGameTime();
        GraphicsContext gc = Sandbox.getGraphicsContext();
        if(sprite == null){ }
        if (sprite.hasValidSpriteImages) {
            playAnimation(sprite.spriteImages, sprite.playSpeed, sprite.getXPosition(), sprite.getYPosition(), sprite.width*sprite.getScale(), sprite.height*sprite.getScale());
        } else {
            playAnimation(gc, time, sprite.actualSize, sprite.spriteLocationOnSheetX, sprite.spriteLocationOnSheetY,
                    sprite.numberOfFrames, sprite.getXPosition(), sprite.getYPosition(), sprite.width, sprite.height, sprite.getScale(), sprite.playSpeed);
        }
    }

    public static void playAnimation(Image[] imgs, double speed, int x, int y, double w, double h) {
        double time = GameLoop.getCurrentGameTime();
        GraphicsContext gc = Sandbox.getGraphicsContext();
        int numberOfFrames = imgs.length;
        int index = findCurrentFrame(time, numberOfFrames, speed);
        gc.drawImage(imgs[index], x, y, w, h);

    }


    public static void playAnimation(GraphicsContext gc, double time, int actualSize, int startingPointX, int startingPointY, int numberOfFrames, int x, int y, double width,
double height, double scale, double playSpeed) {
        double speed = playSpeed >= 0 ? playSpeed : 0.3;
        int index = findCurrentFrame(time, numberOfFrames, speed);
        int newSpriteSheetX = startingPointX;
        int newSpriteSheetY = startingPointY + index * actualSize;
        gc.drawImage(img, newSpriteSheetX, newSpriteSheetY, width, height, x, y, width * scale, height * scale);
    }

    private static int findCurrentFrame(double time, int totalFrames, double speed) {
        return (int) (time % (totalFrames * speed) / speed);
    }
}
