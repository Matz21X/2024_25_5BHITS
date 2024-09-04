package at.htlhl.algoperformance;

public class AlgoPerformance {

    public static final int N = 8;

    public AlgoPerformance() {
        constantRuntime(N);
        logarithmRuntime(N);
        linearRuntime(N);
    }

    /**
     * The code takes a constant amount of time to run.
     * It's not dependent on the size of n.
     *
     * 0(1)
     *
     * @param n input
     */
    private void constantRuntime(int n) {
        System.out.println("Constant Runtime 0(1)");
        System.out.println("The input is n: " + n);
        System.out.println();
    }

    /**
     * The running time grows in proportion to the logarithm of the input
     *
     * 0(log n)
     *
     * @param n input
     */
    private void logarithmRuntime(int n) {
        System.out.println("Logarithmic Runtime 0(log n)");
        for (int i = 1; i <= n; i = i * 2) {
            System.out.println("Busy looking at: " + i);
        }
        System.out.println();
    }


    public static void main(String[] args) {
        new AlgoPerformance();
    }

    /**
     * The running time grows linearly, which means it grows directly
     * proportional to the input
     *
     * 0(n)
     *
     * @param n input
     */
    private void linearRuntime(int n) {
        System.out.println("Linear Runtime 0(1)");
        for (int i = 1; i <= n; i++) {
            System.out.println("Busy looking at: " + i);
        }
    }
}
