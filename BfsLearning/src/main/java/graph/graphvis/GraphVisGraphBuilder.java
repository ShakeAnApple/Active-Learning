package graph.graphvis;

import automaton.Automaton;
import automaton.State;
import automaton.Transition;
import values.IntervalValueHolder;
import values.Symbol;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

public class GraphVisGraphBuilder {
    public static void saveAsGraphVisGraph(Automaton automaton, String fileName) throws IOException {
        GraphVisGraph graph = toGraphVisGraph(automaton);
        Files.write( Paths.get(fileName), graph.toString().getBytes(), StandardOpenOption.CREATE);
    }

    private static GraphVisGraph toGraphVisGraph(Automaton automaton){
        GraphVisGraph graph = new GraphVisGraph();

        Map<State, String> statesNames = automaton.getStatesNames();

        for (State state: automaton.getStates())
        {
            String name = "";

            name += statesNames.get(state);

            String label = extractLabel(state.getStateValue().getSymbol());

            graph.createNode(name, label);
        }

        for (Transition tr: automaton.getAllTransitions())
        {
            String label = extractLabel(tr.getSymbol());
            graph.getNodeById(statesNames.get(tr.getFrom()))
                    .connectTo(graph.getNodeById(statesNames.get(tr.getTo())), label);
        }

        return graph;
    }

    private static String extractLabel(Symbol symbol){
        return symbol.getVariablesValues().stream()
                .filter(vv -> !(vv.getValueHolder() instanceof IntervalValueHolder))
                .sorted(Comparator.comparing(vv -> vv.getVarInfo().getOrder()))
                .map(vv -> vv.getValueHolder().toString())
                .collect(Collectors.joining());
    }
}
