package at.htlhl.observerpatterndemo;

/**
 * Observer
 */
public class NetworkSender implements LineReadListener {

    @Override
    public void lineRead(String text) {
        System.out.println("Networksender, tcp send:'" + text + "'");
    }

}
