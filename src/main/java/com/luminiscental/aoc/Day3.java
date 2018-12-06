package com.luminiscental.aoc;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Day3 extends Day {

    Day3() {

        super(3);
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

                    Set<Integer> val = grid.getOrDefault(index, new HashSet<>());
                    val.add(id);
                    grid.put(index, val);
                }
            }
        }

        checkOverlaps(grid, validIds);
    }

    private void checkOverlaps(Map<Integer, Set<Integer>> claims, Set<Integer> unoverlappedIds) {

        int overlaps = (int) claims.values().stream()
                                            .filter(ids -> {

                                                if (ids.size() > 1) unoverlappedIds.removeAll(ids);
                                                return ids.size() > 1;

                                            }).count();

        System.out.println("overlaps = " + overlaps);

        for (int id : unoverlappedIds) {

            System.out.println("#" + id + " isn't overlapped");
        }
    }
}
