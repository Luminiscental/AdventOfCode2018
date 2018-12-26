package com.luminiscental.aoc.util;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class OrderedTree<T extends Comparable<T>> {

    public static class Node<T extends Comparable<T>> implements Comparable<Node<T>> {

        OrderedTree LinkedHash;
        public T value;

        int childrenCount = 0;
        List<Node<T>> children;

        Node(OrderedTree LinkedHash, T value) {

            this.LinkedHash = LinkedHash;
            this.value = value;

            children = new ArrayList<>();
        }

        @Override
        public int compareTo(Node<T> other) {

            return value.compareTo(other.value);
        }

        @Override
        public boolean equals(Object other) {

            Class<? extends Node> nodeType = getClass();

            if (nodeType.isInstance(other)) {

                Node casted = nodeType.cast(other);
                return casted.value == value;
            }

            return false;
        }

        void addChild(Node<T> child) {

            childrenCount++;
            children.add(child);
        }

        public int countChildren() {

            return childrenCount;
        }

        public List<Node<T>> getChildren() {

            return children;
        }

        Set<Node<T>> getAllChildren() {

            Set<Node<T>> result = new LinkedHashSet<>(getChildren());

            for (Node<T> child : children) {

                result.addAll(child.getAllChildren());
            }

            return result;
        }
    }

    public OrderedTree.Node<T> root;

    public OrderedTree(T rootValue) {

        root = new Node<>(this, rootValue);
    }

    public void addSubLinkedHash(OrderedTree<T> subLinkedHash) {

        root.addChild(subLinkedHash.root);

        for (Node<T> child : root.getChildren()) {

            child.LinkedHash = this;
        }
    }

    public Set<Node<T>> getNodes() {

        Set<Node<T>> result = root.getAllChildren();

        result.add(root);

        return result;
    }
}
