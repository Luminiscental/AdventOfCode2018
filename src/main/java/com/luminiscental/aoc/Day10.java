package com.luminiscental.aoc;

import java.awt.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day10 extends Day {

    static class Light {

        final Point initialPosition;

        final Point velocity;

        Point position;

        Light(int initialPositionX, int initialPositionY, int velocityX, int velocityY) {

            initialPosition = new Point(initialPositionX, initialPositionY);
            velocity = new Point(velocityX, velocityY);
            position = initialPosition;
        }

        void step() {

            position.x += velocity.x;
            position.y += velocity.y;
        }
    }

    Day10() {

        super(10);
    }

    @Override
    void solve(String[] lines) throws InvalidInputException {

        Pattern vectorPattern = Pattern.compile("<(-?[0-9]+),(-?[0-9]+)>");

        Set<Light> lights = new LinkedHashSet<>();

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

        int minX = Collections.min(lights, Comparator.comparingInt(l -> l.position.x)).position.x;
        int minY = Collections.min(lights, Comparator.comparingInt(l -> l.position.y)).position.y;
        int maxX = Collections.max(lights, Comparator.comparingInt(l -> l.position.x)).position.x;
        int maxY = Collections.max(lights, Comparator.comparingInt(l -> l.position.y)).position.y;

        if (maxX - minX <= 80 && maxY - minY <= 10) { // magic numbers :p

            for (int y = minY; y <= maxY; y++) {

                for (int x = minX; x <= maxX; x++) {

                    final Point pos = new Point(x, y);

                    long count = lights.stream().filter(light -> light.position.equals(pos)).count();

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
