import automaton.Automaton;
import config.*;
import values.Symbol;
import connector.IConnector;
//import connector.matlab.MatlabConnector;
import connector.nxt.NxtStudioConnector;
import impl.LearningService;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        String path = "C:\\Projects\\Uni\\Active Learning\\BFSLearning\\config.conf";
        //Config conf = ConfigReader.read(args[1]);
        Config conf = ConfigReader.read(path);
        ConfigParser parser = new ConfigParser(new ValueHandlerFabric());
        AbstractContext context = parser.parse(conf);

        boolean isMatlabModel = context instanceof MatlabContext;

        if (isMatlabModel) {
            processMatlabModel((MatlabContext) context);
        }
        else{
            processNxtModel((NxtContext) context);
        }
    }

    private static void processNxtModel(NxtContext context){

        //////////// old declaration /////////////////////////
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
//        Interval[] posIntervals = new Interval[]{
//                new Interval(30.0, 30.5),
//                new Interval(30.5, 224.5),
//                new Interval(224.5, 225.5),
//                new Interval(225.5, 418.5),
//                new Interval(418.5, 419.5)
//        };
//
//        List<IntervalValueHandler> possiblePosValues = new ArrayList<>();
//
//        for (Interval interval : posIntervals) {
//            possiblePosValues.add(
//                    new IntervalValueHandler(posIntervals, interval)
//            );
//        }
//
//        VariableInfo<IntervalValueHandler> outputPos = new VariableInfo("pos", 9, possiblePosValues,
//                () -> new IntervalValueHandler(posIntervals, posIntervals[0]));
//
////        VariableInfo<BooleanValueHandler> outputButtonFloor0 = new VariableInfo("buttonFloor0", 0, possibleBoolValues, BooleanValueHandler::new);
////        VariableInfo<BooleanValueHandler> outputButtonFloor1 = new VariableInfo("buttonFloor1", 1, possibleBoolValues, BooleanValueHandler::new);
////        VariableInfo<BooleanValueHandler> outputButtonFloor2 = new VariableInfo("buttonFloor2", 2, possibleBoolValues, BooleanValueHandler::new);
//
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
        //////////////////////////////////////////////////////////////////////

        AlphabetBuilder alphabetBuilder = new AlphabetBuilder();
        List<Symbol> inputAlphabet = alphabetBuilder.build(context.getInputVariablesInfos());
        List<Symbol> outputAlphabet = alphabetBuilder.build(context.getOutputVariablesInfos());

//        ModelInfo modelInfo = new ModelInfo("C:\\Projects\\FCP\\active_learning\\cylinder_simulink", "Cylinder_simple");


        Automaton hypothesis = new Automaton(context.getInputVariablesInfos(), context.getOutputVariablesInfos());
        // magic numbers oO
        IConnector connector = new NxtStudioConnector(context.getInPort(), context.getOutPort(), context);
        //IConnector connector = new NxtStudioConnector(64999, 64998);
//        IConnector connector = new NxtStudioConnector(1010, 1011);

        //hypothesis.setInputVariables(inputVars);
        //hypothesis.setOutputVariables(outputVars);

        boolean needToLearn = true;
        if (needToLearn) {
            LearningService ls = new LearningService(inputAlphabet, outputAlphabet, hypothesis, connector, context);
            long start = System.currentTimeMillis();
            while (!ls.isReady()) {
                ls.stepForward();
            }
            System.out.print("Total alg: " + (TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - start)));
        } else {
            hypothesis.loadTransitions("C:\\tmp\\trans2");

            try {
                hypothesis.getNusmvRepresentation();
            } catch (Exception e) {
                System.out.print(e.getMessage());
            }
        }
    }

    private static void processMatlabModel(MatlabContext context){

        /////////////////////// old declaration //////////////////////////////////
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
/////////////////////////////////////////////////////////////////////////////////////////////////////

        AlphabetBuilder alphabetBuilder = new AlphabetBuilder();
        List<Symbol> inputAlphabet = alphabetBuilder.build(context.getInputVariablesInfos());
        List<Symbol> outputAlphabet = alphabetBuilder.build(context.getOutputVariablesInfos());

//        ModelInfo modelInfo = new ModelInfo("", "Cylinder_simple");


        Automaton hypothesis = new Automaton(context.getInputVariablesInfos(), context.getOutputVariablesInfos());

//        String workingDirectory = "C:\\Projects\\FCP\\active_learning\\cylinder_simulink";
//        String sysName = "Cylinder_simple";
//
        String workingDirectory = context.getWorkingDir();
        String sysName = context.getSysName();

//        hypothesis.setInputVariables(inputVars);
//        hypothesis.setOutputVariables(outputVars);

        boolean needToLearn = false;
//        if (needToLearn) {
//
//            MatlabConnector connector = new MatlabConnector(workingDirectory, sysName, context);
//
//            connector.connect();
//            connector.loadModelAsync(sysName);
//
//            LearningService ls = new LearningService(inputAlphabet, outputAlphabet, hypothesis, connector, context);
//            long start = System.currentTimeMillis();
//            while (!ls.isReady()) {
//                ls.stepForward();
//            }
//            System.out.print("Total alg: " + (TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - start)));
//        } else {
//            hypothesis.loadTransitions("C:\\Temp\\trans2");
//
//            try {
//                hypothesis.getNusmvRepresentation();
//            } catch (Exception e) {
//                System.out.print(e.getMessage());
//            }
//        }
    }
}
