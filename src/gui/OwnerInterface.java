package gui;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.net.ssl.SSLServerSocketFactory;

import concurrence.ThreadServer;
import users.User;
import session.Session;
import session.SessionPhase;

/**
* Class OwnerInterface is a class that represents the interface for
* the owner of the brainstorm tool (as a server) with users (clients)
* with some information encapsulated.
*
* @author  Luis Eduardo  (cruxiu@ufrn.edu.br)
* @version 18.05.2018
*/

public class OwnerInterface {

    // The port to wait for request of clients
    static final int portServer = 9000;
    // The scanner used to read informations of the owner
    private static Scanner scanner  = new Scanner(System.in);

    /**
    * This is the main method to connected with the clients.
    * @param args Unused.
    * @throws IOException The exception if is the io failed
    */
    public static void main(String[] args) throws IOException {

        System.setProperty("javax.net.ssl.keyStore","/home/luiseduardo/eclipse-workspace/BrainstormTool/src/gui/examplestore");
        System.setProperty("javax.net.ssl.keyStorePassword", "123456");

        System.out.println("Welcome to the BrainstormTool!");
        System.out.println("Let's start the session.");
        System.out.println("First, what is your name?");
        User owner = new User(scanner.nextLine());

        System.out.println("Now give me a description about the next session.");
        Session session = new Session(owner, scanner.nextLine());

        // Read SSL key to connect via SSL to client
        SSLServerSocketFactory sslServerSocketFactory = (SSLServerSocketFactory)SSLServerSocketFactory.getDefault();
        ServerSocket sslSocketToProxy = sslServerSocketFactory.createServerSocket(portServer);
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        System.out.println("We are in the welcome phase.");
        String answer = "";
        while (session.getPhase() == SessionPhase.WELCOME) {
            new ThreadServer(sslSocketToProxy.accept(), session, lock, condition);
            System.out.println("Do you want to go to the brainstorm phase? y or n");
            answer = scanner.nextLine();
            if(answer.equals("y")) {
                lock.lock();
                try {
                    session.nextPhase();
                    condition.signalAll();
                } finally {
                    lock.unlock();
                }
            }
            else if(answer.equals("n")) {
                System.out.println("So let's wait for more users.");
            }
            else {
                System.out.println("I didn't understand.");
            }

        }

        System.out.println("We are in the brainstorm phase.");
        while (session.getPhase() == SessionPhase.BRAINSTORM) {
            System.out.println("Do you want to go to the voting phase? y or n");
            answer = scanner.nextLine();
            if(answer.equals("y")) {
                lock.lock();
                try {
                    session.nextPhase();
                    condition.signalAll();
                } finally {
                    lock.unlock();
                }
            }
            else if(answer.equals("n")) {
                System.out.println("So let's wait for more ideas.");
            }
            else {
                System.out.println("I didn't understand.");
            }
        }

        System.out.println("We are in the voting phase.");
        while (session.getPhase() == SessionPhase.VOTING) {
            System.out.println("Do you want to go to the rank phase? y or n");
            answer = scanner.nextLine();
            if(answer.equals("y")) {
                lock.lock();
                try {
                    session.nextPhase();
                    condition.signalAll();
                } finally {
                    lock.unlock();
                }
            }
            else if(answer.equals("n")) {
                System.out.println("So let's wait for more votes.");
            }
            else {
                System.out.println("I didn't understand.");
            }
            System.out.println("We are in the rank phase.");
            System.out.println(session.rankIdeas());
            System.out.println("Thank you for using the BrainstormTool!");
        }
    }

}
