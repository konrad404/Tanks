package agh.cs.lab1;

public interface IPositionChangeObserver {

    public void positionChanged(Vector2d oldPosition, Vector2d newPosition);
}
