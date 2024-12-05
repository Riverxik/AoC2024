package ru.riverx;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day5 {

    public static void day5() throws IOException {
        int res = 0;
        List<int[]> pairList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(Files.newBufferedReader(Path.of("src/main/resources/day5.txt")))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isEmpty()) {
                    continue;
                }

                if (line.contains("|")) {
                    String[] split = line.split("\\|");
                    int[] pairs = new int[]{Integer.parseInt(split[0].trim()), Integer.parseInt(split[1].trim())};
                    pairList.add(pairs);
                } else if (line.contains(",")) {
                    String[] split = line.split(",");
                    List<Integer> numberList = new ArrayList<>();
                    for (String s : split) {
                        numberList.add(Integer.parseInt(s.trim()));
                    }
                    boolean isWrong = isOrderWrong(numberList, pairList);
                    if (!isWrong) {
                        res += numberList.get(numberList.size() / 2);
                    }
                }
            }
        }
        System.out.println(res);
    }

    public static void day5star() throws IOException {
        int res = 0;
        List<int[]> pairList = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(Path.of("src/main/resources/day5.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isEmpty()) {
                    continue;
                }

                if (line.contains("|")) {
                    String[] split = line.split("\\|");
                    int[] pairs = {Integer.parseInt(split[0].trim()), Integer.parseInt(split[1].trim())};
                    pairList.add(pairs);
                } else if (line.contains(",")) {
                    String[] split = line.split(",");
                    List<Integer> numberList = new ArrayList<>();
                    for (String s : split) {
                        numberList.add(Integer.parseInt(s.trim()));
                    }
                    boolean isWrong = isOrderWrong(numberList, pairList);
                    if (isWrong) {
                        sortList(numberList, pairList);
                        res += numberList.get(numberList.size() / 2);
                    }
                }
            }
        }
        System.out.println(res);
    }

    private static boolean isOrderWrong(List<Integer> numberList, List<int[]> pairList) {
        // Complexity O(n + m), where n - sizeof numberList, m - sizeof pairList.
        Map<Integer, Integer> indexMap = new HashMap<>();
        for (int i = 0; i < numberList.size(); i++) {
            indexMap.put(numberList.get(i), i);
        }

        for (int[] pair : pairList) {
            Integer firstIndex = indexMap.get(pair[0]);
            Integer secondIndex = indexMap.get(pair[1]);

            if (firstIndex != null && secondIndex != null && firstIndex > secondIndex) {
                return false; // pair[0] appears after pair[1]
            }
        }

        return true; // No order issues found
    }

    private static boolean isOrderWrongStraightForwardWay(List<Integer> numberList, List<int[]> pairList) {
        // Complexity O(n * m^2), where n - sizeof numberList, m - sizeof pairList.
        boolean isWrong = false;
        for (int i = 0; i < numberList.size(); i++) {
            for (int[] pair : pairList) {
                if (pair[0] == numberList.get(i)) {
                    for (int j = i; j >= 0; j--) {
                        if (numberList.get(j) == pair[1]) {
                            isWrong = true;
                            break;
                        }
                    }
                }
                if (pair[1] == numberList.get(i)) {
                    for (int j = i; j < numberList.size(); j++) {
                        if (numberList.get(j) == pair[0]) {
                            isWrong = true;
                            break;
                        }
                    }
                }
            }
        }
        return isWrong;
    }

    private static void sortList(List<Integer> numberList, List<int[]> pairList) {
        // Complexity O(n log n), where n - sizeof numberList
        Comparator<Integer> comparator = (i1, i2) -> {
            for (int[] pair : pairList) {
                if (pair[0] == i1 && pair[1] == i2) {
                    return -1;
                }
                if (pair[0] == i2 && pair[1] == i1) {
                    return 1;
                }
            }
            return 0;
        };

        numberList.sort(comparator);
    }

    private static void sortListBubble(List<Integer> numberList, List<int[]> pairList) {
        // Complexity O(n^2)
        for (int i = 0; i < numberList.size(); i++) {
            for (int j = i + 1; j < numberList.size(); j++) {
                if (sort(numberList.get(i), numberList.get(j), pairList) == 1) {
                    int temp = numberList.get(i);
                    numberList.set(i, numberList.get(j));
                    numberList.set(j, temp);
                }
            }
        }
    }

    private static int sort(int i1, int i2, List<int[]> pairList) {
        for (int[] pair : pairList) {
            if (pair[0] == i1 && pair[1] == i2) {
                return -1;
            }
            if (pair[0] == i2 && pair[1] == i1) {
                return 1;
            }
        }
        return 0;
    }
}
