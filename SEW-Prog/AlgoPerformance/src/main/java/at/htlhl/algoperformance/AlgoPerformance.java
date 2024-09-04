package at.htlhl.algoperformance;

public class AlgoPerformance {

    public static final int N = 8;

    public AlgoPerformance() {
        constantRuntime(N);
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

    private void logarithmRuntime(int n) {
        System.out.println("Logarithm Runtime 0(1)");
        System.out.println("The input is n: " + n);
        System.out.println();
    }


    public static void main(String[] args) {
        new AlgoPerformance();
    }
}
