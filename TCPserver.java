/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TCP;
import java.io.*;
import java.net.*;

/**
 *
 * @author sishunw
 */
public class TCPserver {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        String clientCommand;
        
        boolean terminate = false;
        ServerSocket socketNum = new ServerSocket(6918);
        

        while (!terminate) {
         Socket connectionSocket = socketNum.accept();
         BufferedReader inFromClient =
          new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
         DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
         clientCommand = inFromClient.readLine();
         String[] fields = clientCommand.split("\\s+");
         int answer = 0;
         switch(fields[0]){
             case "bye":
                 answer = -5;
                 break;
             case "terminate":
                 answer = -5;
                 terminate = true;
                 break;
             case "add":
                 if(fields.length < 3) answer = -2;
                 else if (fields.length > 5) answer = -3;
                 else {
                     try  
                        {for(int i = 1; i < fields.length; i++){
                         //Integer check = Integer.parseInt(fields[i]);                  
                         answer += Integer.parseInt(fields[i]);
                        }
                    }catch(NumberFormatException nfe)  
                        {  
                        answer = -4;
                    }
                 }
                        break;
             case "multiply":
                 if(fields.length < 3) answer = -2;
                 else if (fields.length > 5) answer = -3;
                 else {
                     for(int i = 1; i <= fields.length; i++){
                         //Integer check = Integer.parseInt(fields[i]);
                         if(!fields[i].chars().allMatch( Character::isDigit ) ) {
                             answer = -4;
                             break;
                         }
                         answer *= Integer.parseInt(fields[i]);
                     }
                 }
                 break;
             case "subtract":
                 if(fields.length < 3) answer = -2;
                 else if (fields.length > 5) answer = -3;
                 else {
                     for(int i = 1; i <= fields.length; i++){
                        //Integer check = Integer.parseInt(fields[i]);
                         if(!fields[i].chars().allMatch( Character::isDigit ) ) {
                             answer = -4;
                             break;
                         }
                         if(i == 1)
                            answer = Integer.parseInt(fields[i]);
                         else answer += Integer.parseInt(fields[i]);
                     }
                 }
                 break;
             default:
                 answer = -1;
         }          
         System.out.println("Received: " + clientCommand);
         //answer = clientCommand.toUpperCase() + '\n';
         outToClient.writeBytes(Integer.toString(answer) + '\n');
//outToClient.writeBytes("haha" + '\n');
         System.out.println("hi");
        }
        System.out.println("teriminate");
        socketNum.close();
    }  
}
