package com.luminiscental.aoc;

import com.luminiscental.aoc.util.DirectedGraph;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day7 extends Day {

    private static final int workerCount = 5;

    static class WorkerState {

        int timeRemaining = 0;
        int currentTask = -1;

        private static int timeTaken(int node) {

            return 61 + node - 'A';
        }

        void assignTask(int task) {

            currentTask = task;
            timeRemaining = timeTaken(task);
        }

        boolean isWorking() {

            return currentTask != -1;
        }

        boolean timeStep() {

            if (isWorking()) {

                timeRemaining--;

                if (timeRemaining == 0) {

                    currentTask = -1;
                    return true;
                }
            }

            return false;
        }
    }

    Day7() {

        super(7);
    }

    @Override
    void solve(String[] lines) {

        DirectedGraph graph = new DirectedGraph();

        Pattern nodePattern = Pattern.compile("\\s[A-Z]\\s");

        for (String line : lines) {

            if (line.trim().length() == 0) continue;

            Matcher matcher = nodePattern.matcher(line);
            List<Character> parsedChars = new ArrayList<>();

            while (matcher.find()) {

                parsedChars.add(matcher.group().trim().charAt(0));
            }

            if (parsedChars.size() != 2) {

                System.out.println("Failed to parse line: \"" + line + "\"");

            } else {

                graph.addEdgeWithNodes((int) parsedChars.get(0), (int) parsedChars.get(1));
            }
        }

        Set<Integer> available = graph.nodeSet().stream()
                                                .filter(x -> graph.countParents(x) == 0)
                                                .collect(Collectors.toSet());

        List<Integer> ordering = getAlphabeticalOrdering(available, graph);

        System.out.print("Alphabetical order of steps: ");
        ordering.forEach(x -> System.out.print((char) x.intValue()));
        System.out.println();

        // Reset because it's been emptied
        available = graph.nodeSet().stream()
                                   .filter(x -> graph.countParents(x) == 0)
                                   .collect(Collectors.toSet());

        int completeTime = work(available, graph);

        System.out.println("It took " + completeTime + " seconds to complete all tasks");
    }


    private List<Integer> getAlphabeticalOrdering(Set<Integer> available, DirectedGraph graph) {

        List<Integer> completed = new ArrayList<>();
        Set<Integer> pending = new HashSet<>();

        do {

            available.addAll(pending.stream()
                                    .filter(x -> completed.containsAll(graph.getParents(x)))
                                    .collect(Collectors.toSet()));

            Optional<Integer> nextPossible = available.stream()
                                                      .min(Comparator.comparingInt(x -> x));

            if (nextPossible.isPresent()) {

                int step = nextPossible.get();

                completed.add(step);
                pending.remove(step);
                available.remove(step);

                pending.addAll(graph.getChildren(step));
            }

        } while (pending.size() > 0);

        return completed;
    }

    private int work(Set<Integer> available, DirectedGraph graph) {

        int time = 0;

        List<Integer> completed = new ArrayList<>();
        Set<Integer> pending = new HashSet<>();

        List<WorkerState> workers = new ArrayList<>();

        for (int i = 0; i < workerCount; i++) {

            workers.add(new WorkerState());
        }

        do {

            available.addAll(pending.stream()
                                    .filter(x -> completed.containsAll(graph.getParents(x)))
                                    .collect(Collectors.toSet()));

            List<Integer> todo = new ArrayList<>(available);
            Collections.sort(todo);

            Set<Integer> assignedTasks = new HashSet<>();

            for (int task : todo) {

                for (WorkerState worker : workers) {

                    if (!worker.isWorking()) {

                        worker.assignTask(task);
                        assignedTasks.add(task);
                        break;
                    }
                }
            }

            pending.removeAll(assignedTasks);
            available.removeAll(assignedTasks);

            for (WorkerState worker : workers) {

                int assignedTask = worker.currentTask;

                if (worker.timeStep()) {

                    pending.remove(assignedTask);
                    available.remove(assignedTask);

                    completed.add(assignedTask);
                    pending.addAll(graph.getChildren(assignedTask));
                }
            }

            time++;

        } while (completed.size() < graph.size());

        return time;
    }
}
