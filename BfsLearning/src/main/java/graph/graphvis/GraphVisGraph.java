package graph.graphvis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphVisGraph {
    private Map<String, GraphVisNode> _nodes = new HashMap<>();

    public GraphVisNode createNode(String id, String content){
        GraphVisNode node = new GraphVisNode(id, content);
        _nodes.put(id, node);
        return  node;
    }

    public GraphVisNode getNodeById(String id) {
        return _nodes.get(id);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("digraph g {\n");

        List<GraphVisLink> links = new ArrayList<>();

        for (GraphVisNode node: _nodes.values()){
            sb.append(node.toString()).append("\n");

            links.addAll(node.getLinks());
        }

        sb.append("\n");

        for(GraphVisLink link: links){
            sb.append(link.toString()).append("\n");
        }

        sb.append("}");
        return sb.toString();
    }
}
