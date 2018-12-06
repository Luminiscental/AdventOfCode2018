package com.luminiscental.aoc;

import java.util.HashSet;
import java.util.Set;

public class Day1 extends Day {

    Day1() {

        super(1);
    }

    @Override
    void solve(String[] lines) {

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
    }
}

