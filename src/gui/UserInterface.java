package gui;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLSocketFactory;

/**
* Class UserInterface is a class that represents the interface for
* the user of the brainstorm tool (as a client) to connect with the
* owner (as a server) with some information encapsulated.
*
* @author  Luis Eduardo  (cruxiu@ufrn.edu.br)
* @version 18.05.2018
*/


public class UserInterface {

    // The port to connect with the server
    static final int portServer = 9000;
    // The if of the server to connect with his
    static final String ipServer = "127.0.0.1";
    // The scanner used to read informations of the user
    static final Scanner scanner = new Scanner(System.in);

    /**
    * This is the main method to connected with the owner.
    * @param args Unused.
    * @throws InterruptedException The exception if is interrupted
    */
    public static void main(String[] args) throws InterruptedException {
        System.setProperty("javax.net.ssl.trustStore","/home/luiseduardo/eclipse-workspace/BrainstormTool/src/gui/examplestore");
        System.setProperty("javax.net.ssl.trustStorePassword","123456");

        System.out.println("Welcome to the BrainstormTool!");
        System.out.println("Let's connect you to the session.");
        System.out.println("First, what is your name?");
        String name = scanner.nextLine();

        // Read SSL key to connect via SSL to server
        SSLSocketFactory sslSocketFactory = (SSLSocketFactory)SSLSocketFactory.getDefault();
        try {
            // Create socket to server
            Socket sslSocketToServer = sslSocketFactory.createSocket(ipServer, portServer);
            // Create output stream object to send messages to server
            PrintWriter outServer = new PrintWriter(sslSocketToServer.getOutputStream(), true);
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(sslSocketToServer.getInputStream()))) {
                System.out.println("Welcome to the BrainstormTool!");
                String phase = "";
                int hashCode = 0;
                while(!phase.equals("RANK")) {
                    phase = bufferedReader.readLine();
                    if(phase.equals("WELCOME")) {
                        hashCode = welcomePhase(outServer, bufferedReader, name);
                    }
                    else if(phase.equals("BRAINSTORM")) {
                        brainstormPhase(outServer, bufferedReader, hashCode);
                    }
                    else if(phase.equals("VOTING")) {
                        votingPhase(outServer, bufferedReader, hashCode);
                    }
                    else if(phase.equals("RANK")) {
                        rankPhase(outServer, bufferedReader);
                    }
                    else {
                        System.out.println("Something went wrong :( \nThe program will be finished");
                    }
                }
                sslSocketToServer.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(UserInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
    * Private method to run the welcome phase.
    * @param outServer A PrintWriter that represents a channel to send messages
    * to the server.
    * @param bufferedReader A BufferedReader that represents a buffer to receive
    * messages from the server.
    * @param name A String that represents the name of this user.
    * @return A int that represents a unique hash code for this user.
    */
    private static int welcomePhase(PrintWriter outServer, BufferedReader bufferedReader, String name) throws IOException {
        System.out.println("We are in the welcome phase.");
        outServer.println(name);
        return Integer.parseInt(bufferedReader.readLine());
    }

    /**
    * Private method to run the brainstorm phase.
    * @param outServer A PrintWriter that represents a channel to send messages
    * to the server.
    * @param bufferedReader A BufferedReader that represents a buffer to receive
    * messages from the server.
    * @param hashCode A int that represents a unique hash code for this user.
    */
    private static void brainstormPhase(PrintWriter outServer, BufferedReader bufferedReader, int hashCode)  throws IOException {
        System.out.println("We are in the brainstorm phase. \nType a idea.");
        String idea = scanner.nextLine();
        outServer.println(hashCode);
        outServer.println(idea);
    }

    /**
    * Private method to run the voting phase.
    * @param outServer A PrintWriter that represents a channel to send messages
    * to the server.
    * @param bufferedReader A BufferedReader that represents a buffer to receive
    * messages from the server.
    * @param hashCode A int that represents a unique hash code for this user.
    */
    private static void votingPhase(PrintWriter outServer, BufferedReader bufferedReader, int hashCode) throws IOException {
        System.out.println("We are in the voting phase. \nVote for a idea.");
        String ideas = bufferedReader.readLine();
        System.out.println(ideas);
        System.out.println("Note: Write the numbers of the ideas you would like to choose separated by commas.");
        String ideasVoted = scanner.nextLine();
        outServer.println(hashCode);
        outServer.println(ideasVoted);
    }

    /**
    * Private method to run the rank phase.
    * @param outServer A PrintWriter that represents a channel to send messages
    * to the server.
    * @param bufferedReader A BufferedReader that represents a buffer to receive
    * messages from the server.
    */
    private static void rankPhase(PrintWriter outServer, BufferedReader bufferedReader)  throws IOException {
        System.out.println("We are in the rank phase. \nWait for results delivered to owner. \nThank you for using the brainstorm tool :) \nThe program will be finished.");
    }
}
