package ru.riverx;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Day4 {

    public static final String WORD = "XMAS";

    public static void day4() throws IOException {
        int res = 0;
        int resX = 0;
        int width = 0;
        int height = 0;
        try (BufferedReader reader = Files.newBufferedReader(Path.of("src/main/resources/day4.txt"))) {
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                if (width == 0) {
                    width = line.length();
                }
                sb.append(line);
                height++;
            }
            char[] puzzle = sb.toString().toCharArray();

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    char c = puzzle[y * width + x];
                    if (c == 'A') {
                        res += tryFindWord(puzzle, x, y, width, height);
                    }
                    if (c == 'X') {
                        resX += tryFindWordX(puzzle, 1, x, y, width, height, Direction.ALL);
                    }
                }
            }
        }

        System.out.println(resX);
        System.out.println(res);
    }

    private static int tryFindWord(char[] puzzle, int x, int y, int width, int height) {
        if (x + 1 < width && y - 1 >= 0 && x - 1 >= 0 && y + 1 < height) {
            if (
                       (puzzle[(y - 1) * width + x + 1] == 'M') // TOP RIGHT
                    && (puzzle[(y + 1) * width + x - 1] == 'S') // BOTTOM LEFT
                    && (puzzle[(y - 1) * width + x - 1] == 'S') // TOP LEFT
                    && (puzzle[(y + 1) * width + x + 1] == 'M') // BOTTOM RIGHT
            ) {
                return 1;
            }

            if (
                       (puzzle[(y - 1) * width + x + 1] == 'S') // TOP RIGHT
                    && (puzzle[(y + 1) * width + x - 1] == 'M') // BOTTOM LEFT
                    && (puzzle[(y - 1) * width + x - 1] == 'S') // TOP LEFT
                    && (puzzle[(y + 1) * width + x + 1] == 'M') // BOTTOM RIGHT
            ) {
                return 1;
            }

            if (
                       (puzzle[(y - 1) * width + x + 1] == 'S') // TOP RIGHT
                    && (puzzle[(y + 1) * width + x - 1] == 'M') // BOTTOM LEFT
                    && (puzzle[(y - 1) * width + x - 1] == 'M') // TOP LEFT
                    && (puzzle[(y + 1) * width + x + 1] == 'S') // BOTTOM RIGHT
            ) {
                return 1;
            }

            if (
                       (puzzle[(y - 1) * width + x + 1] == 'M') // TOP RIGHT
                    && (puzzle[(y + 1) * width + x - 1] == 'S') // BOTTOM LEFT
                    && (puzzle[(y - 1) * width + x - 1] == 'M') // TOP LEFT
                    && (puzzle[(y + 1) * width + x + 1] == 'S') // BOTTOM RIGHT
            ) {
                return 1;
            }
        }
        return 0;
    }

    private static int tryFindWordX(char[] puzzle, int next, int x, int y, int width, int height, Direction dir) {
        if (next == WORD.length()) {
            return 1;
        }
        int ac = 0;
        int shift = WORD.length() - next;
        // Top
        if (dir.equals(Direction.ALL) || dir.equals(Direction.TOP)) {
            if (y - shift >= 0 && (puzzle[(y - 1) * width + x] == WORD.charAt(next))) {
                ac += tryFindWordX(puzzle, next + 1, x, y - 1, width, height, Direction.TOP);
            }
        }
        // Top Right
        if (dir.equals(Direction.ALL) || dir.equals(Direction.TOP_RIGHT)) {
            if (x + shift < width && y - shift >= 0 && (puzzle[(y - 1) * width + x + 1] == WORD.charAt(next))) {
                ac += tryFindWordX(puzzle, next + 1, x + 1, y - 1, width, height, Direction.TOP_RIGHT);
            }
        }
        // Right
        if (dir.equals(Direction.ALL) || dir.equals(Direction.RIGHT)) {
            if (x + shift < width && (puzzle[y * width + x + 1] == WORD.charAt(next))) {
                ac += tryFindWordX(puzzle, next + 1, x + 1, y, width, height, Direction.RIGHT);
            }
        }
        // Bottom Right
        if (dir.equals(Direction.ALL) || dir.equals(Direction.BOTTOM_RIGHT)) {
            if (x + shift < width && y + shift < height && (puzzle[(y + 1) * width + x + 1] == WORD.charAt(next))) {
                ac += tryFindWordX(puzzle, next + 1, x + 1, y + 1, width, height, Direction.BOTTOM_RIGHT);
            }
        }
        // Bottom
        if (dir.equals(Direction.ALL) || dir.equals(Direction.BOTTOM)) {
            if (y + shift < height && (puzzle[(y + 1) * width + x] == WORD.charAt(next))) {
                ac += tryFindWordX(puzzle, next + 1, x, y + 1, width, height, Direction.BOTTOM);
            }
        }
        // Bottom Left
        if (dir.equals(Direction.ALL) || dir.equals(Direction.BOTTOM_LEFT)) {
            if (x - shift >= 0 && y + shift < height && (puzzle[(y + 1) * width + x - 1] == WORD.charAt(next))) {
                ac += tryFindWordX(puzzle, next + 1, x - 1, y + 1, width, height, Direction.BOTTOM_LEFT);
            }
        }
        // Left
        if (dir.equals(Direction.ALL) || dir.equals(Direction.LEFT)) {
            if (x - shift >= 0 && (puzzle[y * width + x - 1] == WORD.charAt(next))) {
                ac += tryFindWordX(puzzle, next + 1, x - 1, y, width, height, Direction.LEFT);
            }
        }
        // Top Left
        if (dir.equals(Direction.ALL) || dir.equals(Direction.TOP_LEFT)) {
            if (x - shift >= 0 && y - shift >= 0 && (puzzle[(y - 1) * width + x - 1] == WORD.charAt(next))) {
                ac += tryFindWordX(puzzle, next + 1, x - 1, y - 1, width, height, Direction.TOP_LEFT);
            }
        }
        return ac;
    }

    public enum Direction {
        ALL,
        TOP,
        TOP_RIGHT,
        RIGHT,
        BOTTOM_RIGHT,
        BOTTOM,
        BOTTOM_LEFT,
        LEFT,
        TOP_LEFT
    }
}
