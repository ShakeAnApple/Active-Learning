package utils;

import automaton.Automaton;
import automaton.State;
import automaton.Transition;
import values.AbstractVariableInfo;
import values.Interval;
import values.IntervalValueHolder;
import values.VariableValue;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class NusmvConverter {
    // "C:\\Temp\\m_gen_c.smv"
    private static void appendVars(Writer writer, Automaton automaton, Map<State, String> statesNames) throws IOException {
        writer.append("VAR\n");

        writer.append("state: {");
        int cnt = 0;
        for (State st : statesNames.keySet()) {
            writer.append(statesNames.get(st));
            if (cnt < automaton.getStates().size() - 1) {
                writer.append(", ");
            }
            cnt++;
        }
        writer.append("};\n");

        for (AbstractVariableInfo var : automaton.getInputVariables()) {
            writer.append(String.format("%1$s: %2$s .. %3$s;\n", var.getName(), var.getPossibleValues().get(0), var.getPossibleValues().get(var.getPossibleValues().size() - 1)));
        }
    }

    private static void appendAssignBlock(Writer writer, Automaton automaton, Map<State, String> statesNames) throws IOException {
        writer.append("ASSIGN\n");

        writer.append(String.format("init(state) := %1$s;\n", statesNames.get(automaton.getStartState())));
        writer.append("next(state) := case\n");
//
        for (State st : automaton.getStates()) {
            List<Transition> transitionsFromSt = automaton.getTransitionsByFrom(st);
            String curStateName = statesNames.get(st);
            for (Transition tr : transitionsFromSt) {
                writer.append(String.format("(state = %1$s)", curStateName));

                if (tr.getTransitionFormula() != null) {
                    //writer.append(" & ").append(convertBoolTransToNusmvRow(tr.getStringFormula()));
                    writer.append(" & ").append(tr.toNusmvString());
                } else {
                    for (VariableValue val : tr.getSymbol().getVariablesValues()) {
                        writer.append(String.format(" & (%1$s = %2$s)", val.getVarInfo().getName(), val.getValueHolder()));
                    }
                }
                writer.append(String.format(": %1$s; \n", statesNames.get(tr.getTo())));
            }
        }

        writer.append("TRUE: state;\n");
        writer.append("esac;\n");
    }

    private static void appendDefineBlock(Writer writer, Automaton automaton, Map<State, String> statesNames, Map<String, Interval[]> intervalsByName) throws IOException {
        writer.append("DEFINE\n");

        for (AbstractVariableInfo var : automaton.getOutputVariables()) {
            writer.append(String.format("%1$s := ", var.getName()));

            boolean isIntervalValue = intervalsByName.containsKey(var.getName());

            for (int i = 0; i < var.getPossibleValues().size(); i++) {
                List<State> curValStates = new ArrayList<>();
                for (State st : automaton.getStates()) {
                    if (isIntervalValue) {
                        IntervalValueHolder i1 = (IntervalValueHolder) st.getStateValue().getSymbol().getVariableValueByName(var.getName()).getValueHolder();
                        IntervalValueHolder i2 = (IntervalValueHolder) var.getPossibleValues().get(i);
                        if (i1.equals(i2)) {
                            curValStates.add(st);
                        }
                    } else {
                        if (st.getStateValue().getSymbol().getVariableValueByName(var.getName()).getValueHolder().equals(var.getPossibleValues().get(i))) {
                            curValStates.add(st);
                        }
                    }
                }

                if (curValStates.size() > 0) {
                    if (i == var.getPossibleValues().size() - 1) {
                        if (!isIntervalValue) {
                            writer.append(String.format("%1$s;", var.getPossibleValues().get(i)));
                        } else {
                            writer.append(String.format("%1$s;", ((IntervalValueHolder) var.getPossibleValues().get(i)).getCurrentIntervalNum()));
                        }
                        break;
                    }

                    writer.append("(");
                    for (int j = 0; j < curValStates.size(); j++) {
                        if (j < curValStates.size() - 1) {
                            writer.append(String.format("(state = %1$s) | ", statesNames.get(curValStates.get(j))));
                        } else {
                            writer.append(String.format("(state = %1$s) ", statesNames.get(curValStates.get(j))));
                        }
                    }
                    if (!isIntervalValue) {
                        writer.append(String.format(") ? %1$s :", var.getPossibleValues().get(i)));
                    } else {
                        writer.append(String.format(") ? %1$s :", ((IntervalValueHolder) var.getPossibleValues().get(i)).getCurrentIntervalNum()));
                    }
                }
            }
            writer.append("\n");
        }
    }

    private static void appendContDefineBlock(Writer writer, Map<String, Interval[]> intervalsByName) throws IOException {
        if (intervalsByName.keySet().size() > 0) {
            writer.append("DEFINE\n");

            for (String varName : intervalsByName.keySet()) {
                writer.append(String.format("CONT_%1$s := case\n", varName));

                Interval[] curIntervals = intervalsByName.get(varName);
                for (int i = 0; i < curIntervals.length; i++) {
                    writer.append(String.format("%1$s = %2$s: %3$s..%4$s;\n", varName, i, Math.round(curIntervals[i].getFrom()), Math.round(curIntervals[i].getTo())));
                }
                writer.append("esac;\n");
            }
        }
    }

    private static Map<State, String> getStatesNamesMap(Automaton automaton) {
        Map<State, String> statesNames = new HashMap<>();
        List<State> statesArr = new ArrayList<>(automaton.getStates());
        for (int i = 0; i < automaton.getStates().size(); i++) {
            String stateName = "s" + i;
            statesNames.put(statesArr.get(i), stateName);
        }
        return statesNames;
    }

    private static Map<String, Interval[]> getIntervalsByName(Automaton automaton) {
        List<AbstractVariableInfo> intervalVars = automaton.getOutputVariables().stream()
                .filter(v -> v.getPossibleValues().get(0) instanceof IntervalValueHolder)
                .collect(Collectors.toList());
        Map<String, Interval[]> intervalsByName = new HashMap<>();
        if (intervalVars.size() > 0) {
            for (AbstractVariableInfo var : intervalVars) {
                Interval[] values = new Interval[var.getPossibleValues().size()];

                for (Object posValue : var.getPossibleValues()) {
                    IntervalValueHolder curValue = (IntervalValueHolder) posValue;

                    values[curValue.getCurrentIntervalNum()] = curValue.getCurrentInterval();
                }

                intervalsByName.put(var.getName(), values);
            }
        }
        return intervalsByName;
    }

    public static void saveInNusmvFormat(Automaton automaton, String path) {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), StandardCharsets.UTF_8))) {

            Map<State, String> statesNames = getStatesNamesMap(automaton);
            Map<String, Interval[]> intervalsByName = getIntervalsByName(automaton);

            writer.append("MODULE main\n");
            appendVars(writer, automaton, statesNames);
            appendAssignBlock(writer, automaton, statesNames);
            appendDefineBlock(writer, automaton, statesNames, intervalsByName);
            appendContDefineBlock(writer, intervalsByName);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static String convertBoolTransToNusmvRow(String stringFormula) {
        StringBuilder result = new StringBuilder();
        stringFormula = stringFormula.replace(" ", "");
        List<Character> controlSeq = new ArrayList<Character>(Arrays.asList('(', ')', '&', '|'));
        boolean negateNext = false;
        boolean buildingVarName = false;
        for (int i = 0; i < stringFormula.length(); i++) {
            Character c = stringFormula.charAt(i);
            if (buildingVarName && (controlSeq.contains(c) || c.equals("~"))) {
                buildingVarName = false;
                if (negateNext) {
                    result.append(" = 0 ");
                } else {
                    result.append(" = 1 ");
                }
            }
            if (controlSeq.contains(c)) {
                result.append(c).append(" ");
            } else if (c.equals('~')) {
                if (controlSeq.contains(stringFormula.charAt(i + 1))) {
                    result.append("!");
                } else {
                    negateNext = true;
                }
            } else {
                buildingVarName = true;
                result.append(c);
            }
        }
        if (buildingVarName) {
            if (negateNext) {
                result.append(" = 0 ");
            } else {
                result.append(" = 1 ");
            }
        }
        return result.toString();
    }
}
