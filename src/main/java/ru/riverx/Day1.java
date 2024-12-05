package ru.riverx;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day1 {

    public static void day1() throws IOException {
        List<Integer> first = new ArrayList<>();
        List<Integer> second = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(Path.of("src/main/resources/day1.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] split = line.split("   ");
                first.add(Integer.parseInt(split[0]));
                second.add(Integer.parseInt(split[1]));
            }
        }
        Collections.sort(first);
        Collections.sort(second);

        int sum = 0;
        assert first.size() == second.size();
        for (int i = 0; i < first.size(); i++) {
            sum += Math.abs(first.get(i) - second.get(i));
        }

        System.out.println(sum);
    }

    public static void day1star() throws IOException {
        List<Integer> first = new ArrayList<>();
        List<Integer> second = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(Path.of("src/main/resources/day1.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] split = line.split("   ");
                first.add(Integer.parseInt(split[0]));
                second.add(Integer.parseInt(split[1]));
            }
        }

        Collections.sort(first);
        Collections.sort(second);

        int sum = 0;
        assert first.size() == second.size();
        for (int num1 : first) {
            for (Integer integer : second) {
                if (num1 == integer) {
                    sum += num1;
                }
            }
        }

        System.out.println(sum);
    }
}
