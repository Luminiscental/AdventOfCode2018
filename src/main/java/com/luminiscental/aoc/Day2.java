package com.luminiscental.aoc;

import java.util.*;
import java.util.stream.Collectors;

public class Day2 extends Day {

    Day2() {

        super(2);
    }

    @Override
    void solve(String[] lines) {

        int doubleCount = 0;
        int tripleCount = 0;

        for (String line : lines) {

            Collection<Integer> characterCounts = countCharacters(line);

            doubleCount += characterCounts.contains(2) ? 1 : 0;
            tripleCount += characterCounts.contains(3) ? 1 : 0;
        }

        for (int i = 0; i < lines.length; i++) {

            for (int j = 0; j < i; j++) {

                String line = lines[i];
                String other = lines[j];

                int differAt = oneOff(line, other);

                if (differAt > -1) {

                    String charRemoved = line.substring(0, differAt) + line.substring(differAt + 1);
                    System.out.println("The ids \"" + line + "\" and \"" + other + "\" differ once, leaving \"" + charRemoved + "\"");
                }
            }
        }

        System.out.println("Checksum = " + doubleCount * tripleCount);
    }

    private Collection<Integer> countCharacters(String word) {

          return word   .chars()
                        .mapToObj(x -> (char)x)
                        .collect(Collectors.toMap(c -> c, c -> 1, (a, b) -> a + b))
                        .values();

    }

    private int oneOff(String a, String b) {

        if (a.length() != b.length()) {

            return -1;

        } else {

            for (int i = 0; i < a.length(); i++) {

                StringBuilder test = new StringBuilder(a);
                test.setCharAt(i, b.charAt(i));

                if (test.toString().equals(b)) {

                    return i;
                }
            }

            return -1;
        }
    }
}
