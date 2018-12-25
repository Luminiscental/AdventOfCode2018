package com.luminiscental.aoc;

import com.luminiscental.aoc.util.Point;

import java.util.*;

public class Day13 extends Day {

    static class Cart {

        Point<Integer> coordinate;
        int direction; // 0 = ^, 1 = <, 2 = v, 3 = >
        int turns;

        Cart(int direction, Point<Integer> coordinate) {

            this.coordinate = coordinate;
            this.direction = direction;
            this.turns = 0;
        }

        boolean vertical() {

            return direction % 2 == 0;
        }

        void turnLeft() {

            direction = (direction + 1) % 4;
        }

        void turnRight() {

            direction = (direction + 3) % 4;
        }

        void move() {

            switch (direction) {

                case 0:

                    coordinate.y--;
                    break;

                case 1:

                    coordinate.x--;
                    break;

                case 2:

                    coordinate.y++;
                    break;

                case 3:

                    coordinate.x++;
                    break;
            }
        }

        void readDirection(Map<Point<Integer>, Character> map) {

            boolean v = vertical();
            boolean turn = true;
            boolean left = false;

            switch (map.get(coordinate)) {

                case '\\':

                    left = v;
                    break;

                case '/':

                    left = !v;
                    break;

                case '+':

                    int remainder = (turns++) % 3;
                    left = remainder == 0;
                    turn = remainder != 1;
                    break;

                default:

                    turn = false;
                    break;
            }

            if (turn) {

                if (left) turnLeft();
                else turnRight();
            }
        }

        void step(Map<Point<Integer>, Character> map) {

            move();
            readDirection(map);
        }

    }

    Day13() {

        super(13);
    }

    @Override
    void solve(String[] lines) throws InvalidInputException {

        List<Cart> carts = new ArrayList<>();
        Map<Point<Integer>, Character> map = new TreeMap<>();

        readMap(lines, carts, map);

        boolean collided = false;

        while (!collided) {

            carts.sort(Comparator.comparingInt((Cart c) -> c.coordinate.y).thenComparingInt(c -> c.coordinate.x));

            for (int i = 0; i < carts.size(); i++) {

                carts.get(i).step(map);

                if (checkForCollision(i, carts)) {

                    collided = true;
                    break;
                }
            }
        }
    }

    private void readMap(String[] lines, List<Cart> carts, Map<Point<Integer>, Character> map) {

        for (int y = 0; y < lines.length; y++) {

            for (int x = 0; x < lines[y].length(); x++) {

                char val = lines[y].charAt(x);

                switch (val) {

                    case '^':

                        carts.add(new Cart(0, new Point<>(x, y)));
                        map.put(new Point<>(x, y), '|');

                        break;

                    case '<':

                        carts.add(new Cart(1, new Point<>(x, y)));
                        map.put(new Point<>(x, y), '-');

                        break;

                    case 'v':

                        carts.add(new Cart(2, new Point<>(x, y)));
                        map.put(new Point<>(x, y), '|');

                        break;

                    case '>':

                        carts.add(new Cart(3, new Point<>(x, y)));
                        map.put(new Point<>(x, y), '-');

                        break;

                    default:

                        if (val != ' ' && val != '\n') map.put(new Point<>(x, y), val);

                        break;
                }
            }
        }
    }

    private boolean checkForCollision(int index, List<Cart> carts) {

        Point<Integer> coordToCheck = carts.get(index).coordinate;

        for (int i = 0; i < carts.size(); i++) {

            if (i == index) continue;

            if (carts.get(i).coordinate.equals(coordToCheck)) {

                System.out.println("Collision at " + coordToCheck); // wrong?!

                return true;
            }
        }

        return false;
    }
}
