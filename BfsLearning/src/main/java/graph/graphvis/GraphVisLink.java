package graph.graphvis;

public class GraphVisLink {
    private GraphVisNode _to;
    private GraphVisNode _from;
    private String _label;

    public GraphVisLink(GraphVisNode from, GraphVisNode to, String label){
        _label = label;
        _to = to;
        _from = from;
    }

    @Override
    public String toString() {
        return _from.getId() + " -> " + _to.getId() + "[label = " + _label +"];";
    }
}
