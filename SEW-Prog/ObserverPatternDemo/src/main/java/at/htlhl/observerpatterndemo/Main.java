package at.htlhl.observerpatterndemo;


public class Main {
    public static void main(String[] args) {
        Subject subject = new Subject();

        ForscherFranz forscherFranz = new ForscherFranz();
        subject.addObserver(forscherFranz);

        NetworkSender networkSender = new NetworkSender();
        subject.addObserver(networkSender);

        LineLogger lineLogger = new LineLogger();
        subject.addObserver(lineLogger);

        subject.scanConsole();
    }
}
