package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import session.Session;
import users.Idea;
import users.User;

/**
* Class IdeaTest it's a class to test the methods of the Class Idea.
*
* @author Luís Eduardo (cruxiu@ufrn.edu.br)
* @version 2018.06.15
*/

class IdeaTest {

	// The owner user of the session
	User owner = new User("Luís");
	// The description of the session
	String description = "Melhor brainstorm";
	// The session of brainstorm
	Session session = new Session(owner, description);

	/**
	* Method to test the if when an idea is created its author is the
	* same as the past in the constructor.
	*/
	@Test
	void testAuthorIdea() {
		System.out.println("Inside testAuthorIdea()");
		User userTest = new User("Rayan");
		Idea ideaTest = new Idea(userTest, "Criar uma ferramenta de Brainstorm.", this.session);
		assertEquals(userTest, ideaTest.getAuthor());
		System.out.println("Test Completed Successfully");
	}

	/**
	* Method to test the if when an idea is created, it has no voters.
	*/
	@Test
	void testListVoters() {
		System.out.println("Inside testListVoters()");
		User userTest = new User("Rayan");
		Idea ideaTest = new Idea(userTest, "Criar uma ferramenta de Brainstorm.", this.session);
		assertEquals(0, ideaTest.countVotes());
		System.out.println("Test Completed Successfully");
	}

	/**
	* Method to test the if someone tries to vote for a created idea,
	* it remains without voters, if the session is not in the voting
	* phase.
	*/
	@Test
	void testVoteNotVotingPhase() {
		System.out.println("Inside testVoteNotVotingPhase()");
		User userTest1 = new User("Rayan");
		User userTest2 = new User("Anserson");
		this.session.addParticipant(userTest1);
		this.session.addParticipant(userTest2);
		this.session.nextPhase();
		Idea ideaTest = new Idea(userTest1, "Criar uma ferramenta de Brainstorm.", this.session);
		this.session.addIdea(ideaTest);
		ideaTest.registerVote(userTest2);
		System.out.println(ideaTest);
		assertEquals(0, ideaTest.countVotes());
		this.session.nextPhase();
		this.session.nextPhase();
		ideaTest.registerVote(userTest2);
		assertEquals(0, ideaTest.countVotes());
		System.out.println("Test Completed Successfully");
	}

	/**
	* Method to test the if someone tries to vote for a created idea,
	* it remains without voters, if the voter is not included in the
	* session.
	*/
	@Test
	void testVoterNotInSession() {
		System.out.println("Inside testVoterNotInSession()");
		User userTest1 = new User("Rayan");
		User userTest2 = new User("Anserson");
		this.session.addParticipant(userTest1);
		this.session.nextPhase();
		Idea ideaTest = new Idea(userTest1, "Criar uma ferramenta de Brainstorm.", this.session);
		this.session.addIdea(ideaTest);
		this.session.nextPhase();
		ideaTest.registerVote(userTest2);
		assertEquals(0, ideaTest.countVotes());
		System.out.println("Test Completed Successfully");
	}

	/**
	* Method to test the if someone tries to vote for a created idea,
	* it remains without voters, if the voter is the author of the
	* idea.
	*/
	@Test
	void testVoterNotAuthor() {
		System.out.println("Inside testVoterNotAuthor()");
		User userTest = new User("Rayan");
		this.session.addParticipant(userTest);
		this.session.nextPhase();
		Idea ideaTest = new Idea(userTest, "Criar uma ferramenta de Brainstorm.", this.session);
		this.session.addIdea(ideaTest);
		this.session.nextPhase();
		ideaTest.registerVote(userTest);
		assertEquals(0, ideaTest.countVotes());
		System.out.println("Test Completed Successfully");
	}

	/**
	* Method to test the if someone tries to vote for a created idea,
	* it remains without voters, if the voter already passed the
	* voting limit of the session.
	*/
	@Test
	void testVoterLimit() {
		System.out.println("Inside testVoterLimit()");
		User userTest1 = new User("Rayan");
		User userTest2 = new User("Lucas");
		this.session.addParticipant(userTest1);
		this.session.addParticipant(userTest2);
		this.session.nextPhase();
		Idea ideaTest1 = new Idea(userTest1, "Criar uma ferramenta de Brainstorm.", this.session);
		Idea ideaTest2 = new Idea(userTest1, "Criar uma ferramenta mobile.", this.session);
		Idea ideaTest3 = new Idea(userTest1, "Criar uma ferramenta concorrente.", this.session);
		Idea ideaTest4 = new Idea(userTest1, "Criar uma ferramenta distribuida.", this.session);
		this.session.addIdea(ideaTest1);
		this.session.addIdea(ideaTest2);
		this.session.addIdea(ideaTest3);
		this.session.addIdea(ideaTest4);
		this.session.nextPhase();
		ideaTest1.registerVote(userTest2);
		ideaTest2.registerVote(userTest2);
		ideaTest3.registerVote(userTest2);
		ideaTest4.registerVote(userTest2);
		assertEquals(3, userTest2.getVotes());
		assertEquals(1, ideaTest1.countVotes());
		assertEquals(1, ideaTest2.countVotes());
		assertEquals(1, ideaTest3.countVotes());
		assertEquals(0, ideaTest4.countVotes());
		System.out.println("Test Completed Successfully");
	}

