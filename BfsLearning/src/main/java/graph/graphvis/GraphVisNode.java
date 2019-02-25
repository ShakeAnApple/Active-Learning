package graph.graphvis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GraphVisNode {
    private String _id;
    private String _label;

    private List<GraphVisLink> _links = new ArrayList();

    public GraphVisNode(String id, String label) {
        _id = id;
        _label = label;
    }

    public String getId() {
        return _id;
    }

    public List<GraphVisLink> getLinks(){
        return Collections.unmodifiableList(_links);
    }

    public void connectTo(GraphVisNode node, String label) {
        _links.add(new GraphVisLink(this, node, label));
    }

    @Override
    public String toString() {
        return _id + " [style=bold, label=<" + _id.substring(1) + "<br/>" + _label + ">];";
    }
}
