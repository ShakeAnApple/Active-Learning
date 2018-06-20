package utils;

import automaton.Automaton;
import automaton.Transition;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SerializationUtils {

    public static void serializeAutomaton(Automaton automaton, String filename){
        try {
            FileOutputStream fileOut = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(automaton);
            out.close();
            fileOut.close();
            System.out.println("\nSerialization Successful... Checkout your specified output file..\n");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Automaton deserializeAutomaton(String filename){
        try {
            FileInputStream fis = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(fis);
            Automaton automaton = (Automaton)in.readObject();
            in.close();
            return automaton;
        }
        catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public static void serializeTransitions(List<Transition> list, String filename){
        try {
            FileOutputStream fileOut = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(list);
            out.close();
            fileOut.close();
            System.out.println("\nSerialization Successful... Checkout your specified output file..\n");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Transition> deserializeTransitions(String filename){
        try {
            FileInputStream fis = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(fis);
            ArrayList<Transition> arr = (ArrayList<Transition>)in.readObject();
            in.close();
            return arr;
        }
        catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
