package com.luminiscental.aoc;


import com.luminiscental.aoc.util.OrderedTree;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day8 extends Day {

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

        OrderedTree<List<Integer>> tree = parseOrderedTree(inputValues);

        int totalMetadataSum = tree.getNodes().stream()
                                              .flatMapToInt(x -> x.value.stream().mapToInt(y -> y))
                                              .sum();

        System.out.println("All metadata sums to " + totalMetadataSum + ", the value of the root node is " + getValue(tree.root));
    }

    private long getValue(OrderedTree.Node<List<Integer>> node) {

        if (node.countChildren() == 0) {

            return node.value.stream().mapToInt(x -> x).sum();

        } else {

            long result = 0;

            for (int entry : node.value) {

                if (node.getChildren().size() > entry - 1) {

                    OrderedTree.Node<List<Integer>> referencedChild = node.getChildren().get(entry - 1);
                    result += getValue(referencedChild);
                }
            }

            return result;
        }
    }

    private int parseSize(OrderedTree.Node<List<Integer>> node) {

        int result = 0;

        result += 2; // header

        for (OrderedTree.Node<List<Integer>> child : node.getChildren()) { // children data

            result += parseSize(child);
        }

        result += node.value.size(); // metadata

        return result;
    }

    private OrderedTree<List<Integer>> parseOrderedTree(List<Integer> values) {

        List<Integer> data = new ArrayList<>();

        int childCount = values.get(0);
        int metadataCount = values.get(1);

        int cursor = 2;

        OrderedTree<List<Integer>> result = new OrderedTree<>(data);

        for (int i = 0; i < childCount; i++) {

            OrderedTree<List<Integer>> subtree = parseOrderedTree(values.subList(cursor, values.size()));
            result.addSubtree(subtree);

            cursor += parseSize(subtree.root);
        }

        result.root.value.addAll(values.subList(cursor, cursor + metadataCount));

        return result;
    }
}
