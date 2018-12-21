package com.luminiscental.aoc;


import com.luminiscental.aoc.util.OrderedTree;
import org.apache.commons.collections4.list.TreeList;

import java.util.List;
import java.util.Scanner;

public class Day8 extends Day {

    static class Metadata implements Comparable<Metadata> {

        List<Integer> values = new TreeList<>();

        @Override
        public int compareTo(Metadata other) {

            int count = Integer.min(values.size(), other.values.size());

            for (int i = 0; i < count; i++) {

                if (values.get(i) < other.values.get(i)) {

                    return -1;

                } else if (values.get(i) > other.values.get(i)) {

                    return 1;
                }
            }

            if (values.size() > other.values.size()) {

                return 1;

            } else if (values.size() < other.values.size()) {

                return -1;
            }

            return 0;
        }
    }

    Day8() {

        super(8);
    }

    @Override
    void solve(String[] lines) {

        List<Integer> inputValues = new TreeList<>();

        Scanner inputScanner = new Scanner(lines[0]);

        while (inputScanner.hasNextInt()) {

            inputValues.add(inputScanner.nextInt());
        }

        OrderedTree<Metadata> tree = parseOrderedTree(inputValues);

        int totalMetadataSum = tree.getNodes().stream()
                                              .flatMapToInt(x -> x.value.values.stream().mapToInt(y -> y))
                                              .sum();

        System.out.println("All metadata sums to " + totalMetadataSum + ", the value of the root node is " + getValue(tree.root));
    }

    private long getValue(OrderedTree.Node<Metadata> node) {

        if (node.countChildren() == 0) {

            return node.value.values.stream().mapToInt(x -> x).sum();

        } else {

            long result = 0;

            for (int entry : node.value.values) {

                if (node.getChildren().size() > entry - 1) {

                    OrderedTree.Node<Metadata> referencedChild = node.getChildren().get(entry - 1);
                    result += getValue(referencedChild);
                }
            }

            return result;
        }
    }

    private int parseSize(OrderedTree.Node<Metadata> node) {

        int result = 0;

        result += 2; // header

        for (OrderedTree.Node<Metadata> child : node.getChildren()) { // children data

            result += parseSize(child);
        }

        result += node.value.values.size(); // metadata

        return result;
    }

    private OrderedTree<Metadata> parseOrderedTree(List<Integer> values) {

        Metadata data = new Metadata();

        int childCount = values.get(0);
        int metadataCount = values.get(1);

        int cursor = 2;

        OrderedTree<Metadata> result = new OrderedTree<>(data);

        for (int i = 0; i < childCount; i++) {

            OrderedTree<Metadata> subtree = parseOrderedTree(values.subList(cursor, values.size()));
            result.addSubtree(subtree);

            cursor += parseSize(subtree.root);
        }

        result.root.value.values.addAll(values.subList(cursor, cursor + metadataCount));

        return result;
    }
}
