package com.luminiscental.aoc;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day4 extends Day {

    private class GuardData {

        List<Integer> toggleMinutes;
        int id;

        GuardData(int id, List<Integer> toggleMinutes) {

            this.id = id;
            this.toggleMinutes = toggleMinutes;
        }
    }

    public static void main(String[] args) {

        new Day4().run();
    }

    private Day4() {

        super("inputDay4.txt");
    }

    @Override
    void solve(String[] lines) {

        List<Map.Entry<Date, String>> records = new ArrayList<>();

        SimpleDateFormat parser = new SimpleDateFormat("[yyyy-MM-dd HH:mm]");

        for (String line : lines) {

            ParsePosition pp = new ParsePosition(0);
            String dateString = line.substring(0, 18);
            Date date = parser.parse(dateString, pp);

            String data = line.substring(19);
            records.add(new HashMap.SimpleEntry<>(date, data));
        }

        records.sort(Comparator.comparing(Map.Entry::getKey));

        Map<Integer, GuardData> days = parseGuards(records);
        Set<Integer> guards = days.values() .stream()
                                            .map((x) -> x.id)
                                            .collect(Collectors.toSet());

        Map<Integer, Integer> sleepTotals = new HashMap<>();

        for (int id : guards) {

            for (int i = 0; i < 60; i++) {

                int count = countAsleep(id, days, i);

                if (sleepTotals.containsKey(id)) {

                    int total = sleepTotals.get(id) + count;
                    sleepTotals.put(id, total);

                } else {

                    sleepTotals.put(id, count);
                }
            }
        }

        int consistentGuard = -1;
        int consistentMinute = -1;

        int frequentGuard = -1;
        int frequentMinute = -1;

        int maxTotal = 0;
        int maxCount = 0;

        for (int id : guards) {

            var sleepData = sleepData(id, days);

            int sleepiestMinute = sleepData.getKey();
            int sleepiestCount = sleepData.getValue().getKey();
            int totalSleep = sleepData.getValue().getValue();

            if (totalSleep > maxTotal) {

                maxTotal = totalSleep;
                frequentGuard = id;
                frequentMinute = sleepiestMinute;
            }

            if (sleepiestCount > maxCount) {

                maxCount = sleepiestCount;
                consistentGuard = id;
                consistentMinute = sleepiestMinute;
            }
        }

        System.out.println("Part 1 checksum = " + frequentGuard * frequentMinute);
        System.out.println("Part 2 checksum = " + consistentGuard * consistentMinute);
    }

    private Map<Integer, GuardData> parseGuards(List<Map.Entry<Date, String>> records) {

        Map<Integer, GuardData> days = new HashMap<>();
        int id = -1;

        Calendar calendar = Calendar.getInstance();

        for (Map.Entry<Date, String> entry : records) {

            String info = entry.getValue();

            Pattern guardId = Pattern.compile("#\\d+");
            Matcher matcher = guardId.matcher(info);

            if (matcher.find()) {

                id = Integer.parseInt(matcher.group().substring(1));

            } else if (id != -1) {

                calendar.setTime(entry.getKey());

                int minutes = calendar.get(Calendar.MINUTE);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);

                int index = month * 100 + day;

                if (days.containsKey(index)) {

                    GuardData data = days.get(index);
                    data.toggleMinutes.add(minutes);
                    days.put(index, data);

                } else {

                    List<Integer> toggleMinutes = new ArrayList<>();
                    toggleMinutes.add(minutes);

                    days.put(index, new GuardData(id, toggleMinutes));
                }
            }
        }

        return days;
    }

    private int countAsleep(int id, Map<Integer, GuardData> days, int minute) {

        int count = 0;

        for (var entry : days.entrySet()) {

            GuardData data = entry.getValue();

            if (data.id == id) {

                boolean asleep = false;

                int lastToggle = -1;

                for (int toggle : data.toggleMinutes) {

                    if (asleep) {

                        if (toggle > minute && lastToggle < minute) {

                            count++;
                            break;
                        }

                    } else {

                        if (toggle == minute && lastToggle < minute) {

                            count++;
                            break;
                        }
                    }

                    asleep = !asleep;
                    lastToggle = toggle;
                }
            }
        }

        return count;
    }

    private Map.Entry<Integer, Map.Entry<Integer, Integer>> sleepData(int id, Map<Integer, GuardData> days) {

        int sleepiest = -1;
        int max = 0;
        int total = 0;

        for (int i = 0; i < 60; i++) {

            int count = countAsleep(id, days, i);

            total += count;

            if (max < count) {

                max = count;
                sleepiest = i;
            }
        }

        return new HashMap.SimpleEntry<>(sleepiest, new HashMap.SimpleEntry<>(max, total));
    }
}
