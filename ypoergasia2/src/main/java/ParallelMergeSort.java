import java.util.Arrays;

public class ParallelMergeSort {
    public static void main(String[] args) {
        int[] numThreads = {1, 2, 4, 8}; // Αριθμός νημάτων
        int[] array = generateRandomArray((int) Math.pow(2, 20)); // Δημιουργία τυχαίου πίνακα

        for (int nThreads : numThreads) { // τρέχει επαναληπτικά για (1,2,4,8)
            long start = System.currentTimeMillis();

            // Δημιουργία νημάτων
            MergeSortThread[] threads = new MergeSortThread[nThreads];
            int segmentSize = array.length / nThreads;

            // διαχωρισμός των τμημάτων του πίνακα για κάθε νήμα
            for (int i = 0; i < nThreads; i++) {
                int startIndex = i * segmentSize;
                int endIndex = (i == nThreads - 1) ? array.length : (i + 1) * segmentSize;
                threads[i] = new MergeSortThread(array, startIndex, endIndex);
                threads[i].start();
            }

            // Αναμονή για την ολοκλήρωση των νημάτων
            try {
                for (int i = 0; i < nThreads; i++) {
                    threads[i].join();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            long finish = System.currentTimeMillis();
            //System.out.println("Sorted array with " + nThreads + " threads: " + Arrays.toString(array));
            System.out.println("Execution time with " + nThreads + " threads: " + (finish - start) + " ms");
        }
    }

    //δημιουργία πίνακα τυχαίων αριθμών
    private static int[] generateRandomArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = (int) (Math.random() * 1000); // Τυχαίος ακέραιος από 0 έως 999
        }
        return array;
    }



}
