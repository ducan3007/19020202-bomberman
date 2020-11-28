package Bomberman.Entity.Boundedbox;

import Bomberman.GlobalVariables.GlobalVariables;
import javafx.geometry.Rectangle2D;

import static javafx.geometry.Rectangle2D.EMPTY;

public class RectBoundedBox {

    public int x;
    public int y;
    int width;
    int height;
    Rectangle2D boundary;

    public RectBoundedBox(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        width = w;
        height = h;
        boundary = new Rectangle2D(x, y, width, height);
    }

    public void setOffset() {
        this.x -= GlobalVariables.offSet;
    }

    public Rectangle2D getBoundary() {
        return boundary;
    }

    public void removeRect() {
        this.boundary = EMPTY;
    }

    public void setBoundary() {
        this.boundary = new Rectangle2D(this.x, this.y, width, height);
    }

    public boolean checkCollision(RectBoundedBox b) {
        return b.getBoundary().intersects(getBoundary());
    }

    public void setPosition(int x, int y) {
        this.x = x + (int) (GlobalVariables.PLAYER_WIDTH);
        this.y = y + (int) (GlobalVariables.PLAYER_HEIGHT);
        boundary = new Rectangle2D(this.x, this.y, width, height);
    }

    public void setEnmeyPosition(int x, int y) {
        this.x = x + 18;
        this.y = y + 18;
        boundary = new Rectangle2D(this.x, this.y, 30, 30);
    }

}
