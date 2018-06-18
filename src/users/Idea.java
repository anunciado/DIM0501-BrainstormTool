package users;

import java.util.ArrayList;
import java.util.List;

import session.Session;
import session.SessionPhase;

/**
* Class Idea it's a class that represents an idea of a user that
* participate of a brainstorming session with some informations
* encapsulated. And also implements the interface Comparable for
* comparison of ideas.
*
* @author Luís Eduardo (cruxiu@ufrn.edu.br)
* @version 2018.06.15
*/

public class Idea implements Comparable<Object> {

    // Participant brainstorm session of this idea
    private Session session;
    // Description of the idea
    private String description;
   	// Hash code of the idea
    private int hashCode;
    // Author of the idea
    private User author;
    // List of users who voted for this idea
    private List<User> voters;
    
    
    /**
    * Creates a new Idea object given the parameters a User author,
    * a String description, Session session and initializes a empty list
    * of voters.
    *
    * @param author A User that represents the author of the idea.
    * @param description A String that represents the description of the idea.
    * @param session A Session that represents the participant brainstorm
    * session of this idea.
    *
    */
    public Idea(User author, String description, Session session) {
        this.author = author;
        this.description = description;
        this.session = session;
        this.voters = new ArrayList<User>();
    }

    /**
    * Public method to get the author of the idea.
    * @return A User that represents the author of the idea.
    */
    public User getAuthor() {
        return author;
    }

    /**
    * Public method to get the list of users who voted for
    * this idea.
    * @return A List<User> that represents the list of users
    * who voted for this idea.
    */
    public List<User> getVoters() {
        return voters;
    }
    
    /**
	* Public method to get the number of votes of the idea.
	* @return A int that represents the number of votes of the idea.
	*/
    public int getHashCode() {
		return hashCode;
	}
    
    /**
	* Public method to set the hash code of the idea.
	* @param hashCode A int that represents the hash code of the idea.
	*/
    public void setHashCode(int hashCode) {
		this.hashCode = hashCode;
	}   

    /**
    * Public method to register a vote of some user given as parameter.
    * @param user A User that represents a voter for this idea,
    */
    public void registerVote(User user) {
        if(!this.voters.contains(user) &&
        this.session.getPhase() == SessionPhase.VOTING &&
        this.session.getParticipants().containsValue(user) &&
        this.author != user &&
        user.getVotes() < this.session.getVotingLimit()) {
            this.voters.add(user);
            user.setVotes(user.getVotes() + 1);
        }
    }

    /**
    * Public method to register remove a vote of some user given
    * as parameter.
    * @param user A User that represents a non voter for this idea.
    */
    public void removeVote(User user) {
        if(this.session.getPhase() == SessionPhase.VOTING &&
        this.session.getParticipants().containsValue(user) &&
        this.author != user &&
        this.getVoters().contains(user)) {
            this.voters.remove(user);
            user.setVotes(user.getVotes() - 1);
        }
    }

    /**
    * Public method to get the number of voters for this idea.
    * @return A int that represents the number of voters for
    * this idea.
    */
    public int countVotes() {
        return voters.size();
    }

    /**
    * Public method compare two ideas.
    * @param otherIdea A Idea that represents other idea to compare with this.
    * @return A negative integer, zero, or a positive integer as this object
    * has less voters than, equal to, or greater than the other idea object.
    */
    @Override
    public int compareTo(Object otherIdea) {
        return ((Idea) otherIdea).countVotes()-this.countVotes();
    }

    /**
    * Public method to print the informations encapsulated of this idea.
    * @return A String that represents the informations encapsulated of this
    * idea.
    */
    @Override
    public String toString() {
        return "Description: " + this.description + " | Author: " + this.author.getUsername() + " | Number of votes: " + this.countVotes();
    }

}
