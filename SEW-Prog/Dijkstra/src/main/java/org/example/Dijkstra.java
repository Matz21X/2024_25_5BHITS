package org.example;

import java.util.*;

class Dijkstra {

    // Hilfsklasse, um eine Kante darzustellen
    static class Edge {
        int target, weight;

        Edge(int target, int weight) {
            this.target = target;
            this.weight = weight;
        }
    }

    // Methode für den Dijkstra-Algorithmus
    static void dijkstra(List<List<Edge>> graph, int source) {
        int n = graph.size(); // Anzahl der Knoten im Graphen
        int[] distances = new int[n];
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[source] = 0;

        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        pq.offer(new int[]{source, 0});

        boolean[] visited = new boolean[n];

        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int node = current[0];
            int distance = current[1];

            if (visited[node]) continue;
            visited[node] = true;

            for (Edge edge : graph.get(node)) {
                int neighbor = edge.target;
                int newDist = distance + edge.weight;

                if (newDist < distances[neighbor]) {
                    distances[neighbor] = newDist;
                    pq.offer(new int[]{neighbor, newDist});
                }
            }
        }

        // Ausgabe der kürzesten Entfernungen
        System.out.println("Kürzeste Entfernungen vom Startknoten " + source + ":");
        for (int i = 0; i < n; i++) {
            System.out.println("Knoten " + i + " -> " + distances[i]);
        }
    }

    public static void main(String[] args) {
        int n = 6; // Anzahl der Knoten
        List<List<Edge>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        // Beispielgraph hinzufügen
        graph.get(0).add(new Edge(1, 4));
        graph.get(0).add(new Edge(2, 2));
        graph.get(1).add(new Edge(2, 5));
        graph.get(1).add(new Edge(3, 10));
        graph.get(2).add(new Edge(4, 3));
        graph.get(3).add(new Edge(5, 11));
        graph.get(4).add(new Edge(3, 4));

        // Dijkstra-Algorithmus starten
        dijkstra(graph, 0);
    }
}
