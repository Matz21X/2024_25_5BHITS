package at.htlhl.observerpatterndemo;

public class ObserverPatternDemo {
    public ObserverPatternDemo() {
        Subject subject = new Subject();

        LineLogger lineLogger = new LineLogger();
        subject.addObserver(lineLogger);

        NetworkSender networkSender = new NetworkSender();
        subject.addObserver(networkSender);

        ForscherFranz forscherFranz = new ForscherFranz();
        subject.addObserver(forscherFranz);


        subject.scanConsole();

    }
    public static void main(String[] args) {
        new ObserverPatternDemo();
    }
}
