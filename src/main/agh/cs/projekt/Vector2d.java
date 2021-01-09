package agh.cs.projekt;


public class Vector2d {
    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public final int x;
    public final int y;

    public String toString() {
        return ("(" + x + "," + y + ")");
    }

    public boolean precedes(Vector2d other) {
        return x <= other.x && y <= other.y;
    }

    public boolean follows(Vector2d other) {
        return x >= other.x && y >= other.y;
    }

    public Vector2d upperRight(Vector2d other) {
        int x1;
        int y1;
        x1 = Math.max(x, other.x);
        y1 = Math.max(y, other.y);
        return new Vector2d(x1, y1);
    }

    public Vector2d lowerLeft(Vector2d other) {
        int x1;
        int y1;
        x1 = Math.min(x, other.x);
        y1 = Math.min(y, other.y);
        return new Vector2d(x1, y1);
    }

    public Vector2d add(Vector2d other,int mapHeight,int mapWidth) {
        int x1 = (x + other.x+mapWidth)%mapWidth;
        int y1 = (y + other.y+mapHeight)%mapHeight;
        return new Vector2d(x1, y1);
    }

    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof Vector2d))
            return false;
        Vector2d that = (Vector2d) other;
        return that.x == x && that.y == y;
    }

    public Vector2d opposite() {
        int x1 = -x;
        int y1 = -y;
        return new Vector2d(x1, y1);
    }

    public Vector2d subtract(Vector2d other) {
        int x1 = x - other.x;
        int y1 = y - other.y;
        return new Vector2d(x1, y1);
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash += this.x*13;
        hash += this.y*23;
        return hash;
    }

//    funkcja zwraca losowe miejsce dokoła danej pozycji
    public Vector2d goInDirection(int direction, int mapHeight, int mapWidth) { // czy wektory mogą chodzić?
        switch (direction) {
            case 0: {
                return this.add(new Vector2d(0, 1), mapHeight, mapWidth);   // nowy wektor co wywołanie
            }
            case 1: {
                return this.add(new Vector2d(1, 1), mapHeight, mapWidth);
            }
            case 2: {
                return this.add(new Vector2d(1, 0), mapHeight, mapWidth);
            }
            case 3: {
                return this.add(new Vector2d(1, -1), mapHeight, mapWidth);
            }
            case 4: {
                return this.add(new Vector2d(0, -1), mapHeight, mapWidth);
            }
            case 5: {
                return this.add(new Vector2d(-1, -1), mapHeight, mapWidth);
            }
            case 6: {
                return this.add(new Vector2d(-1, 0), mapHeight, mapWidth);
            }
            case 7: {
                return this.add(new Vector2d(-1, 1), mapHeight, mapWidth);
            }
            default: {
                return this;
            }
        }
    }


}
