package ru.riverx;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class Day8 {

    private static int size = 0;
    private static final Map<Coordinate, Character> locMap = new HashMap<>();
    private static final Map<Character, Integer> charCountMap = new HashMap<>();
    private static final Map<Coordinate, Character> uniqueLocMap2 = new HashMap<>();

    /**
     * This method reads a file line by line and stores each line in a map where
     * the key is a coordinate and the value is the character at that coordinate
     * in the map. It also counts the number of occurrences of each character,
     * and if a character occurs more than once, it calculates the 'antinode'
     * (the coordinate which is equidistant from all occurrences of that character)
     * and stores it in a second map. Finally, it prints out the number of
     * antinodes.
     *
     * @throws IOException if there is an error reading the file
     */
    public static void day8() throws IOException {

        try (BufferedReader reader = Files.newBufferedReader(Path.of("src/main/resources/day8.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                processLine(line, size++);
            }
        }

        for (Map.Entry<Character, Integer> entry : charCountMap.entrySet()) {
            if (entry.getValue() > 1) {
                calculateAntinode(entry.getKey());
            }
        }

        System.out.println(uniqueLocMap2.size());
    }

    private static void processLine(String line, int y) {
        for (int x = 0; x < line.length(); x++) {
            char c = line.charAt(x);
            if (c != '.') {
                charCountMap.merge(c, 1, Integer::sum);
                locMap.put(new Coordinate(x, y), c);
            }
        }
    }

    private static void calculateAntinode(Character c) {
        Set<Coordinate> uniqueLocSet = new HashSet<>();
        List<Coordinate> pointList = new ArrayList<>();

        locMap.forEach((k, v) -> {
            if (v.equals(c)) {
                pointList.add(k);
            }
        });

        int listSize = pointList.size();

        for (int i = 0; i < listSize; i++) {
            Coordinate p1 = pointList.get(i);
            for (int j = 0; j < listSize; j++) {
                if (i == j) { continue; } // Skip self

                Coordinate p2 = pointList.get(j);
                int dx = p1.x() - p2.x();
                int dy = p1.y() - p2.y();

                int dx1 = p1.x() + dx;
                int dy1 = p1.y() + dy;

                while (dx1 >= 0 && dy1 >= 0 && dx1 < size && dy1 < size) {
                    uniqueLocSet.add(new Coordinate(dx1, dy1));
                    dx1 += dx;
                    dy1 += dy;
                    //break; // Uncomment for part 1
                }

            }
        }

        // Comment out this if for part 1
        if (listSize > 2) {
            uniqueLocSet.addAll(pointList);
        }

        uniqueLocMap2.putAll(uniqueLocSet.stream().collect(Collectors.toMap(k -> k, v -> c)));
    }

    private record Coordinate(int x, int y) {

        @Override
            public boolean equals(Object obj) {
                if (this == obj) return true;
                if (!(obj instanceof Coordinate other)) return false;
                return this.x == other.x && this.y == other.y;
            }

    }
}
