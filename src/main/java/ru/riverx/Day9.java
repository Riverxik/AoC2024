package ru.riverx;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

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

        // Part 1
//        defrag(files);
//        System.out.println(getCheckSum(files));

        // Part 2
        defrag2(files);
        System.out.println(getCheckSumExtra(files));
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

    private static void defrag2(List<Integer> files) {
        Map<Integer, Long> mapNumSize = files.stream().collect(Collectors.groupingBy(Integer::intValue, Collectors.counting()));
        List<Integer> uniqueFileList = mapNumSize.keySet().stream().sorted(Collections.reverseOrder()).toList();

        for (int num : uniqueFileList) {
            if (num == -1) break;
            int pos = files.indexOf(num);
            int len = Math.toIntExact(mapNumSize.get(num));

            // get free position available for this file
            int freePos = getFreePosForSize(files, len, pos);
            if (freePos == -1) { continue; }

            // if pos != null then swap
            for (int i = freePos; i < freePos + len; i++) {
                files.set(i, num);
            }
            for (int i = pos; i < pos + len; i++) {
                files.set(i, -1);
            }
        }

        printFiles(files, "Step 2: ");
    }

    private static int getFreePosForSize(List<Integer> files, int len, int right) {
        int pos = files.indexOf(-1);
        int i = pos;
        int end = right;
        boolean isFound = false;
        while (i < end) {
            while (i < end) {
                if (files.get(i) != -1) {
                    break;
                }
                i++;
            }
            if (i - pos >= len) {
                isFound = true;
                break; // found
            }
            i++;
            pos = i;
        }

        return isFound ? pos : -1;
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

    private static long getCheckSumExtra(List<Integer> files) {
        long checkSum = 0;

        for (int i = 0; i < files.size(); i++) {
            long num = files.get(i);
            if (num == -1) {continue;}
            checkSum += num * i;
        }

        return checkSum;
    }
}
