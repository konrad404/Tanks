package agh.cs.projekt;

public interface IPositionChangeObserver {

    public void positionChanged(Animal animal, Vector2d oldPosition, Vector2d newPosition);
}
