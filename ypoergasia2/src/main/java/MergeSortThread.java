import java.util.Arrays;

public class MergeSortThread extends Thread{
    private int[] array;
    private int startIndex, endIndex;

    // constructor
    public MergeSortThread(int[] array, int startIndex, int endIndex) {
        this.array = array;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    @Override
    public void run() {
        Arrays.sort(array, startIndex, endIndex);
    }
}
