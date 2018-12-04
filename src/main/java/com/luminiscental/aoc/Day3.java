package com.luminiscental.aoc;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Day3 extends Day {

    public static void main(String[] args) {

        new Day3().run();
    }

    private Day3() {

        super("inputDay3.txt");
    }

    @Override
    void solve(String[] lines) {

        Map<Integer, Set<Integer>> grid = new HashMap<>();
        Set<Integer> validIds = new HashSet<>();

        for (String line : lines) {

            String[] tokens = line.split("[# ,x:]");

            int id = Integer.parseInt(tokens[1]);
            int rectX = Integer.parseInt(tokens[3]);
            int rectY = Integer.parseInt(tokens[4]);
            int rectWidth = Integer.parseInt(tokens[6]);
            int rectHeight = Integer.parseInt(tokens[7]);

            validIds.add(id);

            for (int y = rectY; y < rectY + rectHeight; y++) {

                for (int x = rectX; x < rectX + rectWidth; x++) {

                    int index = x + y * 1000;

                    if (grid.containsKey(index)) {

                        grid.get(index).add(id);

                    } else {

                        Set<Integer> ids = new HashSet<>();
                        ids.add(id);
                        grid.put(index, ids);
                    }
                }
            }
        }

        checkOverlaps(grid, validIds);
    }

    private void checkOverlaps(Map<Integer, Set<Integer>> claims, Set<Integer> unoverlappedIds) {

        int overlaps = 0;
        int covered = 0;

        for (var entry : claims.entrySet()) {

            Set<Integer> ids = entry.getValue();

            if (ids.size() > 1) {

                overlaps++;
                unoverlappedIds.removeAll(ids);
            }

            covered++;
        }

        System.out.println("overlaps = " + overlaps + ", covered = " + covered);

        for (int id : unoverlappedIds) {

            System.out.println("#" + id + " isn't overlapped");
        }
    }
}