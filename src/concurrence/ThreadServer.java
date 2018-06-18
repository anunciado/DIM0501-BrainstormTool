package concurrence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.logging.Level;
import java.util.logging.Logger;

import session.Session;
import users.Idea;
import users.User;

/**
* Class ThreadServer it's a class that extends Threads and represents
* a server implementation using sockets  to connect with users (clients)
* with some informations encapsulated.
*
* @author  Luis Eduardo  (cruxiu@ufrn.edu.br)
* @version 18.05.2018
*/

public class ThreadServer extends Thread {
    // The server socket to connect with clients.
    private Socket sslSocketToClient;
    // The session used in owner interface.
    private Session session;
    // The lock used to syncronize the threads.
    private Lock lock;
    // The condition used to syncronize the threads.
    private Condition condition;

    /**
    * Creates a new ThreadServer object given parameters.
    *
    * @param sslSocketToClient A Socket that represents the server socket to
    * connect with clients.
    * @param session A Session that represents the session used in owner interface.
    * @param lock A Lock that represents the lock used to syncronize the threads.
    * @param condition A Condition that represents the condition used to
    * syncronize the threads.
    *
    */
    public ThreadServer(Socket sslSocketToClient, Session session, Lock lock, Condition condition) {
        this.sslSocketToClient = sslSocketToClient;
        this.session = session;
        this.lock = lock;
        this.condition = condition;
        this.start();
    }

    /**
    * Private method to run the welcome phase.
    * @param outServer A PrintWriter that represents a channel to send messages
    * to the client.
    * @param bufferedReader A BufferedReader that represents a buffer to receive
    * messages from the client.
    */
    private void welcomePhase(PrintWriter outProxy, BufferedReader bufferedReader) throws IOException {
        outProxy.println("WELCOME");
        String name = bufferedReader.readLine();
        User user = new User(name);
        this.session.addParticipant(user);
        outProxy.println(user.getHashCode());
    }

    /**
    * Private method to run the brainstorm phase.
    * @param outServer A PrintWriter that represents a channel to send messages
    * to the client.
    * @param bufferedReader A BufferedReader that represents a buffer to receive
    * messages from the client.
    */
    private void brainstormPhase(PrintWriter outProxy, BufferedReader bufferedReader) throws IOException {
        outProxy.println("BRAINSTORM");
        int hashCode = Integer.parseInt(bufferedReader.readLine());
        String description = bufferedReader.readLine();
        User user = this.session.getParticipants().get(hashCode);
        Idea idea = new Idea(user, description, this.session);
        this.session.addIdea(idea);
    }

    /**
    * Private method to run the voting phase.
    * @param outServer A PrintWriter that represents a channel to send messages
    * to the client.
    * @param bufferedReader A BufferedReader that represents a buffer to receive
    * messages from the client.
    */
    private void votingPhase(PrintWriter outProxy, BufferedReader bufferedReader) throws IOException {
        outProxy.println("VOTING");
        outProxy.println(this.session.getIdeas());
        int hashCode = Integer.parseInt(bufferedReader.readLine());
        String ideasVoted = bufferedReader.readLine();
        List<String> list = new ArrayList<String>(Arrays.asList(ideasVoted.split(", ")));
        User user = this.session.getParticipants().get(hashCode);
        if(list  != null) {
            for(String idea : list) {
                this.session.getIdeas().get(Integer.parseInt(idea)).registerVote(user);
            }
        }
    }

    /**
    * Private method to run the rank phase.
    * @param outServer A PrintWriter that represents a channel to send messages
    * to the client.
    * @param bufferedReader A BufferedReader that represents a buffer to receive
    * messages from the client.
    */
    private void rankPhase(PrintWriter outProxy, BufferedReader bufferedReader) throws IOException {
        outProxy.println("RANK");
    }

    /**
    * Private method to run the thread.
    */
    @Override
    public void run() {
        try {
            PrintWriter outProxy = new PrintWriter(sslSocketToClient.getOutputStream(), true);
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(sslSocketToClient.getInputStream()))) {
                welcomePhase(outProxy, bufferedReader);
                this.lock.lock();
                try{
                    this.condition.awaitUninterruptibly();
                    brainstormPhase(outProxy, bufferedReader);
                } finally{
                    this.lock.unlock();
                }
                this.lock.lock();
                try {
                    this.condition.awaitUninterruptibly();
                    votingPhase(outProxy, bufferedReader);
                } finally{
                    this.lock.unlock();
                }

                this.lock.lock();
                try {
                    this.condition.awaitUninterruptibly();
                    rankPhase(outProxy, bufferedReader);
                } finally{
                    this.lock.unlock();
                }
            }
        }
        catch (IOException ex) {
            Logger.getLogger(ThreadServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
