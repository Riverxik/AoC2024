package ru.riverx;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3 {

    public static void day3() throws IOException {
        int res = 0;
        String raw;
        try (BufferedReader reader = Files.newBufferedReader(Path.of("src/main/resources/day3.txt"))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            raw = sb.toString();
        }

        Pattern p = Pattern.compile("[m][u][l]\\(\\d{1,3},\\d{1,3}\\)");
        Pattern pn = Pattern.compile("\\d{1,3},\\d{1,3}");
        Matcher m2;
        Matcher matcher = p.matcher(raw);

        while (matcher.find()) {
            String mRaw = matcher.group();
            m2 = pn.matcher(mRaw);
            while (m2.find()) {
                String[] split = m2.group().split(",");
                res += Integer.parseInt(split[0]) * Integer.parseInt(split[1]);
            }

        }

        System.out.println(res);
    }

    public static void day3star() throws IOException {
        int res = 0;
        boolean isMul = true;
        try (BufferedReader reader = Files.newBufferedReader(Path.of("src/main/resources/day3.txt"))) {
            String line;
            Pattern p = Pattern.compile("(mul\\(\\d+,\\d+\\))|(do\\(\\))|(don't\\(\\))");
            while ((line = reader.readLine()) != null) {
                Matcher m = p.matcher(line);
                while (m.find()) {
                    String s = m.group();
                    if (s.equals("do()")) {
                        isMul = true;
                    } else if (s.equals("don't()")) {
                        isMul = false;
                    } else {
                        s = s.substring(4, s.length() - 1);
                        String[] split = s.split(",");
                        if (isMul) {
                            res += (Integer.parseInt(split[0]) * Integer.parseInt(split[1]));
                        }
                    }
                }
            }
        }

        System.out.println(res);
    }
}
