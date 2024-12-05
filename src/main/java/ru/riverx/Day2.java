package ru.riverx;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Day2 {

    public static void day2() throws IOException {
        int safeCount = 0;

        try (BufferedReader reader = Files.newBufferedReader(Path.of("src/main/resources/day2.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] split = line.split(" ");
                List<Integer> numbers = new ArrayList<>();

                for (String s : split) {
                    numbers.add(Integer.parseInt(s));
                }

                if (isSafe(numbers)) {
                    safeCount++;
                } else {
                    for (int i = 0; i < numbers.size(); i++) {
                        List<Integer> tmp = new ArrayList<>(numbers);
                        tmp.remove(i);
                        if (isSafe(tmp)) {
                            safeCount++;
                            break;
                        }
                    }
                }
            }

        }

        System.out.println("\nSafe: " + safeCount);
    }

    private static boolean isSafe(List<Integer> numbers) {
        List<Integer> diff = calcDiff(numbers);
        int signAndZeroErr = checkDiffListForSignAndZero(diff);
        int adjErr = checkDiffForAdj(diff);
        return (signAndZeroErr + adjErr) < 1;
    }

    private static List<Integer> calcDiff(List<Integer> numbers) {
        List<Integer> diff = new ArrayList<>();
        for (int i = 1; i < numbers.size(); i++) {
            int num1 = numbers.get(i - 1);
            int num2 = numbers.get(i);
            diff.add(num2 - num1);
        }
        return diff;
    }

    private static int checkDiffForAdj(List<Integer> diff) {
        int mostAdj = 0;
        for (Integer integer : diff) {
            int num = Math.abs(integer);
            if (num > 3 || num < 0) {
                mostAdj++;
            }
        }
        return mostAdj;
    }

    private static int checkDiffListForSignAndZero(List<Integer> diff) {
        int zero = 0;
        int positive = 0;
        int negative = 0;

        for (Integer integer : diff) {
            if (integer == 0) {
                zero++;
                continue;
            }
            if (integer < 0) {
                negative++;
            } else {
                positive++;
            }
        }

        return Math.min(positive + zero, negative + zero);
    }
}
