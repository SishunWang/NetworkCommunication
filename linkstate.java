/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.*;
import java.net.Socket;
import java.util.*;

/**
 *
 * @author sishunw
 */
public class linkstate {

    //making a new class that stores current path length from router 1 to the router and previous router
    public String previousRouter;
    public String pathLength;

    //constructor
    public linkstate(String router, String length) {
        this.previousRouter = router;
        this.pathLength = length;
    }

    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        //check if arugment number correct
        if (args.length != 1) {
            System.out.println("Incorret arguments. Usage: linkstate [fileName]");
            return;
        }
        //read the file
        String fileName = args[0];
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line;    //every line
        String N = "";  //N contains the router has already been routed
        int previousMin = 0;    //the lowest path length for current round

        boolean firstRun = true;    //whether firstrun or not
        int router = 1;     //first router
        int spaceNum;       //space number between N' and D(2)p(2)
        int slashNum;       // '-' number

        //store every line in hashmap with key = router number and value = the line
        HashMap<Integer, ArrayList<String>> table = new HashMap<>();
        int count = 0;
        while (!(line = br.readLine()).equals("EOF.")) {
            count++; //count how many router;
            String[] l = line.split("\\W+");

            ArrayList<String> D = new ArrayList<>();
            for (String element : l) {
                D.add(element);
            }
            table.put(router, D);
            router++;
        }
        //set up space number and "-" number
        if(count < 10){
            spaceNum = count * 2 + 1;
        }
        else if (count < 100){
            spaceNum = 9 * 2 + (count - 9) * 3 + 1;
        }
        else {
            spaceNum = 9 * 2 + 90 * 3 + (count - 99) * 4 + 1;
        }
        slashNum = count * 12;
       
        //an arraylist contains the path length to from router 1 to each router and its previous.
        ArrayList<linkstate> linkState = new ArrayList<linkstate>();
        

        for (int i = 1; i <= table.size(); i++) {
            //printing and setting up if firstrun
            if (firstRun == true) {
                printLine(8 + spaceNum + slashNum);
                System.out.print("Step\tN\'");
                printSpace(spaceNum-5);
                for (int j = 2; j <= table.size(); j++) {
                    String format = String.format("%12s","D(" + j + "),p(" + j + ") " );
                    System.out.print(format);
                }
                System.out.println("");
                N += "1";
                printLine(8 + spaceNum + slashNum);
                for (int j = 0; j < table.get(1).size(); j++) {
                    linkstate state = new linkstate("1", table.get(1).get(j));
                    //if cannot reach the router, set previousRouter = -1;
                    if (state.pathLength.equals("N")) {
                        state.previousRouter = "-1";
                    }
                    linkState.add(state);
                }
                firstRun = false;
            } else {
                //keey adding router to N' if it was lowest path and store analyze this router.
                N += "," + previousMin;
                //check if there is shortest path to every router after adding previousMin
                for (int j = 1; j < table.get(1).size(); j++) {
                    int pathL = Integer.MAX_VALUE;
                    if (!linkState.get(j).pathLength.equals("N")) {
                        pathL = Integer.parseInt(linkState.get(j).pathLength);
                    }
                    if (!table.get(previousMin).get(j).equals("N")) {
                        int detour = Integer.parseInt(table.get(previousMin).get(j)) + Integer.parseInt(linkState.get(previousMin - 1).pathLength);
                        if (detour < pathL) {
                            pathL = detour;
                            //if pathL changed, change its previousRouter also
                            linkState.get(j).previousRouter = Integer.toString(previousMin);
                        }
                    }
                    if (pathL != Integer.MAX_VALUE) {
                        linkState.get(j).pathLength = Integer.toString(pathL);
                    }
                }
            }
            //function checking which router has shortest path in current round
            int min = Integer.MAX_VALUE;
            for (int j = 1; j < linkState.size(); j++) {
                if (linkState.get(j).pathLength.equals("N")) {
                    continue;
                }
                if (N.contains(',' + Integer.toString(j + 1))) {
                    continue;
                }
                if (Integer.parseInt(linkState.get(j).pathLength) < min) {
                    min = Integer.parseInt(linkState.get(j).pathLength);
                    previousMin = j + 1;
                }
            }
            //priting 
            String step = String.format("%-8s", Integer.toString(i - 1));
            System.out.print(step + N);
            for (int j = (N.length()); j < (spaceNum+2); j++) {
                System.out.print(" ");
            }
            for (int j = 1; j < linkState.size(); j++) {
                String output = "";
                //if its previous router is 0, means it is in N, -1 means it not reachable.
                if (linkState.get(j).previousRouter.equals("0")) {
                    output = String.format("%-12s", "   ");
                } else if (linkState.get(j).previousRouter.equals("-1")) {
                    output = String.format("%-12s", " ∞ ");
                } else {
                    output = String.format("%-12s", linkState.get(j).pathLength + "," + linkState.get(j).previousRouter);
                }
                System.out.print(output);
            }
            System.out.println("");
            printLine(8 + spaceNum + slashNum);
            //set previousrouter to 0, means it is in N' already
            linkstate Nrouter = new linkstate("0", linkState.get(previousMin - 1).pathLength);
            linkState.set(previousMin - 1, Nrouter);
        }
    }

    private static void printLine(int count) {
        //printing "-" line
        for (int i = 0; i < count; i++) {
            System.out.print("-");
        }
        System.out.println("");
    }

    private static void printSpace(int count) {
        //printing space
        for (int i = 0; i < count; i++) {
            System.out.print(" ");
        }
    }
}
