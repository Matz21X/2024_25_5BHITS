package at.htlhl;

public class ForscherFranz implements Observer{

    @Override
    public void lineRead(String text){
        System.out.println("Froscher Franz sieht:" + text);
    }
}
