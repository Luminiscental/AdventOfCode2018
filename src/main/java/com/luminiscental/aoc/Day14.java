package com.luminiscental.aoc;

import org.apache.commons.collections4.list.TreeList;

import java.util.ArrayList;
import java.util.List;

public class Day14 extends Day {

    Day14() {

        super(14);
    }

    @Override
    void solve(String[] lines) throws InvalidInputException {

        int input = Integer.parseInt(lines[0]);

        part1(input);
        part2(input);
    }

    private void part1(int input) {

        List<Integer> scores = new ArrayList<>();

        scores.add(3);
        scores.add(7);

        int elf1 = 0;
        int elf2 = 1;

        while (scores.size() < input + 10) {

            int sum = scores.get(elf1) + scores.get(elf2);

            if (sum > 9) {

                int score1 = sum / 10;
                int score2 = sum % 10;

                scores.add(score1);
                scores.add(score2);

            } else {

                scores.add(sum);
            }

            int count = scores.size();

            elf1 = (elf1 + 1 + scores.get(elf1)) % count;
            elf2 = (elf2 + 1 + scores.get(elf2)) % count;
        }

        List<Integer> backwards = scores.subList(input, input + 10);

        System.out.print("Scores: ");

        for (int score : backwards) {

            System.out.print(score);
        }

        System.out.println();
    }

    private void part2(int input) {

        List<Integer> pattern = new ArrayList<>();

        for (char digit : String.valueOf(input).toCharArray()) {

            pattern.add(Integer.parseInt(String.valueOf(digit)));
        }

        int patternSize = pattern.size();

        List<Integer> scores = new TreeList<>();

        scores.add(3);
        scores.add(7);

        int elf1 = 0;
        int elf2 = 1;

        boolean notFound = true;

        while (notFound) {

            int sum = scores.get(elf1) + scores.get(elf2);

            boolean twoDigits = false;

            if (sum > 9) {

                twoDigits = true;

                int score1 = sum / 10;
                int score2 = sum % 10;

                scores.add(score1);
                scores.add(score2);

            } else {

                scores.add(sum);
            }

            int count = scores.size();

            elf1 = (elf1 + 1 + scores.get(elf1)) % count;
            elf2 = (elf2 + 1 + scores.get(elf2)) % count;

            if (count < patternSize) continue;

            int index = count - patternSize;

            if (scores.subList(index, count).equals(pattern)) {

                notFound = false;
                System.out.println("Pattern found at index " + index);

            }

            if (count == patternSize || !twoDigits) continue;

            if (scores.subList(index - 1, count - 1).equals(pattern)) {

                notFound = false;
                System.out.println("Pattern found at index " + (index - 1));
            }
        }
    }
}
