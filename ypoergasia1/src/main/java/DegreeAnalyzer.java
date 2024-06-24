import java.io.File;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DegreeAnalyzer {

    public static void main(String[] args) {
        int groupSize = 2000;

        int minNodeID = -1;
        int maxNodeID = -1;

        // Ανάγνωση του αρχείου
        try (Scanner scanner = new Scanner(new File("Enron.txt"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split("\\t");
                int vFrom = Integer.parseInt(parts[0]);
                int vTo = Integer.parseInt(parts[1]);
                minNodeID = Math.min(minNodeID, Math.min(vFrom, vTo));
                maxNodeID = Math.max(maxNodeID, Math.max(vFrom, vTo));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //συνολικός αριθμός κόμβων στο γράφο
        int numNodes = maxNodeID - minNodeID + 1;

        long start = 0;
        int minDegree = 0;
        int maxDegree = 0;

        // Εκτέλεση του προγράμματος για διάφορους αριθμούς νημάτων 1, 2, 4, 8
        for (int numThreads : new int[]{1, 2, 4, 8}) {
            System.out.println("\nRunning analysis with " + numThreads + " threads:");

            int[][] adjacencyMatrix = new int[groupSize][numNodes];

            int groupCounter = 0;

            // Διαμοιρασμός του γράφου σε group και εκτέλεση του προγράμματος
            while (groupCounter * groupSize < numNodes) {
                int fromNodeID = groupCounter * groupSize;
                int toNodeID = Math.min((groupCounter + 1) * groupSize - 1, numNodes - 1);

                // Διάβασμα του αρχείου για την ενημέρωση του adjacencyMatrix
                try (Scanner scanner = new Scanner(new File("Enron.txt"))) {
                    while (scanner.hasNextLine()) {
                        String line = scanner.nextLine();
                        String[] parts = line.split("\\t");
                        int vFrom = Integer.parseInt(parts[0]);
                        int vTo = Integer.parseInt(parts[1]);

                        if (vFrom >= fromNodeID && vFrom <= toNodeID) {
                            adjacencyMatrix[vFrom - fromNodeID][vTo - minNodeID] = 1;
                        }
                        if (vTo >= fromNodeID && vTo <= toNodeID) {
                            adjacencyMatrix[vTo - fromNodeID][vFrom - minNodeID] = 1;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                start = System.currentTimeMillis();
                ExecutorService executor = Executors.newFixedThreadPool(numThreads);

                aThread[] threads = new aThread[numThreads];
                for (int i = 0; i < numThreads; ++i) {
                    int lowerBound = i * groupSize / numThreads;
                    int upperBound = Math.min((i + 1) * groupSize / numThreads - 1, groupSize - 1);
                    threads[i] = new aThread(adjacencyMatrix, lowerBound, upperBound, fromNodeID, toNodeID);
                    executor.execute(threads[i]);
                }

                executor.shutdown();
                try {
                    executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Υπολογισμός του ελάχιστου και μέγιστου βαθμού για κάθε νήμα
                minDegree = numNodes;
                maxDegree = 0;

                for (int i = 0; i < numThreads; ++i) {
                    minDegree = Math.min(minDegree, threads[i].getMinDegree());
                    maxDegree = Math.max(maxDegree, threads[i].getMaxDegree());
                }

                ++groupCounter;

            }
            long finish = System.currentTimeMillis();
            double elapsedTime = finish - start;
            System.out.println("Total time " + elapsedTime + " ms");
        }

        // Εκτυπώσεις
        System.out.println("\nMinimum degree: " + minDegree);
        System.out.println("Maximum degree: " + maxDegree);

    }
}