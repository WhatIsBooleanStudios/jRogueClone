package jrogueclone.entity;

public class Vector2D {

    public Vector2D() {}
    public Vector2D(int X, int Y) {
        this.m_X = X;
        this.m_Y = Y;
    }

    public void setX(int X) {
        this.m_X = X;
    }

    public int getX() {
        return this.m_X;
    }

    public void setY(int Y) {
        this.m_Y = Y;
    }

    public int getY() {
        return this.m_Y;
    }
    
    private int m_X, m_Y;
}
