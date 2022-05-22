package Sort;

import java.util.LinkedList;

public class Merging {
    public static void merge(LinkedList<Integer> linkList, int start, int middle, int stop, int[]buf) {

        int left_offset = start;
        int right_offset = middle + 1;

        int buf_offset = start;

        while (left_offset <= middle && right_offset <= stop) {
            if (linkList.get(left_offset) > linkList.get(right_offset)) {
                buf[buf_offset] = linkList.get(right_offset);
                right_offset++;
            } else {
                buf[buf_offset] = linkList.get(left_offset);
                left_offset++;

            }
            buf_offset += 1;
        }

        for (; left_offset <= middle; left_offset++) {
            buf[buf_offset] = linkList.get(left_offset);
            buf_offset += 1;
        }

        for (; right_offset <= stop; right_offset++) {
            buf[buf_offset] = linkList.get(right_offset);
            buf_offset += 1;
        }

        for (int i = 0; i <= stop; i++){
            linkList.set(i, buf[i]);
        }
    }
}