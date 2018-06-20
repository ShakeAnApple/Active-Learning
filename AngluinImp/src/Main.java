import automaton.Automaton;
import config.*;
import connection.matlab.MatlabConnector;
import connection.nxt.NxtStudioConnector;
import impl.LearningService;
import simulation.SimulationService;
import utils.AlphabetConstructor;
import utils.NusmvConverter;
import utils.SerializationUtils;
import values.Symbol;

import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {

        //String path = "C:\\Projects\\Uni\\Active Learning\\BFSLearning\\config.conf";
        Config conf = ConfigReader.read(args[1]);
        //Config conf = ConfigReader.read(path);
        ConfigParser parser = new ConfigParser(new VariableInfoFabric());
        AbstractContext context = parser.parse(conf);

        boolean isMatlabModel = context instanceof MatlabContext;

//        Automaton a = SerializationUtils.deserializeAutomaton("automaton_gen.bin");
//        NusmvConverter.saveInNusmvFormat(a, "res.txt");

        if (isMatlabModel) {
            processMatlabModel((MatlabContext) context);
        }
        else{
            processNxtModel((NxtContext) context);
        }
    }

    public static void processMatlabModel(MatlabContext context) throws Exception{

        ///////////////////////////// old declarations //////////////////////
        //        List<BooleanValueHandler> possibleBoolValues = List.of(new BooleanValueHandler(true), new BooleanValueHandler(false));
//
//        VariableInfo<BooleanValueHandler> inputFwd = new VariableInfo("Fwd", 0, possibleBoolValues, BooleanValueHandler::new);
//        VariableInfo<BooleanValueHandler> inputRetr = new VariableInfo("Retr", 1, possibleBoolValues, BooleanValueHandler::new);
//        List<VariableInfo> inputVars = List.of(inputFwd,inputRetr);
//
//        VariableInfo<BooleanValueHandler> outputLeft = new VariableInfo("Left", 0, possibleBoolValues, BooleanValueHandler::new);
//        VariableInfo<BooleanValueHandler> outputRight = new VariableInfo("Right", 1, possibleBoolValues, BooleanValueHandler::new);
//        VariableInfo<BooleanValueHandler> outputFailure = new VariableInfo("Failure", 2, possibleBoolValues, BooleanValueHandler::new);
//        List<VariableInfo> outputVars = List.of(outputLeft,outputRight,outputFailure);
//
//        String workingDir = "C:\\Projects\\FCP\\active_learning\\cylinder_simulink";
//        String sysName = "Cylinder_simple";
        ///////////////////////////////////////

        List<Symbol> inputAlphabet = AlphabetConstructor.construct(context.generateInputVariablesValues());
        List<Symbol> outputAlphabet = AlphabetConstructor.construct(context.generateOutputVariablesValues());

        Automaton hypothesis = new Automaton(context.getInputVariablesInfos(), context.getOutputVariablesInfos(), inputAlphabet, outputAlphabet);


        MatlabConnector connector = new MatlabConnector(context.getWorkingDir(), context.getSysName(), context);
        SimulationService simulationService = new SimulationService(connector);
        LearningService ls = new LearningService(simulationService, hypothesis);

        connector.connect();
        connector.loadModelAsync(context.getSysName());


        do{
            ls.stepForward();
        }while((!ls.isReady()));
    }

    public static void processNxtModel(NxtContext context) throws Exception {

        /////////////// old vars declarations ////////////////////////////////////////////////////
        //        List<BooleanValueHandler> possibleBoolValues = List.of(new BooleanValueHandler(true), new BooleanValueHandler(false));
//
//        VariableInfo<BooleanValueHandler> inputDoor0Open = new VariableInfo("door0", 2, possibleBoolValues, BooleanValueHandler::new);
//        VariableInfo<BooleanValueHandler> inputDoor1Open = new VariableInfo("door1", 3, possibleBoolValues, BooleanValueHandler::new);
//        VariableInfo<BooleanValueHandler> inputDoor2Open = new VariableInfo("door2", 4, possibleBoolValues, BooleanValueHandler::new);
//
//        VariableInfo<BooleanValueHandler> inputDoMotorUp = new VariableInfo("motorUp", 0, possibleBoolValues, BooleanValueHandler::new);
//        VariableInfo<BooleanValueHandler> inputDoMotorDown = new VariableInfo("motorDown", 1, possibleBoolValues, BooleanValueHandler::new);
//        List<VariableInfo> inputVars = List.of(inputDoMotorDown, inputDoMotorUp,
//                inputDoor0Open, inputDoor1Open, inputDoor2Open);
//
//        Interval<Double>[] posIntervals = new Interval[]{
//                new Interval<>(30.0, 30.5),
//                new Interval<>(30.5, 224.5),
//                new Interval<>(224.5, 225.5),
//                new Interval<>(225.5, 418.5),
//                new Interval<>(418.5, 419.5)
//        };
//
//        List<IntervalValueHandler> possiblePosValues = new ArrayList<>();
//
//        for (Interval<Double> interval : posIntervals) {
//            possiblePosValues.add(
//                    new IntervalValueHandler(posIntervals, Double::parseDouble, (Double o1, Double o2) -> {
//                        if (01 > o2) return 1;
//                        if (o1 < o2) return -1;
//                        return 0;
//                    }, interval)
//            );
//        }
//
//        VariableInfo<IntervalValueHandler> outputPos = new VariableInfo("pos", 9, possiblePosValues, () -> new IntervalValueHandler(posIntervals, (String str) -> Double.parseDouble(str), (Double o1, Double o2) -> {
//            if (01 > o2) return 1;
//            if (o1 < o2) return -1;
//            return 0;
//        }, posIntervals[0]));
//        VariableInfo<BooleanValueHandler> outputButtonFloor0 = new VariableInfo("buttonFloor0", 0, possibleBoolValues, BooleanValueHandler::new);
//        VariableInfo<BooleanValueHandler> outputButtonFloor1 = new VariableInfo("buttonFloor1", 1, possibleBoolValues, BooleanValueHandler::new);
//        VariableInfo<BooleanValueHandler> outputButtonFloor2 = new VariableInfo("buttonFloor2", 2, possibleBoolValues, BooleanValueHandler::new);

//        VariableInfo<BooleanValueHandler> outputRequestFloor0 = new VariableInfo("requestFloor0", 0, possibleBoolValues, BooleanValueHandler::new);
//        VariableInfo<BooleanValueHandler> outputRequestFloor1 = new VariableInfo("requestFloor1", 1, possibleBoolValues, BooleanValueHandler::new);
//        VariableInfo<BooleanValueHandler> outputRequestFloor2 = new VariableInfo("requestFloor2", 2, possibleBoolValues, BooleanValueHandler::new);
//
//        VariableInfo<BooleanValueHandler> outputElevatorAtFloor0 = new VariableInfo("elevatorAtFloor0", 3, possibleBoolValues, BooleanValueHandler::new);
//        VariableInfo<BooleanValueHandler> outputElevatorAtFloor1 = new VariableInfo("elevatorAtFloor1", 4, possibleBoolValues, BooleanValueHandler::new);
//        VariableInfo<BooleanValueHandler> outputElevatorAtFloor2 = new VariableInfo("elevatorAtFloor2", 5, possibleBoolValues, BooleanValueHandler::new);
//
//        VariableInfo<BooleanValueHandler> outputDoor0Closed = new VariableInfo("door0Closed", 6, possibleBoolValues, BooleanValueHandler::new);
//        VariableInfo<BooleanValueHandler> outputDoor1Closed = new VariableInfo("door1Closed", 7, possibleBoolValues, BooleanValueHandler::new);
//        VariableInfo<BooleanValueHandler> outputDoor2Closed = new VariableInfo("door2Closed", 8, possibleBoolValues, BooleanValueHandler::new);
//
//        List<VariableInfo> outputVars = List.of(
//                outputDoor0Closed, outputDoor1Closed, outputDoor2Closed,
//                outputElevatorAtFloor0, outputElevatorAtFloor1, outputElevatorAtFloor2,
//                outputRequestFloor0, outputRequestFloor1, outputRequestFloor2,
//                outputPos);
//////////////////////////////////////////////////////////////

        List<Symbol> inputAlphabet = AlphabetConstructor.construct(context.generateInputVariablesValues());
        List<Symbol> outputAlphabet = AlphabetConstructor.construct(context.generateOutputVariablesValues());

        Automaton hypothesis = new Automaton(context.getInputVariablesInfos(), context.getOutputVariablesInfos(), inputAlphabet, outputAlphabet);

//        Automaton a = SerializationUtils.deserializeAutomaton("automaton.bin");

        NxtStudioConnector connector = new NxtStudioConnector(context.getInPort(), context.getOutPort(), context);
        connector.connect();
        SimulationService simulationService = new SimulationService(connector);
        LearningService ls = new LearningService(simulationService, hypothesis);

        do{
            ls.stepForward();
        }while((!ls.isReady()));
    }
}
