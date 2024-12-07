package ru.riverx;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Day7 {

    public static void day7() throws IOException {
        long res = 0;
        try (BufferedReader reader = Files.newBufferedReader(Path.of("src/main/resources/day7.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] split = line.split(": ");
                long value = Long.parseLong(split[0]);

                String[] operandsStrings = split[1].trim().split("\\s+");
                long[] operands = new long[operandsStrings.length];
                for (int i = 0; i < operands.length; i++) {
                    operands[i] = Long.parseLong(operandsStrings[i]);
                }

                if (isEval(value, operands, operands[0], 1)) {
                    res += value;
                }
            }
        }

        System.out.println(res);
    }

    private static boolean isEval(long value, long[] operands, long acc, int index) {
        if (index == operands.length) {
            return acc == value;
        }

        return isEval(value, operands, acc + operands[index], index + 1)
                || isEval(value, operands, acc * operands[index], index + 1)
                // Remove next line for part 1
                || isEval(value, operands, Long.parseLong(String.valueOf(acc) + operands[index]), index + 1);
    }
}
