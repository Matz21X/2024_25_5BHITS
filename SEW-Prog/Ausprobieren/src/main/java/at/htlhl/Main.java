package at.htlhl;

public class Main {
    public static void main(String[] args) {
        Subject subject = new Subject();

        ForscherFranz forscherFranz = new ForscherFranz();
        subject.addObserver(forscherFranz);

        //subject.scanBash();


        int bruh = 8;


        for (int i = 1; i<=bruh; i= i*2){
            System.out.println(i);
        }

        for (int i = 1; i<= Math.pow(2, bruh);i++){
            System.out.println("Bruh:"+i);
        }





    }
}