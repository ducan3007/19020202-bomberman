package Bomberman.Animations;

import Bomberman.Entity.Entity;
import Bomberman.Entity.Tiles.Brick;
import Bomberman.Entity.BombnFlame.Flame;
import Bomberman.Entity.Tiles.Wall;
import Bomberman.Scene.Board;

public class FlameAnimations {
     int direction;
     int centerX;
     int centerY;
     int positionX;
     int positionY;
     int radius;
     Flame[] flames;
     int time;
    public FlameAnimations(int x, int y, int direction, int radius) {
        centerX = x;
        centerY = y;
        positionX = x;
        positionY = y;
        this.direction = direction;
        this.radius = radius * 2;
        if (direction == 4) {
            flames = new Flame[1];
        } else {
            flames = new Flame[CalculateRadius()];
        }
        createExplosion();
    }

    public int CalculateRadius() {
        int radius = 0;
        int x = this.positionX;
        int y = this.positionY;
        boolean check = true;
        while (radius < this.radius) {
            if (direction == 0) y -= 24;
            if (direction == 1) x += 24;
            if (direction == 2) y += 24;
            if (direction == 3) x -= 24;
            for (Entity e : Board.getEntities()) {
                if (e instanceof Wall || e instanceof Brick) {
                    if ((Math.abs(y - (e).getPositionY()) < 40) && (Math.abs(x - e.getPositionX()) < 40)) {
                        if (e instanceof Brick) {
                            if (!((Brick) e).getBrickState()) {
                                break;
                            } else {
                                ((Brick) e).setBrickState(true);
                            }
                        }
                        check = false;
                        break;
                    }
                }
            }
            if (check) {
                radius++;
            } else {
                break;
            }
        }
        return radius;
    }

    public void createExplosion() {
        boolean last = false;
        int x = this.positionX;
        int y = this.positionY;
        if (direction == 4) {
            flames[0] = new Flame(this.positionX, this.positionY, direction, false);
        } else {
            for (int i = 0; i < flames.length; i++) {
                last = (i == flames.length - 1) ? true : false;

                switch (direction) {
                    case 0:
                        y -= 24;
                        break;
                    case 1:
                        x += 24;
                        break;
                    case 2:
                        y += 24;
                        break;
                    case 3:
                        x -= 24;
                        break;
                }
                flames[i] = new Flame(x, y, direction, last);
            }
        }
    }
    public void setTimeDuration(int time) {
        this.time = 2000 - time;
        for (int i = 0; i < flames.length; ++i) {
            flames[i].setTimeDuratione(time);
        }
    }
    public void addFlameToGame(){
        for(int i = 0;i<flames.length;++i){
            Board.addEntityToGame(flames[i]);
        }
    }
    public void Render() {
        for (int i = 0; i < flames.length; ++i) {
            flames[i].setFlameState(true);
            flames[i].render();
        }
    }

}
