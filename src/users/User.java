package users;

/**
* Class User it's a class that represents a user which can participate in
* a brainstorming session with some informations encapsulated.
*
* @author Luís Eduardo (cruxiu@ufrn.edu.br)
* @version 2018.06.15
*/

public class User {

	// The username of the user
	private String username;
	// The hash code of the user
	private int hashCode;
	//The number of votes of the user
	private int votes;

	/**
	* Creates a new User object given the parameters String username
	* and initializes the number of votes with 0.
	*
	* @param username A String that represents the username of the user.
	*/
	public User(String username) {
		this.username = username;
		this.votes = 0;
	}

	/**
	* Public method to get the username of the user.
	* @return A String that represents the username of the user.
	*/
	public String getUsername() {
		return username;
	}

	/**
	* Public method to set the number of votes of the user.
	* @param votes A int that represents the number of votes of the user.
	*/
	public void setVotes(int votes) {
		this.votes = votes;
	}

	/**
	* Public method to get the number of votes of the user.
	* @return A int that represents the number of votes of the user.
	*/
	public int getVotes() {
		return votes;
	}

	/**
	* Public method to set the hash code of the user.
	* @param hashCode A int that represents the hash code of the user.
	*/
	public void setHashCode(int hashCode) {
		this.hashCode = hashCode;
	}

	/**
	* Public method to get the hash code of the user.
	* @return A int that represents the hash code of the user.
	*/
	public int getHashCode() {
		return hashCode;
	}

	/**
	* Public method to print the informations encapsulated of this user.
	* @return A String that represents the informations encapsulated of this
	* user.
	*/
	@Override
	public String toString() {
		return "Username: " + this.getUsername() + " | Number of votes: " + this.getVotes();
	}
}
