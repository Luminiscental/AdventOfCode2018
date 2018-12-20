package com.luminiscental.aoc.util;

import java.util.*;

public class DirectedGraph {

    static class Node {

        List<Integer> inEdges = new ArrayList<>();
        List<Integer> outEdges = new ArrayList<>();
    }

    public static class Edge {

        int head;
        int tail;

        public Edge(int head, int tail) {

            this.head = head;
            this.tail = tail;
        }

        @Override
        public boolean equals(Object obj) {

            return (obj instanceof Edge)
                    && (((Edge) obj).head == head)
                    && (((Edge) obj).tail == tail);
        }

        @Override
        public int hashCode() {

            return head * 100 + tail * 1000000;
        }
    }

    private final Map<Integer, Node> nodes = new LinkedHashMap<>();
    private int edges = 0;

    public int size() {

        return nodes.size();
    }

    public int edgeCount() {

        return edges;
    }

    public int countChildren(int id) {

        return nodes.getOrDefault(id, new Node()).outEdges.size();
    }

    public int countParents(int id) {

        return nodes.getOrDefault(id, new Node()).inEdges.size();
    }

    public int countEdges(int id) {

        return countChildren(id) + countParents(id);
    }

    public boolean hasNode(int id) {

        return nodes.containsKey(id);
    }

    public void addNode(int id) {

        nodes.put(id, new Node());
    }

    public void removeNode(int id) {

        clearNode(id);
        nodes.remove(id);
    }

    public void clearNode(int id) {

        if (hasNode(id)) {

            Node node = nodes.get(id);

            for (int tail : node.outEdges) {

                removeEdge(id, tail);
            }

            for (int head : node.inEdges) {

                removeEdge(head, id);
            }
        }
    }

    public void addEdge(int idHead, int idTail) throws IllegalArgumentException {

        if (!hasNode(idHead) || !hasNode(idTail)) {

            throw new IllegalArgumentException("Can't add an edge between non-existent nodes!");
        }

        nodes.get(idHead).outEdges.add(idTail);
        nodes.get(idTail).inEdges.add(idHead);
        edges++;
    }

    public void addEdgeWithNodes(int idHead, int idTail) {

        if (!hasNode(idHead)) {

            addNode(idHead);
        }

        if (!hasNode(idTail)) {

            addNode(idTail);
        }

        addEdge(idHead, idTail);
    }

    public void removeEdge(int idHead, int idTail) {

        if (hasNode(idHead) && hasNode(idTail)) {

            nodes.get(idHead).outEdges.remove(idTail);
            nodes.get(idTail).inEdges.remove(idHead);
            edges--;
        }
    }

    public boolean hasEdge(int idHead, int idTail) {

        return hasNode(idHead) && nodes.get(idHead).outEdges.contains(idTail);
    }

    public Set<Integer> getChildren(int id) {

        return new HashSet<>(nodes.getOrDefault(id, new Node()).outEdges);
    }

    public Set<Integer> getParents(int id) {

        return new HashSet<>(nodes.getOrDefault(id, new Node()).inEdges);
    }

    public Set<Integer> nodeSet() {

        return nodes.keySet();
    }

    public Set<Edge> edgeSet() {

        return nodes.keySet().stream().map(node -> {

            List<Integer> edgeList = nodes.get(node).inEdges;
            Set<Edge> result = new HashSet<>();

            for (int head : edgeList) {

                result.add(new Edge(head, node));
            }

            return result;

        }).reduce((a, b) -> {

            a.addAll(b);
            return a;

        }).get();
    }
}
