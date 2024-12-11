package ru.riverx;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day11 {

    public static void day11() throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(Path.of("src/main/resources/day11.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                processLine(line);
            }
        }
    }

    private static void processLine(String line) {
        List<Long> numbers = new ArrayList<>(Arrays.stream(line.split("\\s+")).map(Long::parseLong).toList());
        List<Long> next = new ArrayList<>();
        int blinkCount = 0;
        int size;
        while (blinkCount < 25) {
            for (long n : numbers) {
                if (n == 0) {
                    next.add(1L);
                } else if ((size = getSizeFromNum(n)) % 2 == 0) {
                    next.add(getRoundLeftFromNum(n, size / 2));
                    next.add(getRoundRightFromNum(n, size / 2));
                } else {
                    next.add(n * 2024);
                }
            }

            numbers.clear();
            numbers.addAll(next);
            next.clear();
            blinkCount++;
        }

        System.out.println(numbers.size());
    }

    private static long getRoundLeftFromNum(Long num, int size) {
        for (int i = 0; i < size; i++) {
            num /= 10;
        }
        return num;
    }

    private static long getRoundRightFromNum(Long num, int size) {
        long res = 0;
        for (int i = 0; i < size; i++) {
            res += num % 10 * Math.pow(10, i);
            num /= 10;
        }
        return res;
    }

    private static int getSizeFromNum(Long num) {
        int size = 0;
        while (num > 0) {
            size++;
            num /= 10;
        }
        return size;
    }
}
