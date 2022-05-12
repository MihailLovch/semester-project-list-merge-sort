
import java.util.LinkedList;

public class MergeSortLL {
    public static void sort(LinkedList<Integer> linkList){
        int[] buf = new int[linkList.size()];

        merge_sort(linkList, 0, linkList.size() - 1, buf);
    }

    private static void merge_sort(LinkedList<Integer> linkList, int start, int stop, int[] buf){
        if (start >= stop) {
            return;
        }

        final int middle = middle_of(start, stop);

        merge_sort(linkList, start, middle, buf);
        merge_sort(linkList, middle + 1, stop, buf);
        Merging.merge(linkList, start, middle, stop, buf);
    }

    private static int middle_of(int start, int stop){
        return start + (stop - start) / 2;
    }
}
