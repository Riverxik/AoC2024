package ru.riverx;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

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
        List<Long> numbers = new LinkedList<>(Arrays.stream(line.split("\\s+")).map(Long::parseLong).toList());
        blinkStoneTimes(numbers, 75);
    }

    private static void blinkStoneTimes(List<Long> stones, int steps) {
        Map<Long, BigInteger> stoneCounts = new HashMap<>();

        for (Long stone : stones) {
            stoneCounts.put(stone, stoneCounts.getOrDefault(stone, BigInteger.ZERO).add(BigInteger.ONE));
        }

        for (int step = 0; step < steps; step++) {
            Map<Long, BigInteger> newStoneCounts = new HashMap<>();

            for (Map.Entry<Long, BigInteger> entry : stoneCounts.entrySet()) {
                long stone = entry.getKey();
                BigInteger count = entry.getValue();

                processStone(stone, count, newStoneCounts);
            }

            stoneCounts = newStoneCounts;
        }

        BigInteger result = BigInteger.ZERO;
        for (Map.Entry<Long, BigInteger> entry : stoneCounts.entrySet()) {
            BigInteger count = entry.getValue();
            result = result.add(count);
        }
        System.out.println("Result: " + result);
    }

    private static void processStone(long stone, BigInteger count, Map<Long, BigInteger> newStoneCounts) {
        String strStone = Long.toString(stone);

        if (stone == 0) {
            newStoneCounts.put(1L, newStoneCounts.getOrDefault(1L, BigInteger.ZERO).add(count));
            return;
        }

        if (strStone.length() % 2 == 0) {
            int mid = strStone.length() / 2;
            String leftPart = strStone.substring(0, mid);
            String rightPart = strStone.substring(mid);

            if (!leftPart.isEmpty()) {
                long leftStone = Long.parseLong(leftPart);
                newStoneCounts.put(leftStone, newStoneCounts.getOrDefault(leftStone, BigInteger.ZERO).add(count));
            }
            if (!rightPart.isEmpty()) {
                long rightStone = Long.parseLong(rightPart);
                newStoneCounts.put(rightStone, newStoneCounts.getOrDefault(rightStone, BigInteger.ZERO).add(count));
            }
            return;
        }

        long newStone = stone * 2024;
        newStoneCounts.put(newStone, newStoneCounts.getOrDefault(newStone, BigInteger.ZERO).add(count));
    }
}
