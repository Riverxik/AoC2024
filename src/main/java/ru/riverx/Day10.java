package ru.riverx;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Day10 {

    private static final List<Point> finalPointList = new ArrayList<>();

    public static void day10() throws IOException {
        List<Point> pointList = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(Path.of("src/main/resources/day10.txt"))) {
            String line;
            int y = 0;
            while ((line = reader.readLine()) != null) {
                for (int i = 0; i < line.length(); i++) {
                    char c = line.charAt(i);
                    int value = -1;
                    if (c != '.') {
                        value = Integer.parseInt(String.valueOf(c));
                    }
                    pointList.add(new Point(i, y, value));
                }
                y++;
            }
        }

        int score = 0;
        for (Point p : pointList) {
            if (p.value == 0) {
                score += checkNeighbors(pointList, p);
                finalPointList.clear();
            }
        }

        System.out.println(score);
    }

    private static int checkNeighbors(List<Point> pointList, Point start) {
        if (start.value == 9) {
            // Uncomment for part 1
//            if (!finalPointList.contains(start)) {
//                finalPointList.add(start);
//                return 1;
//            }
            return 1; // Change to return 0 for part 1
        }
        int res = 0;
        int x = start.x;
        int y = start.y;
        int value = start.value;
        for (Point p : pointList) {
            if (
                    (p.x == x + 1 && p.y == y
                    || p.x == x - 1 && p.y == y
                    || p.x == x && p.y == y + 1
                    || p.x == x && p.y == y - 1)
                            && p.value == value + 1) {
                    res += checkNeighbors(pointList, p);
                }
        }

        return res;
    }

    static class Point {
        int x;
        int y;
        int value;

        public Point(int x, int y, int value) {
            this.x = x;
            this.y = y;
            this.value = value;
        }
    }
}
