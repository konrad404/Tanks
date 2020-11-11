package agh.cs.lab1;

public class Vector2d {
    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public final int x;
    public final int y;

    public String toString() {
        String answer = ("(" + x + "," + y + ")");
        return answer;
    }

    public boolean precedes(Vector2d other) {
        if (x <= other.x && y <= other.y) return true;
        else return false;
    }

    public boolean follows(Vector2d other) {
        if (x >= other.x && y >= other.y) return true;
        else return false;
    }

    public Vector2d upperRight(Vector2d other) {
        int x1;
        int y1;
        if (x > other.x) x1 = x;
        else x1 = other.x;
        if (y > other.y) y1 = y;
        else y1 = other.y;
        Vector2d upperRight = new Vector2d(x1, y1);
        return upperRight;
    }

    public Vector2d lowerLeft(Vector2d other) {
        int x1;
        int y1;
        if (x < other.x) x1 = x;
        else x1 = other.x;
        if (y < other.y) y1 = y;
        else y1 = other.y;
        Vector2d lowwerLeft = new Vector2d(x1, y1);
        return lowwerLeft;
    }

    public Vector2d add(Vector2d other) {
        int x1 = x + other.x;
        int y1 = y + other.y;
        Vector2d added = new Vector2d(x1, y1);
        return added;
    }

    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof Vector2d))
            return false;
        Vector2d that = (Vector2d) other;
        if (that.x == x && that.y == y) return true;
        else return false;
    }

    public Vector2d opposite() {
        int x1 = -x;
        int y1 = -y;
        Vector2d opposite = new Vector2d(x1, y1);
        return opposite;
    }

    public Vector2d subtract(Vector2d other) {
        int x1 = x - other.x;
        int y1 = y - other.y;
        Vector2d added = new Vector2d(x1, y1);
        return added;
    }
    @Override
    public int hashCode() {
        int hash = 17;
        hash += this.x*13;
        hash += this.y*23;
        return hash;
    }
}
