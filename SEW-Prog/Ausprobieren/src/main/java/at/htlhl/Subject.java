package at.htlhl;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Subject {

    private List<Observer> observerList;

    public Subject(){
        observerList = new ArrayList<>();
    }

    public void addObserver (Observer observer){
        observerList.add(observer);
    }

    public void removeObserver (Observer observer){
        observerList.remove(observer);
    }

    public void notify (String line){
        for (Observer observer : observerList){
            observer.lineRead(line);
        }
    }

    public void scanBash(){
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()){
            String line = scanner.nextLine();
            notify(line);
        }
    }

}
