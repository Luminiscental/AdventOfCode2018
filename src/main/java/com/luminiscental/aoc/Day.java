package com.luminiscental.aoc;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

abstract class Day {

    private final String inputFile;

    Day(String input) {

        inputFile = input;
    }

    abstract void solve(String[] lines);

    void run() {

        InputStream inputStream = Day1.class.getResourceAsStream("/com/luminiscental/aoc/" + inputFile);
        String[] lines = new BufferedReader(new InputStreamReader(inputStream)).lines().toArray(String[]::new);

        long startTime = System.currentTimeMillis();

        solve(lines);

        long deltaTime = System.currentTimeMillis() - startTime;
        System.out.println("Program took " + (deltaTime / 1000.0f) + " seconds to run");
    }
}
