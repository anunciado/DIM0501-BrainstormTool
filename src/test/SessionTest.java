package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import session.Session;
import session.SessionPhase;
import users.Idea;
import users.User;

/**
* Class SessionTest it's a class to test the methods of the Class Session.
*
* @author Luís Eduardo (cruxiu@ufrn.edu.br)
* @version 2018.06.15
*/

class SessionTest {

	// The owner user of the session
	User owner = new User("Luís");
	// The description of the session
	String description = "Melhor brainstorm";
	// The session of brainstorm
	Session session = new Session(owner, description);

	/**
	* Method to test the if a session is created, the owner must be the
	* same as in the constructor.
	*/
	@Test
	void testOwner() {
		System.out.println("Inside testOwner()");
		assertEquals(owner,session.getOwner());
		System.out.println("Test Completed Successfully");
	}

	/**
	* Method to test the if a session is created, it should be in the
	* welcome phase.
	*/
	@Test
	void testWelcomeSession() {
		System.out.println("Inside testWelcomeSession()");
		assertEquals(SessionPhase.WELCOME, session.getPhase());
		System.out.println("Test Completed Successfully");
	}

	/**
	* Method to test the if a session is created, it should has no
	* ideas in the session.
	*/
	@Test
	void testListIdeas() {
		System.out.println("Inside testListParticipants()");
		assertEquals(0, session.getIdeas().size());
		System.out.println("Test Completed Successfully");
	}

	/**
	* Method to test the if a session is created, it should has no
	* participants in the session.
	*/
	@Test
	void testListParticipants() {
		System.out.println("Inside testListParticipants()");
		assertEquals(0, session.getParticipants().size());
		System.out.println("Test Completed Successfully");
	}

	/**
	* Method to test the if we go to the next phase and we are in
	* the welcome phase, we will go to the brainstorm phase.
	*/
	@Test
	void testBrainstormPhase() {
		System.out.println("Inside testBrainstormPhase()");
		this.session.nextPhase();
		assertEquals(SessionPhase.BRAINSTORM, session.getPhase());
		System.out.println("Test Completed Successfully");
	}

	/**
	* Method to test the if we go to the next phase and we are in
	* the brainstorm phase, we will go to the voting phase.
	*/
	@Test
	void testVotingPhase() {
		System.out.println("Inside testVotingPhase()");
		this.session.nextPhase();
		this.session.nextPhase();
		assertEquals(SessionPhase.VOTING, session.getPhase());
		System.out.println("Test Completed Successfully");
	}

	/**
	* Method to test the if we go to the next phase and we are in
	* the voting phase, we will go to the rank phase.
	*/
	@Test
	void testRankPhase() {
		System.out.println("Inside testRankPhase()");
		this.session.nextPhase();
		this.session.nextPhase();
		this.session.nextPhase();
		assertEquals(SessionPhase.RANK, session.getPhase());
		System.out.println("Test Completed Successfully");
	}

	/**
	* Method to test the if we go to the next phase and we are in
	* the rank phase, we will go to the rank phase.
	*/
	@Test
	void testRankAgainPhase() {
		System.out.println("Inside testRankAgainPhase()");
		this.session.nextPhase();
		this.session.nextPhase();
		this.session.nextPhase();
		this.session.nextPhase();
		assertEquals(SessionPhase.RANK, session.getPhase());
		System.out.println("Test Completed Successfully");
	}

	/**
	* Method to test the if we try to add a new participant to the
	* session, the list of participants remains the same, if we
	* were not in the welcome phase.
	*/
	@Test
	void testParticipantNotWelcomePhase() {
		System.out.println("Inside testParticipantNotWelcomePhase()");
		User userTest1 = new User("Rayan");
		User userTest2 = new User("Anserson");
		User userTest3 = new User("Lucas");
		this.session.addParticipant(userTest1);
		this.session.addParticipant(userTest2);
		this.session.nextPhase();
		this.session.addParticipant(userTest3);
		assertEquals(2, session.getParticipants().size());
		this.session.nextPhase();
		this.session.addParticipant(userTest3);
		assertEquals(2, session.getParticipants().size());
		this.session.nextPhase();
		this.session.addParticipant(userTest3);
		assertEquals(2, session.getParticipants().size());
		System.out.println("Test Completed Successfully");
	}

	/**
	* Method to test the if we try to add a new participant to the
	* session, the list of participants remains the same, if we
	* already added the participant.
	*/
	@Test
	void testParticipantAlreadyAdded() {
		System.out.println("Inside testParticipantAlreadyAdded()");
		User userTest1 = new User("Rayan");
		User userTest2 = new User("Anserson");
		User userTest3 = new User("Lucas");
		this.session.addParticipant(userTest1);
		this.session.addParticipant(userTest2);
		this.session.addParticipant(userTest3);
		this.session.addParticipant(userTest1);
		assertEquals(3, session.getParticipants().size());
		System.out.println("Test Completed Successfully");
	}

	/**
	* Method to test the if we try to add a new participant to the
	* session, the list of participants will changes.
	*/
	@Test
	void testParticipantNotAdded() {
		System.out.println("Inside testParticipantAlreadyAdded()");
		User userTest1 = new User("Rayan");
		User userTest2 = new User("Anserson");
		User userTest3 = new User("Lucas");
		this.session.addParticipant(userTest1);
		assertEquals(1, session.getParticipants().size());
		this.session.addParticipant(userTest2);
		assertEquals(2, session.getParticipants().size());
		this.session.addParticipant(userTest3);
		assertEquals(3, session.getParticipants().size());
		System.out.println("Test Completed Successfully");
	}

