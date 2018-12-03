package com.luminiscental.aoc;

import java.util.HashMap;
import java.util.Map;

public class Day2 extends Day {

    public static void main(String[] args) {

        new Day2().run();
    }

    private Day2() {

        super("inputDay2.txt");
    }

    @Override
    void solve(String[] lines) {

        int doubleCount = 0;
        int tripleCount = 0;

        for (int i = 0; i < lines.length; i++) {

            String line = lines[i];

            if (containsDuplicate(line, 2)) {

                doubleCount++;
            }

            if (containsDuplicate(line, 3)) {

                tripleCount++;
            }

            for (int j = 0; j < i; j++) {

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

    private boolean containsDuplicate(String word, int n) {

        Map<Character, Integer> characterCounts = new HashMap<>();

        for (char c : word.toCharArray()) {

            if (characterCounts.containsKey(c)) {

                characterCounts.put(c, characterCounts.get(c) + 1);

            } else {

                characterCounts.put(c, 1);
            }
        }

        for (int count : characterCounts.values()) {

            if (count == n) {

                return true;
            }
        }

        return false;
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
