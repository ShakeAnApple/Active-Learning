package utils;

import automaton.Automaton;
import automaton.State;
import automaton.Transition;
import values.AbstractVariableInfo;
import values.Interval;
import values.IntervalValueHolder;
import values.VariableValue;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class NusmvConverter {
    // "C:\\Temp\\m_gen_c.smv"
    public static void saveInNusmvFormat(Automaton automaton, String path){
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), "utf-8"))) {

            writer.append("MODULE main\n");
            writer.append("VAR\n");

            // states
            writer.append("state: {");
            Map<State, String> statesNames = new HashMap<>();
            List<State> statesArr = new ArrayList<>(automaton.getStates());
            for (int i = 0; i < automaton.getStates().size(); i++) {
                String stateName = "s" + i;
                statesNames.put(statesArr.get(i), stateName);
                writer.append(stateName);
                if (i < automaton.getStates().size() - 1){
                    writer.append(", ");
                }
            }
            writer.append("};\n");

            // inputs
            for (AbstractVariableInfo var : automaton.getInputVariables()) {
                writer.append(String.format("%1$s: %2$s .. %3$s;\n", var.getName(), var.getPossibleValues().get(0), var.getPossibleValues().get(var.getPossibleValues().size() - 1)));
            }

            List<AbstractVariableInfo> intervalVars = automaton.getOutputVariables().stream()
                    .filter(v -> v.getPossibleValues().get(0) instanceof IntervalValueHolder)
                    .collect(Collectors.toList());
            HashMap<String, Interval[]> intervalsByName = new HashMap<>();
            if (intervalVars.size() > 0) {
                for(AbstractVariableInfo var: intervalVars) {
                    Interval[] values = new Interval[var.getPossibleValues().size()];

                    for(Object posValue : var.getPossibleValues()){
                        IntervalValueHolder curValue = (IntervalValueHolder)posValue;

                        values[curValue.getCurrentIntervalNum()] = curValue.getCurrentInterval();
                    }

                    intervalsByName.put(var.getName(), values);
                }
            }
//
//            // outputs
////        for (AbstractVariableInfo var : _outputVariableInfos) {
////            writer.append(String.format("%1$s: %2$s .. %3$s", var.getName(), var.getPossibleValues().get(0), var.getPossibleValues().get(var.getPossibleValues().size() - 1)));
////        }
//
            writer.append("ASSIGN\n");

            //transitions
            writer.append(String.format("init(state) := %1$s;\n", statesNames.get(automaton.getStartState())));
            writer.append("next(state) := case\n");
//
            for (State st : automaton.getStates()) {
                List<Transition> transitionsFromSt = automaton.getTransitionsByFrom(st);
                String curStateName = statesNames.get(st);
                for (Transition tr: transitionsFromSt) {
                    writer.append(String.format("(state = %1$s)",curStateName));
                    for (VariableValue val : tr.getSymbol().getVariablesValues()){
                        writer.append(String.format(" & (%1$s = %2$s)",val.getVarInfo().getName(), val.getValue()));
                    }
                    writer.append(String.format(": %1$s; \n",statesNames.get(tr.getTo())));
                }


            }

            writer.append("TRUE: state;\n");
            writer.append("esac;\n");
            writer.append("DEFINE\n");
            for (AbstractVariableInfo var : automaton.getOutputVariables()) {
                writer.append(String.format("%1$s := ", var.getName()));

                boolean isIntervalValue = intervalsByName.containsKey(var.getName());

                for (int i = 0; i < var.getPossibleValues().size(); i++) {


                    List<State> curValStates = new ArrayList<>();
                    for (State st : automaton.getStates()) {
                        if (isIntervalValue) {
                            IntervalValueHolder i1 = (IntervalValueHolder) st.getStateValue().getSymbol().getVariableValueByName(var.getName()).getValue();

//                            //fix serialization =(
//                            for(Object o: var.getPossibleValues()){
//                                IntervalValueHolder interval = (IntervalValueHolder)o;
//                                if (interval.getCurrentInterval().getFrom() <= i1.getConcreteValue() && interval.getCurrentInterval().getTo() > i1.getConcreteValue()){
//                                    i1.setCurrentInterval(interval.getCurrentInterval());
//                                    break;
//                                }
//                            }

                            IntervalValueHolder i2 = (IntervalValueHolder) var.getPossibleValues().get(i);
                            boolean res = i1.equals(i2);
                            if (res){
                                curValStates.add(st);
                            }
                        } else {
                            if (st.getStateValue().getSymbol().getVariableValueByName(var.getName()).getValue().equals(var.getPossibleValues().get(i))) {
                                curValStates.add(st);
                            }
                        }
                    }

                    if (curValStates.size() > 0) {

                        if (i == var.getPossibleValues().size() - 1){
                            if (!isIntervalValue) {
                                writer.append(String.format("%1$s;", var.getPossibleValues().get(i)));
                            } else{
                                writer.append(String.format("%1$s;", ((IntervalValueHolder)var.getPossibleValues().get(i)).getCurrentIntervalNum()));
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
                        } else{
                            writer.append(String.format(") ? %1$s :", ((IntervalValueHolder)var.getPossibleValues().get(i)).getCurrentIntervalNum()));
                        }
                    }
                }
                writer.append("\n");
            }

            if (intervalsByName.keySet().size() > 0){
                writer.append("DEFINE\n");

                for (String varName : intervalsByName.keySet()){
                    writer.append(String.format("CONT_%1$s := case\n", varName));

                    Interval[] curIntervals = intervalsByName.get(varName);
                    for (int i = 0; i < curIntervals.length; i++){
                        writer.append(String.format("%1$s = %2$s: %3$s..%4$s;\n", varName, i, Math.round(curIntervals[i].getFrom()), Math.round(curIntervals[i].getTo())));
                    }

                    writer.append("esac;\n");
                }


            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
