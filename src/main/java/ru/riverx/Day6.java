package ru.riverx;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class Day6 {

    public static final int MAX_STEP = 10000;

    public static void day6() throws IOException {
        Map<String, Boolean> mapRes = new HashMap<>();
        int width = 0;
        int height = 0;
        Map<String, Boolean> map = new HashMap<>();
        try (BufferedReader reader = Files.newBufferedReader(Path.of("src/main/resources/day6.txt"))) {
            Direction direction = Direction.LEFT;
            int[] playerPos = new int[]{0, 0};
            String line;
            while ((line = reader.readLine()) != null) {
                if (width == 0) {
                    width = line.length();
                }
                for (int i = 0; i < line.length(); i++) {
                    char c = line.charAt(i);
                    if (c == '#') {
                        map.put(new String(i+","+height), false);
                    } else if (c == '^' | c == 'v' | c == '<' | c == '>') {
                        playerPos = new int[]{i, height};
                        if (c == '^') {
                            direction = Direction.UP;
                        } else if (c == 'v') {
                            direction = Direction.DOWN;
                        } else if (c == '<') {
                            direction = Direction.LEFT;
                        } else {
                            direction = Direction.RIGHT;
                        }
                    }
                }
                height++;
            }
            doPuzzle(playerPos, width, height, direction, map, mapRes);
        }
        System.out.println(mapRes.size());
    }

    public static void day6star() throws IOException {
        Map<String, Boolean> mapRes = new HashMap<>();
        int res = 0;
        int width = 0;
        int height = 0;
        int[] playerPos = new int[]{0, 0};
        Direction direction = Direction.LEFT;
        Map<String, Boolean> map = new HashMap<>();
        try (BufferedReader reader = Files.newBufferedReader(Path.of("src/main/resources/day6.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (width == 0) {
                    width = line.length();
                }
                for (int i = 0; i < line.length(); i++) {
                    char c = line.charAt(i);
                    if (c == '#') {
                        map.put(new String(i + "," + height), false);
                    } else if (c == '^' | c == 'v' | c == '<' | c == '>') {
                        playerPos = new int[]{i, height};
                        if (c == '^') {
                            direction = Direction.UP;
                        } else if (c == 'v') {
                            direction = Direction.DOWN;
                        } else if (c == '<') {
                            direction = Direction.LEFT;
                        } else {
                            direction = Direction.RIGHT;
                        }
                    }
                }
                height++;
            }
        }

        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                Map<String, Boolean> map2 = new HashMap<>(map);
                map2.put(new String(i + "," + j), true);
                int[] playerPos2 = playerPos.clone();
                if (doPuzzle(playerPos2, width, height, direction, map2, mapRes)) {
                    res++;
                }
            }
        }
        System.out.println(res);
    }

    private static boolean doPuzzle(int[] playerPos, int width, int height, Direction direction, Map<String, Boolean> map, Map<String, Boolean> mapRes) {
        int step = 0;
        while (playerPos[0] >= 0 && playerPos[0] < width - 1 && playerPos[1] >= 0 && playerPos[1] < height - 1 && step < MAX_STEP) {
            switch (direction) {
                case LEFT: {
                    if (map.get(new String((playerPos[0] - 1) + "," + playerPos[1])) != null) {
                        direction = Direction.UP;
                    } else {
                        playerPos[0]--;
                        step++;
                        mapRes.put(new String(playerPos[0] + "," + playerPos[1]), true);
                    }
                    break;
                }
                case RIGHT: {
                    if (map.get(new String((playerPos[0] + 1) +"," + playerPos[1])) != null) {
                        direction = Direction.DOWN;
                    } else {
                        playerPos[0]++;
                        step++;
                        mapRes.put(new String(playerPos[0] + "," + playerPos[1]), true);
                    }
                    break;
                }
                case UP: {
                    if (map.get(new String(playerPos[0] + "," + (playerPos[1] - 1))) != null) {
                        direction = Direction.RIGHT;
                    } else {
                        playerPos[1]--;
                        step++;
                        mapRes.put(new String(playerPos[0] + "," + playerPos[1]), true);
                    }
                    break;
                }
                case DOWN: {
                    if (map.get(new String(playerPos[0] + "," + (playerPos[1] + 1))) != null) {
                        direction = Direction.LEFT;
                    } else {
                        playerPos[1]++;
                        step++;
                        mapRes.put(new String(playerPos[0] + "," + playerPos[1]), true);
                    }
                    break;
                }
                default: {
                    System.out.println("ERR");
                }
            }
        }
        return step == MAX_STEP;
    }

    enum Direction {
        LEFT,
        RIGHT,
        UP,
        DOWN
    }
}
