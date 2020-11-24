package Bomberman.Animations;

import Bomberman.Renderer;
import Bomberman.Entity.Entity;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;


import java.util.ArrayList;
import java.util.List;

public class OnealAnimations {
    Sprite oneal;
    Sprite die;
    Sprite idle;
    double playSpeed;
    public OnealAnimations(Entity e, double scale){
        Image img = Renderer.getSpriteSheet();
        playSpeed = 0.2;
        List<Rectangle> Oneal = new ArrayList<>();
        
        Oneal.add(new Rectangle(121,123,16,16));
        Oneal.add(new Rectangle(151,123,16,16));
        Oneal.add(new Rectangle(181,123,16,16));
        oneal = new Sprite(e,16,playSpeed,img,Oneal,16,16,scale);
        idle = new Sprite(e,16,playSpeed,121,123,1,16,16,scale);
        
        List<Rectangle> Die = new ArrayList<>();
        
        Die.add(new Rectangle(211,123,16,16));
        Die.add(new Rectangle(241,123,16,16));
        Die.add(new Rectangle(31,153,16,16));
        Die.add(new Rectangle(61,153,16,16));
        Die.add(new Rectangle(91,153,16,16));
        Die.add(new Rectangle(121,153,16,16));
        Die.add(new Rectangle(151,153,16,16));
        die = new Sprite(e,16,0.12,img,Die,16,16,scale);
    }
    public Sprite getOneal(){return oneal;}
    public Sprite getDie(){return die;}
    public Sprite getIdle(){return idle;}
}
