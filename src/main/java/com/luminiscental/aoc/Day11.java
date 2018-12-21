package com.luminiscental.aoc;

import com.luminiscental.aoc.util.Point;

import java.util.Comparator;
import java.util.Optional;
import java.util.stream.IntStream;

public class Day11 extends Day {

    Day11() {

        super(11);
    }

    @Override
    void solve(String[] lines) throws InvalidInputException {

        final int serialNumber = Integer.parseInt(lines[0]);

        class SectionData {

            private Point<Integer> topLeft;
            private int width;
            private int power;

            private SectionData(int index, int width) {

                this.width = width;
                topLeft = new Point<>(1 + index % 300, 1 + index / 300);
                power = getSectionPower(topLeft, width, serialNumber);
            }

            private int getPower() {

                return power;
            }
        }

        int lastPower = Integer.MIN_VALUE;
        int maxPower = Integer.MIN_VALUE;
        SectionData maxSection = null;

        // Iterate over widths
        for (int i = 0; i < 300; i++) {

            final int width = 1 + i;
            int bound = 300 - i;

            // Iterate over possible positions
            Optional<SectionData> maxPowerSectionForWidth = IntStream.rangeClosed(0, (bound - 1) * 301)
                                                                     .mapToObj(j -> new SectionData(j, width))
                                                                     .max(Comparator.comparingInt(SectionData::getPower));
            if (maxPowerSectionForWidth.isPresent()) {

                SectionData result = maxPowerSectionForWidth.get();

                if (width == 3) {

                    System.out.println("3x3 grid with most power is at " + result.topLeft);
                }

                int currentPower = result.getPower();

                if (currentPower < lastPower) { // There is only one local maximum don't ask me why

                    break;
                }

                if (currentPower > maxPower) {

                    maxSection = result;
                }

                lastPower = currentPower;

            } else {

                throw new InvalidInputException();
            }
        }

        if (maxSection == null) {

            throw new InvalidInputException();

        } else {

            System.out.println("Grid of max power was at " + maxSection.topLeft + " with width " + maxSection.width);
        }
    }

    private int getPositionPower(int x, int y, int serialNumber) {

        int rackId = x + 10;

        int raw = rackId * (rackId * y + serialNumber);

        int digit = (raw % 1000) / 100;

        return digit - 5;
    }

    private int getSectionPower(Point<Integer> topLeft, int width, int serialNumber) {

        return IntStream.range(0, width * width)
                        .map(index -> {

                            int x = index % width;
                            int y = index / width;

                            return getPositionPower(topLeft.x + x, topLeft.y + y, serialNumber);

                        }).sum();
    }
}
