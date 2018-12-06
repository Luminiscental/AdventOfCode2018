package com.luminiscental.aoc;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day4 extends Day {

    private class GuardData {

        List<Map.Entry<Integer, Integer>> sleepPeriods = new ArrayList<>();
        int id;

        GuardData(int id) {

            this.id = id;
        }

        GuardData add(int startTime, int endTime) {

            sleepPeriods.add(new HashMap.SimpleEntry<>(startTime, endTime));
            return this;
        }
    }

    private class SleepData {

        int id;

        int sleepiestMinute;
        int sleepiestSleepTime;
        int totalSleepTime;

        SleepData(int id, Map<Integer, GuardData> days) {

            this.id = id;

            Set<Map.Entry<Integer, Integer>> entries = IntStream.range(0, 60)
                                                                .mapToObj(i -> new HashMap.SimpleEntry<>(i, countAsleep(id, days, i)))
                                                                .collect(Collectors.toSet());

            Map.Entry<Integer, Integer> sleepiestEntry = entries.stream()
                                                                .max(Comparator.comparingInt(Map.Entry::getValue))
                                                                .get();

            this.sleepiestMinute = sleepiestEntry.getKey();
            this.sleepiestSleepTime = sleepiestEntry.getValue();

            this.totalSleepTime = entries   .stream()
                                            .mapToInt(Map.Entry::getValue)
                                            .sum();
        }
    }

    Day4() {

        super(4);
    }

    @Override
    void solve(String[] lines) {

        SimpleDateFormat parser = new SimpleDateFormat("[yyyy-MM-dd HH:mm]");

        var records = Arrays.stream(lines)
                            .map(line -> (Map.Entry<Date, String>) new HashMap.SimpleEntry<>(parser.parse(line.substring(0, 18), new ParsePosition(0)), line.substring(19)))
                            .sorted(Comparator.comparing(Map.Entry::getKey))
                            .collect(Collectors.toList());

        Map<Integer, GuardData> days = parseGuards(records);

        Set<Integer> guards = days.values() .stream()
                                            .map((x) -> x.id)
                                            .collect(Collectors.toSet());

        Set<SleepData> sleepData = guards   .stream()
                                            .map(id -> new SleepData(id, days))
                                            .collect(Collectors.toSet());

        SleepData consistent = sleepData.stream()
                                        .max(Comparator.comparingInt(a -> a.sleepiestSleepTime))
                                        .get();

        SleepData frequent = sleepData  .stream()
                                        .max(Comparator.comparingInt(a -> a.totalSleepTime))
                                        .get();

        System.out.println("Part 1 checksum = " + frequent.id * frequent.sleepiestMinute);
        System.out.println("Part 2 checksum = " + consistent.id * consistent.sleepiestMinute);
    }

    private Map<Integer, GuardData> parseGuards(List<Map.Entry<Date, String>> records) {

        Map<Integer, GuardData> days = new HashMap<>();
        int id = -1;

        Calendar calendar = Calendar.getInstance();

        boolean asleep = false;
        int sleepStart = -1;

        for (Map.Entry<Date, String> entry : records) {

            Matcher matcher = Pattern.compile("#\\d+").matcher(entry.getValue());

            if (matcher.find()) {

                id = Integer.parseInt(matcher.group().substring(1));

            } else {

                calendar.setTime(entry.getKey());

                int minutes = calendar.get(Calendar.MINUTE);

                if (asleep) {

                    int index = calendar.get(Calendar.MONTH) * 100 + calendar.get(Calendar.DAY_OF_MONTH);
                    days.put(index, days.getOrDefault(index, new GuardData(id)).add(sleepStart, minutes));

                } else {

                    sleepStart = minutes;
                }

                asleep = !asleep;
            }
        }

        return days;
    }

    private int countAsleep(int id, Map<Integer, GuardData> days, int minute) {

        return (int) days.values()  .stream()
                                    .filter(data -> data.id == id)
                                    .mapToLong(data -> data.sleepPeriods    .stream()
                                                                            .filter(p -> p.getKey() <= minute && p.getValue() > minute)
                                                                            .count())
                                    .sum();
    }
}
