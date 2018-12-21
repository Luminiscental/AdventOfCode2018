package com.luminiscental.aoc.util;

import org.apache.commons.collections4.list.TreeList;

import java.util.TreeSet;
import java.util.List;
import java.util.Set;

public class OrderedTree<T extends Comparable<T>> {

    public static class Node<T extends Comparable<T>> implements Comparable<Node<T>> {

        OrderedTree tree;
        public T value;

        int childrenCount = 0;
        List<Node<T>> children;

        Node(OrderedTree tree, T value) {

            this.tree = tree;
            this.value = value;

            children = new TreeList<>();
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

        @Override
        public int hashCode() {

            return value.hashCode() * 13 + tree.hashCode() * 17 + childrenCount * 131;
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

            Set<Node<T>> result = new TreeSet<>(getChildren());

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

    public void addSubtree(OrderedTree<T> subtree) {

        root.addChild(subtree.root);

        for (Node<T> child : root.getChildren()) {

            child.tree = this;
        }
    }

    public Set<Node<T>> getNodes() {

        Set<Node<T>> result = root.getAllChildren();

        result.add(root);

        return result;
    }
}
