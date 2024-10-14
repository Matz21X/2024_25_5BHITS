package at.htlhl;

public class Main {
    public static void main(String[] args) {
        Subject subject = new Subject();

        ForscherFranz forscherFranz = new ForscherFranz();
        subject.addObserver(forscherFranz);

        subject.scanBash();
        
    }
}