import automaton.Automaton;
import automaton.Transition;
import config.*;
import utils.NusmvConverter;
import utils.Utils;
import values.Symbol;
import connector.IConnector;
//import connector.matlab.MatlabConnector;
import connector.nxt.NxtStudioConnector;
import impl.LearningService;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        //String path = "C:\\Projects\\Uni\\Active Learning\\BFSLearning\\config.conf";
        Config conf = ConfigReader.read(args[0]);
        //Config conf = ConfigReader.read(path);
        ConfigParser parser = new ConfigParser(new VariableInfoFabric());
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
//        List<BooleanValueHolder> possibleBoolValues = List.of(new BooleanValueHolder(true), new BooleanValueHolder(false));
//
//        AbstractVariableInfo<BooleanValueHolder> inputDoor0Open = new AbstractVariableInfo("door0", 2, possibleBoolValues, BooleanValueHolder::new);
//        AbstractVariableInfo<BooleanValueHolder> inputDoor1Open = new AbstractVariableInfo("door1", 3, possibleBoolValues, BooleanValueHolder::new);
//        AbstractVariableInfo<BooleanValueHolder> inputDoor2Open = new AbstractVariableInfo("door2", 4, possibleBoolValues, BooleanValueHolder::new);
//
//        AbstractVariableInfo<BooleanValueHolder> inputDoMotorUp = new AbstractVariableInfo("motorUp", 0, possibleBoolValues, BooleanValueHolder::new);
//        AbstractVariableInfo<BooleanValueHolder> inputDoMotorDown = new AbstractVariableInfo("motorDown", 1, possibleBoolValues, BooleanValueHolder::new);
//        List<AbstractVariableInfo> inputVars = List.of(inputDoMotorDown, inputDoMotorUp,
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
//        List<IntervalValueHolder> possiblePosValues = new ArrayList<>();
//
//        for (Interval interval : posIntervals) {
//            possiblePosValues.add(
//                    new IntervalValueHolder(posIntervals, interval)
//            );
//        }
//
//        AbstractVariableInfo<IntervalValueHolder> outputPos = new AbstractVariableInfo("pos", 9, possiblePosValues,
//                () -> new IntervalValueHolder(posIntervals, posIntervals[0]));
//
////        AbstractVariableInfo<BooleanValueHolder> outputButtonFloor0 = new AbstractVariableInfo("buttonFloor0", 0, possibleBoolValues, BooleanValueHolder::new);
////        AbstractVariableInfo<BooleanValueHolder> outputButtonFloor1 = new AbstractVariableInfo("buttonFloor1", 1, possibleBoolValues, BooleanValueHolder::new);
////        AbstractVariableInfo<BooleanValueHolder> outputButtonFloor2 = new AbstractVariableInfo("buttonFloor2", 2, possibleBoolValues, BooleanValueHolder::new);
//
//        AbstractVariableInfo<BooleanValueHolder> outputRequestFloor0 = new AbstractVariableInfo("requestFloor0", 0, possibleBoolValues, BooleanValueHolder::new);
//        AbstractVariableInfo<BooleanValueHolder> outputRequestFloor1 = new AbstractVariableInfo("requestFloor1", 1, possibleBoolValues, BooleanValueHolder::new);
//        AbstractVariableInfo<BooleanValueHolder> outputRequestFloor2 = new AbstractVariableInfo("requestFloor2", 2, possibleBoolValues, BooleanValueHolder::new);
//
//        AbstractVariableInfo<BooleanValueHolder> outputElevatorAtFloor0 = new AbstractVariableInfo("elevatorAtFloor0", 3, possibleBoolValues, BooleanValueHolder::new);
//        AbstractVariableInfo<BooleanValueHolder> outputElevatorAtFloor1 = new AbstractVariableInfo("elevatorAtFloor1", 4, possibleBoolValues, BooleanValueHolder::new);
//        AbstractVariableInfo<BooleanValueHolder> outputElevatorAtFloor2 = new AbstractVariableInfo("elevatorAtFloor2", 5, possibleBoolValues, BooleanValueHolder::new);
//
//        AbstractVariableInfo<BooleanValueHolder> outputDoor0Closed = new AbstractVariableInfo("door0Closed", 6, possibleBoolValues, BooleanValueHolder::new);
//        AbstractVariableInfo<BooleanValueHolder> outputDoor1Closed = new AbstractVariableInfo("door1Closed", 7, possibleBoolValues, BooleanValueHolder::new);
//        AbstractVariableInfo<BooleanValueHolder> outputDoor2Closed = new AbstractVariableInfo("door2Closed", 8, possibleBoolValues, BooleanValueHolder::new);
//
//        List<AbstractVariableInfo> outputVars = List.of(
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
//            List<Transition> tr = hypothesis.getAllTransitions();
//            Utils.serializeTransitions(tr, "C:\\tmp\\trans2");
//            List<Transition> list = Utils.deserializeTransitions("C:\\tmp\\trans2");

            System.out.print("Total alg: " + (TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - start)));
            NusmvConverter.saveInNusmvFormat(hypothesis, "C:\\tmp\\m_gen_newref.smv");
        } else {
            hypothesis.loadTransitions("C:\\tmp\\trans2");

            try {
                NusmvConverter.saveInNusmvFormat(hypothesis, "C:\\tmp\\m_gen.smv");
            } catch (Exception e) {
                System.out.print(e.getMessage());
            }
        }
    }

    private static void processMatlabModel(MatlabContext context){

        /////////////////////// old declaration //////////////////////////////////
//        List<BooleanValueHolder> possibleBoolValues = List.of(new BooleanValueHolder(true), new BooleanValueHolder(false));
//
//        AbstractVariableInfo<BooleanValueHolder> inputFwd = new AbstractVariableInfo("Fwd", 0, possibleBoolValues, BooleanValueHolder::new);
//        AbstractVariableInfo<BooleanValueHolder> inputRetr = new AbstractVariableInfo("Retr", 1, possibleBoolValues, BooleanValueHolder::new);
//        List<AbstractVariableInfo> inputVars = List.of(inputFwd,inputRetr);
//
//        AbstractVariableInfo<BooleanValueHolder> outputLeft = new AbstractVariableInfo("Left", 0, possibleBoolValues, BooleanValueHolder::new);
//        AbstractVariableInfo<BooleanValueHolder> outputRight = new AbstractVariableInfo("Right", 1, possibleBoolValues, BooleanValueHolder::new);
//        AbstractVariableInfo<BooleanValueHolder> outputFailure = new AbstractVariableInfo("Failure", 2, possibleBoolValues, BooleanValueHolder::new);
//        List<AbstractVariableInfo> outputVars = List.of(outputLeft,outputRight,outputFailure);
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
