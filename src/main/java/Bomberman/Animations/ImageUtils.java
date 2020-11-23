package Bomberman.Animations;

import javafx.scene.image.Image;

import java.io.File;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

public class ImageUtils {
    public static Image loadImage(String path) {
        File file = new File(path);
        String imagePath = file.getAbsolutePath();
        imagePath="file:"+imagePath;
        return new Image(imagePath);
    }
    public static Image crop(Image img,int x,int y,int w,int h){
        PixelReader reader = img.getPixelReader();
        WritableImage Img = new WritableImage(reader, x, y, w, h);
        return Img;
    }
}
