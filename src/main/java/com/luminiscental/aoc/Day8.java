package com.luminiscental.aoc;


import com.luminiscental.aoc.util.OrderedTree;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day8 extends Day {

    static class OrderedTreeData {

        int childCount;
        List<Integer> metadata = new ArrayList<>();

        int metadataSum() {

            return metadata.stream().mapToInt(x -> x).sum();
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

        OrderedTree<OrderedTreeData> tree = parseOrderedTree(inputValues);

        int totalMetadataSum = tree.getNodes().stream().mapToInt(x -> x.value.metadataSum()).sum();

        System.out.println("All metadata sums to " + totalMetadataSum + ", the value of the root node is " + getValue(tree.root));
    }

    private long getValue(OrderedTree.Node<OrderedTreeData> node) {

        if (node.countChildren() == 0) {

            return node.value.metadataSum();

        } else {

            long result = 0;

            for (int entry : node.value.metadata) {

                if (node.getChildren().size() > entry - 1) {

                    OrderedTree.Node<OrderedTreeData> referencedChild = node.getChildren().get(entry - 1);
                    result += getValue(referencedChild);
                }
            }

            return result;
        }
    }

    private int parseSize(OrderedTree.Node<OrderedTreeData> node) {

        int result = 0;

        result += 2; // header

        for (OrderedTree.Node<OrderedTreeData> child : node.getChildren()) { // children data

            result += parseSize(child);
        }

        result += node.value.metadata.size(); // metadata

        return result;
    }

    private OrderedTree<OrderedTreeData> parseOrderedTree(List<Integer> values) {

        OrderedTreeData data = new OrderedTreeData();
        data.childCount = values.get(0);

        int metadataCount = values.get(1);

        int cursor = 2;

        OrderedTree<OrderedTreeData> result = new OrderedTree<>(data);

        for (int i = 0; i < data.childCount; i++) {

            OrderedTree<OrderedTreeData> subtree = parseOrderedTree(values.subList(cursor, values.size()));
            result.addSubtree(subtree);

            cursor += parseSize(subtree.root);
        }

        result.root.value.metadata.addAll(values.subList(cursor, cursor + metadataCount));

        return result;
    }
}
