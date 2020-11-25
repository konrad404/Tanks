package agh.cs.projekt;

public enum MapDirection {
    NORTH,
    SOUTH,
    WEST,
    EAST;
    public String toString(){
        switch(this){
            case NORTH: return "N";
            case SOUTH: return "S";
            case WEST: return "W";
            case EAST: return "E";
            default: return null;
        }
    }
    public MapDirection next(){
        switch(this){
            case NORTH: return EAST;
            case SOUTH: return WEST;
            case WEST: return NORTH;
            case EAST: return SOUTH;
            default: return null;
        }
    }
    public MapDirection previous(){
        switch(this){
            case NORTH: return WEST;
            case SOUTH: return EAST;
            case WEST: return SOUTH;
            case EAST: return NORTH;
            default: return null;
        }
    }
    public Vector2d toUnitVector(){
        switch(this){
            case NORTH: {
                Vector2d direction = new Vector2d(0,1);
                return direction;
            }
            case SOUTH: {
                Vector2d direction = new Vector2d(0,-1);
                return direction;
            }
            case WEST: {
                Vector2d direction = new Vector2d(-1,0);
                return direction;
            }
            case EAST: {
                Vector2d direction = new Vector2d(1,0);
                return direction;
            }
            default: return null;
        }
    }
}
