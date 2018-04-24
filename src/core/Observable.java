package core;

public interface Observable {
    void update ();

    void addListener(Observer listener);

    void removeListener(Observer listener);
}
