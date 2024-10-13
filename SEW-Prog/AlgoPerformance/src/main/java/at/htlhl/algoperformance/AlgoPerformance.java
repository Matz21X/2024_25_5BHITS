package at.htlhl.algoperformance;

public class AlgoPerformance {

    public static final int N = 200;

    public AlgoPerformance() {
        constantRuntime(N);
        logarithmitcRuntime(N);
        linearRuntime(N);
        //quadraticRuntime(N);
        //exponentialRuntime(N);
    }

    /**
     * The code takes a constant amount time to run.     * It's not dependent on the size on n.     *     * @param n input
     */
    private void constantRuntime(int n) {
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


    private void linearRuntime(int n) {
        System.out.println("Linear Runtime 0(n)");
        for (int i = 1; i <= n; i++) {
            System.out.println("    Busy looking at: " + i);
        }
        System.out.println();
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
     */
    private void exponentialRuntime(int n) {
        System.out.println("Exponential Runtime, example here exponential 0(2^n)");
        for (int i = 1; i <= Math.pow(2, n); i++) {
            System.out.println("    Busy looking at: " + i);
        }
        System.out.println();
    }

    public static void main(String[] args) {
        new AlgoPerformance();
    }
}