package session;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import users.Idea;
import users.User;

/**
* Class Session it's a class that represents a session of brainstorming
* with some informations encapsulated.
*
* @author Luís Eduardo (cruxiu@ufrn.edu.br)
* @version 2018.06.15
*/

public class Session {

    // The owner of this session of brainstorming
    private User owner;
    // The description of this session of brainstorming
    private String description;
    // The phase of this session of brainstorming
    private SessionPhase phase;
    // The voting limit per user of this session of brainstorming
    private int votingLimit = 3;
    // The hash code of the users added to this class
    private int hashCodeUser;
    // The hash code of the ideas added to this class
    private int hashCodeIdea;
    // The list of ideas of this session of brainstorming
    private ConcurrentMap<Integer, Idea> ideas;
    // The list of participants of this session of brainstorming
    private ConcurrentMap<Integer, User> participants;

    /**
    * Creates a new Session object given the parameters a User owner,
    * a String description and initializes a empty list of participants
    * and ideas and their respectively hash codes.
    *
    * @param owner A User that represents the owner of this session of
    * brainstorming.
    * @param description A String that represents the description of
    * this session of brainstorming.
    *
    */
    public Session(User owner, String description) {
        this.owner = owner;
        this.description = description;
        this.phase = SessionPhase.WELCOME;
        this.ideas = new ConcurrentHashMap<Integer, Idea>();
        this.participants = new ConcurrentHashMap<Integer, User>();
        this.hashCodeUser = 0;
        this.hashCodeIdea = 0;
    }

    /**
    * Public method to get the owner of this session of brainstorming.
    * @return A User that represents the owner of this session of
    * brainstorming.
    */
    public synchronized User getOwner() {
        return owner;
    }

    /**
    * Public method to get the voting limit per user of this session
    * of brainstorming.
    * @return A int that represents the voting limit per user of this
    * session of brainstorming.
    */
    public synchronized int getVotingLimit() {
        return votingLimit;
    }

    /**
    * Public method to get the phase of this session of brainstorming.
    * @return A SessionPhase that represents the phase of this session
    * of brainstorming.
    */
    public synchronized SessionPhase getPhase() {
        return this.phase;
    }

    /**
    * Public method to get the next hash code to be used by a new user.
    * @return A int that represents the next hash code to be
    * used by a new user.
    */
    public synchronized int getHashCodeUser() {
        return hashCodeUser;
    }

    /**
    * Public method to set the next hash code to be used by a new user.
    * @param hashCodeUser A int that represents the next hash code to be
    * used by a new user.
    */
    public synchronized void setHashCodeUser(int hashCodeUser) {
        this.hashCodeUser = hashCodeUser;
    }

    /**
    * Public method to get the next hash code to be used by a new idea.
    * @return A int that represents the next hash code to be used by a new
    * idea.
    */
    public synchronized int getHashCodeIdea() {
        return hashCodeIdea;
    }

    /**
    * Public method to set the next hash code to be used by a new idea.
    * @param hashCodeIdea A int that represents the next hash code to be
    * used by a new idea.
    */
    public synchronized void setHashCodeIdea(int hashCodeIdea) {
        this.hashCodeIdea = hashCodeIdea;
    }

    /**
    * Public method to get the list of ideas of this session of
    * brainstorming.
    * @return A ConcurrentMap<Integer, User> that represents the list
    * of ideas of this session of brainstorming.
    */
    public synchronized ConcurrentMap<Integer, Idea> getIdeas() {
        return this.ideas;
    }

    /**
    * Public method to get the list of participants of this session
    * of brainstorming.
    * @return A ConcurrentMap<Integer, User> that represents the list
    * of participants of this session of brainstorming.
    */
    public synchronized ConcurrentMap<Integer, User> getParticipants() {
        return this.participants;
    }

    /**
    * Public method to advance the session to the next phase.
    */
    public synchronized void nextPhase() {
        if(SessionPhase.values().length > (this.phase.ordinal() + 1)) {
            this.phase = SessionPhase.values()[(this.phase.ordinal() + 1)];
        }
    }

    /**
    * Public method add a new idea for this session.
    * @param idea A Idea that represents a idea of some participant of
    * this session.
    * @return A Idea The idea object, if the idea is added, otherwise null.
    */
    public synchronized Idea addIdea(Idea idea) {
        if (this.getPhase() == SessionPhase.BRAINSTORM
        && this.getParticipants().containsValue(idea.getAuthor())
        && !this.getIdeas().containsValue(idea)) {
            setHashCodeIdea(getHashCodeIdea() + 1);
            idea.setHashCode(getHashCodeIdea());
            return this.ideas.put(getHashCodeIdea(), idea);
        }
        else {
            return null;
        }
    }

    /**
    * Public method returns all ideals with at least one voter in descending
    * order.
    * @return A List<Idea> with all ideals with at least one voter in
    * descending order.
    */
    public synchronized List<Idea> rankIdeas() {
        if(this.getPhase() == SessionPhase.RANK) {
            List<Idea> rankIdeas = new ArrayList<Idea>();
            for(Idea idea : this.ideas.values()) {
                if(idea.countVotes() > 0) {
                    rankIdeas.add(idea);
                }
            }
            Collections.sort(rankIdeas);
            return rankIdeas;
        }
        else {
            return null;
        }
    }

    /**
    * Public method add a new participant for this session.
    * @param participant A User that represents a participant that will be
    * added in this session.
    * @return A User The user object, if the participant is added, otherwise null.
    */
    public synchronized User addParticipant(User participant) {
        if (this.getPhase() == SessionPhase.WELCOME && !(this.getParticipants().containsValue(participant))) {
            setHashCodeUser(getHashCodeUser() + 1);
            participant.setHashCode(getHashCodeUser());
            return this.participants.put(getHashCodeUser(), participant);
        }
        else {
            return null;
        }
    }

    /**
    * Public method add a remove a participant of this session.
    * @param hashCode A int that represents the hash code of the participant that will be
    * removed of this session.
    * @return A User The user object, if the participant is removed, otherwise null.
    */
    public synchronized User removeParticipant(int hashCode) {
        return this.participants.remove(hashCode);
    }

    /**
    * Public method to print the informations encapsulated of this session.
    * @return A String that represents the informations encapsulated of this
    * session.
    */
    @Override
    public String toString() {
        return "Description: " + this.description + " | Owner: " + this.owner.getUsername() + " | Phase: " + this.getPhase();
    }

}
