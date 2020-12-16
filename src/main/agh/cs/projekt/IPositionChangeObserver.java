package agh.cs.projekt;

public interface IPositionChangeObserver {

    public void positionChanged(Animal animal, Vector2d oldPosition, Vector2d newPosition);

    public void death(Animal animal, Vector2d position);
}
