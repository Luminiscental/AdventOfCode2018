package com.luminiscental.aoc;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day5 extends Day {

    public static void main(String[] args) {

        new Day5().run();
    }

    private Day5() {

        super("inputDay5.txt");
    }

    @Override
    void solve(String[] lines) {

        String input = lines[0];

        System.out.println("original polymer reacts down to " + getReactedLength(input) + " units");

        Set<Integer> shortenedLengths = new HashSet<>();

        var charWrapper = new Object() {
            char lower;
            char upper;
        };

        for (char c = 'a'; c <= 'z'; c++) {

            charWrapper.lower = c;
            charWrapper.upper = Character.toUpperCase(c);

            String fixed = input.chars().filter(x -> (x != (int)charWrapper.lower) && (x != (int)charWrapper.upper)).mapToObj(x -> String.valueOf((char)x)).collect(Collectors.joining());
            shortenedLengths.add(getReactedLength(fixed));
        }

        System.out.println("best fixed polymer reacts down to " + shortenedLengths.stream().mapToInt(x -> x).min().getAsInt() + " units");
    }

    private int getReactedLength(String polymer) {

        String reactant = polymer;
        String last;

        do {

            last = reactant;
            reactant = autoReact(reactant);

        } while (!last.equals(reactant));

        return reactant.length();
    }

    private String autoReact(String polymer) {

        char[] units = polymer.toCharArray();
        int chainLength = polymer.length();

        Set<Integer> removed = new HashSet<>();

        for (int i = 1; i < chainLength; i++) {

            if (!removed.contains(i - 1) && annihilate(units[i], units[i - 1])) {

                removed.add(i);
                removed.add(i - 1);
            }
        }

        return IntStream.range(0, chainLength).filter(index -> !removed.contains(index)).mapToObj(index -> String.valueOf(units[index])).collect(Collectors.joining());
    }

    private boolean annihilate(char a, char b) {
        return (Character.toLowerCase(a) == Character.toLowerCase(b)) && (a != b);
    }
}