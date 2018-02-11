package tcpclient;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.*;
import java.net.*;

/**
 *
 * @author sishunw
 */
public class client {

    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        if(args.length != 2) {
            System.out.println("Incorret arguments. Usage: client [serverURI] [port_number]");
            return;
        }
        boolean exit = false;
        //String uri = "sand.cise.ufl.edu";
        String uri  = args[0];
        int port = Integer.parseInt(args[1]);
        System.out.println("./client " + uri + " " + port);
        Socket clientSocket = new Socket(uri, port);
        String sentence;
        String modifiedSentence;
        int answer = 0;
   
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String welcome = inFromServer.readLine();
        System.out.println("receive: " + welcome);
        
        while (!exit){
            sentence = inFromUser.readLine();
            outToServer.writeBytes(sentence + '\n');
            modifiedSentence = inFromServer.readLine();
            answer = Integer.parseInt(modifiedSentence);

            //System.out.println("FROM SERVER: " + modifiedSentence);
            switch (answer){
                case -1:
                    System.out.println("receive: incorrect operation command.");
                    break;
                case -2:
                    System.out.println("receive: number of inputs is less than two.");
                    break;
                case -3:
                    System.out.println("receive: number of inputs is more than four.");
                    break;
                case -4:
                    System.out.println("receive: one or more of the inputs contain(s) non-number(s).");
                    break;
                case -5:
                    System.out.println("receive: exit.");
                    exit = true;
                    break;
                default:
                    System.out.println("receive: " + answer);
            }
        }
        clientSocket.close();
    }  
}
