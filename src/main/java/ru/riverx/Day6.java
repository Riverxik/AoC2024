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

            boolean isMoved = false;
            switch (direction) {
                case LEFT: {
                    keyBuilder.append(playerPos[0] - 1).append(",").append(playerPos[1]);
                    if (map.get(keyBuilder.toString()) != null) {
                        direction = Direction.UP;
                    } else {
                        playerPos[0]--;
                        isMoved = true;
                    }
                    break;
                }
                case RIGHT: {
                    keyBuilder.append(playerPos[0] + 1).append(",").append(playerPos[1]);
                    if (map.get(keyBuilder.toString()) != null) {
                        direction = Direction.DOWN;
                    } else {
                        playerPos[0]++;
                        isMoved = true;
                    }
                    break;
                }
                case UP: {
                    keyBuilder.append(playerPos[0]).append(",").append(playerPos[1] - 1);
                    if (map.get(keyBuilder.toString()) != null) {
                        direction = Direction.RIGHT;
                    } else {
                        playerPos[1]--;
                        isMoved = true;
                    }
                    break;
                }
                case DOWN: {
                    keyBuilder.append(playerPos[0]).append(",").append(playerPos[1] + 1);
                    if (map.get(keyBuilder.toString()) != null) {
                        direction = Direction.LEFT;
                    } else {
                        playerPos[1]++;
                        isMoved = true;
                    }
                    break;
                }
                default: {
                    throw new IllegalStateException(UNEXPECTED_VALUE + direction);
                }
            }
            if (isMoved) {
                step++;
                mapRes.put(keyBuilder.toString(), true);
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
