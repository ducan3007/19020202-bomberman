package Bomberman.Animations;

import Bomberman.Renderer;
import Bomberman.Entity.Entity;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;


import java.util.ArrayList;
import java.util.List;

public class BalloomAnimations {
    Sprite ballom;
    Sprite die;
    Sprite idle;
    double playSpeed;
    public BalloomAnimations(Entity e, double scale){
        Image img = Renderer.getSpriteSheet();
        playSpeed = 0.18;
        List<Rectangle> Ballom = new ArrayList<>();
        Ballom.add(new Rectangle(1,123,16,16));
        Ballom.add(new Rectangle(31,123,16,16));
        Ballom.add(new Rectangle(61,123,16,16));
        ballom = new Sprite(e,16,playSpeed,img,Ballom,16,16,scale);
        idle = new Sprite(e,16,playSpeed,1,123,1,16,16,scale);
        List<Rectangle> Die = new ArrayList<>();
        Die.add(new Rectangle(91,123,16,16));
        Die.add(new Rectangle(1,153,16,16));
        Die.add(new Rectangle(31,153,16,16));
        Die.add(new Rectangle(61,153,16,16));
        Die.add(new Rectangle(91,153,16,16));
        Die.add(new Rectangle(121,153,16,16));
        Die.add(new Rectangle(151,153,16,16));
        die = new Sprite(e,16,0.12,img,Die,16,16,scale);
    }
    public Sprite getBallom(){return ballom;}
    public Sprite getDie(){return die;}
    public Sprite getIdle(){return idle;}
}
