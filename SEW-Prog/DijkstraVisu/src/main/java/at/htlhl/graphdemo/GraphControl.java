package at.htlhl.graphdemo;

import com.brunomnsilva.smartgraph.graph.Graph;
import com.brunomnsilva.smartgraph.graph.GraphEdgeList;


public class GraphControl {

    private Graph<VertexData,EdgeData> graph;

    public GraphControl() {
        graph = new GraphEdgeList<>();
        buildGraph();
    }

    private void buildGraph() {
        VertexData seattle = createCityAndInsert("Seattle");
        VertexData sanFrancisco = createCityAndInsert("San Francisco");
        VertexData losAngeles = createCityAndInsert("Los Angeles");
        VertexData riverside = createCityAndInsert("Riverside");
        VertexData phoenix = createCityAndInsert("Phoenix");
        VertexData chicago = createCityAndInsert("Chicago");
        VertexData boston = createCityAndInsert("Boston");
        VertexData newYork = createCityAndInsert("New York");
        VertexData atlanta = createCityAndInsert("Atlanta");
        VertexData miami = createCityAndInsert("Miami");
        VertexData dallas = createCityAndInsert("Dallas");
        VertexData houston = createCityAndInsert("Houston");
        VertexData detroit = createCityAndInsert("Detroit");
        VertexData philadelphia = createCityAndInsert("Philadelphia");
        VertexData washington = createCityAndInsert("Washington");
        graph.insertEdge(seattle, chicago, new EdgeData(1737));
        graph.insertEdge(seattle, sanFrancisco, new EdgeData(678));
        graph.insertEdge(sanFrancisco, riverside, new EdgeData(386));
        graph.insertEdge(sanFrancisco, losAngeles, new EdgeData(348));
        graph.insertEdge(losAngeles, riverside, new EdgeData(50));
        graph.insertEdge(losAngeles, phoenix, new EdgeData(357));
        graph.insertEdge(riverside, phoenix, new EdgeData(307));
        graph.insertEdge(riverside, chicago, new EdgeData(1704));
        graph.insertEdge(phoenix, dallas, new EdgeData(887));
        graph.insertEdge(phoenix, houston, new EdgeData(1015));
        graph.insertEdge(dallas, chicago, new EdgeData(805));
        graph.insertEdge(dallas, atlanta, new EdgeData(721));
        graph.insertEdge(dallas, houston, new EdgeData(225));
        graph.insertEdge(houston, atlanta, new EdgeData(702));
        graph.insertEdge(houston, miami, new EdgeData(968));
        graph.insertEdge(atlanta, chicago, new EdgeData(588));
        graph.insertEdge(atlanta, washington, new EdgeData(543));
        graph.insertEdge(atlanta, miami, new EdgeData(604));
        graph.insertEdge(miami, washington, new EdgeData(923));
        graph.insertEdge(chicago, detroit, new EdgeData(238));
        graph.insertEdge(detroit, boston, new EdgeData(613));
        graph.insertEdge(detroit, washington, new EdgeData(396));
        graph.insertEdge(detroit, newYork, new EdgeData(482));
        graph.insertEdge(boston, newYork, new EdgeData(190));
        graph.insertEdge(newYork, philadelphia, new EdgeData(81));
        graph.insertEdge(philadelphia, washington, new EdgeData(123));
    }
    private VertexData createCityAndInsert(String name) {
        VertexData city = new VertexData(name);
        graph.insertVertex(city);
        return city;
    }

    public Graph<VertexData,EdgeData> getGraph() {
        return graph;
    }
}
