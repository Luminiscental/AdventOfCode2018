package com.luminiscental.aoc.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OrderedTree<T> {

    public static class Node<T> {

        OrderedTree tree;
        public T value;

        int childrenCount = 0;
        List<Node<T>> children;

        Node(OrderedTree tree, T value) {

            this.tree = tree;
            this.value = value;

            children = new ArrayList<>();
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

            Set<Node<T>> result = new HashSet<>(getChildren());

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
