package com.luminiscental.aoc;


import com.luminiscental.aoc.util.OrderedTree;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day8 extends Day {

    static class Metadata implements Comparable<Metadata> {

        List<Integer> values = new ArrayList<>();

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

        @Override
        public boolean equals(Object other) {

            if (other instanceof Metadata) {

                Metadata otherMetadata = (Metadata) other;
                return values.equals(otherMetadata.values);
            }

            return false;
        }
    }

    Day8() {

        super(8);
    }

    @Override
    void solve(String[] lines) {

        List<Integer> inputValues = new ArrayList<>();

        Scanner inputScanner = new Scanner(lines[0]);

        while (inputScanner.hasNextInt()) {

            inputValues.add(inputScanner.nextInt());
        }

        OrderedTree<Metadata> LinkedHash = parseOrderedLinkedHash(inputValues);

        int totalMetadataSum = LinkedHash.getNodes().stream()
                                              .flatMapToInt(x -> x.value.values.stream().mapToInt(y -> y))
                                              .sum();

        System.out.println("All metadata sums to " + totalMetadataSum + ", the value of the root node is " + getValue(LinkedHash.root));
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

    private OrderedTree<Metadata> parseOrderedLinkedHash(List<Integer> values) {

        Metadata data = new Metadata();

        int childCount = values.get(0);
        int metadataCount = values.get(1);

        int cursor = 2;

        OrderedTree<Metadata> result = new OrderedTree<>(data);

        for (int i = 0; i < childCount; i++) {

            OrderedTree<Metadata> subLinkedHash = parseOrderedLinkedHash(values.subList(cursor, values.size()));
            result.addSubLinkedHash(subLinkedHash);

            cursor += parseSize(subLinkedHash.root);
        }

        result.root.value.values.addAll(values.subList(cursor, cursor + metadataCount));

        return result;
    }
}
