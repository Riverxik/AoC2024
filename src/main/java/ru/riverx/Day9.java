package ru.riverx;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Day9 {

    public static void day9() throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(Path.of("src/main/resources/day9.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                processLine(line);
            }
        }
    }

    private static void processLine(String line) {
        List<Integer> files = new ArrayList<>();

        processInput(line, files);

        defrag(files);

        long checkSum = getCheckSum(files);
        System.out.println(checkSum);
    }

    private static void processInput(String line, List<Integer> files) {
        int id = 0;
        for (int i = 0; i < line.length(); i++) {
            int length = Integer.parseInt(line.substring(i, i + 1));
            if (i % 2 != 0) {
                for (int j = 0; j < length; j++) {
                    files.add(-1);
                }
            } else {
                for (int j = 0; j < length; j++) {
                    files.add(id);
                }
                id++;
            }
        }

        printFiles(files, "Step 1: ");
    }

    private static void printFiles(List<Integer> files, String s) {
        System.out.println(s);
        for (int num : files) {
            if (num == -1) {
                System.out.printf(".");
                continue;
            }
            System.out.print(num);
        }
        System.out.println();
    }


    private static void defrag(List<Integer> files) {
        for (int j = files.size() - 1; j >= 0; j--) {
            int freeInd = files.indexOf(-1);
            if (j <= freeInd) {
                break;
            }
            int last = files.get(j);
            if (last == -1) {
                continue;
            }
            files.set(freeInd, last);
            files.set(j, -1);
        }

        printFiles(files, "Step 2: ");
    }

    private static long getCheckSum(List<Integer> files) {
        long checkSum = 0;
        for (int i = 0; i < files.size(); i++) {
            long num = files.get(i);
            if (num == -1) {
                break;
            }
            checkSum += num * i;
        }
        return checkSum;
    }
}
