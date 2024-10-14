package at.htlhl.observerpatterndemo;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class is responsible for reading input (line wise) from
 * the console
 *
 * <b>Subjects</b>
 */

public class Subject {

    private List<Observer> observers;


    public Subject() {
        observers = new ArrayList<>();
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    private void notifyObservers(String line) {
        //LineReadListener lineReadListener;
        for (Observer observer : observers) {
            observer.lineRead(line);
        }

    }

    public void scanConsole() {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            notifyObservers(line);
        }
    }
}
