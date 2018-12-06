package com.luminiscental.aoc;

import java.util.*;
import java.util.stream.Collectors;

public class Day6 extends Day {

    private static final int width = 1000;
    private static final int bound = 10000;

    Day6() {

        super(6);
    }

    @Override
    void solve(String[] lines) {

        List<Integer> centers = new ArrayList<>();

        for (String line : lines) {

            String[] coords = line.split(", ");

            int x = Integer.parseInt(coords[0]);
            int y = Integer.parseInt(coords[1]);

            centers.add(x + y * width);
        }

        int[] grid = loadGrid(centers);

        Set<Integer> finite = Arrays.stream(grid).boxed().filter(x -> x != -1).collect(Collectors.toSet());

        for (int i = 0; i < width; i++) {
            
            finite.remove(grid[i]);
            finite.remove(grid[i + (width - 1) * width]);
            finite.remove(grid[i * width]);
            finite.remove(grid[(width - 1) + i * width]);
        }

        Map<Integer, Integer> areas = Arrays.stream(grid).boxed().filter(finite::contains).collect(Collectors.toMap(c -> c, c -> 1, (a, b) -> a + b));

        int largestArea = areas.values().stream().max(Comparator.comparingInt(x -> x)).get();
        System.out.println("largest area is " + largestArea);

        Set<Integer> region = getRegionBounded(centers);
        System.out.println("bounded region area is " + region.size());
    }

    private int[] loadGrid(List<Integer> centers) {

        int centerCount = centers.size();
        int[] grid = new int[width * width];

        for (int y = 0; y < width; y++) {

            for (int x = 0; x < width; x++) {

                int minD = -1;
                int minIndex = -1;

                for (int i = 0; i < centerCount; i++) {

                    int centerIndex = centers.get(i);

                    int centerX = centerIndex % width;
                    int centerY = centerIndex / width;

                    int d = Math.abs(x - centerX) + Math.abs(y - centerY);

                    if (minD == -1) {

                        minD = d;
                        minIndex = i;

                    } else if (d < minD) {

                        minD = d;
                        minIndex = i;
                    }
                }

                grid[x + y * width] = minIndex;
            }
        }

        return grid;
    }

    private Set<Integer> getRegionBounded(List<Integer> centers) {

        Set<Integer> boundedRegion = new HashSet<>();

        for (int y = 0; y < width; y++) {

            for (int x = 0; x < width; x++) {

                int distanceSum = 0;

                for (int center : centers) {

                    int centerX = center % width;
                    int centerY = center / width;

                    distanceSum += Math.abs(x - centerX) + Math.abs(y - centerY);
                }

                if (distanceSum < bound) {

                    boundedRegion.add(x + y * width);
                }
            }
        }

        return boundedRegion;
    }
}
