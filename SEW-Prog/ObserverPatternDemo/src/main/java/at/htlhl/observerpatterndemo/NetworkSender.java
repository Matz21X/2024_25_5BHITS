package at.htlhl.observerpatterndemo;

/**
 * Observer
 */
public class NetworkSender implements Observer {

    @Override
    public void lineRead(String text) {
        System.out.println("Networksender, tcp send:'" + text + "'");
    }

}
