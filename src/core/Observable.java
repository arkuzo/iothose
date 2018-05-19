package core;

public interface Observable {
    void notifyListeners ();

    void addListener(Observer listener);

    void removeListener(Observer listener);
}
