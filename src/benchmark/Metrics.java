package benchmark;

import dataset.GenerateInput;
import Sort.MergeSortLL;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class Metrics {
    private static final int INPUT_AMOUNT = 4;
    private static final int FILE_AMOUNT = 11;
    private static final int SOURCE = 0;
    private static final int OUT = 1;
    private static final int SET = 2;
    private static final int ITERATIONS = 3;
    /*
    1 - откуда
    2 - куда
    3 - команда -
    4 - набор
    5 - кол-во раз
     */
    public static void main(String[] args) throws IOException {
        if (!validateInput(args)) {
            System.out.println("Invalid input");
        } else {
            ArrayList<Long> averageTime = new ArrayList<>(5);
            int iterations = Integer.parseInt(args[ITERATIONS]);
            for (int i = 0; i < iterations; i++) {
                GenerateInput.generateData(args[SOURCE]);
                averageTime.addAll(Arrays.asList(testMethod(args)));
            }
            writeMetrics(args, countAverage(averageTime,iterations));

        }
    }

    public static long[] countAverage(ArrayList<Long> time, int iterations) {
        long[] average = new long[FILE_AMOUNT];
        for (int i = 0; i < time.size(); i++) {
            average[i % FILE_AMOUNT] += time.get(i);
        }
        for (int i =0 ; i < FILE_AMOUNT; i++){
            average[i] /= iterations;
        }
        average = Arrays.stream(average).sorted().toArray();
        return average;
    }

    public static void writeMetrics(String[] args, long[] time) throws IOException {
        try (FileWriter writer = new FileWriter(Paths.get(args[SOURCE]).resolve("metrics").resolve("sort.txt").toString())) {
            for (long el : time) {
                writer.write(el + "\n");
            }
            writer.flush();
        }
    }
    public static Long[] testMethod(String[] args) throws IOException {
        Path path = Paths.get(args[0]).resolve("methodsData").resolve(args[SET]);
        Long[] time = new Long[FILE_AMOUNT];
        long start;
        long end;
        int c = 0;

        for (File file : new File(path.toString()).listFiles()){
            try (FileReader reader = new FileReader(file)){
                LinkedList<Integer> arrayToSort = new LinkedList<>();
                int num;
                while ((num = reader.read()) != -1){
                    arrayToSort.add(num);
                }
                start = System.currentTimeMillis();
                MergeSortLL.sort(arrayToSort);
                end = System.currentTimeMillis();
                time[c++] = end - start;
            }
        }
        return time;
    }

    public static boolean validateInput(String[] args) {
        if (args.length != INPUT_AMOUNT) {
            return false;
        }
        return new File(args[0]).exists();
    }
}
