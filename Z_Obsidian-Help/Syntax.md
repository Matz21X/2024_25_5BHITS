Syntax ist die Grammatik der Programmiersprachen. Hier z.B. [[Java]].

```java
public class Main {

    public static void main(String[] args) {
        Muster(5);
    }

  

    private static void Muster(int zeilen) {

        for (int i = zeilen; i >= 1; i--) {
            for (int j = 1; j <= i - 1; j++) {
                System.out.print("#");
            }
            
            for (int j = zeilen; j >= 1; j--) {
                System.out.print("+");
            }  
            
            System.out.println();
        }
    }
}
```

#Programming #finished