package at.htlhl.observerpatterndemo;

public class ObserverPatternDemo {
    public ObserverPatternDemo() {
        ConsoleLineReader lineReader = new ConsoleLineReader();

        LineLogger lineLogger = new LineLogger();
        lineReader.addLineReadListener(lineLogger);

        NetworkSender networkSender = new NetworkSender();
        lineReader.addLineReadListener(networkSender);

        ForscherFranz forscherFranz = new ForscherFranz();
        lineReader.addLineReadListener(forscherFranz);


        lineReader.scanConsole();

    }
    public static void main(String[] args) {
        new ObserverPatternDemo();
    }
}
