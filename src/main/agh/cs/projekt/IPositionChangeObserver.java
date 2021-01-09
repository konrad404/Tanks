package agh.cs.projekt;

public interface IPositionChangeObserver {

    public void positionChanged(Animal animal, Vector2d oldPosition, Vector2d newPosition); // czy potrzebujemy nowej pozycji, skoro mamy całe zwierzę?

    public void death(Animal animal, Vector2d position);    // sugeruję animalDied - będzie spójnie z powyższą metodą
}
