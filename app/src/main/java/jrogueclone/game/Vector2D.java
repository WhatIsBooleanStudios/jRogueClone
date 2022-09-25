package jrogueclone.game;

import java.util.Vector;

public class Vector2D {

    public Vector2D() {}
    public Vector2D(int X, int Y) {
        this.m_X = X;
        this.m_Y = Y;
    }
    public Vector2D(Vector2D vector) {
        this.m_X = vector.getX();
        this.m_Y = vector.getY();
    }

    @Override
    public boolean equals(Object o) {
        return equals((Vector2D)o);
    }
    public boolean equals(Vector2D vector) {
        return m_X == vector.m_X && m_Y == vector.m_Y;
    }
    public boolean Equals(Vector2D vector) {
        return equals(vector);
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

    public String toString() {
        return "(" + m_X + "," + m_Y + ")";
    }
    
    private int m_X, m_Y;
}
