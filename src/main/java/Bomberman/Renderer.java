package Bomberman;

import Bomberman.Animations.Sprite;
import Bomberman.Scene.Board;
import Bomberman.Animations.ImageUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Renderer {

    static Image img;

    static {
        img = ImageUtils.loadImage("Rescourses\\img\\texture.png");
    }

    public static Image getmainSheet() {
        return img;
    }

    public static void playAnimation(Sprite sprite) {
        double time = GameLoop.getcurrentTime();
        GraphicsContext gc = Board.getGraphicsContext();
        if (sprite.hasSpriteArrayImages) {
            playAnimation(sprite.spriteImages, sprite.playSpeed, sprite.getXPosition(), sprite.getYPosition(), sprite.width * sprite.getScale(), sprite.height * sprite.getScale());
        } else {
            playAnimation(gc, time, sprite.actualSize, sprite.spriteLocationOnSheetX, sprite.spriteLocationOnSheetY,
                    sprite.numberOfFrames, sprite.getXPosition(), sprite.getYPosition(), sprite.width, sprite.height, sprite.getScale(), sprite.playSpeed);
        }
    }

    public static void playAnimation(Image[] imgs, double speed, int x, int y, double w, double h) {
        double time = GameLoop.getcurrentTime();
        GraphicsContext gc = Board.getGraphicsContext();
        int numberOfFrames = imgs.length;
        int index = CurrentFrame(time, numberOfFrames, speed);
        gc.drawImage(imgs[index], x, y, w, h);
    }


    public static void playAnimation(GraphicsContext gc, double time, int sizeOnSheet, int pointX, int pointY, int numberOfFrames, int x, int y, double width, double height, double scale, double playSpeed) {
        double speed = playSpeed >= 0 ? playSpeed : 0.3;
        int index = CurrentFrame(time, numberOfFrames, speed);
        int SpriteSheetX = pointX;
        int SpriteSheetY = pointY + index * sizeOnSheet;
        gc.drawImage(img, SpriteSheetX, SpriteSheetY, width, height, x, y, width * scale, height * scale);
    }

    private static int CurrentFrame(double time, int numberOfFrames, double speed) {
        return (int) (time % (numberOfFrames * speed) / speed);
    }
}
