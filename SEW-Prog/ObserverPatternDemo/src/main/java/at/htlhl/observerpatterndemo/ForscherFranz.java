package at.htlhl.observerpatterndemo;

public class ForscherFranz implements Observer {
    @Override
    public void lineRead(String text) {
        System.out.println("Forscher Franz sieht: "+text);
    }
}
