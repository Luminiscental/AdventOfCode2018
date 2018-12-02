package com.luminiscental.aoc;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class Day1 {

    public static void main(String[] args) {

        long startTime = System.currentTimeMillis();

        InputStream inputStream = Day1.class.getResourceAsStream("/com/luminiscental/aoc/inputDay1.txt");
        String[] lines = new BufferedReader(new InputStreamReader(inputStream)).lines().toArray(String[]::new);

        boolean repeated = false;
        boolean first = true;
        int firstRepeated = 0;

        Set<Integer> frequencySet = new HashSet<>();
        int resultantFrequency = 0;

        while (!repeated) {

             for (String line : lines) {

                if (!repeated && !frequencySet.add(resultantFrequency)) {

                    firstRepeated = resultantFrequency;
                    repeated = true;
                }

                resultantFrequency += Integer.parseInt(line);
            }

            if (first) {
                first = false;
                System.out.println("The resultant frequency is " + resultantFrequency);
            }
        }

        System.out.println("The first repeated frequency is " + firstRepeated);

        long deltaTime = System.currentTimeMillis() - startTime;
        System.out.println("Program took " + (deltaTime / 1000.0f) + " seconds to run");
    }
}
