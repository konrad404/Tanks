package agh.cs.projekt2_Tanks;

public class Vector2d {
    public final int x;
    public final int y;
    private static Vector2d leftDownCorner = new Vector2d(0,0);

    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return ("(" + x + "," + y + ")");
    }

    public boolean precedes(Vector2d other) {
        return x <= other.x && y <= other.y;
    }

    public boolean follows(Vector2d other) {
        return x >= other.x && y >= other.y;
    }

    private Vector2d add(Vector2d other) {
        int x1 = (x + other.x);
        int y1 = (y + other.y);
        return new Vector2d(x1,y1);
    }

    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof Vector2d))
            return false;
        Vector2d that = (Vector2d) other;
        return that.x == x && that.y == y;
    }

    public boolean isFarEnough(Vector2d other){
        return (Math.abs(this.x-other.x)>2 && Math.abs(this.y-other.y)>2);
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash += this.x*13;
        hash += this.y*23;
        return hash;
    }

    public double squereDist(Vector2d other){
        return (Math.pow(this.x-other.x,2 ) + Math.pow(this.y-other.y,2 ));
    }

    public boolean isInBattlefield(Vector2d rightUpCorner){
        return (this.follows(leftDownCorner) && this.precedes(rightUpCorner));
    }

    public int getDirectionToPlayer(Vector2d playersPosition, Vector2d rightUpCorner){
        Vector2d tryingPosition;
        double minDist = Math.pow(rightUpCorner.x+100,2 ) + Math.pow(rightUpCorner.y+100,2 );
        int id = 0;
        for (int i=0; i<8;i++){
            tryingPosition = this;
            double currentMinDist = minDist;
            while(tryingPosition.isInBattlefield(rightUpCorner)){
                if(tryingPosition.squereDist(playersPosition) == 0.0) {
                    return i;
                }
                if (tryingPosition.squereDist(playersPosition) < currentMinDist)
                    currentMinDist = tryingPosition.squereDist(playersPosition);
                tryingPosition = tryingPosition.positionAfterMove(i);
            }
            if (currentMinDist < minDist) {
                id = i;
                minDist =currentMinDist;
            }
        }
        return id;
    }

//    funkcja zwracająca miejsce po ruchu w wybranym kierunku
    public Vector2d positionAfterMove(int direction) {
        switch (direction) {
            case 0 -> {
                return this.add(new Vector2d(0, 1));
            }
            case 1 -> {
                return this.add(new Vector2d(1, 1));
            }
            case 2 -> {
                return this.add(new Vector2d(1, 0));
            }
            case 3 -> {
                return this.add(new Vector2d(1, -1));
            }
            case 4 -> {
                return this.add(new Vector2d(0, -1));
            }
            case 5 -> {
                return this.add(new Vector2d(-1, -1));
            }
            case 6 -> {
                return this.add(new Vector2d(-1, 0));
            }
            case 7 -> {
                return this.add(new Vector2d(-1, 1));
            }
            default -> {
                return this;
            }
        }
    }


}
