package com.luminiscental.aoc;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

abstract class Day {

    private final String inputFile;

    Day(int day) {

        inputFile = "inputDay" + day + ".txt";
    }

    abstract void solve(String[] lines);

    void run() {

        InputStream inputStream = Day1.class.getResourceAsStream("/com/luminiscental/aoc/" + inputFile);
        var lines = new BufferedReader(new InputStreamReader(inputStream)).lines();

        long startTime = System.currentTimeMillis();

        solve(lines.toArray(String[]::new));

        long deltaTime = System.currentTimeMillis() - startTime;
        System.out.println("Program took " + (deltaTime / 1000.0f) + " seconds to run");
    }

    public static void main(String[] args) {

        // new Day1().run();
        // new Day2().run();
        // new Day3().run();
        // new Day4().run();
        // new Day5().run();
        new Day6().run();
    }
}
