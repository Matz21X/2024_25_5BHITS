# Performance Bewertung Datenstrukturen
#SEW

Performance der Datenstrukturen im schlechtesten Fall

| Datenstruktur          | Zugriff  | Einfügen | Löschen  | Suchen   |
| ---------------------- | -------- | -------- | -------- | -------- |
| Array (List)           | O(1)     | O(1)     | O(n)     | O(n)     |
| Sortiertes Array       | O(1)     | O(n)     | O(n)     | O(log n) |
| LinkedList             | O(n)     | O(1)     | O(n)     | O(n)     |
| Baum                   | O(n)     | O(1)     | O(n)     | O(n)     |
| Balancierter Baum      | O(log n) | O(log n) | O(log n) | O(log n) |
| HashMap                | Suchen   | O(log n) | O(log n) | O(log n) |
| HashMap ohne Kollision | Suchen   | O(1)     | O(1)     | O(1)     |
Die Performance der HashMap bei Verwendung von Listen im Kollisionsfall beträgt O(n). In Java wird jedoch ab einer gewissen Anzahl von Elementen ein balancierter Baum verwendet, dadurch beträgt die Performance O(log n)

Info Video: https://youtu.be/__vX2sjlpXU 
Kürzer: https://youtu.be/g2o22C3CRfU

``` java
package at.htlhl.algoperformance;  
  
public class AlgoPerformance {  
  
    public static final int N = 8;  
  
    public AlgoPerformance() {  
        constantRuntime(N);  
        logarithmitcRuntime(N);  
        linearRuntime(N);  
        quadraticRuntime(N);  
        exponentialRuntime(N);  
    }  
  
    /**  
     * The code takes a constant amount time to run.     * It's not dependent on the size on n.     *     * @param n input  
     */    private void constantRuntime(int n) {  
        System.out.println("Constant Runtime 0(1)2");  
        System.out.println("    The input is: " + n);  
        System.out.println();  
    }  
  
    private void logarithmitcRuntime(int n) {  
        System.out.println("Logarithmic Runtime 0(log n)");  
        for (int i = 1; i < n; i = i * 2) {  
            System.out.println("    Busy looking at: " + i);  
        }  
        System.out.println();  
    }  
  
    public static void main(String[] args) {  
        new AlgoPerformance();  
    }  
  
    private void linearRuntime(int n) {  
        System.out.println("Linear Runtime 0(n)");  
        for (int i = 1; i <= n; i++) {  
            System.out.println("    Busy looking at: " + i);  
        }  
    }  
  
  
    /**  
     * The running time grows polynomial to the input (quadratic n^2, cubic n^3, ...)     * to the input     *     * @param n  
     */  
    private void quadraticRuntime(int n) {  
        System.out.println("Polynominal Runtime, example here quadratic 0(n^2)");  
        for (int i = 1; i <= n; i++) {  
            for (int j = 1; j <= n; j++) {  
                System.out.println("    Busy looking at: " + i + " and " + j);  
            }  
        }  
        System.out.println();  
    }  
  
    /**  
     * The running time grows in proportion to some factor exponential to the input     *     * (very slow algorithm)     *     * 0(k^n) e.g. 0(2^n)     * @param n input  
     */    private void exponentialRuntime(int n) {  
        System.out.println("Exponential Runtime, example here exponential 0(2^n)");  
        for (int i = 1; i <= Math.pow(2, n); i++) {  
                System.out.println("    Busy looking at: " + i);  
        }  
        System.out.println();  
    }  
}
```