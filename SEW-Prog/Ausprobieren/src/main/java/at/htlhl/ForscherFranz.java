package at.htlhl;

public class ForscherFranz implements Observer{

    @Override
    public void lineRead (String line){
        System.out.println("FroscherFranz sieht: "+ line);
    }

}
