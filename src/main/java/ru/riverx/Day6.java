package ru.riverx;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class Day6 {

    public static final int MAX_STEP = 10000;
    public static final String UNEXPECTED_VALUE = "Unexpected value: ";

    private static final int[][] directionsDeltas = {
            {0, -1},    // UP
            {1, 0},     // RIGHT
            {0, 1},     // DOWN
            {-1, 0},    // LEFT
    };

    public static void day6() throws IOException {
        Map<String, Boolean> mapRes = new HashMap<>();
        int mapSize = 0;
        Map<String, Boolean> map = new HashMap<>();
        try (BufferedReader reader = Files.newBufferedReader(Path.of("src/main/resources/day6.txt"))) {
            Direction direction = Direction.LEFT;
            int[] playerPos = new int[]{0, 0};
            String line;
            while ((line = reader.readLine()) != null) {
                for (int i = 0; i < line.length(); i++) {
                    char c = line.charAt(i);
                    if (c == '#') {
                        String key = i + "," + mapSize;
                        map.put(key, false);
                    } else if ("^v<>".indexOf(c) != -1) {
                        playerPos = new int[]{i, mapSize};
                        direction = switch (c) {
                            case '^' -> Direction.UP;
                            case 'v' -> Direction.DOWN;
                            case '<' -> Direction.LEFT;
                            case '>' -> Direction.RIGHT;
                            default -> throw new IllegalStateException(UNEXPECTED_VALUE + c);
                        };
                    }
                }
                mapSize++;
            }
            doPuzzle(playerPos, mapSize, direction, map, mapRes);
        }
        System.out.println(mapRes.size());
    }

    public static void day6star() throws IOException {
        Map<String, Boolean> mapRes = new HashMap<>();
        int res = 0;
        int mapSize = 0;
        int[] playerPos = new int[]{0, 0};
        Direction direction = Direction.LEFT;
        Map<String, Boolean> map = new HashMap<>();
        try (BufferedReader reader = Files.newBufferedReader(Path.of("src/main/resources/day6.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                for (int i = 0; i < line.length(); i++) {
                    char c = line.charAt(i);
                    if (c == '#') {
                        String key = i + "," + mapSize;
                        map.put(key, false);
                    } else if ("^v<>".indexOf(c) != -1) {
                        playerPos = new int[]{i, mapSize};
                        direction = switch (c) {
                            case '^' -> Direction.UP;
                            case 'v' -> Direction.DOWN;
                            case '<' -> Direction.LEFT;
                            case '>' -> Direction.RIGHT;
                            default -> throw new IllegalStateException(UNEXPECTED_VALUE + c);
                        };
                    }
                }
                mapSize++;
            }
        }

        for (int j = 0; j < mapSize; j++) {
            for (int i = 0; i < mapSize; i++) {
                Map<String, Boolean> map2 = new HashMap<>(map);

                String key = i + "," + j;
                map2.put(key, true);

                int[] playerPos2 = playerPos.clone();

                if (doPuzzle(playerPos2, mapSize, direction, map2, mapRes)) {
                    res++;
                }
            }
        }
        System.out.println(res);
    }

    private static boolean doPuzzle(int[] playerPos, int mapSize, Direction direction, Map<String, Boolean> map, Map<String, Boolean> mapRes) {
        int step = 0;
        StringBuilder keyBuilder = new StringBuilder(10);

        while (playerPos[0] >= 0 && playerPos[0] < mapSize - 1 && playerPos[1] >= 0 && playerPos[1] < mapSize - 1 && step < MAX_STEP) {
            keyBuilder.setLength(0);

            int deltaX = directionsDeltas[direction.ordinal()][0];
            int deltaY = directionsDeltas[direction.ordinal()][1];

            String key = keyBuilder.append(playerPos[0] + deltaX).append(",").append(playerPos[1] + deltaY).toString();
            if (map.get(key) != null) {
                direction = Direction.values()[(direction.ordinal() + 1) % 4];
            } else {
                playerPos[0] += deltaX;
                playerPos[1] += deltaY;
                step++;
                mapRes.put(key, true);
            }
        }
        return step == MAX_STEP;
    }

    enum Direction {
        UP,
        RIGHT,
        DOWN,
        LEFT
    }
}
