package com.luminiscental.aoc;

import com.luminiscental.aoc.util.Point;

import java.util.Comparator;
import java.util.TreeSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day5 extends Day {

    Day5() {

        super(5);
    }

    @Override
    void solve(String[] lines) throws InvalidInputException {

        String input = lines[0];

        System.out.println("original polymer reacts down to " + getReactedLength(input) + " units");

        Set<Integer> shortenedLengths = new TreeSet<>();

        Point<Integer> letter = new Point<>();

        for (char c = 'a'; c <= 'z'; c++) {

            letter.x = (int) c;
            letter.y = (int) Character.toUpperCase(c);

            String fixed = input.chars()
                                .filter(a -> a != letter.x)
                                .filter(a -> a != letter.y)
                                .mapToObj(x -> String.valueOf((char)x))
                                .collect(Collectors.joining());

            shortenedLengths.add(getReactedLength(fixed));
        }

        Optional<Integer> reactedLength = shortenedLengths.stream()
                                                          .min(Comparator.comparingInt(x -> x));

        if (reactedLength.isPresent()) {

            System.out.println("best fixed polymer reacts down to " + reactedLength.get() + " units");

        } else {

            throw new InvalidInputException();
        }
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

        Set<Integer> removed = new TreeSet<>();

        for (int i = 1; i < chainLength; i++) {

            if (!removed.contains(i - 1) && annihilate(units[i], units[i - 1])) {

                removed.add(i);
                removed.add(i - 1);
            }
        }

        return IntStream.range(0, chainLength)
                        .filter(index -> !removed.contains(index))
                        .mapToObj(index -> String.valueOf(units[index]))
                        .collect(Collectors.joining());
    }

    private boolean annihilate(char a, char b) {

        return (Character.toLowerCase(a) == Character.toLowerCase(b)) && (a != b);
    }
}
