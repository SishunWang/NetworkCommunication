/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TCPproject;
import java.io.*;
import java.net.*;

/**
 *
 * @author sishunw
 */
public class server {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        if(args.length != 1) {
            System.out.println("Incorret arguments. Usage: server [port_number]");
            return;
        }
        int portNum = Integer.parseInt(args[0]);
        System.out.println("./server " + portNum);
        String clientCommand;
        boolean terminate = false;
        ServerSocket socketNum = null;
        Socket connectionSocket = null;
       
        while (!terminate) {
            if (socketNum == null || socketNum.isClosed()){
                socketNum = new ServerSocket(portNum);
                connectionSocket = socketNum.accept();
                System.out.println("get connection from " + connectionSocket.getInetAddress().getHostAddress());
                       
                DataOutputStream welcome = new DataOutputStream(connectionSocket.getOutputStream());
                welcome.writeBytes("Hello!" + '\n');
            }
         // = socketNum.accept();
         BufferedReader inFromClient =
          new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
         DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
         clientCommand = inFromClient.readLine();
         if(clientCommand == null) {
             socketNum.close();
         }
         else{
            String[] fields = clientCommand.split("\\s+");
            int answer = 0;
            if(fields[0].equals("bye") || fields[0].equals("terminate")){
              answer = -5;
              if (fields[0].equals("terminate")) terminate = true;
            }
            else if (fields[0].equals("add")){
              if(fields.length < 3) answer = -2;
              else if (fields.length > 5) answer = -3;
              else {
                        try  
                           {for(int i = 1; i < fields.length; i++){
                            //Integer check = Integer.parseInt(fields[i]);                  
                               answer+= Integer.parseInt(fields[i]);
                               }

                       }catch(NumberFormatException nfe)  
                           {  
                           answer = -4;
                       }
                    }
            }
            else if (fields[0].equals("multiply")){
                    if(fields.length < 3) answer = -2;
                    else if (fields.length > 5) answer = -3;
                    else {
                        answer = Integer.parseInt(fields[1]);
                        try  
                           {for(int i = 2; i < fields.length; i++){
                            //Integer check = Integer.parseInt(fields[i]);                  
                            answer *= Integer.parseInt(fields[i]);
                           }
                       }catch(NumberFormatException nfe)  
                           {  
                           answer = -4;
                       }
                    }
                }
            else if (fields[0].equals("subtract")){
                    if(fields.length < 3) answer = -2;
                    else if (fields.length > 5) answer = -3;
                    else {
                        answer = Integer.parseInt(fields[1]);
                        try  
                           {for(int i = 2; i < fields.length; i++){
                            //Integer check = Integer.parseInt(fields[i]);                  
                            answer -= Integer.parseInt(fields[i]);
                           }
                       }catch(NumberFormatException nfe)  
                           {  
                           answer = -4;
                       }
                 }
              }
            else answer = -1;
            System.out.println("get: " + clientCommand + ", return: " + answer);
            //answer = clientCommand.toUpperCase() + '\n';
            outToClient.writeBytes(Integer.toString(answer) + '\n');
            if(answer == -5){
               socketNum.close();
   //            socketNum = new ServerSocket(6918);
               //connectionSocket = socketNum.accept();
            }
         }
        }
        //System.out.println("teriminate");
        //socketNum.close();
        //System.exit(0);
    }  
}
