package com.luminiscental.aoc;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class Day1 {

    public static void main(String[] args) {

        InputStream inputStream = Day1.class.getResourceAsStream("/com/luminiscental/aoc/inputDay1.txt");
        Stream<String> frequencyChanges = new BufferedReader(new InputStreamReader(inputStream)).lines();

        var wrapper = new Object() {

            boolean repeated = false;
            int firstRepeated;

            Set<Integer> frequencySet = new HashSet<>();
            int resultantFrequency = 0;
        };

        String[] lines = frequencyChanges.toArray(String[]::new);

        while (!wrapper.repeated) {
             for (String line : lines) {

                wrapper.frequencySet.add(wrapper.resultantFrequency);
                wrapper.resultantFrequency += Integer.parseInt(line);

                if (!wrapper.repeated && wrapper.frequencySet.contains(wrapper.resultantFrequency)) {

                    wrapper.firstRepeated = wrapper.resultantFrequency;
                    wrapper.repeated = true;
                }
            }
        }

        System.out.println("The first repeated frequency is " + wrapper.firstRepeated);
    }
}