	/**
	* Method to test the if we try to remove a participant that
	* are not added in the list of the session, the list of
	* participants remains the same.
	*/
	@Test
	void testRemoveParticipantNotAdded() {
		System.out.println("Inside testRemoveParticipantNotAdded()");
		User userTest1 = new User("Rayan");
		User userTest2 = new User("Anserson");
		User userTest3 = new User("Lucas");
		this.session.addParticipant(userTest1);
		assertEquals(1, session.getParticipants().size());
		this.session.addParticipant(userTest2);
		assertEquals(2, session.getParticipants().size());
		this.session.removeParticipant(userTest3.getHashCode());
		assertEquals(2, session.getParticipants().size());
		System.out.println("Test Completed Successfully");
	}

	/**
	* Method to test the if we try to remove a participant that
	* of the list of the session, the list of participants will
	* change.
	*/
	@Test
	void testRemoveParticipantAdded() {
		System.out.println("Inside testRemoveParticipantAdded()");
		User userTest1 = new User("Rayan");
		User userTest2 = new User("Anserson");
		User userTest3 = new User("Lucas");
		this.session.addParticipant(userTest1);
		assertEquals(1, session.getParticipants().size());
		this.session.addParticipant(userTest2);
		assertEquals(2, session.getParticipants().size());
		this.session.addParticipant(userTest3);
		assertEquals(3, session.getParticipants().keySet().size());
		this.session.removeParticipant(userTest1.getHashCode());
		assertEquals(2, session.getParticipants().keySet().size());
		this.session.removeParticipant(userTest2.getHashCode());
		assertEquals(1, session.getParticipants().keySet().size());
		this.session.removeParticipant(userTest3.getHashCode());
		assertEquals(0, session.getParticipants().size());
		System.out.println("Test Completed Successfully");
	}

	/**
	* Method to test the if we try to add a new idea, the list
	* of ideas will remain the same if we are not in the voting
	* phase.
	*/
	@Test
	void testIdeaNotBrainstormPhase() {
		System.out.println("Inside testIdeaNotBrainstormPhase()");
		Idea ideaTest = new Idea(this.owner, "Criar uma ferramenta de Brainstorm.", this.session);
		this.session.addIdea(ideaTest);
		assertEquals(0, session.getIdeas().size());
		this.session.nextPhase();
		this.session.nextPhase();
		this.session.addIdea(ideaTest);
		assertEquals(0, session.getIdeas().size());
		this.session.nextPhase();
		this.session.addIdea(ideaTest);
		assertEquals(0, session.getIdeas().size());
		System.out.println("Test Completed Successfully");
	}

	/**
	* Method to test the if we try to add a new idea, the list
	* of ideas will remain the same if the author of idea is not
	* contained in the list of participants of the session.
	*/
	@Test
	void testIdeaOfParticipantNotAdded() {
		System.out.println("Inside testIdeaOfParticipantNotAdded()");
		User userTest = new User("Rayan");
		Idea ideaTest = new Idea(userTest, "Criar uma ferramenta de Brainstorm.", this.session);
		this.session.nextPhase();
		this.session.addIdea(ideaTest);
		assertEquals(0, session.getIdeas().size());
		System.out.println("Test Completed Successfully");
	}

	/**
	* Method to test the if we try to add a new idea, the list
	* of ideas will change if the author of idea is contained
	* in the list of participants of the session.
	*/
	@Test
	void testIdeaOfParticipantAdded() {
		System.out.println("Inside testIdeaOfParticipantAdded()");
		User userTest = new User("Rayan");
		this.session.addParticipant(userTest);
		Idea ideaTest = new Idea(userTest, "Criar uma ferramenta de Brainstorm.", this.session);
		this.session.nextPhase();
		this.session.addIdea(ideaTest);
		assertEquals(1, session.getIdeas().size());
		System.out.println("Test Completed Successfully");
	}

	/**
	* Method to test the rankIdeas method.
	*/
	@Test
	void testRankIdeas() {
		System.out.println("Inside testRankIdeas()");
		User userTest1 = new User("Rayan");
		User userTest2 = new User("Anserson");
		User userTest3 = new User("Lucas");
		User userTest4 = new User("Bruna");
		this.session.addParticipant(userTest1);
		this.session.addParticipant(userTest2);
		this.session.addParticipant(userTest3);
		this.session.addParticipant(userTest4);
		this.session.nextPhase();
		Idea ideaTest1 = new Idea(userTest1, "Criar uma ferramenta de Brainstorm.", this.session);
		Idea ideaTest2 = new Idea(userTest2, "Criar uma ferramenta mobile.", this.session);
		Idea ideaTest3 = new Idea(userTest3, "Criar uma ferramenta concorrente.", this.session);
		this.session.addIdea(ideaTest2);
		this.session.addIdea(ideaTest1);
		this.session.addIdea(ideaTest3);
		this.session.nextPhase();
		ideaTest1.registerVote(userTest4);
		ideaTest1.registerVote(userTest2);
		ideaTest1.registerVote(userTest3);
		ideaTest2.registerVote(userTest4);
		ideaTest3.registerVote(userTest4);
		ideaTest3.registerVote(userTest2);
		this.session.nextPhase();
		List<Idea> rankIdeas = this.session.rankIdeas();
		System.out.println(rankIdeas);
		for(Idea idea : rankIdeas) {
			if(idea.countVotes() < 1) {
				fail("Something went wrong");
			}
		}
		System.out.println("Test Completed Successfully");
	}
}
