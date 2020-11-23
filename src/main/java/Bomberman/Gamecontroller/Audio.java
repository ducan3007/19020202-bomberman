package Bomberman.Gamecontroller;


import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;


public class Audio {

    public static void ThemeMusic() {
        String path = "Rescourses\\SFX\\Theme.wav";
        try {
            AudioInputStream audio = AudioSystem.getAudioInputStream(new File(loadFile(path)));
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        } catch (Exception e) {

        }
    }

    public static String loadFile(String path) {
        File file = new File(path);
        String filePath = file.getAbsolutePath();
        return filePath;
    }
}