	/**
	* Method to test the if someone tries to vote for a created idea,
	* it remains without voters, if the voter not passed the voting
	* limit of the session.
	*/
	@Test
	void testVoterNotLimit() {
		System.out.println("Inside testVoterNotLimit()");
		User userTest1 = new User("Rayan");
		User userTest2 = new User("Lucas");
		this.session.addParticipant(userTest1);
		this.session.addParticipant(userTest2);
		this.session.nextPhase();
		Idea ideaTest = new Idea(userTest1, "Criar uma ferramenta de Brainstorm.", this.session);
		this.session.addIdea(ideaTest);
		this.session.nextPhase();
		ideaTest.registerVote(userTest2);
		assertTrue(ideaTest.getVoters().contains(userTest2));
		assertEquals(1, ideaTest.getVoters().size());
		System.out.println("Test Completed Successfully");
	}

	/**
	* Method to test the if someone tries to remove the vote of a
	* created idea, the votes remains the same, if is not in the
	* voting phase.
	*/
	@Test
	void testRemoveVoteNotVotingPhase() {
		System.out.println("Inside testRemoveVoteNotVotingPhase()");
		User userTest1 = new User("Rayan");
		User userTest2 = new User("Lucas");
		this.session.addParticipant(userTest1);
		this.session.addParticipant(userTest2);
		this.session.nextPhase();
		Idea ideaTest = new Idea(userTest1, "Criar uma ferramenta de Brainstorm.", this.session);
		this.session.addIdea(ideaTest);
		this.session.nextPhase();
		ideaTest.registerVote(userTest2);
		this.session.nextPhase();
		ideaTest.removeVote(userTest2);
		assertEquals(1, ideaTest.getVoters().size());
		System.out.println("Test Completed Successfully");
	}

	/**
	* Method to test the if someone tries to remove the vote of a
	* created idea, the votes remains the same, if the voter not
	* have voted in this idea.
	*/
	@Test
	void testRemoveVoteNotVoter() {
		System.out.println("Inside testRemoveVoteNotVoter()");
		User userTest1 = new User("Rayan");
		User userTest2 = new User("Lucas");
		User userTest3 = new User("Anderson");
		this.session.addParticipant(userTest1);
		this.session.addParticipant(userTest2);
		this.session.addParticipant(userTest3);
		this.session.nextPhase();
		Idea ideaTest = new Idea(userTest1, "Criar uma ferramenta de Brainstorm.", this.session);
		this.session.addIdea(ideaTest);
		this.session.nextPhase();
		ideaTest.registerVote(userTest2);
		ideaTest.removeVote(userTest3);
		assertEquals(1, ideaTest.getVoters().size());
		System.out.println("Test Completed Successfully");
	}

	/**
	* Method to test the if someone tries to remove the vote of a
	* created idea.
	*/
	@Test
	void testRemoveVote() {
		System.out.println("Inside testRemoveVote()");
		User userTest1 = new User("Rayan");
		User userTest2 = new User("Lucas");
		this.session.addParticipant(userTest1);
		this.session.addParticipant(userTest2);
		this.session.nextPhase();
		Idea ideaTest = new Idea(userTest1, "Criar uma ferramenta de Brainstorm.", this.session);
		this.session.addIdea(ideaTest);
		this.session.nextPhase();
		ideaTest.registerVote(userTest2);
		assertEquals(1, userTest2.getVotes());
		assertEquals(1, ideaTest.getVoters().size());
		assertTrue(ideaTest.getVoters().contains(userTest2));
		ideaTest.removeVote(userTest2);
		assertEquals(0, userTest2.getVotes());
		assertEquals(0, ideaTest.getVoters().size());
		assertFalse(ideaTest.getVoters().contains(userTest2));
		System.out.println("Test Completed Successfully");
	}
}
