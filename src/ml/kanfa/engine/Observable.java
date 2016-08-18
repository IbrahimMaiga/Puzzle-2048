package ml.kanfa.engine;


/**
 * @uthor Ibrahim Maïga.
 */
public interface Observable {
    void addObserver(Observer observer);
    void addObserver(String name, Observer observer);
    void removeObserver(Observer observer);
    void removeObserver(String name);
    void notifyObserver(Observer observer, Object o);
    void notifyObserver(String name, Object o);
    void notifyObservers(Object o);

}
