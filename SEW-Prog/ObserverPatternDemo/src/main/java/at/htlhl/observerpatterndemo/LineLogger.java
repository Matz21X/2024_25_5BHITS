package at.htlhl.observerpatterndemo;

public class LineLogger implements LineReadListener {

    @Override
    public void lineRead(String text) {
        System.out.println("LineLogger '" + text + "'");
    }

}
