package com.luminiscental.aoc;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day10 extends Day {

    static class Light implements Comparable<Light> {

        final int initialPositionX;
        final int initialPositionY;

        final int velocityX;
        final int velocityY;

        int positionX;
        int positionY;

        Light(int initialPositionX, int initialPositionY, int velocityX, int velocityY) {

            this.initialPositionX = initialPositionX;
            this.initialPositionY = initialPositionY;

            this.velocityX = velocityX;
            this.velocityY = velocityY;

            this.positionX = initialPositionX;
            this.positionY = initialPositionY;
        }

        void step() {

            positionX += velocityX;
            positionY += velocityY;
        }

        private String getSerial() {

            return initialPositionX + "." + initialPositionY + "." + velocityX + "." + velocityY;
        }

        int getX() {

            return positionX;
        }

        int getY() {

            return positionY;
        }

        @Override
        public boolean equals(Object other) {

            if (!(other instanceof Light)) {

                return false;
            }

            return getSerial().equals(((Light)other).getSerial());
        }

        @Override
        public int hashCode() {

             return initialPositionX * 13 + initialPositionY * 29 + velocityX * 7 + velocityY * 31;
        }

        @Override
        public int compareTo(Light other) {

            return getSerial().compareTo(other.getSerial());
        }
    }

    Day10() {

        super(10);
    }

    @Override
    void solve(String[] lines) throws InvalidInputException {

        Pattern vectorPattern = Pattern.compile("<(-?[0-9]+),(-?[0-9]+)>");

        Set<Light> lights = new TreeSet<>();

        for (String line : lines) {

            if (line.length() < 2) continue;

            Matcher matcher = vectorPattern.matcher(line.replaceAll("\\s",""));

            if (!matcher.find() || matcher.groupCount() != 2) {

                throw new InvalidInputException();
            }

            int positionX = Integer.parseInt(matcher.group(1));
            int positionY = Integer.parseInt(matcher.group(2));

            if (!matcher.find() || matcher.groupCount() != 2) {

                throw new InvalidInputException();
            }

            int velocityX = Integer.parseInt(matcher.group(1));
            int velocityY = Integer.parseInt(matcher.group(2));

            lights.add(new Light(positionX, positionY, velocityX, velocityY));
        }

        int time = 0;

        while (!printLightsIfSmall(lights)) {

            lights.forEach(Light::step);
            time++;
        }

        System.out.println("After " + time + " seconds");
    }

    private boolean printLightsIfSmall(Set<Light> lights) {

        int minX = Collections.min(lights, Comparator.comparingInt(Light::getX)).positionX;
        int minY = Collections.min(lights, Comparator.comparingInt(Light::getY)).positionY;
        int maxX = Collections.max(lights, Comparator.comparingInt(Light::getX)).positionX;
        int maxY = Collections.max(lights, Comparator.comparingInt(Light::getY)).positionY;

        if (maxX - minX <= 80 && maxY - minY <= 10) { // magic numbers :p

            for (int y = minY; y <= maxY; y++) {

                for (int x = minX; x <= maxX; x++) {

                    final int _x = x;
                    final int _y = y;

                    long count = lights.stream().filter(light -> light.positionY == _y && light.positionX == _x).count();

                    char value = count == 0 ? ' ' : '■';

                    System.out.print(value);
                }

                System.out.println();
            }

            System.out.println();

            return true;
        }

        return false;
    }
}
