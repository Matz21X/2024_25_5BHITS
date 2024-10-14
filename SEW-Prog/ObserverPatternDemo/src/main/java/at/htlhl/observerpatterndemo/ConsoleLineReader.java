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

public class ConsoleLineReader {

    private List<LineReadListener> lineReadListeners;


    public ConsoleLineReader() {
        lineReadListeners = new ArrayList<>();
    }

    public void addLineReadListener(LineReadListener lineReadListener) {
        lineReadListeners.add(lineReadListener);
    }

    public void removeLineReadListener(LineReadListener lineReadListener) {
        lineReadListeners.remove(lineReadListener);
    }

    private void notifyLineReadListeners(String line) {
        for (LineReadListener lineReadListener : lineReadListeners) {
            lineReadListener.lineRead(line);
       }

    }

    public void scanConsole() {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            notifyLineReadListeners(line);
        }
    }
}
