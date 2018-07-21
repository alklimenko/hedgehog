package ru.avklimenko.hedgehog;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class Utils {
    private Utils() {
        //
    }

    public static Map<String, Object> map(Object... args) {
        HashMap<String, Object> resultMap = new HashMap<>();
        if (args.length % 2 != 0) {
            throw new IllegalArgumentException("Count of arguments must be even!");
        }

        for (int i = 0; i < args.length; i += 2) {
            resultMap.put(args[i].toString(), args[i + 1]);
        }

        return resultMap;
    }

    public static boolean delay(int millis, int quantum) {
        while (millis > 0) {
            quantum = Math.min(millis, quantum);
            millis -= quantum;
            try {
                Thread.sleep(quantum);
            } catch (InterruptedException e) {
                return false;
            }
        }
        return true;
    }

    public static boolean delay(int millis) {
        return delay(millis, 10);
    }

    public static String getTextFileContent(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            return sb.toString();
        } catch (IOException e) {
            return null;
        }
    }
}
