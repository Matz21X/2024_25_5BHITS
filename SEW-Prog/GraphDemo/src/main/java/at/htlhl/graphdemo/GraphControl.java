package at.htlhl.graphdemo;

import com.brunomnsilva.smartgraph.graph.DigraphEdgeList;
import com.brunomnsilva.smartgraph.graph.Graph;

public class GraphControl {
    private Graph<String, String> graph;

    public GraphControl() {
        graph = new DigraphEdgeList<>();
        buildGraph();
    }

    private void buildGraph() {
        graph.insertVertex("A");
        graph.insertVertex("B");
        graph.insertVertex("C");
        graph.insertVertex("D");
        graph.insertVertex("E");
        graph.insertVertex("F");
        graph.insertVertex("G");

        // Muss in beide Richtungen definiert werden
        graph.insertEdge("A", "B", "AB");
        graph.insertEdge("B", "A", "BA");
        graph.insertEdge("A", "C", "AC");
        graph.insertEdge("A", "D", "CA");
        graph.insertEdge("A", "E", "AE");
        graph.insertEdge("A", "F", "AF");
        graph.insertEdge("A", "G", "AG");

    }

    public Graph<String, String> getGraph() {
        return graph;
    }
}