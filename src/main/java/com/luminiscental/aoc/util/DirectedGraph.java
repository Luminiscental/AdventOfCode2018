package com.luminiscental.aoc.util;

import java.util.TreeSet;
import java.util.TreeMap;
import java.util.Map;
import java.util.Set;

public class DirectedGraph {

    static class Node {

        Set<Integer> inEdges = new TreeSet<>();
        Set<Integer> outEdges = new TreeSet<>();
    }

    private final Map<Integer, Node> nodes = new TreeMap<>();

    public int size() {

        return nodes.size();
    }

    public int countParents(int id) {

        return nodes.getOrDefault(id, new Node()).inEdges.size();
    }

    private boolean notNode(int id) {

        return !nodes.containsKey(id);
    }

    private void addNode(int id) {

        nodes.put(id, new Node());
    }

    private void addEdge(int idHead, int idTail) throws IllegalArgumentException {

        if (notNode(idHead) || notNode(idTail)) {

            throw new IllegalArgumentException("Can't add an edge between non-existent nodes!");
        }

        nodes.get(idHead).outEdges.add(idTail);
        nodes.get(idTail).inEdges.add(idHead);
    }

    public void addEdgeWithNodes(int idHead, int idTail) {

        if (notNode(idHead)) {

            addNode(idHead);
        }

        if (notNode(idTail)) {

            addNode(idTail);
        }

        addEdge(idHead, idTail);
    }

    public Set<Integer> getChildren(int id) {

        return nodes.getOrDefault(id, new Node()).outEdges;
    }

    public Set<Integer> getParents(int id) {

        return nodes.getOrDefault(id, new Node()).inEdges;
    }

    public Set<Integer> nodeSet() {

        return nodes.keySet();
    }

}
