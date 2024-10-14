package at.htlhl.observerpatterndemo;

public class LineLogger implements Observer {

    @Override
    public void lineRead(String text) {
        System.out.println("LineLogger '" + text + "'");
    }

}