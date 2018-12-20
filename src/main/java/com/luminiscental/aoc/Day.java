package com.luminiscental.aoc;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

abstract class Day {

    private final int day;

    Day(int day) {

        this.day = day;
    }

    abstract void solve(String[] lines);

    void run() {

        InputStream inputStream = Day1.class.getResourceAsStream("/com/luminiscental/aoc/inputDay" + day + ".txt");
        var lines = new BufferedReader(new InputStreamReader(inputStream)).lines();

        System.out.println("## Day " + day + " Solution ##\n");

        long startTime = System.currentTimeMillis();

        solve(lines.toArray(String[]::new));

        long deltaTime = System.currentTimeMillis() - startTime;
        System.out.println("\ntook " + (deltaTime / 1000.0f) + " seconds to run\n");
    }

    public static void main(String[] args) {

        // new Day1().run();
        // new Day2().run();
        // new Day3().run();
        // new Day4().run();
        // new Day5().run();
        // new Day6().run();
        new Day7().run();
    }
}
