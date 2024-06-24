public class aThread extends Thread {

    private final int[][] adjacencyMatrix;
    private final int lowerBound;
    private final int upperBound;
    private final int minNodeID;
    private final int maxNodeID;
    private int minDegree;
    private int maxDegree;

    //Constructor
    public aThread(int[][] adjacencyMatrix, int lowerBound, int upperBound, int minNodeID, int maxNodeID) {
        this.adjacencyMatrix = adjacencyMatrix;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.minNodeID = minNodeID;
        this.maxNodeID = maxNodeID;
        this.minDegree = maxNodeID; // αρχικοποίηση ελάχιστου βαθμού
        this.maxDegree = 0; // αρχικοποίηση μέγιστου βαθμού
    }

    @Override
    public void run() {
        for (int i = lowerBound; i <= upperBound; ++i) { // Υπολογισμός βαθμού για κάθε κόμβο
            int degree = 0;
            for (int j = 0; j < maxNodeID; ++j) { // Υπολογισμός άθροισης των ακμών του κόμβου
                degree += adjacencyMatrix[i][j];
            }
            if (degree != 0) { // Αν ο βαθμός δεν είναι μηδέν, ενημέρωση ελάχιστου και μέγιστου βαθμού
                minDegree = Math.min(minDegree, degree);
                maxDegree = Math.max(maxDegree, degree);
            }
        }
    }

    public int getMinDegree() {
        return minDegree;
    }

    public int getMaxDegree() {
        return maxDegree;
    }
}


