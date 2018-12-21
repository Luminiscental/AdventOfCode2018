package com.luminiscental.aoc;

import org.apache.commons.collections4.list.TreeList;
import java.util.List;
import java.util.OptionalLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day9 extends Day {

    static class Circle {

        List<Integer> values;
        int selectedIndex;
        int count;

        Circle() {

            values = new TreeList<>();
            values.add(0);
            selectedIndex = 0;
            count = 1;
        }

        void normalTurn(int marble) {

            int insertIndex = 1;

            if (count > 1) {

                insertIndex += (selectedIndex + 1) % count;
            }

            values.add(insertIndex, marble);
            count++;
            selectedIndex = insertIndex;
        }

        int scoreTurn(int marble) {

            int otherIndex = selectedIndex - 7;

            otherIndex += (-otherIndex / count + 1) * count;
            otherIndex = otherIndex % count;

            int score = values.get(otherIndex) + marble;

            values.remove(otherIndex);
            count--;
            selectedIndex = otherIndex;

            return score;
        }
    }

    Day9() {

        super(9);
    }

    @Override
    void solve(String[] lines) throws InvalidInputException {

        Matcher matcher = Pattern.compile("[0-9]+").matcher(lines[0]);

        if (!matcher.find()) {

            throw new InvalidInputException();
        }

        int playerCount = Integer.parseInt(matcher.group());

        if (!matcher.find()) {

            throw new InvalidInputException();
        }

        int lastMarble = Integer.parseInt(matcher.group());

        System.out.println("After " + lastMarble + " turns the winner has a score of " + getWinningScore(lastMarble, playerCount));
        System.out.println("After " + (100 * lastMarble) + " turns the winner has a score of " + getWinningScore(100 * lastMarble, playerCount));
    }

    private long getWinningScore(int lastMarble, int playerCount) {

        Circle circle = new Circle();
        List<Long> scores = new TreeList<>();

        for (int i = 0; i < playerCount; i++) {

            scores.add((long) 0);
        }

        for (int i = 0; i < lastMarble; i++) {

            int player = i % playerCount;
            int marble = i + 1;

            takeTurn(marble, player, circle, scores);
        }

        OptionalLong winningScore = scores.stream().mapToLong(x -> x).max();

        if (winningScore.isPresent()) {

            return winningScore.getAsLong();

        } else {

            throw new InvalidInputException();
        }
    }

    private void takeTurn(int marbleToPlace, int player, Circle circle, List<Long> scores) {

        if (marbleToPlace % 23 == 0) {

            scores.set(player, scores.get(player) + circle.scoreTurn(marbleToPlace));

        } else {

            circle.normalTurn(marbleToPlace);
        }
    }
}
