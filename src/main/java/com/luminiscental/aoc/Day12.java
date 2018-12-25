package com.luminiscental.aoc;

import org.apache.commons.collections4.list.TreeList;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;


public class Day12 extends Day {

    static class State {

        Set<Integer> plants = new TreeSet<>();

        int minIndex = Integer.MAX_VALUE;
        int maxIndex = Integer.MIN_VALUE;

        State(String line) {

            for (int i = 0; i < line.length(); i++) {

                if (line.charAt(i) == '#') {

                    add(i);
                }
            }
        }

        void add(int index) {

            plants.add(index);

            if (index < minIndex) {

                minIndex = index;

            } else if (index > maxIndex) {

                maxIndex = index;
            }
        }

        void remove(int index) {

            plants.remove(index);

            if (index == minIndex) {

                minIndex = Collections.min(plants);

            } else if (index == maxIndex) {

                maxIndex = Collections.max(plants);
            }
        }

        boolean hasPlant(int index) {

            return plants.contains(index);
        }

        boolean matches(Boolean[] rule, int index) {

            for (int i = 0; i < 5; i++) {

                if (rule[i] != hasPlant(index + i - 2)) {

                    return false;
                }
            }

            return true;
        }

        void generate(List<Boolean[]> rules) {

            Set<Integer> newPlants = new TreeSet<>();

            int startIndex = minIndex - 2;
            int endIndex = maxIndex + 2;

            for (Boolean[] rule : rules) {

                for (int i = startIndex; i <= endIndex; i++) {

                    if (matches(rule, i)) {

                        newPlants.add(i);
                    }
                }
            }

            for (int i = startIndex; i <= endIndex; i++) {

                if (newPlants.contains(i)) {

                    add(i);

                } else if (hasPlant(i)) {

                    remove(i);
                }
            }
        }

        int checksum() {

            return plants.stream().mapToInt(x -> x).sum();
        }
    }

    Day12() {

        super(12);
    }

    @Override
    void solve(String[] lines) throws InvalidInputException {

        State state = new State(lines[0].substring(15));

        List<Boolean[]> rules = new TreeList<>();

        for (int i = 2; i < lines.length; i++) {

            Boolean[] rule = new Boolean[5];

            if (lines[i].length() < 10) continue;

            if (lines[i].charAt(9) == '#') {

                for (int j = 0; j < 5; j++) {

                    rule[j] = lines[i].charAt(j) == '#';
                }

                rules.add(rule);
            }
        }

        long checksum = state.checksum();
        long lastDiff = Long.MIN_VALUE; // random value that won't happen at the start

        for (long generation = 1; generation <= 50_000_000_000L; generation++) {

            state.generate(rules);

            long newChecksum = state.checksum();
            long diff = newChecksum - checksum;
            checksum = newChecksum;

            if (generation == 20) {

                System.out.println("Checksum at 20 is " + checksum);
            }

            if (diff == lastDiff) { // Becomes a linear increase

                checksum += diff * (50_000_000_000L - generation);
                break;

            } else {

                lastDiff = diff;
            }
        }

        System.out.println("After 50 billion generations the checksum is " + checksum);
    }
}
