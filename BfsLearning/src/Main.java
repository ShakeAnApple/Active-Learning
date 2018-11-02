import automaton.Automaton;
import automaton.optimization.AutomatonOptimizer;
import config.*;
import simulation.ISimulationService;
import simulation.SimulationService;
import utils.AlphabetBuilder;
import utils.NusmvConverter;
import utils.logging.ConsoleLogger;
import utils.logging.Log;
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
        ConfigParser parser = new ConfigParser(new VariableInfoFabric());
        AbstractContext context = parser.parse(conf);
        Log.init(new ConsoleLogger());

        boolean isMatlabModel = context instanceof MatlabContext;

        if (isMatlabModel) {
            processMatlabModel((MatlabContext) context);
        }
        else{
            processNxtModel((NxtContext) context);
        }
    }

    private static void processNxtModel(NxtContext context){

        AlphabetBuilder alphabetBuilder = new AlphabetBuilder();
        List<Symbol> inputAlphabet = alphabetBuilder.build(context.getInputVariablesInfos());
        List<Symbol> outputAlphabet = alphabetBuilder.build(context.getOutputVariablesInfos());

//        ModelInfo modelInfo = new ModelInfo("C:\\Projects\\FCP\\active_learning\\cylinder_simulin k", "Cylinder_simple");


        Automaton hypothesis = new Automaton(context.getInputVariablesInfos(), context.getOutputVariablesInfos());
        IConnector connector = new NxtStudioConnector(context.getInPort(), context.getOutPort(), context);
        //IConnector connector = new NxtStudioConnector(64999, 64998);
//        IConnector connector = new NxtStudioConnector(1010, 1011);

//        hypothesis.loadTransitions("C:\\tmp\\trans-reduced");
//        NusmvConverter.saveInNusmvFormat(hypothesis, "C:\\tmp\\m-gen-fixed.smv");

        boolean needToLearn = false;
        if (needToLearn) {
            ISimulationService simulationService = new SimulationService(connector);
            LearningService ls = new LearningService(inputAlphabet, outputAlphabet, hypothesis, simulationService);
            long start = System.currentTimeMillis();
            while (!ls.isReady()) {
                ls.stepForward();
            }
//            List<Transition> tr = hypothesis.getAllTransitions();
//            Utils.serializeTransitions(tr, "C:\\tmp\\trans2");


            System.out.print("Total alg: " + (TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - start)));
            NusmvConverter.saveInNusmvFormat(hypothesis, "C:\\tmp\\m_gen_newref_refactor.smv");
        } else {
            hypothesis.loadTransitions("C:\\tmp\\trans3");
            AutomatonOptimizer ao = new AutomatonOptimizer();
            Automaton a = ao.reduceTransitions(hypothesis);

            try {
                NusmvConverter.saveInNusmvFormat(a, "C:\\tmp\\m_gen-optimized-3.smv");
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
