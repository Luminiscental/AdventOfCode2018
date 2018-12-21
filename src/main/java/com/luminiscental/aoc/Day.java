package com.luminiscental.aoc;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

abstract class Day {

    static class InvalidInputException extends RuntimeException {

        InvalidInputException() {

            super("Input was invalid!");
        }
    }

    private final int day;

    Day(int day) {

        this.day = day;
    }

    abstract void solve(String[] lines) throws InvalidInputException;

    void run() {

        InputStream inputStream = Day1.class.getResourceAsStream("/com/luminiscental/aoc/inputDay" + day + ".txt");
        var lines = new BufferedReader(new InputStreamReader(inputStream)).lines();

        System.out.println("## Day " + day + " Solution ##\n");

        long startTime = System.currentTimeMillis();

        try {

            solve(lines.toArray(String[]::new));

        } catch(InvalidInputException e) {

            System.out.println("Could not solve; input exception thrown\n" + e.getMessage());
        }

        long deltaTime = System.currentTimeMillis() - startTime;
        System.out.println("\n -- Took " + (deltaTime / 1000.0f) + " seconds to run\n");
    }

    public static void main(String[] args) {

        System.out.println();

        // new Day1().run();
        // new Day2().run();
        // new Day3().run();
        // new Day4().run();
        // new Day5().run();
        // new Day6().run();
        // new Day7().run();
        // new Day8().run();
        // new Day9().run();
        // new Day10().run();
        new Day11().run();
    }
}
