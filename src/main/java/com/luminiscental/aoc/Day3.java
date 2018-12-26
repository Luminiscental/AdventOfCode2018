package com.luminiscental.aoc;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class Day3 extends Day {

    Day3() {

        super(3);
    }

    @Override
    void solve(String[] lines) {

        Map<Integer, Set<Integer>> grid = new LinkedHashMap<>();
        Set<Integer> validIds = new LinkedHashSet<>();

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

                    Set<Integer> val = grid.getOrDefault(index, new LinkedHashSet<>());
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

                                                boolean idOverlaps = ids.size() > 1;

                                                if (idOverlaps) {

                                                    unoverlappedIds.removeAll(ids);
                                                }

                                                return idOverlaps;

                                            }).count();

        System.out.println("overlaps = " + overlaps);

        for (int id : unoverlappedIds) {

            System.out.println("#" + id + " isn't overlapped");
        }
    }
}
